package com.epam.rd.autotasks.wallet;

import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AccountImpl implements Account {

    private final String name;

    private long balance;

    private final Lock lock = new ReentrantLock();

    public AccountImpl(String name, long balance) {
        this.name = name;
        this.balance = balance;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public void pay(long price) {
        try {
            lock.lock();
            if (price <= balance) balance -= price;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public long balance() {
        return balance;
    }

    @Override
    public Lock lock() {
        return lock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountImpl account = (AccountImpl) o;
        return balance == account.balance && Objects.equals(name, account.name) && Objects.equals(lock, account.lock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, balance, lock);
    }

    @Override
    public String toString() {
        return "AccountImpl{" +
                "name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }
}
