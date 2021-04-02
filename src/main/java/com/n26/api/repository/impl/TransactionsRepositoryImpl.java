package com.n26.api.repository.impl;

import com.n26.api.model.Transaction;
import com.n26.api.repository.TransactionsRepository;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.lang.ref.SoftReference;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

@Repository("TransactionsRepository")
public class TransactionsRepositoryImpl implements TransactionsRepository {



    @Override
    public void save(Transaction transaction) {

    }

    @Getter
    private static class Cache implements Delayed {

        private final Long key;
        private SoftReference<List<Transaction>> reference;
        private final Long expiryTime;

        public Cache(Long key, SoftReference<List<Transaction>> reference, Long expiryTime) {
            this.key = key;
            this.reference = reference;
            this.expiryTime = expiryTime;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(expiryTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            return Long.compare(expiryTime, ((Cache) o).expiryTime);
        }
    }
}
