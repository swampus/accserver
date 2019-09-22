package com.bank.handler;

import com.bank.config.JsonDBConfig;
import com.bank.controller.OrderController;
import com.bank.model.json.ExecutedOrder;
import com.bank.model.json.OrderPacketHeader;
import com.bank.model.json.PaymentOrder;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import io.jsondb.JsonDBTemplate;

public class BankModule extends AbstractModule {

    @Provides
    @Inject
    public OrderController orderController(JsonDBTemplate jsonDBTemplate){
        return new OrderController(jsonDBTemplate);
    }



    @Provides
    public JsonDBTemplate createJsonDBTemplate(){
        JsonDBTemplate jsonDBTemplate =  new JsonDBTemplate(JsonDBConfig.DB_FILES_LOCATION, JsonDBConfig.BASE_SCAN_PACKAGE);
        jsonDBTemplate.dropCollection(OrderPacketHeader.class);
        jsonDBTemplate.dropCollection(PaymentOrder.class);
        jsonDBTemplate.dropCollection(ExecutedOrder.class);

        if(!jsonDBTemplate.collectionExists(OrderPacketHeader.class)){
            jsonDBTemplate.createCollection(OrderPacketHeader.class);
        }
        if(!jsonDBTemplate.collectionExists(PaymentOrder.class)){
            jsonDBTemplate.createCollection(PaymentOrder.class);
        }
        if(!jsonDBTemplate.collectionExists(ExecutedOrder.class)){
            jsonDBTemplate.createCollection(ExecutedOrder.class);
        }
        jsonDBTemplate.reLoadDB();
        return jsonDBTemplate;
    }
}