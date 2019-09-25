package com.bank.model.jsondb;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;

@Document(collection = "OrderPacketHeader", schemaVersion = "1.0")
@JsonRootName("order_packet_header")
public class OrderPacketHeader {

    @Id
    @JsonProperty("packet_header_id")
    private String packetHeaderId;
    @JsonProperty("order_quantity")
    private Integer orderQuantity;

    public OrderPacketHeader() {
    }

    public void setPacketHeaderId(String packetHeaderId) {
        this.packetHeaderId = packetHeaderId;
    }

    public Integer getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(Integer orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public String getPacketHeaderId() {
        return packetHeaderId;
    }

    @Override
    public String toString() {
        return "OrderPacketHeader{" +
                "packetHeaderId='" + packetHeaderId + '\'' +
                ", orderQuantity=" + orderQuantity +
                '}';
    }
}
