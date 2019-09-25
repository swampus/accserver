package com.bank.config;

import com.bank.controller.OrderController;
import com.bank.factory.JsonDbModelObjectsFactory;
import com.bank.mapper.AccountMapper;
import com.bank.model.jsondb.ExecutedOrder;
import com.bank.model.jsondb.OrderPacketHeader;
import com.bank.model.jsondb.PaymentOrder;
import com.bank.model.jsondb.RequestPaymentOrder;
import com.bank.resource.OrderH2Resource;
import com.bank.resource.OrderJsonResource;
import com.bank.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.jsondb.JsonDBTemplate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ApplicationTestModule extends AbstractModule {

    private static final ThreadLocal<EntityManager> ENTITY_MANAGER_CACHE = new ThreadLocal<>();

    @Provides
    @Singleton
    public EntityManagerFactory createEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("BANK_DB_TEST");
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
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Provides
    public AccountMapper accountMapper() {
        return new AccountMapper();
    }

    @Provides
    @Inject
    public OrderController orderController(OrderService orderService,
                                           AccountMapper accountMapper,
                                           ObjectMapper objectMapper) {
        return new OrderController(orderService, accountMapper, objectMapper);
    }

    @Provides
    @Inject
    public OrderJsonResource orderJsonResource(JsonDBTemplate jsonDBTemplate) {
        return new OrderJsonResource(jsonDBTemplate);
    }

    @Provides
    @Inject
    public OrderH2Resource orderH2Resource(EntityManager entityManager) {
        return new OrderH2Resource(entityManager);
    }

    @Provides
    @Inject
    public OrderService orderService(OrderJsonResource orderJsonResource,
                                     OrderH2Resource orderH2Resource,
                                     JsonDbModelObjectsFactory jsonDbModelObjectsFactory) {
        return new OrderService(orderJsonResource, orderH2Resource, jsonDbModelObjectsFactory);
    }


    @Provides
    public JsonDBTemplate jsonDBTemplate() {
        JsonDBTemplate jsonDBTemplate = new JsonDBTemplate(JsonDBConfig.DB_FILES_LOCATION_TEST, JsonDBConfig.BASE_SCAN_PACKAGE);

        if (jsonDBTemplate.collectionExists(PaymentOrder.class)) {
            jsonDBTemplate.dropCollection(PaymentOrder.class);
        }
        jsonDBTemplate.createCollection(PaymentOrder.class);

        if (jsonDBTemplate.collectionExists(OrderPacketHeader.class)) {
            jsonDBTemplate.dropCollection(OrderPacketHeader.class);
        }
        jsonDBTemplate.createCollection(OrderPacketHeader.class);

        if (jsonDBTemplate.collectionExists(ExecutedOrder.class)) {
            jsonDBTemplate.dropCollection(ExecutedOrder.class);
        }
        jsonDBTemplate.createCollection(ExecutedOrder.class);

        if (jsonDBTemplate.collectionExists(RequestPaymentOrder.class)) {
            jsonDBTemplate.dropCollection(RequestPaymentOrder.class);
        }
        jsonDBTemplate.createCollection(RequestPaymentOrder.class);
        return jsonDBTemplate;
    }
}
