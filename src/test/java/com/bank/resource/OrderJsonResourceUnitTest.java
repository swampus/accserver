package com.bank.resource;

import com.bank.BankTest;
import com.bank.model.json.ExecutedOrder;
import com.bank.model.json.OrderPacketHeader;
import com.bank.model.json.PaymentOrder;
import com.bank.model.json.RequestPaymentOrder;
import com.google.common.collect.ImmutableList;
import io.jsondb.JsonDBTemplate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderJsonResourceUnitTest extends BankTest {

    @Mock
    private JsonDBTemplate jsonDBTemplate;

    @InjectMocks
    private OrderJsonResource orderJsonResource;

    @Test
    public void putPaymentOrder() {
        PaymentOrder paymentOrder = new PaymentOrder();

        orderJsonResource.putPaymentOrder(paymentOrder);

        verify(jsonDBTemplate, times(1)).insert(paymentOrder);
        verifyNoMoreInteractions(jsonDBTemplate);
    }

    @Test
    public void putExecutedOrder() {
        ExecutedOrder executedOrder = new ExecutedOrder();

        orderJsonResource.putExecutedOrder(executedOrder);

        verify(jsonDBTemplate, times(1)).insert(executedOrder);
        verifyNoMoreInteractions(jsonDBTemplate);
    }

    @Test
    public void addAllRequestPaymentOrders() {
        RequestPaymentOrder requestPaymentOrder1 = new RequestPaymentOrder();
        RequestPaymentOrder requestPaymentOrder2 = new RequestPaymentOrder();
        List<RequestPaymentOrder> paymentOrders = ImmutableList.of(requestPaymentOrder1, requestPaymentOrder2);

        orderJsonResource.addAllRequestPaymentOrders(paymentOrders);
        verify(jsonDBTemplate, times(1)).insert(requestPaymentOrder1);
        verify(jsonDBTemplate, times(1)).insert(requestPaymentOrder2);

        verifyNoMoreInteractions(jsonDBTemplate);
    }

    @Test
    public void addAllExecutedOrderPaymentOrders() {
        ExecutedOrder executedOrder1 = new ExecutedOrder();
        ExecutedOrder executedOrder2 = new ExecutedOrder();
        List<ExecutedOrder> executedOrders = ImmutableList.of(executedOrder1, executedOrder2);

        orderJsonResource.addAllExecutedOrderPaymentOrders(executedOrders);
        verify(jsonDBTemplate, times(1)).insert(executedOrder1);
        verify(jsonDBTemplate, times(1)).insert(executedOrder2);

        verifyNoMoreInteractions(jsonDBTemplate);
    }


    @Test
    public void getExecutedOrders() {

        List<ExecutedOrder> executedOrders = new ArrayList<>();
        executedOrders.add(new ExecutedOrder());
        executedOrders.add(new ExecutedOrder());

        when(jsonDBTemplate.getCollection(ExecutedOrder.class)).thenReturn(executedOrders);

        List<ExecutedOrder> executedOrdersResult = orderJsonResource.getExecutedOrders();
        assertEquals(executedOrders, executedOrdersResult);
    }

    @Test
    public void isPaymentOrderWithProvidedIdExists_True() {
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setPaymentOrderId("p1");
        List<PaymentOrder> paymentOrders = new ArrayList<>();
        paymentOrders.add(paymentOrder);

        when(jsonDBTemplate.getCollection(PaymentOrder.class)).thenReturn(paymentOrders);

        assertTrue(orderJsonResource.isPaymentOrderWithProvidedIdExists("p1"));
    }

    @Test
    public void isPaymentOrderWithProvidedIdExists_False() {

        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setPaymentOrderId("p1");
        List<PaymentOrder> paymentOrders = new ArrayList<>();
        paymentOrders.add(paymentOrder);

        when(jsonDBTemplate.getCollection(PaymentOrder.class)).thenReturn(paymentOrders);

        assertFalse(orderJsonResource.isPaymentOrderWithProvidedIdExists("p2"));
    }

    @Test
    public void getPaymentOrderExecutableBlock_single_new_come_payment_in_block() {

        OrderPacketHeader orderPacketHeader = new OrderPacketHeader();
        orderPacketHeader.setPacketHeaderId("123");
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setPaymentOrderId("1234");

        List<PaymentOrder> results = orderJsonResource.getPaymentOrderExecutableBlock(paymentOrder);

        assertEquals(paymentOrder, results.get(0));

    }

    @Test
    public void getPaymentOrderExecutableBlock_same_id_payment_in_block() {

        OrderPacketHeader orderPacketHeader = new OrderPacketHeader();
        orderPacketHeader.setPacketHeaderId("123");

        OrderPacketHeader orderPacketHeaderForOtherPayment = new OrderPacketHeader();
        orderPacketHeaderForOtherPayment.setPacketHeaderId("123");


        PaymentOrder paymentOrder1 = createPaymentOrder("1", orderPacketHeader, "01");
        PaymentOrder paymentOrder2 = createPaymentOrder("2", orderPacketHeader, "02");

        PaymentOrder paymentOrder2SameId = createPaymentOrder("2", orderPacketHeaderForOtherPayment, "03");


        List<PaymentOrder> listFromDB = ImmutableList.of(paymentOrder1, paymentOrder2);
        when(jsonDBTemplate.getCollection(PaymentOrder.class)).thenReturn(listFromDB);

        List<PaymentOrder> results = orderJsonResource.getPaymentOrderExecutableBlock(paymentOrder2SameId);

        assertEquals(paymentOrder1, results.get(0));
        assertEquals(paymentOrder2SameId, results.get(1));

    }

    @Test
    public void getPaymentOrderExecutableBlock() {
        OrderPacketHeader orderPacketHeader = new OrderPacketHeader();
        orderPacketHeader.setPacketHeaderId("123");

        OrderPacketHeader orderPacketHeaderForOtherPayment = new OrderPacketHeader();
        orderPacketHeaderForOtherPayment.setPacketHeaderId("123");


        PaymentOrder paymentOrder1 = createPaymentOrder("1", orderPacketHeader, "01");
        PaymentOrder paymentOrder2 = createPaymentOrder("2", orderPacketHeader, "02");
        PaymentOrder paymentOrder3 = createPaymentOrder("3", orderPacketHeader, "03");
        PaymentOrder paymentOrder4 = createPaymentOrder("4", orderPacketHeaderForOtherPayment, "04");

        List<PaymentOrder> listFromDB = ImmutableList.of(paymentOrder1, paymentOrder2, paymentOrder3);
        when(jsonDBTemplate.getCollection(PaymentOrder.class)).thenReturn(listFromDB);

        List<PaymentOrder> results = orderJsonResource.getPaymentOrderExecutableBlock(paymentOrder4);

        assertEquals(paymentOrder1, results.get(0));
        assertEquals(paymentOrder2, results.get(1));
        assertEquals(paymentOrder3, results.get(2));
        assertEquals(paymentOrder4, results.get(3));
    }

    @Test
    public void getPaymentOrderExecutableBlock_drop_same_id() {
        OrderPacketHeader orderPacketHeader = new OrderPacketHeader();
        orderPacketHeader.setPacketHeaderId("123");

        OrderPacketHeader orderPacketHeaderForOtherPayment = new OrderPacketHeader();
        orderPacketHeaderForOtherPayment.setPacketHeaderId("123");


        PaymentOrder paymentOrder1 = createPaymentOrder("1", orderPacketHeader, "01");
        PaymentOrder paymentOrder2 = createPaymentOrder("2", orderPacketHeader, "02");
        PaymentOrder paymentOrder3 = createPaymentOrder("2", orderPacketHeader, "03");

        PaymentOrder paymentOrder4 = createPaymentOrder("1", orderPacketHeaderForOtherPayment, "04");

        List<PaymentOrder> listFromDB = ImmutableList.of(paymentOrder1, paymentOrder2, paymentOrder3);
        when(jsonDBTemplate.getCollection(PaymentOrder.class)).thenReturn(listFromDB);

        List<PaymentOrder> results = orderJsonResource.getPaymentOrderExecutableBlock(paymentOrder4);


        assertEquals(2, results.size());
        assertEquals(paymentOrder2, results.get(0));
        assertEquals(paymentOrder4, results.get(1));
    }

}