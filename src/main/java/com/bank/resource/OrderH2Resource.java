package com.bank.resource;

import com.bank.model.h2.Account;
import org.javamoney.moneta.Money;

import javax.jdo.annotations.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

import static com.bank.model.h2.Account.ACCOUNT_FIND_ALL;
import static com.bank.model.h2.Account.ACCOUNT_FIND_BY_ACCOUNT_NUMBER;


public class OrderH2Resource {

    private EntityManager entityManager;

    public OrderH2Resource(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public Boolean updateAccountBalance(String accountNumber, Money balanceChange) {
        entityManager.getTransaction().begin();
        Boolean result = getAccountByAccountNumber(accountNumber).map((Account account) -> {
            account.setBalance(balanceChange.add(account.getBalance()));
            entityManager.persist(account);
            return true;
        }).orElse(false);
        entityManager.getTransaction().commit();
        return result;
    }


    public void addAccount(Account account) {
        entityManager.getTransaction().begin();
        entityManager.persist(account);
        entityManager.getTransaction().commit();
    }

    public List<Account> getAllAccounts() {
        return entityManager.createNamedQuery(ACCOUNT_FIND_ALL, Account.class).getResultList();
    }

    private Optional<Account> getAccountByAccountNumber(String accountNumber) {
        TypedQuery<Account> query = entityManager.createNamedQuery(ACCOUNT_FIND_BY_ACCOUNT_NUMBER, Account.class)
                .setParameter("accNumber", accountNumber);
        query.setLockMode(LockModeType.PESSIMISTIC_WRITE);
        return query.getResultStream().findFirst();
    }
}