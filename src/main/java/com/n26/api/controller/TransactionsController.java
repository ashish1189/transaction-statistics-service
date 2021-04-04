package com.n26.api.controller;

import com.n26.api.model.Transaction;
import com.n26.api.service.TransactionsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@Slf4j
@RestController
public class TransactionsController {

    @Autowired
    TransactionsService transactionsService;

    @PostMapping(value = "/transactions", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createTransactions (@RequestBody Transaction transaction) {
        Instant now = Instant.now();
        if (!transactionsService.isOlderTransaction(transaction, now))
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else if (transactionsService.isFutureTransaction(transaction, now))
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        else if (!transactionsService.isValidAmount(transaction))
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        else
            return transactionsService.saveTransaction(transaction);
    }

    @DeleteMapping(value = "/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteTransactions() {
        return transactionsService.deleteTransactions();
    }
}
