package com.example.libraryautomationapi.repository;

import com.example.libraryautomationapi.model.Book;
import com.example.libraryautomationapi.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    boolean existsByBookAndReturnedFalse(Book book);
}
