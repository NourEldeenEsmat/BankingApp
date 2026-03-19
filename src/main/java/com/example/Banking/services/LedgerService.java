package com.example.Banking.services;

import com.example.Banking.DTOs.LedgerDto;
import com.example.Banking.Mapper.LedgerMapper;
import com.example.Banking.Reposetries.LedgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LedgerService {

    @Autowired
    private LedgerRepository ledgerRepo;

    public Page<LedgerDto> getLedgerByAccount(Long accountNumber, Pageable pageable) {
        return ledgerRepo.findByAccountAccountNumberOrderByCreatedAtDesc(accountNumber,pageable).map(LedgerMapper::toDto);
    }
}