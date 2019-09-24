package com.bank.handler;


import com.bank.model.h2.Account;
import com.bank.resource.OrderH2Resource;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.javamoney.moneta.Money;

public class HellowWourld {

    public static final String APP_PACKAGE = "com.bank";


    public static void main(String[] args) {

        Injector injector = Guice.createInjector(new BankModule());

        //OrderController orderController = injector.getInstance(OrderController.class);
        OrderH2Resource orderH2Resource = injector.getInstance(OrderH2Resource.class);

        Account account = new Account();
        account.setBalance(Money.of(100, "EUR"));
        account.setAccNumber("JHASIOUFHOUISAF");
        orderH2Resource.addAccount(account);



        Account account2 = new Account();
        account.setBalance(Money.of(200, "EUR"));
        account.setAccNumber("JHASIOUFHO345UISAF");
        orderH2Resource.addAccount(account2);

        orderH2Resource.updateAccountBalance("JHASIOUFHOUISAF", Money.of(100, "EUR"));

        orderH2Resource.getAllAccounts().forEach(System.out::println);

    }

}
