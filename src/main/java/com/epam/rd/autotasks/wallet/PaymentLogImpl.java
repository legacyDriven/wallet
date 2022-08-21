package com.epam.rd.autotasks.wallet;

import java.util.ArrayList;
import java.util.List;

public class PaymentLogImpl implements PaymentLog {

    private final List<Record> logs = new ArrayList<>();

    @Override
    public void add(Account account, String recipient, long amount) {
        logs.add(new Record(account,recipient, amount));
    }

    public List<Record> getLogs() {
        return logs;
    }
}
