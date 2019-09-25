package com.bank.model.rest;

import com.bank.model.jsondb.OrderPacketHeader;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("reject_transaction")
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
