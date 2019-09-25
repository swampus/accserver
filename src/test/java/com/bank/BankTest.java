package com.bank;

import com.bank.model.jsondb.OrderPacketHeader;
import com.bank.model.jsondb.PaymentOrder;

public class BankTest {
    public PaymentOrder createPaymentOrder(String id, OrderPacketHeader orderPacketHeader, String timestamp) {
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setPaymentOrderId(id);
        paymentOrder.setOrderPacketHeader(orderPacketHeader);
        paymentOrder.setCurrentTimestamp(timestamp);
        return paymentOrder;
    }
}
