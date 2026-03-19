package com.example.Banking.Reposetries;

import com.example.Banking.Entities.Ledger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LedgerRepository extends JpaRepository<Ledger, Long> {
    Page<Ledger> findByAccountAccountNumberOrderByCreatedAtDesc(Long accountId, Pageable pageable);
}