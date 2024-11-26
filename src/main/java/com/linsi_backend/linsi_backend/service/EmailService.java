package com.linsi_backend.linsi_backend.service;

public interface EmailService {
    void registration(String subject, String firstName, String lastName, String id);
}
