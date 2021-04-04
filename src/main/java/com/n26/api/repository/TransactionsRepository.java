package com.n26.api.repository;

import com.n26.api.model.Transaction;

import java.util.List;

public interface TransactionsRepository {
    void save(Transaction transaction);

    void removeAll();

    List<Transaction> getTransactions();
}
