package com.bank.controller;

import com.bank.config.ApplicationTestModule;
import com.bank.model.ennum.OrderPriority;
import com.bank.model.jsondb.OrderPacketHeader;
import com.bank.model.jsondb.PaymentOrder;
import com.despegar.http.client.GetMethod;
import com.despegar.http.client.HttpClientException;
import com.despegar.http.client.HttpResponse;
import com.despegar.http.client.PutMethod;
import com.despegar.sparkjava.test.SparkServer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.jsondb.JsonDBTemplate;
import org.junit.ClassRule;
import org.junit.Test;
import spark.servlet.SparkApplication;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OrderControllerIntegrationTest {

    public static class OrderControllerTestSparkApplication implements SparkApplication {
        @Override
        public void init() {
            Injector injector = Guice.createInjector(new ApplicationTestModule());
            OrderController orderController = injector.getInstance(OrderController.class);
            orderController.route();
        }
    }

    @ClassRule
    public static SparkServer<OrderControllerTestSparkApplication> testServer
            = new SparkServer<>(OrderControllerIntegrationTest.OrderControllerTestSparkApplication.class, 4567);

    @Test
    public void testSinglePaymentInBlockChangeBalance() throws JsonProcessingException, HttpClientException {
        cleanUP();

        OrderPacketHeader orderPacketHeader = new OrderPacketHeader();
        orderPacketHeader.setPacketHeaderId("Header1");
        orderPacketHeader.setOrderQuantity(1);

        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setMoneyCurrency("EUR");
        paymentOrder.setOrderPositionInPacket("1");
        paymentOrder.setMoneyAmount("1000");
        paymentOrder.setAccount("HABA0102030405");
        paymentOrder.setCurrentTimestamp(String.valueOf(System.currentTimeMillis()));
        paymentOrder.setPaymentOrderId("1");
        paymentOrder.setOrderPriority(OrderPriority.HIGH);
        paymentOrder.setOrderPacketHeader(orderPacketHeader);


        PutMethod putMethod = testServer.put("/add_payment", new ObjectMapper().writeValueAsString(paymentOrder), false);
        HttpResponse httpResponse = testServer.execute(putMethod);

        assertTrue(new String(httpResponse.body()).contains("timestamp"));

        GetMethod getMethod = testServer.get("/accounts", false);
        httpResponse = testServer.execute(getMethod);
        assertEquals("[{\"account_number\":\"HABA0102030405\",\"money_amount\":\"1001\","
                + "\"money_currency\":\"EUR\"},{\"account_number\":\"PARX0102030405\",\"money_amount\":\"100\","
                + "\"money_currency\":\"USD\"}]", new String(httpResponse.body()));
    }


    @Test
    public void testTwoPaymentInBlockChangeBalance() throws JsonProcessingException, HttpClientException {
        cleanUP();

        OrderPacketHeader orderPacketHeader = new OrderPacketHeader();
        orderPacketHeader.setPacketHeaderId("HeaderForTwo");
        orderPacketHeader.setOrderQuantity(2);

        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setMoneyCurrency("EUR");
        paymentOrder.setOrderPositionInPacket("1");
        paymentOrder.setMoneyAmount("100");
        paymentOrder.setAccount("HABA0102030405");
        paymentOrder.setCurrentTimestamp(String.valueOf(System.currentTimeMillis()));
        paymentOrder.setPaymentOrderId("TWO_1");
        paymentOrder.setOrderPriority(OrderPriority.HIGH);
        paymentOrder.setOrderPacketHeader(orderPacketHeader);

        PaymentOrder paymentOrder2 = new PaymentOrder();
        paymentOrder2.setMoneyCurrency("USD");
        paymentOrder2.setOrderPositionInPacket("2");
        paymentOrder2.setMoneyAmount("-120");
        paymentOrder2.setAccount("PARX0102030405");
        paymentOrder2.setCurrentTimestamp(String.valueOf(System.currentTimeMillis()));
        paymentOrder2.setPaymentOrderId("TWO_2");
        paymentOrder2.setOrderPriority(OrderPriority.HIGH);
        paymentOrder2.setOrderPacketHeader(orderPacketHeader);

        PutMethod putMethod = testServer.put("/add_payment", new ObjectMapper().writeValueAsString(paymentOrder), false);
        HttpResponse httpResponse = testServer.execute(putMethod);

        assertTrue(new String(httpResponse.body()).contains("timestamp"));

        putMethod = testServer.put("/add_payment", new ObjectMapper().writeValueAsString(paymentOrder2), false);
        httpResponse = testServer.execute(putMethod);

        assertTrue(new String(httpResponse.body()).contains("timestamp"));

        GetMethod getMethod = testServer.get("/accounts", false);
        httpResponse = testServer.execute(getMethod);
        assertEquals("[{\"account_number\":\"HABA0102030405\",\"money_amount\":\"101\","
                + "\"money_currency\":\"EUR\"},{\"account_number\":\"PARX0102030405\",\"money_amount\":\"-20\","
                + "\"money_currency\":\"USD\"}]", new String(httpResponse.body()));
    }

    private void cleanUP() throws HttpClientException {
        Injector injector = Guice.createInjector(new ApplicationTestModule());
        EntityManager em = injector.getInstance(EntityManager.class);
        JsonDBTemplate jsonDBTemplate = injector.getInstance(JsonDBTemplate.class);
        jsonDBTemplate.reLoadDB();
        em.getTransaction().begin();
        em.createQuery("DELETE FROM account ").executeUpdate();
        em.getTransaction().commit();
        testServer.execute(testServer.get("/init_data", false));
    }


}