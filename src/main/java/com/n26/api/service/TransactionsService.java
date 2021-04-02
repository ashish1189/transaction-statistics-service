package com.n26.api.service;

import com.n26.api.model.Transaction;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public interface TransactionsService {

    ResponseEntity<Void> saveTransaction(Transaction transaction);

    /**
     * Method check if transaction is older than 1 minute or 60 seconds
     * @param transaction
     * @param now
     * @return
     */
    default boolean isOlderTransaction(Transaction transaction, Instant now) {
        return transaction.getTimestamp().isAfter(now.minus(60, ChronoUnit.SECONDS));
    }

    /**
     * This method validates if transaction timestamp is in the future
     * @param transaction
     * @param now
     * @return
     */
    default boolean isFutureTransaction(Transaction transaction, Instant now) {
        return transaction.getTimestamp().isAfter(now);
    }

    /**
     * This method validates the amount in the request.
     *
     * @param transaction
     * @return
     */
    default boolean isValidAmount(Transaction transaction) {
        try {
            transaction.setBigAmount(new BigDecimal(transaction.getAmount()));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
