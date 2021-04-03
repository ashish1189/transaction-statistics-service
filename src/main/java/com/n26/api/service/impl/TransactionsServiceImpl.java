package com.n26.api.service.impl;

import com.n26.api.model.Transaction;
import com.n26.api.repository.TransactionsRepository;
import com.n26.api.service.TransactionsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
public class TransactionsServiceImpl implements TransactionsService {

    @Autowired
    TransactionsRepository transactionsRepository;

    @Override
    public ResponseEntity<Void> saveTransaction(Instant now, Transaction transaction) {
        transactionsRepository.save(now, transaction);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteTransactions() {
        transactionsRepository.removeAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
