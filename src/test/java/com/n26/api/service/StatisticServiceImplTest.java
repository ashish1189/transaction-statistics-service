package com.n26.api.service;

import com.n26.api.model.Transaction;
import com.n26.api.model.TransactionsStatistics;
import com.n26.api.repository.TransactionsRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.Instant;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StatisticServiceImplTest {

    @Autowired
    private StatisticService service;
    @Autowired
    private TransactionsService transactionsService;
    @Autowired
    private TransactionsRepository repository;

    @Before
    public void init() {
        transactionsService.deleteTransactions();
        Instant now = Instant.now();

        transactionsService.saveTransaction(new Transaction(BigDecimal.valueOf(30.00).setScale(2, BigDecimal.ROUND_HALF_UP), now));
        transactionsService.saveTransaction(new Transaction(BigDecimal.valueOf(20.00).setScale(2, BigDecimal.ROUND_HALF_UP), now));
        transactionsService.saveTransaction(new Transaction(BigDecimal.valueOf(10.00).setScale(2, BigDecimal.ROUND_HALF_UP), now));
    }

    @After
    public void tearDown() {
        transactionsService.deleteTransactions();
    }

    @Test
    public void testGetTransactionStatistics() {

        TransactionsStatistics statistics = service.getTransactionStatistics();

        Assert.assertNotNull(statistics);
        Assert.assertEquals("60.00", statistics.getSum());
        Assert.assertEquals("20.00", statistics.getAvg());
        Assert.assertEquals("30.00", statistics.getMax());
        Assert.assertEquals("10.00", statistics.getMin());
        Assert.assertEquals(3, statistics.getCount());
    }
}
