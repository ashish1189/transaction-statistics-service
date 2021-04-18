package com.n26.api.controller;

import com.n26.api.model.TransactionsStatistics;
import com.n26.api.service.StatisticService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api")
@Api(value = "Statistics API to retrieve aggregated statistics for the transactions in last 60 seconds.")
public class StatisticsController {

    @Autowired
    StatisticService statisticService;

    @GetMapping(value = "/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Genrates statistics for transactions in last 60 seconds.",
                notes = "Provides aggregated metadata about the transactions occured in last 60 seconds.",
                response = TransactionsStatistics.class)
    public ResponseEntity<TransactionsStatistics> getTransactionsStatistics() {
        log.info("Inside getTransactionsStatistics");
        TransactionsStatistics transactionsStatistics = statisticService.getTransactionStatistics();

        return new ResponseEntity<>(transactionsStatistics, HttpStatus.OK);
    }
}
