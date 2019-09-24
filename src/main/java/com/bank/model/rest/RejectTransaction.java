package com.bank.model.rest;

import com.bank.model.json.OrderPacketHeader;

public class RejectTransaction implements Response {

    private OrderPacketHeader orderPacketHeader;

    public RejectTransaction() {
    }

    public OrderPacketHeader getOrderPacketHeader() {
        return orderPacketHeader;
    }

    public void setOrderPacketHeader(OrderPacketHeader orderPacketHeader) {
        this.orderPacketHeader = orderPacketHeader;
    }
}
