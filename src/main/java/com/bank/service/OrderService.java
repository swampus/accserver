package com.bank.service;

import com.bank.factory.JsonDbModelObjectsFactory;
import com.bank.model.h2.Account;
import com.bank.model.jsondb.ExecutedOrder;
import com.bank.model.jsondb.OrderPacketHeader;
import com.bank.model.jsondb.PaymentOrder;
import com.bank.model.jsondb.RequestPaymentOrder;
import com.bank.resource.OrderH2Resource;
import com.bank.resource.OrderJsonResource;
import com.google.common.annotations.VisibleForTesting;
import org.javamoney.moneta.Money;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderService {

    private static final int FIRST = 0;
    public static final int FIRST_IN_BLOCK = 1;
    private static int FIRST_ELEMENT_THAT_ALWAYS_EXIST = 0;

    private OrderJsonResource orderJsonResource;
    private OrderH2Resource orderH2Resource;
    private JsonDbModelObjectsFactory jsonDbModelObjectsFactory;

    public OrderService(OrderJsonResource orderJsonResource, OrderH2Resource orderH2Resource, JsonDbModelObjectsFactory jsonDbModelObjectsFactory) {
        this.orderJsonResource = orderJsonResource;
        this.orderH2Resource = orderH2Resource;
        this.jsonDbModelObjectsFactory = jsonDbModelObjectsFactory;
    }

    public List<Account> getAllAccounts() {
        return orderH2Resource.getAllAccounts();
    }

    public void addAccount(Account account) {
        orderH2Resource.addAccount(account);
    }

    public void processPaymentOrderRecieved(PaymentOrder paymentOrder) {
        List<PaymentOrder> executableBlock = orderJsonResource.getPaymentOrderExecutableBlock(paymentOrder);
        if (isPaymentBlockComplete(executableBlock)) {
            createExecutionInstructions(executableBlock).forEach((accNumber, money)
                    -> orderH2Resource.updateAccountBalance(accNumber, money));
            orderJsonResource.addAllExecutedOrderPaymentOrders(createAllExecutedOrderList(executableBlock));
        } else {
            orderJsonResource.addAllRequestPaymentOrders(findMissingPaymentOrdersInBlock(executableBlock));
        }
    }

    private Boolean isPaymentBlockComplete(List<PaymentOrder> paymentOrders) {
        Integer orderQuantity = paymentOrders.get(FIRST_ELEMENT_THAT_ALWAYS_EXIST).getOrderPacketHeader().getOrderQuantity();
        return orderQuantity == paymentOrders.size();
    }

    @VisibleForTesting
    Map<String, Money> createExecutionInstructions(List<PaymentOrder> completedPaymentBlock) {
        Map<String, Money> instructions = new HashMap<>();
        completedPaymentBlock.forEach((PaymentOrder paymentOrder) -> {
            Money money = createMoney(paymentOrder.getMoneyAmount(), paymentOrder.getMoneyCurrency());
            if (instructions.containsKey(paymentOrder.getAccount())) {
                instructions.merge(paymentOrder.getAccount(), money, Money::add);
            } else {
                instructions.put(paymentOrder.getAccount(), money);
            }
        });
        return instructions;
    }

    @VisibleForTesting
    List<RequestPaymentOrder> findMissingPaymentOrdersInBlock(List<PaymentOrder> incompletePaymentOrderBlock) {

        OrderPacketHeader orderPacketHeader = getFirstOrderPacketHeaderFromBlock(incompletePaymentOrderBlock);
        final int orderQuantity = orderPacketHeader.getOrderQuantity();
        final String paymentOrderHeaderId = orderPacketHeader.getPacketHeaderId();

        List<Integer> positions = createListWithNumbers(orderQuantity);
        List<Integer> positionsInHeader = incompletePaymentOrderBlock.stream().map((PaymentOrder paymentOrder)
                -> Integer.parseInt(paymentOrder.getOrderPositionInPacket())).collect(Collectors.toList());

        return positions.stream().filter((Integer position) -> !positionsInHeader.contains(position))
                .map((Integer missingPosition) -> jsonDbModelObjectsFactory.createRequestPaymentOrder(paymentOrderHeaderId,
                        String.valueOf(missingPosition))).collect(Collectors.toList());
    }

    @VisibleForTesting
    List<ExecutedOrder> createAllExecutedOrderList(List<PaymentOrder> completedPaymentBlock) {
        return completedPaymentBlock.stream().map((PaymentOrder paymentOrder)
                -> jsonDbModelObjectsFactory.createExecutedPaymentOrder(
                getFirstOrderPacketHeaderFromBlock(completedPaymentBlock).getPacketHeaderId(),
                paymentOrder.getPaymentOrderId())).collect(Collectors.toList());
    }

    private Money createMoney(String amount, String currency) {
        return Money.of(new BigDecimal(amount), currency);
    }

    private List<Integer> createListWithNumbers(int orderQuantity) {
        List<Integer> list = new ArrayList<>();
        for (int i = FIRST_IN_BLOCK; i <= orderQuantity; i++) {
            list.add(i);
        }
        return list;
    }

    private OrderPacketHeader getFirstOrderPacketHeaderFromBlock(List<PaymentOrder> paymentBlock) {
        return paymentBlock.get(FIRST).getOrderPacketHeader();
    }

}
