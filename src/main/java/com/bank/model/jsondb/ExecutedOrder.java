package com.bank.model.jsondb;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;

@Document(collection = "ExecutedOrder", schemaVersion = "1.0")
@JsonRootName("executed_order")
public class ExecutedOrder {

    @Id
    @JsonProperty("order_id")
    private String orderId;


    @JsonProperty("packet_header_id")
    private String packetHeaderId;

    public ExecutedOrder() {

    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPacketHeaderId() {
        return packetHeaderId;
    }

    public void setPacketHeaderId(String packetHeaderId) {
        this.packetHeaderId = packetHeaderId;
    }
}
