package edu.mikita.service;

public class EmailService {

    public String sendEmail(String email) {
        System.out.println("Email sent to " + email);
        return email;
    }
}
