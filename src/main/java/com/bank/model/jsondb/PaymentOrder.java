package com.bank.model.jsondb;

import com.bank.model.ennum.OrderPriority;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;


@Document(collection = "PaymentOrder", schemaVersion = "1.0")
@JsonRootName("payment_order")
public class PaymentOrder {

    @Id
    @JsonProperty("payment_order_id")
    private String paymentOrderId;
    @JsonProperty("order_packet_header")
    private OrderPacketHeader orderPacketHeader;
    @JsonProperty("order_position_in_packet")
    private String orderPositionInPacket;
    @JsonProperty("account")
    private String account;
    @JsonProperty("money_amount")
    private String moneyAmount;
    @JsonProperty("money_currency")
    private String moneyCurrency;
    @JsonProperty("order_priority")
    private OrderPriority orderPriority;
    @JsonProperty("current_timestamp")
    private String currentTimestamp;

    public PaymentOrder() {
    }

    public String getPaymentOrderId() {
        return paymentOrderId;
    }

    public void setPaymentOrderId(String paymentOrderId) {
        this.paymentOrderId = paymentOrderId;
    }

    public OrderPacketHeader getOrderPacketHeader() {
        return orderPacketHeader;
    }

    public void setOrderPacketHeader(OrderPacketHeader orderPacketHeader) {
        this.orderPacketHeader = orderPacketHeader;
    }

    public String getOrderPositionInPacket() {
        return orderPositionInPacket;
    }

    public void setOrderPositionInPacket(String orderPositionInPacket) {
        this.orderPositionInPacket = orderPositionInPacket;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCurrentTimestamp() {
        return currentTimestamp;
    }

    public void setCurrentTimestamp(String currentTimestamp) {
        this.currentTimestamp = currentTimestamp;
    }

    public String getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(String moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public String getMoneyCurrency() {
        return moneyCurrency;
    }

    public void setMoneyCurrency(String moneyCurrency) {
        this.moneyCurrency = moneyCurrency;
    }

    public OrderPriority getOrderPriority() {
        return orderPriority;
    }

    public void setOrderPriority(OrderPriority orderPriority) {
        this.orderPriority = orderPriority;
    }

    @Override
    public String toString() {
        return "PaymentOrder{" +
                "paymentOrderId='" + paymentOrderId + '\'' +
                ", orderPacketHeader=" + orderPacketHeader +
                ", account='" + account + '\'' +
                ", moneyAmount='" + moneyAmount + '\'' +
                ", moneyCurrency='" + moneyCurrency + '\'' +
                ", orderPriority=" + orderPriority +
                ", currentTimestamp='" + currentTimestamp + '\'' +
                '}';
    }
}
