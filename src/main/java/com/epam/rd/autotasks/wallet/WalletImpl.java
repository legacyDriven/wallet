package com.epam.rd.autotasks.wallet;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WalletImpl implements Wallet {

    private final List<Account> accountList;
    final PaymentLog logs;
    private final Lock paymentLock;

    public WalletImpl(List<Account> accountList, PaymentLog paymentLog) {
        this.accountList = accountList;
        this.logs = paymentLog;
        paymentLock = new ReentrantLock();
    }

    @Override
    public void pay(String recipient, long amount) throws ShortageOfMoneyException {
        try {
            paymentLock.lock();
            Account provider = accountList.stream()
                    .filter(n -> n.balance()>=amount)
                    .findFirst()
                    .orElseThrow(() -> new ShortageOfMoneyException(recipient, amount));
            provider.pay(amount);
            logs.add(provider, recipient, amount);
        } finally {
            paymentLock.unlock();
        }
    }
}
