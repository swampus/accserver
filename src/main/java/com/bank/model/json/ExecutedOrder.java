package com.bank.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;

@Document(collection = "ExecutedOrder", schemaVersion= "1.0")
@JsonRootName("executed_order")
public class ExecutedOrder {

    @Id
    @JsonProperty("packet_header_id")
    private String packetHeaderId;

    public ExecutedOrder() {

    }

    public String getPacketHeaderId() {
        return packetHeaderId;
    }

    public void setPacketHeaderId(String packetHeaderId) {
        this.packetHeaderId = packetHeaderId;
    }
}
