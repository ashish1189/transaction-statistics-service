package com.n26.api.model;

import org.junit.Assert;
import org.junit.Test;

public class TransactionsStatisticsTests {

    @Test
    public void testObject() {
        TransactionsStatistics statistics = new TransactionsStatistics("60.00", "20.00", "30.00", "10.00", 3);

        Assert.assertNotNull(statistics);
        Assert.assertEquals(statistics.getSum(), "60.00");
        Assert.assertEquals(statistics.getAvg(), "20.00");
        Assert.assertEquals(statistics.getMax(), "30.00");
        Assert.assertEquals(statistics.getMin(), "10.00");
        Assert.assertEquals(statistics.getCount(), 3);
    }

    @Test
    public void testSettersGetters() {
        TransactionsStatistics statistics = new TransactionsStatistics();

        statistics.setSum("60.00");
        statistics.setAvg("20.00");
        statistics.setMax("30.00");
        statistics.setMin("10.00");
        statistics.setCount(3);

        Assert.assertNotNull(statistics);
        Assert.assertEquals(statistics.getSum(), "60.00");
        Assert.assertEquals(statistics.getAvg(), "20.00");
        Assert.assertEquals(statistics.getMax(), "30.00");
        Assert.assertEquals(statistics.getMin(), "10.00");
        Assert.assertEquals(statistics.getCount(), 3);
    }
}
