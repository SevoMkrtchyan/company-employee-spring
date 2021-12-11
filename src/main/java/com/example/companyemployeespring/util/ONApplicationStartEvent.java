package com.example.companyemployeespring.util;

import com.example.companyemployeespring.model.Company;
import com.example.companyemployeespring.model.Employee;
import com.example.companyemployeespring.model.Position;
import com.example.companyemployeespring.repository.EmployeeRepository;
import com.example.companyemployeespring.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Locale;

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
                    .username("admin")
                    .password(passwordEncoder.encode("admin087"))
                    .position(Position.ADMINISTRATOR)
                    .salary(4)
                    .phoneNumber(46546)
                    .company(companyService.findByName("no_company"))
                    .isEmailVerified(true)
                    .token(null)
                    .build());
        }
        if (companyService.findByName("no_company") == null) {
            companyService.save(Company.builder()
                    .name("no_company")
                    .size(5)
                    .address("no_company")
                    .build());
        }

    }
}
