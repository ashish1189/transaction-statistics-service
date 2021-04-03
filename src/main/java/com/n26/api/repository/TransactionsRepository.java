package com.n26.api.repository;

import com.n26.api.model.Transaction;

import java.time.Instant;

public interface TransactionsRepository {
    void save(Instant now, Transaction transaction);

    void removeAll();
}
