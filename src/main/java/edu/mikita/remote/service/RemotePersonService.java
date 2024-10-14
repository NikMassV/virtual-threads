package edu.mikita.remote.service;

import com.google.gson.Gson;
import edu.mikita.domain.PersonRequestDto;
import edu.mikita.domain.PersonResponseDto;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class RemotePersonService {

    private final String filePath = "src/main/resources/person.json";
    private final Gson gson = new Gson();

    public PersonResponseDto getPerson(PersonRequestDto requestDto) {
        try {
            Thread.sleep(1);
            PersonResponseDto personFromFile = gson.fromJson(new FileReader(filePath), PersonResponseDto.class);
            return new PersonResponseDto(
                    requestDto.uid(),
                    personFromFile.firstName(),
                    personFromFile.lastName(),
                    requestDto.uid().concat("@mail.com")
            );
        } catch (FileNotFoundException | InterruptedException e) {
            return new PersonResponseDto(
                    requestDto.uid(),
                    "John",
                    "Doe",
                    requestDto.uid().concat("@mail.com"));
        }
    }
}
