package com.example.helloworld;

import java.util.ArrayList;
import java.util.List;

public class ShoppingList {
    public String name;
    public List<String> items;

    public ShoppingList(String name) {
        this.name = name;
        this.items = new ArrayList<>();
    }
}
