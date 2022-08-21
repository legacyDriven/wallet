package com.epam.rd.autotasks.wallet;

import java.util.Objects;

public class Record {

    private final Account account;
    private final String recipient;
    private final long amount;

    public Record(Account account, String recipient, long amount) {
        this.account = account;
        this.recipient = recipient;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Record record = (Record) o;
        return amount == record.amount && account.equals(record.account) && recipient.equals(record.recipient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, recipient, amount);
    }

    @Override
    public String toString() {
        return "Record{" +
                "account=" + account +
                ", recipient='" + recipient + '\'' +
                ", amount=" + amount +
                '}';
    }

}
