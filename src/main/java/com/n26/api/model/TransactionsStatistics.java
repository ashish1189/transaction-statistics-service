package com.n26.api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TransactionsStatistics {

    private String sum = "0.00";
    private String avg = "0.00";
    private String max = "0.00";
    private String min = "0.00";
    private long count;
}
