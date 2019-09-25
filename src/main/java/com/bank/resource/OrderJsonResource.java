package com.bank.resource;

import com.bank.model.jsondb.ExecutedOrder;
import com.bank.model.jsondb.PaymentOrder;
import com.bank.model.jsondb.RequestPaymentOrder;
import io.jsondb.JsonDBTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class OrderJsonResource {

    private JsonDBTemplate jsonDBTemplate;

    public OrderJsonResource(JsonDBTemplate jsonDBTemplate) {
        this.jsonDBTemplate = jsonDBTemplate;
    }

    public void putPaymentOrder(PaymentOrder paymentOrder) {
        jsonDBTemplate.insert(paymentOrder);
    }

    public void putExecutedOrder(ExecutedOrder executedOrder) {
        jsonDBTemplate.insert(executedOrder);
    }

    public List<ExecutedOrder> getExecutedOrders() {
        return jsonDBTemplate.getCollection(ExecutedOrder.class);
    }

    public Boolean isPaymentOrderWithProvidedIdExists(String paymentOrderId) {
        return jsonDBTemplate
                .getCollection(PaymentOrder.class)
                .stream().anyMatch((PaymentOrder paymentOrder) -> paymentOrder.getPaymentOrderId()
                        .equals(paymentOrderId));
    }

    public void addAllRequestPaymentOrders(List<RequestPaymentOrder> requestPaymentOrders) {
        requestPaymentOrders.forEach((RequestPaymentOrder requestPaymentOrder)
                -> jsonDBTemplate.insert(requestPaymentOrder));
    }

    public void addAllExecutedOrderPaymentOrders(List<ExecutedOrder> executedOrders) {
        executedOrders.forEach((ExecutedOrder executedOrder)
                -> jsonDBTemplate.insert(executedOrder));
    }

    public void addPaymentOrder(PaymentOrder paymentOrder) {
        jsonDBTemplate.insert(paymentOrder);
    }

    public List<PaymentOrder> getPaymentOrderExecutableBlock(PaymentOrder paymentOrder) {
        List<PaymentOrder> paymentOrders = jsonDBTemplate
                .getCollection(PaymentOrder.class)
                .stream()
                .filter((PaymentOrder payOrder)
                        -> payOrder.getOrderPacketHeader().getPacketHeaderId()
                        .equals(paymentOrder.getOrderPacketHeader().getPacketHeaderId()) &&
                        !payOrder.getPaymentOrderId().equals(paymentOrder.getPaymentOrderId())
                )
                .filter(distinctByKey(PaymentOrder::getPaymentOrderId))
                .collect(Collectors.toList());

        paymentOrders.add(paymentOrder);

        return paymentOrders;
    }

    private <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        final Set<Object> seen = new HashSet<>();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
