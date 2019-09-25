package com.bank.factory;

import com.bank.model.jsondb.ExecutedOrder;
import com.bank.model.jsondb.RequestPaymentOrder;

public class JsonDbModelObjectsFactory {


    public RequestPaymentOrder createRequestPaymentOrder(String paymentOrderHeaderId, String position) {
        RequestPaymentOrder requestPaymentOrder = new RequestPaymentOrder();
        requestPaymentOrder.setPaymentOrderHeaderId(paymentOrderHeaderId);
        requestPaymentOrder.setPosition(position);
        return requestPaymentOrder;
    }

    public ExecutedOrder createExecutedPaymentOrder(String paymentOrderHeaderId, String orderId) {
        ExecutedOrder executedOrder = new ExecutedOrder();
        executedOrder.setPacketHeaderId(paymentOrderHeaderId);
        executedOrder.setOrderId(orderId);
        return executedOrder;
    }

}
