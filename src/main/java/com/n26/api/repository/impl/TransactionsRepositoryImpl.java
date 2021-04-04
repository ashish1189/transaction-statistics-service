package com.n26.api.repository.impl;

import com.n26.api.model.Transaction;
import com.n26.api.repository.TransactionsRepository;
import com.n26.api.utils.Cache;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository("TransactionsRepository")
public class TransactionsRepositoryImpl implements TransactionsRepository {

    private Cache<Long, Transaction> inMemoryCache = new Cache<>();

    @Override
    public void save(Transaction transaction) {
        inMemoryCache.put((long)transaction.hashCode(), transaction);
    }

    @Override
    public void removeAll() {
        inMemoryCache.clear();
    }

    @Override
    public List<Transaction> getTransactions() {
        return inMemoryCache
                .values()
                .parallelStream()
                .collect(Collectors.toList());
    }
}
