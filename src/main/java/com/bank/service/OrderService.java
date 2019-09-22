package com.bank.service;

import com.bank.model.json.PaymentOrder;
import com.bank.resource.OrderJsonResource;
import com.bank.resource.OrderH2Resource;
import com.google.common.annotations.VisibleForTesting;
import io.jsondb.JsonDBTemplate;
import org.javamoney.moneta.Money;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService {

    private static int FIRST_ELEMENT_THAT_ALWAYS_EXIST = 0;

    private JsonDBTemplate jsonDBTemplate;

    private OrderJsonResource orderJsonResource;
    private OrderH2Resource orderH2Resource;

    public OrderService(OrderJsonResource orderJsonResource, OrderH2Resource orderH2Resource) {
        this.orderJsonResource = orderJsonResource;
        this.orderH2Resource = orderH2Resource;
    }

    public void processPaymentOrderRecieved(PaymentOrder paymentOrder) {
        List<PaymentOrder> executableBlock = orderJsonResource.getPaymentOrderExecutableBlock(paymentOrder);
        if (isPaymentBlockComplete(executableBlock)) {
            Map<String, Money> instructions = createExecutionInstructions(executableBlock);
        }else{
            //return
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

    private Money createMoney(String amount, String currency) {
        return Money.of(new BigDecimal(amount), currency);
    }


}
