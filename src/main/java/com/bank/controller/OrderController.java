package com.bank.controller;


import com.bank.config.BankSystemInitData;
import com.bank.mapper.AccountMapper;
import com.bank.model.h2.Account;
import com.bank.model.jsondb.PaymentOrder;
import com.bank.model.rest.Accepted;
import com.bank.model.rest.AccountDTO;
import com.bank.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.stream.Collectors;

import static spark.Spark.get;
import static spark.Spark.put;

public class OrderController {

    private OrderService orderService;
    private AccountMapper accountMapper;
    private ObjectMapper objectMapper;

    public OrderController(OrderService orderService, AccountMapper accountMapper, ObjectMapper objectMapper) {
        this.orderService = orderService;
        this.accountMapper = accountMapper;
        this.objectMapper = objectMapper;
    }

    public void route() {
        initData();
        addAccount();
        addPayment();
        accounts();
    }

    private void accounts() {
        get("/accounts", (request, response) -> objectMapper.writeValueAsString(orderService.getAllAccounts().stream()
                .map((Account account) -> accountMapper.createAccountDTO(account))
                .collect(Collectors.toList())));
    }

    private void addPayment() {
        put("/add_payment", (request, response) -> {
            orderService.processPaymentOrderRecieved(objectMapper.readValue(request.body(), PaymentOrder.class));
            return objectMapper.writeValueAsString(createAccepted());
        });
    }

    private void addAccount() {
        put("/add_account", (request, response) -> {
            orderService.addAccount(accountMapper.createAccount(objectMapper.readValue(request.body(), AccountDTO.class)));
            return objectMapper.writeValueAsString(createAccepted());
        });
    }

    private void initData() {
        get("/init_data", (request, response) -> {
            BankSystemInitData.initAccountsData().forEach(account -> orderService.addAccount(account));
            return objectMapper.writeValueAsString(createAccepted());
        });
    }

    private Accepted createAccepted() {
        return new Accepted(String.valueOf(System.currentTimeMillis()));
    }

}
