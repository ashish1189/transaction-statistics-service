package com.n26.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonPropertyOrder({ "amount", "timestamp" })
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Transaction {

    @JsonProperty("amount")
    private String amount;
    private BigDecimal bigAmount;
    @JsonProperty("timestamp")
    private Instant timestamp;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (!bigAmount.equals(that.bigAmount)) return false;
        return timestamp.equals(that.timestamp);
    }

    @Override
    public int hashCode() {
        int result = bigAmount.hashCode();
        result = 31 * result + timestamp.hashCode();
        return result;
    }
}
