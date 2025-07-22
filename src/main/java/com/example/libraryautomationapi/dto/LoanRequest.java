package com.example.libraryautomationapi.dto;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class LoanRequest {
    private int bookId;
    private int memberId;
    private LocalDateTime borrowDate;  // İstersen, istemci belirtebilir
    private LocalDateTime returnDate;
}