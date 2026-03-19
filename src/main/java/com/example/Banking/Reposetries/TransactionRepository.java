package com.example.Banking.Reposetries;

import com.example.Banking.Entities.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Page<Transaction> findByFromAccountAccountNumberContainingIgnoreCaseOrToAccountAccountNumberContainingIgnoreCase(
            String fromAcc, String toAcc, Pageable pageable);

    Page<Transaction> findByType(String type, Pageable pageable);

    Page<Transaction> findByTypeAndFromAccountAccountNumberContainingIgnoreCaseOrTypeAndToAccountAccountNumberContainingIgnoreCase(
            String type1, String search1, String type2, String search2, Pageable pageable);
}