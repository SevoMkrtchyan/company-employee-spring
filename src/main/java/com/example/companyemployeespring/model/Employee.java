package com.example.companyemployeespring.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    private String email;
    private String username;
    private String password;
    private int phoneNumber;
    private double salary;
    @Enumerated(EnumType.STRING)
    private Position position;
    @ManyToOne
    private Company company;
    private boolean isEmailVerified;
    private UUID token;

}
