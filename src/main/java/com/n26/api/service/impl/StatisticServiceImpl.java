package com.n26.api.service.impl;

import com.n26.api.model.Transaction;
import com.n26.api.model.TransactionsStatistics;
import com.n26.api.repository.TransactionsRepository;
import com.n26.api.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;

@Service
public class StatisticServiceImpl implements StatisticService {
    @Autowired
    TransactionsRepository transactionsRepository;

    @Override
    public TransactionsStatistics getTransactionStatistics() {

        List<Transaction> transactions = transactionsRepository.getTransactions();
        TransactionsStatistics transactionStatistics = new TransactionsStatistics();

        if (transactions == null || transactions.isEmpty())
            return transactionStatistics;

        BigDecimal sum = transactions
                .stream()
                .map(Transaction::getBigAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, BigDecimal.ROUND_HALF_UP);

        transactionStatistics.setSum(sum.toString());

        transactionStatistics.setAvg(sum.
                divide(new BigDecimal(transactions.size()), RoundingMode.HALF_UP).toString());

        transactionStatistics.setMax(transactions
                .stream()
                .map(Transaction::getBigAmount)
                .max(Comparator.naturalOrder())
                .get()
                .setScale(2, BigDecimal.ROUND_HALF_UP)
                .toString());

        transactionStatistics.setMin(transactions
                .stream()
                .map(Transaction::getBigAmount)
                .min(Comparator.naturalOrder())
                .get()
                .setScale(2, BigDecimal.ROUND_HALF_UP)
                .toString());

        transactionStatistics.setCount(transactions.size());

        return transactionStatistics;
    }
}
