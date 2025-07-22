package com.example.libraryautomationapi.controller;


import com.example.libraryautomationapi.dto.LoanRequest;
import com.example.libraryautomationapi.model.Book;
import com.example.libraryautomationapi.model.Loan;
import com.example.libraryautomationapi.model.Member;
import com.example.libraryautomationapi.repository.BookRepository;
import com.example.libraryautomationapi.repository.LoanRepository;
import com.example.libraryautomationapi.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MemberRepository memberRepository;

    // Tüm ödünç alma kayıtları
    @GetMapping
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    @PostMapping("/borrow")
    public ResponseEntity<Loan> borrowBook(@RequestBody LoanRequest request) {
        // Kitap ve üye kontrolü
        Optional<Book> bookOpt = bookRepository.findById((long) request.getBookId());
        Optional<Member> memberOpt = memberRepository.findById((long) request.getMemberId());

        if (bookOpt.isEmpty() || memberOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Book book = bookOpt.get();
        Member member = memberOpt.get();
        Loan loan = new Loan();
        loan.setBook(book);
        loan.setMember(member);
        loan.setBorrowDate(request.getBorrowDate() != null ? request.getBorrowDate() : LocalDateTime.now());
        loan.setReturnDate(request.getReturnDate());

        Loan savedLoan = loanRepository.save(loan);

        return ResponseEntity.ok(savedLoan);
    }

    // Kitabı iade etme
    @PostMapping("/return/{loanId}")
    public ResponseEntity<Loan> returnBook(@PathVariable Long loanId) {
        Optional<Loan> loanOpt = loanRepository.findById(loanId);

        if (loanOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Loan loan = loanOpt.get();
        if (loan.isReturned()) {
            return ResponseEntity.badRequest().body(null); // zaten iade edilmiş
        }

        loan.setReturned(true);
        loan.setReturnDate(LocalDateTime.now());

        return ResponseEntity.ok(loanRepository.save(loan));
    }
}