package com.bank.mapper;

import com.bank.model.h2.Account;
import com.bank.model.rest.AccountDTO;
import org.javamoney.moneta.Money;

import java.math.BigDecimal;

public class AccountMapper {

    public AccountDTO createAccountDTO(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccount(account.getAccNumber());
        accountDTO.setMoneyAmount(String.valueOf(account.getBalance().getNumber()));
        accountDTO.setMoneyCurrency(account.getBalance().getCurrency().getCurrencyCode());
        return accountDTO;
    }

    public Account createAccount(AccountDTO accountDTO) {
        Account account = new Account();
        account.setAccNumber(account.getAccNumber());
        account.setBalance(Money.of(new BigDecimal(accountDTO.getMoneyAmount()), accountDTO.getMoneyCurrency()));
        return account;
    }

}
