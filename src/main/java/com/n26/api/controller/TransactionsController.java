package com.n26.api.controller;

import com.n26.api.model.Transaction;
import com.n26.api.service.TransactionsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@Slf4j
@RestController
@RequestMapping(value = "/api")
@Api(value = "Transactions API to store & delete transactions in memory.")
public class TransactionsController {

    @Autowired
    TransactionsService transactionsService;

    @PostMapping(value = "/transactions", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Creates transactions for last 60 seconds.",
            notes = "Creates a transaction by storing in memory if UTC time within last 60 seconds.",
            response = Transaction.class)
    public ResponseEntity<Void> createTransactions (@RequestBody Transaction transaction) {
        log.info("Inside TransactionsController.createTransactions()");
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
    @ApiOperation(value = "Deletes transactions for last 60 seconds.",
            notes = "Deletes all transactions from memory.")
    public ResponseEntity<Void> deleteTransactions() {
        log.info("Inside TransactionsController.deleteTransactions()");
        return transactionsService.deleteTransactions();
    }
}
