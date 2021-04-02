package com.n26.api.service.impl;

import com.n26.api.model.Transaction;
import com.n26.api.service.TransactionsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransactionsServiceImpl implements TransactionsService {

    @Override
    public ResponseEntity<Void> saveTransaction(Transaction transaction) {
        return null;
    }
}
