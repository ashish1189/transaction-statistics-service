package com.n26.api.service;

import com.n26.api.model.Transaction;
import com.n26.api.service.impl.TransactionsServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.*;

public class TransactionsServiceTest {

    private TransactionsService service;

    private Transaction transaction1;
    private Transaction transaction2;
    private Transaction transaction3;

    @Before
    public void init() {
        service = new TransactionsServiceImpl();

        transaction1 = new Transaction("30.00",
                                        Instant.now().minus(120, ChronoUnit.SECONDS));
        transaction2 = new Transaction( BigDecimal.valueOf(30.00).setScale(2, BigDecimal.ROUND_HALF_UP),
                                        Instant.now().plus(90, ChronoUnit.SECONDS));
        transaction3 = new Transaction("30a", Instant.now());
    }

    @Test
    public void isOlderTransaction_true() {
        Assert.assertTrue(service.isOlderTransaction(transaction2, Instant.now()));
    }

    @Test
    public void isOlderTransaction_false() {
        Assert.assertFalse(service.isOlderTransaction(transaction1, Instant.now()));
    }

    @Test
    public void isFutureTransaction_true() {
        Assert.assertTrue(service.isFutureTransaction(transaction2, Instant.now()));
    }

    @Test
    public void isFutureTransaction_false() {
        Assert.assertFalse(service.isFutureTransaction(transaction1, Instant.now()));
    }

    @Test
    public void isValidAmount_true() {
        Assert.assertTrue(service.isValidAmount(transaction1));
    }

    @Test
    public void isValidAmount_false() {
        Assert.assertFalse(service.isValidAmount(transaction3));
    }
}