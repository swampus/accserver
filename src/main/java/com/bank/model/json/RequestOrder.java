package com.bank.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.jsondb.annotation.Id;

public class RequestOrder {
    @Id
    @JsonProperty("id")
    private String id;

    @JsonProperty("payment_order_header_id")
    private String paymentOrderHeaderId;

    @JsonProperty("position")
    private String position;

    public RequestOrder() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPaymentOrderHeaderId() {
        return paymentOrderHeaderId;
    }

    public void setPaymentOrderHeaderId(String paymentOrderHeaderId) {
        this.paymentOrderHeaderId = paymentOrderHeaderId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
