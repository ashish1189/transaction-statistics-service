package com.n26.api.controller;

import com.n26.api.model.TransactionsStatistics;
import com.n26.api.service.StatisticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class StatisticsController {

    @Autowired
    StatisticService statisticService;

    @GetMapping(value = "/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionsStatistics> getTransactionsStatistics() {
        log.info("Inside getTransactionsStatistics");
        TransactionsStatistics transactionsStatistics = statisticService.getTransactionStatistics();

        return new ResponseEntity<>(transactionsStatistics, HttpStatus.OK);
    }
}
