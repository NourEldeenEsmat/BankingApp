package com.example.Banking.Controller;

import com.example.Banking.DTOs.LedgerDto;
import com.example.Banking.services.LedgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/ledger")
public class LedgerController {

    @Autowired
    private LedgerService ledgerService;

    @GetMapping("/{accountNumber}")
    public Map<String, Object> getLedgerByAccount( @PathVariable Long accountNumber,Pageable pageable) {
        Page<LedgerDto> page = ledgerService.getLedgerByAccount(accountNumber, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("content", page.getContent());
        response.put("pageNumber", page.getNumber());
        response.put("pageSize", page.getSize());
        response.put("totalElements", page.getTotalElements());
        response.put("totalPages", page.getTotalPages());
        response.put("isFirst", page.isFirst());
        response.put("isLast", page.isLast());

        return response;
    }
}