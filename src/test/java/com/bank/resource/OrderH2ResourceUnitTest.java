package com.bank.resource;

import com.bank.model.h2.Account;
import org.javamoney.moneta.Money;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.bank.model.h2.Account.ACCOUNT_FIND_ALL;
import static com.bank.model.h2.Account.ACCOUNT_FIND_BY_ACCOUNT_NUMBER;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderH2ResourceUnitTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private OrderH2Resource orderH2Resource;

    @Test
    public void updateAccountBalance() {
        Account account = new Account();
        account.setBalance(Money.of(100, "EUR"));
        account.setAccNumber("123456789");

        Money money = Money.of(150, "EUR");

        EntityTransaction entityTransaction = mock(EntityTransaction.class);
        TypedQuery<Account> typedQuery = mock(TypedQuery.class);
        Stream<Account> stream = Stream.of(account);


        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        when(entityManager.createNamedQuery(ACCOUNT_FIND_BY_ACCOUNT_NUMBER, Account.class)).thenReturn(typedQuery);
        when(typedQuery.setParameter("accNumber", "123456789")).thenReturn(typedQuery);
        when(typedQuery.getResultStream()).thenReturn(stream);


        Boolean result = orderH2Resource.updateAccountBalance("123456789", money);

        verify(entityManager, times(2)).getTransaction();
        verify(entityTransaction, times(1)).begin();
        verify(entityManager, times(1)).persist(account);
        verify(entityManager, times(1)).createNamedQuery(ACCOUNT_FIND_BY_ACCOUNT_NUMBER, Account.class);
        verify(typedQuery, times(1)).setParameter("accNumber", "123456789");
        verify(typedQuery, times(1)).getResultStream();
        verify(entityTransaction, times(1)).commit();

        verifyNoMoreInteractions(entityTransaction, entityManager);

        assertEquals(250, account.getBalance().getNumber().intValue());
        assertEquals("EUR", account.getBalance().getCurrency().getCurrencyCode());
        assertTrue(result);
    }

    @Test
    public void updateAccountBalance_Empty_Account() {

        Money money = Money.of(150, "EUR");

        EntityTransaction entityTransaction = mock(EntityTransaction.class);
        TypedQuery<Account> typedQuery = mock(TypedQuery.class);
        Stream<Account> stream = Stream.of();


        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        when(entityManager.createNamedQuery(ACCOUNT_FIND_BY_ACCOUNT_NUMBER, Account.class)).thenReturn(typedQuery);
        when(typedQuery.setParameter("accNumber", "123456789")).thenReturn(typedQuery);
        when(typedQuery.getResultStream()).thenReturn(stream);


        Boolean result = orderH2Resource.updateAccountBalance("123456789", money);

        verify(entityManager, times(2)).getTransaction();
        verify(entityTransaction, times(1)).begin();
        verify(entityManager, times(1)).createNamedQuery(ACCOUNT_FIND_BY_ACCOUNT_NUMBER, Account.class);
        verify(typedQuery, times(1)).setParameter("accNumber", "123456789");
        verify(typedQuery, times(1)).getResultStream();
        verify(entityTransaction, times(1)).commit();

        verifyNoMoreInteractions(entityTransaction, entityManager);

        assertFalse(result);
    }

    @Test
    public void addAccount() {
        EntityTransaction entityTransaction = mock(EntityTransaction.class);

        Account account = new Account();
        when(entityManager.getTransaction()).thenReturn(entityTransaction);

        orderH2Resource.addAccount(account);
        verify(entityManager, times(2)).getTransaction();
        verify(entityTransaction, times(1)).begin();
        verify(entityManager, times(1)).persist(account);
        verify(entityTransaction, times(1)).commit();


        verifyNoMoreInteractions(entityTransaction, entityManager);
    }

    @Test
    public void getAllAccounts() {
        EntityTransaction entityTransaction = mock(EntityTransaction.class);

        List<Account> accounts = new ArrayList<>();
        TypedQuery<Account> typedQuery = mock(TypedQuery.class);

        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        when(entityManager.createNamedQuery(ACCOUNT_FIND_ALL, Account.class)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(accounts);

        List<Account> result = orderH2Resource.getAllAccounts();

        assertEquals(accounts, result);

        verify(entityManager, times(1)).createNamedQuery(ACCOUNT_FIND_ALL, Account.class);
        verifyNoMoreInteractions(entityTransaction, entityManager);
    }
}