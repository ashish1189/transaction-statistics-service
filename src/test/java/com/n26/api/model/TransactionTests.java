package com.n26.api.model;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Instant;

public class TransactionTests {

    @Test
    public void testObject() {
        Instant now = Instant.now();
        Transaction transaction = new Transaction("10.00", BigDecimal.valueOf(10.00)
                .setScale(2, BigDecimal.ROUND_HALF_UP), now);

        Assert.assertNotNull(transaction);
        Assert.assertEquals(transaction.getAmount(), "10.00");
        Assert.assertEquals(transaction.getBigAmount().toString(), "10.00");
        Assert.assertEquals(transaction.getTimestamp(), now);
    }

    @Test
    public void testSettersGetters() {
        Transaction transaction = new Transaction();
        Instant now = Instant.now();

        transaction.setAmount("12.25");
        transaction.setTimestamp(now);
        transaction.setBigAmount(BigDecimal.valueOf(10.00)
                .setScale(2, BigDecimal.ROUND_HALF_UP));

        Assert.assertNotNull(transaction);
        Assert.assertEquals(transaction.getAmount(), "12.25");
        Assert.assertEquals(transaction.getBigAmount().toString(), "10.00");
        Assert.assertEquals(transaction.getTimestamp(), now);
    }
}
