package com.bank.service;

import com.bank.BankTest;
import com.bank.model.json.OrderPacketHeader;
import com.bank.model.json.PaymentOrder;
import com.bank.resource.OrderH2Resource;
import com.bank.resource.OrderJsonResource;
import io.jsondb.JsonDBTemplate;
import org.javamoney.moneta.Money;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceUnitTest extends BankTest {

    @Mock
    private OrderJsonResource orderJsonResource;

    @Mock
    private OrderH2Resource orderH2Resource;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void processPaymentOrderRecieved() {
        fail();
    }

    @Test
    public void findMissingPaymentOrdersInBlock(){
        fail();
    }

    @Test
    public void createExecutionInstructions() {
        List<PaymentOrder> completedPaymentBlock = new ArrayList<>();

        OrderPacketHeader orderPacketHeader1 = new OrderPacketHeader();
        OrderPacketHeader orderPacketHeader2 = new OrderPacketHeader();

        PaymentOrder paymentOrder1 = createPaymentOrder("1",orderPacketHeader1,"01");
        paymentOrder1.setAccount("A1");
        paymentOrder1.setMoneyAmount("40");
        paymentOrder1.setMoneyCurrency("EUR");

        PaymentOrder paymentOrder2 = createPaymentOrder("2",orderPacketHeader1,"02");
        paymentOrder2.setAccount("A1");
        paymentOrder2.setMoneyAmount("-20");
        paymentOrder2.setMoneyCurrency("EUR");

        PaymentOrder paymentOrder3 = createPaymentOrder("3",orderPacketHeader2,"03");
        paymentOrder3.setAccount("A2");
        paymentOrder3.setMoneyAmount("200");
        paymentOrder3.setMoneyCurrency("USD");

        completedPaymentBlock.add(paymentOrder1);
        completedPaymentBlock.add(paymentOrder2);
        completedPaymentBlock.add(paymentOrder3);

        Map<String, Money> result = orderService.createExecutionInstructions(completedPaymentBlock);

        assertEquals(20, result.get("A1").getNumber().intValue());
        assertEquals(200, result.get("A2").getNumber().intValue());

        assertEquals("EUR", result.get("A1").getCurrency().getCurrencyCode());
        assertEquals("USD", result.get("A2").getCurrency().getCurrencyCode());



    }
}