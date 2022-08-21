package com.epam.rd.autotasks.wallet;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WalletImpl implements Wallet {

    private final List<Account> accountList;
    final PaymentLogImpl logs;
    private final Lock paymentLock;

    public WalletImpl(List<Account> accountList, PaymentLog paymentLog) {
        this.accountList = accountList;
        this.logs = new PaymentLogImpl();
        paymentLock = new ReentrantLock();
    }

//    @Override
//    public void pay(String recipient, long amount) throws Exception {
//        Account provider = this.getProvider(recipient, amount);
//
//    }
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
            provider.lock();
        } finally {
            paymentLock.unlock();
        }
    }


    public Account getProvider(String recipient, long amount) throws ShortageOfMoneyException {
        return accountList.stream().filter(n-> n.balance()>=amount).findFirst().orElseThrow(()->new ShortageOfMoneyException(recipient, amount));
    }


    public static void main(String[] args) throws Exception {
        List<Account> accounts = List.of(new AccountImpl("gienek", 500), new AccountImpl("franek" , 400 ));

       // WalletImpl wallet = WalletFactory.wallet(accounts, new PaymentLogImpl());
        WalletImpl wallet = new WalletImpl(accounts, new PaymentLogImpl());

        System.out.println();
        System.out.println(wallet);
        System.out.println(wallet.logs.getLogs().size());
        wallet.pay("marek", 55);
        System.out.println(wallet.logs.getLogs().size());
        System.out.println(wallet.logs.getLogs());
    }

    @Override
    public String toString() {
        return "WalletImpl{" +
                "accountList=" + accountList +
                ", paymentLog=" + logs +
                ", paymentLock=" + paymentLock +
                '}';
    }


}
