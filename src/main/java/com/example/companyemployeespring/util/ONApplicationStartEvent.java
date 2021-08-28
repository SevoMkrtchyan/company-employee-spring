package com.example.companyemployeespring.util;

import com.example.companyemployeespring.model.Employee;
import com.example.companyemployeespring.model.Position;
import com.example.companyemployeespring.repository.EmployeeRepository;
import com.example.companyemployeespring.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ONApplicationStartEvent implements ApplicationListener<ApplicationReadyEvent> {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final CompanyService companyService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        if (!employeeRepository.findEmployeeByUsername("admin087").isPresent()) {
            employeeRepository.save(Employee.builder()
                    .name("admin")
                    .surname("adminyan")
                    .email("admin@mail.ru")
                    .username("admin087")
                    .password(passwordEncoder.encode("admin"))
                    .position(Position.ADMINISTRATOR)
                    .salary(4)
                    .phoneNumber(46546)
                    .company(companyService.findByName("no_company"))
                    .build());
        }

    }
}
