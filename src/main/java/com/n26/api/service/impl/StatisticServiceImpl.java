package com.n26.api.service.impl;

import com.n26.api.model.Transaction;
import com.n26.api.model.TransactionsStatistics;
import com.n26.api.repository.TransactionsRepository;
import com.n26.api.service.StatisticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class StatisticServiceImpl implements StatisticService {
    @Autowired
    TransactionsRepository transactionsRepository;

    @Override
    public TransactionsStatistics getTransactionStatistics() {

        log.info("Retrieving all in memory transactios");
        List<Transaction> transactions = transactionsRepository.getTransactions();
        TransactionsStatistics transactionsStatistics = new TransactionsStatistics();

        if (transactions == null || transactions.isEmpty())
            return transactionsStatistics;

        log.info("Creating aggregated statistics for the transactions");
        BigDecimal sum = transactions
                .stream()
                .map(Transaction::getBigAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, BigDecimal.ROUND_HALF_UP);

        transactionsStatistics.setSum(sum.toString());

        transactionsStatistics.setAvg(sum.
                divide(new BigDecimal(transactions.size()), RoundingMode.HALF_UP).toString());

        transactionsStatistics.setMax(transactions
                .stream()
                .map(Transaction::getBigAmount)
                .max(Comparator.naturalOrder())
                .get()
                .setScale(2, BigDecimal.ROUND_HALF_UP)
                .toString());

        transactionsStatistics.setMin(transactions
                .stream()
                .map(Transaction::getBigAmount)
                .min(Comparator.naturalOrder())
                .get()
                .setScale(2, BigDecimal.ROUND_HALF_UP)
                .toString());

        transactionsStatistics.setCount(transactions.size());

        return transactionsStatistics;
    }
}
