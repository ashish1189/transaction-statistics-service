package com.n26.api.service;

import com.n26.api.model.Transaction;
import com.n26.api.repository.TransactionsRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionsServiceImplTest {

    @Autowired
    private TransactionsService service;

    @Autowired
    private TransactionsRepository repository;

    @Test
    public void testSave() {
        service.deleteTransactions();
        Instant now = Instant.now();

        service.saveTransaction(new Transaction(BigDecimal.valueOf(30.00).setScale(2, BigDecimal.ROUND_HALF_UP), now));
        service.saveTransaction(new Transaction(BigDecimal.valueOf(20.00).setScale(2, BigDecimal.ROUND_HALF_UP), now));
        service.saveTransaction(new Transaction(BigDecimal.valueOf(10.00).setScale(2, BigDecimal.ROUND_HALF_UP), now));

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
        service.deleteTransactions();
        Instant now = Instant.now();

        service.saveTransaction(new Transaction(BigDecimal.valueOf(30.00).setScale(2, BigDecimal.ROUND_HALF_UP), now));
        service.saveTransaction(new Transaction(BigDecimal.valueOf(20.00).setScale(2, BigDecimal.ROUND_HALF_UP), now));
        service.saveTransaction(new Transaction(BigDecimal.valueOf(10.00).setScale(2, BigDecimal.ROUND_HALF_UP), now));

        List<Transaction> transactions = repository.getTransactions();
        Assert.assertNotNull(transactions);
        Assert.assertEquals(3, transactions.size());

        service.deleteTransactions();

        transactions = repository.getTransactions();

        Assert.assertEquals(0, transactions.size());
    }
}
