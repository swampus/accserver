package com.bank.model.jsondb;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;

@Document(collection = "RequestPaymentOrder", schemaVersion = "1.0")
@JsonRootName("request_payment_order")
public class RequestPaymentOrder {
    @Id
    @JsonProperty("id")
    private String id;

    @JsonProperty("payment_order_header_id")
    private String paymentOrderHeaderId;

    @JsonProperty("position")
    private String position;

    public RequestPaymentOrder() {
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
