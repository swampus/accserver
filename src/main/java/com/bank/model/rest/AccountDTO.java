package com.bank.model.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("account")
public class AccountDTO {

    @JsonProperty("account_number")
    private String account;

    @JsonProperty("money_amount")
    private String moneyAmount;

    @JsonProperty("money_currency")
    private String moneyCurrency;

    public AccountDTO() {

    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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
}
