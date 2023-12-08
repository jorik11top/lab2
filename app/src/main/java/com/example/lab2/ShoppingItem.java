package com.example.lab2;

public class ShoppingItem {
    private long id;
    private String name;

    public ShoppingItem(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String newItemName) {
        this.name = newItemName;
    }
}
