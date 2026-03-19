package com.example.Banking.Controller;

import com.example.Banking.DTOs.TransactionDTO;
import com.example.Banking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "http://localhost:4200")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public Map<String, Object> getTransactions(
            Pageable pageable,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String type) {

        // استدعاء الخدمة مع البحث والفلتر
        Page<TransactionDTO> page = transactionService.getTransactions(pageable, search, type, "");

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

    @PostMapping("/transfer")
    public ResponseEntity transfer(@RequestParam Long fromAccountId,
                                   @RequestParam Long toAccountId,
                                   @RequestParam Double amount) {
        try {
            return new ResponseEntity<>(transactionService.transfer(fromAccountId, toAccountId, amount), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(
            @RequestParam Long accountId,
            @RequestParam Double amount) {
        try {
            return new ResponseEntity<>(transactionService.deposit(accountId, amount), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    // سحب
    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(
            @RequestParam Long accountId,
            @RequestParam Double amount) {
        try{
            TransactionDTO transactionDTO = transactionService.withdraw(accountId, amount);
            return ResponseEntity.ok(transactionDTO);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}