package com.example.companyemployeespring.service;

import com.example.companyemployeespring.model.Employee;

import javax.mail.MessagingException;
import java.util.Locale;

public interface GmailService {

    void send(String to, String subject, String message);

    void sendMail(Employee employee, Locale locale, String link, String subject, String template) throws MessagingException;


}
