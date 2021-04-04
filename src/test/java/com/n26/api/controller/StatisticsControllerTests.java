package com.n26.api.controller;

import com.n26.api.model.Transaction;
import com.n26.api.model.TransactionsStatistics;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.Instant;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StatisticsControllerTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private TransactionsStatistics transactionsStatistics;

    @Before
    public void init() {
        this.transactionsStatistics = new TransactionsStatistics("60.00", "20.00", "30.00", "10.00", 3);
    }

    @Test
    public void testStatistics_OK() {
        testRestTemplate.delete("/transactions");

        testRestTemplate.postForEntity("/transactions",
                new Transaction(BigDecimal.valueOf(10).setScale(2, BigDecimal.ROUND_HALF_UP).toString(), Instant.now()), Void.class);
        testRestTemplate.postForEntity("/transactions",
                new Transaction(BigDecimal.valueOf(20).setScale(2, BigDecimal.ROUND_HALF_UP).toString(), Instant.now()), Void.class);
        testRestTemplate.postForEntity("/transactions",
                new Transaction(BigDecimal.valueOf(30).setScale(2, BigDecimal.ROUND_HALF_UP).toString(), Instant.now()), Void.class);

        ResponseEntity<TransactionsStatistics> response = testRestTemplate.getForEntity("/statistics", TransactionsStatistics.class);
        Assert.assertEquals(" response code is OK ", HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(" response body equals to expected body ", transactionsStatistics, response.getBody());
    }
}
