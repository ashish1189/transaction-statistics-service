package com.n26.api.repository.impl;

import com.n26.api.model.Transaction;
import com.n26.api.repository.TransactionsRepository;
import com.n26.api.utils.Cache;
import org.springframework.stereotype.Repository;

import java.lang.ref.SoftReference;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository("TransactionsRepository")
public class TransactionsRepositoryImpl implements TransactionsRepository {

    private Cache<Long, Transaction> inMemoryCache = new Cache<>();

    @Override
    public void save(Instant now, Transaction transaction) {
        System.out.println("Cache before: "+inMemoryCache.size());
        inMemoryCache.put(now.toEpochMilli(), transaction);
        System.out.println("Cache after: "+inMemoryCache.size());
    }

    @Override
    public void removeAll() {
        System.out.println("Before delete: "+inMemoryCache.size());
        inMemoryCache.clear();
        System.out.println("After delete: "+inMemoryCache.size());
    }
}
