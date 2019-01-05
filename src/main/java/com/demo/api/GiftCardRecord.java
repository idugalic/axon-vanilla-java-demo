package com.demo.api;

import java.io.Serializable;

public class GiftCardRecord implements Serializable {

    private String id;
    private Integer initialValue;
    private Integer remainingValue;

    public GiftCardRecord(String id, Integer initialValue, Integer remainingValue) {
        this.id = id;
        this.initialValue = initialValue;
        this.remainingValue = remainingValue;
    }

    public GiftCardRecord() {
    }

    public String getId() {
        return id;
    }

    public Integer getInitialValue() {
        return initialValue;
    }

    public Integer getRemainingValue() {
        return remainingValue;
    }

    @Override
    public String toString() {
        return "GiftCardRecord{" +
                "id='" + id + '\'' +
                ", initialValue=" + initialValue +
                ", remainingValue=" + remainingValue +
                '}';
    }
}
