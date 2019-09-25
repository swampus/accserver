package com.bank.factory;

import com.bank.model.jsondb.ExecutedOrder;
import com.bank.model.jsondb.RequestPaymentOrder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class JsonDbModelObjectsFactoryTest {

    private JsonDbModelObjectsFactory jsonDbModelObjectsFactory = new JsonDbModelObjectsFactory();

    @Test
    public void createRequestPaymentOrder() {
        RequestPaymentOrder result = jsonDbModelObjectsFactory.createRequestPaymentOrder("1", "2");
        assertEquals("1", result.getPaymentOrderHeaderId());
        assertEquals("2", result.getPosition());
    }

    @Test
    public void createExecutedPaymentOrder() {
        ExecutedOrder result = jsonDbModelObjectsFactory.createExecutedPaymentOrder("1", "2");
        assertEquals("1", result.getPacketHeaderId());
        assertEquals("2", result.getOrderId());
    }
}