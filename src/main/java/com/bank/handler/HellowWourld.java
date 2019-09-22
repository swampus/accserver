package com.bank.handler;


import com.bank.controller.OrderController;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.security.GeneralSecurityException;

import static spark.Spark.get;

public class HellowWourld {

    public static final String APP_PACKAGE = "com.bank";


    public static void main(String[] args) {

        Injector injector = Guice.createInjector(new BankModule());

        OrderController orderController = injector.getInstance(OrderController.class);



    }

}
