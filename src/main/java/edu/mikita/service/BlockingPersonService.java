package edu.mikita.service;

import edu.mikita.domain.PersonRequestDto;
import edu.mikita.domain.PersonResponseDto;
import edu.mikita.remote.service.RemotePersonService;

import java.util.UUID;

public class BlockingPersonService {

    private final RemotePersonService remotePersonService = new RemotePersonService();
    private final EmailService emailService = new EmailService();
    private final ActionLogService actionLogService = new ActionLogService();

    public boolean sendEmailToUserByUid(UUID uuid) {
        PersonResponseDto personResponseDto = remotePersonService.getPerson(buildPersonRequest(uuid));
        String email = emailService.sendEmail(personResponseDto.email());
        boolean actionSaved = actionLogService.saveEmailSentAction(email);
        return actionSaved;
    }

    private PersonRequestDto buildPersonRequest(UUID uid) {
        return new PersonRequestDto(uid.toString());
    }
}
