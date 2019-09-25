package com.bank.model.h2;

import org.javamoney.moneta.Money;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "account")
@NamedQuery(
        name = Account.ACCOUNT_FIND_BY_ACCOUNT_NUMBER,
        query = "SELECT acc FROM account acc WHERE acc.accNumber = :accNumber")
@NamedQuery(
        name = Account.ACCOUNT_FIND_ALL,
        query = "SELECT acc FROM account acc")

public class Account implements Serializable {

    public static final String ACCOUNT_FIND_BY_ACCOUNT_NUMBER = "Account.findByAccountNumber";
    public static final String ACCOUNT_FIND_ALL = "Account.findAll";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "acc_number")
    private String accNumber;

    @Column(name = "balance")
    private Money balance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

}
