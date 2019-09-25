package com.bank.config;

import com.bank.model.h2.Account;
import com.google.common.collect.ImmutableList;
import org.javamoney.moneta.Money;

import java.util.List;

public class BankSystemInitData {
    public static List<Account> initAccountsData() {
        Account account = new Account();
        account.setBalance(Money.of(1, "EUR"));
        account.setAccNumber("HABA0102030405");

        Account account2 = new Account();
        account2.setBalance(Money.of(100, "USD"));
        account2.setAccNumber("PARX0102030405");

        return ImmutableList.of(account, account2);
    }


}
