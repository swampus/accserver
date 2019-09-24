package com.bank.handler;

import com.bank.config.JsonDBConfig;
import com.bank.controller.OrderController;
import com.bank.model.json.ExecutedOrder;
import com.bank.model.json.OrderPacketHeader;
import com.bank.model.json.PaymentOrder;
import com.bank.resource.OrderH2Resource;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.jsondb.JsonDBTemplate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

@PersistenceUnit(unitName = "bank")
public class BankModule extends AbstractModule {

    private static final ThreadLocal<EntityManager> ENTITY_MANAGER_CACHE = new ThreadLocal<>();

    @Provides
    @Singleton
    public EntityManagerFactory createEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("BANK_DB");
    }

    @Provides
    public EntityManager createEntityManager(
            EntityManagerFactory entityManagerFactory) {
        EntityManager entityManager = ENTITY_MANAGER_CACHE.get();
        if (entityManager == null) {
            ENTITY_MANAGER_CACHE.set(entityManager = entityManagerFactory.createEntityManager());
        }
        return entityManager;
    }

    @Provides
    @Inject
    public OrderController orderController(JsonDBTemplate jsonDBTemplate){
        return null;
    }

    @Provides
    @Inject
    public OrderH2Resource orderH2Resource(EntityManager entityManager){
        return new OrderH2Resource(entityManager);
    }

    @Provides
    public JsonDBTemplate createJsonDBTemplate(){
        JsonDBTemplate jsonDBTemplate =  new JsonDBTemplate(JsonDBConfig.DB_FILES_LOCATION, JsonDBConfig.BASE_SCAN_PACKAGE);

        if(!jsonDBTemplate.collectionExists(OrderPacketHeader.class)){
            jsonDBTemplate.createCollection(OrderPacketHeader.class);
        }
        if(!jsonDBTemplate.collectionExists(PaymentOrder.class)){
            jsonDBTemplate.createCollection(PaymentOrder.class);
        }
        if(!jsonDBTemplate.collectionExists(ExecutedOrder.class)){
            jsonDBTemplate.createCollection(ExecutedOrder.class);
        }
        return jsonDBTemplate;
    }

}