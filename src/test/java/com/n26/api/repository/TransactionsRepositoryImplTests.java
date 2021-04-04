package com.n26.api.repository;

import com.n26.api.model.Transaction;
import com.n26.api.repository.impl.TransactionsRepositoryImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionsRepositoryImplTests {

    private TransactionsRepository repository;

    @Before
    public void init() {
        repository = new TransactionsRepositoryImpl();
    }

    @Test
    public void testSave() {
        repository.removeAll();

        Instant now = Instant.now();

        repository.save(new Transaction(BigDecimal.valueOf(30.00).setScale(2, BigDecimal.ROUND_HALF_UP), now));
        repository.save(new Transaction(BigDecimal.valueOf(20.00).setScale(2, BigDecimal.ROUND_HALF_UP), now));
        repository.save(new Transaction(BigDecimal.valueOf(10.00).setScale(2, BigDecimal.ROUND_HALF_UP), now));

        List<Transaction> transactions = repository.getTransactions();

        List<BigDecimal> bigDecimals = transactions
                                        .stream()
                                        .map(decimal -> decimal.getBigAmount())
                                        .collect(Collectors.toList());

        Assert.assertNotNull(transactions);
        Assert.assertTrue(bigDecimals.contains(BigDecimal.valueOf(30.00).setScale(2, BigDecimal.ROUND_HALF_UP)));
        Assert.assertTrue(bigDecimals.contains(BigDecimal.valueOf(20.00).setScale(2, BigDecimal.ROUND_HALF_UP)));
        Assert.assertTrue(bigDecimals.contains(BigDecimal.valueOf(10.00).setScale(2, BigDecimal.ROUND_HALF_UP)));
    }

    @Test
    public void testRemoveAll() {
        Instant now = Instant.now();

        repository.save(new Transaction(BigDecimal.valueOf(30.00).setScale(2, BigDecimal.ROUND_HALF_UP), now));
        repository.save(new Transaction(BigDecimal.valueOf(20.00).setScale(2, BigDecimal.ROUND_HALF_UP), now));
        repository.save(new Transaction(BigDecimal.valueOf(10.00).setScale(2, BigDecimal.ROUND_HALF_UP), now));

        List<Transaction> transactions = repository.getTransactions();
        Assert.assertNotNull(transactions);
        Assert.assertEquals(3, transactions.size());

        repository.removeAll();

        transactions = repository.getTransactions();

        Assert.assertEquals(0, transactions.size());
    }
}
