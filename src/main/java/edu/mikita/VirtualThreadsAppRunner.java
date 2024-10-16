package edu.mikita;

import edu.mikita.service.BlockingPersonService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class VirtualThreadsAppRunner {

    public static void main(String[] args) {
        BlockingPersonService blockingPersonService = new BlockingPersonService();
        int numberOfRequests = 1000000;
        List<UUID> uuids = generateRandomUUIDs(numberOfRequests);
        AtomicInteger sentEmailCounter = new AtomicInteger(0);
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<Boolean>> futures = new ArrayList<>();
            long start = System.currentTimeMillis();
            for (UUID uuid : uuids) {
                futures.add(executorService.submit(() -> blockingPersonService.sendEmailToUserByUid(uuid)));
            }
            for (Future<Boolean> future : futures) {
                try {
                    future.get(); // блокирующий вызов, ожидание результата
                    sentEmailCounter.incrementAndGet();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
            long end = System.currentTimeMillis();
            System.out.println(sentEmailCounter.get() + " sent in " + (end - start) + " ms");
            executorService.shutdown();
        }
    }

    private static List<UUID> generateRandomUUIDs(int numberOfUUIDs) {
        List<UUID> uuids = new ArrayList<>();
        for (int i = 0; i < numberOfUUIDs; i++) {
            uuids.add(UUID.randomUUID());
        }
        return uuids;
    }
}
