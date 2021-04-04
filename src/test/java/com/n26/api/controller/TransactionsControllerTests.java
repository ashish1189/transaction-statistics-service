package com.n26.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.n26.api.model.Transaction;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionsControllerTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void testCreatedTransaction_CREATED() {
        Transaction transaction = new Transaction(BigDecimal.valueOf(100.5541)
                .setScale(2, BigDecimal.ROUND_HALF_UP).toString(),
                Instant.now());

        ResponseEntity<Void> response = testRestTemplate.postForEntity("/transactions", transaction, Void.class);
        Assert.assertEquals("Response Code CREATED ", HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testOldTransaction_NO_CONTENT() {
        Transaction transaction = new Transaction(BigDecimal.valueOf(100.5541)
                .setScale(2, BigDecimal.ROUND_HALF_UP).toString(),
                Instant.now().minus(90, ChronoUnit.SECONDS));

        ResponseEntity<Void> response = testRestTemplate.postForEntity("/transactions", transaction, Void.class);
        Assert.assertEquals("Response Code NO_CONTENT ", HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testFutureTransaction_UNPROCESSABLE_ENTITY() {
        Transaction transaction = new Transaction(BigDecimal.valueOf(100.5541)
                .setScale(2, BigDecimal.ROUND_HALF_UP).toString(),
                Instant.now().plus(90, ChronoUnit.SECONDS));

        ResponseEntity<Void> response = testRestTemplate.postForEntity("/transactions", transaction, Void.class);
        Assert.assertEquals("Response Code UNPROCESSABLE_ENTITY ", HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
    }

    @Test
    public void testInvalidTransactionDate_UNPROCESSABLE_ENTITY() {
        String request = "{\"timestamp\": \"4/23/2018 11:32 PM\",\"amount\": \"262.01\"}";

        ResponseEntity<Void> response = null;
        try {
            response = testRestTemplate.postForEntity("/transactions", (new ObjectMapper()).readTree(request), Void.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals("Response Code UNPROCESSABLE_ENTITY ", HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
    }

    @Test
    public void testInvalidRequest_BAD_REQUEST() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        ResponseEntity<Void> response = testRestTemplate.exchange("/transactions", HttpMethod.POST,
                new HttpEntity<>("Test", headers), Void.class);

        Assert.assertEquals("Response Code BAD_REQUEST ", HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testDeleteTransactions_NO_CONTENT() {
        Transaction transaction = new Transaction(BigDecimal.valueOf(100.5541)
                .setScale(2, BigDecimal.ROUND_HALF_UP).toString(),
                Instant.now().minus(90, ChronoUnit.SECONDS));

        ResponseEntity<Void> response = testRestTemplate.exchange("/transactions", HttpMethod.DELETE, null, Void.class);
        Assert.assertEquals("Response Code NO_CONTENT ", HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
