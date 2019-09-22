package com.bank.model.json;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;

@Document(collection = "OrderPacketHeader", schemaVersion= "1.0")
@JsonRootName("order_packet_header")
public class OrderPacketHeader {

    @Id
    @JsonProperty("packet_header_id")
    private String packetHeaderId;
    @JsonProperty("order_quantity")
    private Integer orderQuantity;
    @JsonProperty("order_position_in_packet")
    private Integer orderPositionInPacket;

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

    public Integer getOrderPositionInPacket() {
        return orderPositionInPacket;
    }

    public String getPacketHeaderId() {
        return packetHeaderId;
    }

    public void setOrderPositionInPacket(Integer orderPositionInPacket) {
        this.orderPositionInPacket = orderPositionInPacket;
    }


    @Override
    public String toString() {
        return "OrderPacketHeader{" +
                "packetHeaderId='" + packetHeaderId + '\'' +
                ", orderQuantity=" + orderQuantity +
                ", orderPositionInPacket=" + orderPositionInPacket +
                '}';
    }
}
