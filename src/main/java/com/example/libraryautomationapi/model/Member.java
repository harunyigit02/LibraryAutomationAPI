package com.example.libraryautomationapi.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Members")
@Data
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
