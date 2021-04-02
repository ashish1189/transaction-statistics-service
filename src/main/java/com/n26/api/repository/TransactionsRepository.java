package com.n26.api.repository;

import com.n26.api.model.Transaction;

public interface TransactionsRepository {
    void save(Transaction transaction);
}
