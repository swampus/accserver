package com.bank;


import com.bank.config.ApplicationModule;
import com.bank.controller.OrderController;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class BankApplication {

    public static void main(String[] args) {

        Injector injector = Guice.createInjector(new ApplicationModule());
        OrderController orderController = injector.getInstance(OrderController.class);
        orderController.route();


    }

}
