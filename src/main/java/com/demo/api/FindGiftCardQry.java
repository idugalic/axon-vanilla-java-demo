package com.demo.api;

import java.io.Serializable;

public class FindGiftCardQry implements Serializable {
    private String id;

    public FindGiftCardQry(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "FindGiftCardQry{" +
                "id='" + id + '\'' +
                '}';
    }
}

