package com.example.companyemployeespring.service.impl;

import com.example.companyemployeespring.model.Employee;
import com.example.companyemployeespring.model.Position;
import com.example.companyemployeespring.repository.EmployeeRepository;
import com.example.companyemployeespring.service.EmployeeService;
import com.example.companyemployeespring.service.GmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
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
    //    private final RabbitTemplate template;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.mail.template.path}")
    private String templatePath;

    public Page<Employee> findAll(PageRequest pageRequest) {
        Page<Employee> employees = employeeRepository.findAll(pageRequest);
//        template.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY,  "employees");
        kafkaTemplate.send("topicCustom","requested to find all employees");
        return employees;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public boolean save(Employee employee, Locale locale) {
        if (employee.getPosition() == null) {
            employee.setPosition(Position.NO_POSITION_YET);
        }
        Optional<Employee> employeeByUsername = employeeRepository.findEmployeeByUsername(employee.getUsername());
        if (!employeeByUsername.isPresent() || !employeeByUsername.get().getEmail().equals(employee.getEmail())) {
            UUID token = UUID.randomUUID();
            employee.setToken(token);
            employee.setPassword(passwordEncoder.encode(employee.getPassword()));
            employeeRepository.save(employee);
//            sendVerifyMessage(employee, locale);
            return true;
        }
        return false;
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
            gmailService.sendMail(employee, locale, link, subject, templatePath);
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
