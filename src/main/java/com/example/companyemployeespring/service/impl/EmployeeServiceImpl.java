package com.example.companyemployeespring.service.impl;

import com.example.companyemployeespring.model.Employee;
import com.example.companyemployeespring.model.Position;
import com.example.companyemployeespring.repository.EmployeeRepository;
import com.example.companyemployeespring.service.GmailService;
import com.example.companyemployeespring.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final GmailService gmailService;

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public void save(Employee employee, Locale locale) {
        if (employee.getPosition() == null) {
            employee.setPosition(Position.NO_POSITION_YET);
        }
        Optional<Employee> employeeByUsername = employeeRepository.findEmployeeByUsername(employee.getUsername());
        if (!employeeByUsername.isPresent()) {
            UUID token = UUID.randomUUID();
            employee.setToken(token);
            employee.setPassword(passwordEncoder.encode(employee.getPassword()));
            employeeRepository.save(employee);
            sendVerifyMessage(employee, locale);
        }

    }

    public Optional<Employee> findById(int id) {
        if (!employeeRepository.findById(id).isPresent()) {
            return null;
        }
        return employeeRepository.findById(id);
    }

    public List<Employee> findAllByCompany_Id(int id) {
        return employeeRepository.findAllByCompany_Id(id);
    }

    public void delete(int id) {
        if (id > 0) {
            Employee byId = findById(id).get();
            employeeRepository.delete(byId);
        }
    }

    public Optional<Employee> findEmployeeByUsername(String username) {
        return employeeRepository.findEmployeeByUsername(username);
    }

    @Override
    public Employee findEmployeeByEmail(String email) {
        return employeeRepository.findEmployeeByEmail(email).orElseGet(null);
    }

    public void sendVerifyMessage(Employee employee, Locale locale) {
        String link = String.format("http://localhost:8080/verify?email=%s&token=%s",
                employee.getEmail(), employee.getToken());
        String subject = "Verify Your Account";
        try {
            gmailService.sendMail(employee, locale, link, subject, "verifyTemplate");
        } catch (MessagingException e) {
            log.error(e.getMessage());
        }
    }

    public List<Employee> findEmployeesByCompanyId(int id) {
        return employeeRepository.findAllByCompany_Id(id);
    }

    public void verifyEmail(String email, UUID token) {
        Employee byEmail = findEmployeeByEmail(email);
        if (byEmail != null) {
            if (byEmail.getToken().equals(token)) {
                byEmail.setToken(null);
                byEmail.setEmailVerified(true);
            }
        }
    }

}
