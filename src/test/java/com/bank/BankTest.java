package com.bank;

import com.bank.model.json.OrderPacketHeader;
import com.bank.model.json.PaymentOrder;

public class BankTest {
    public PaymentOrder createPaymentOrder(String id, OrderPacketHeader orderPacketHeader, String timestamp){
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setPaymentOrderId(id);
        paymentOrder.setOrderPacketHeader(orderPacketHeader);
        paymentOrder.setCurrentTimestamp(timestamp);
        return paymentOrder;
    }
}
