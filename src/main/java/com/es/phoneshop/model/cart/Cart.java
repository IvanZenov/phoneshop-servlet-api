package com.es.phoneshop.model.cart;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cart {
    List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "items=" + items +
                '}';
    }

}
