package com.bank.model.rest;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("accepted_transaction")
public class Accepted {

    private String timestamp;

    public Accepted(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
