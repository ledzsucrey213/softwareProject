package com.autocare.transaction;

import java.util.Optional;

public class Transaction {
    private Optional<Long> id;
    private Cart           cart;
    private Item           item;
    private long           amount;

    public Transaction(long id, Cart cart, Item item, long amount) {
        this.id = Optional.of(id);
        this.cart = cart;
        this.item = item;
        this.amount = amount;
    }

    public Transaction(Cart cart, Item item, long amount) {
        this.id = Optional.empty();
        this.cart = cart;
        this.item = item;
        this.amount = amount;
    }

    public Optional<Long> getId() {return id;}

    public void setId(long id) {
        this.id = Optional.of(id);
    }

    public Cart getCart() {return cart;}

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Item getItem() {return item;}

    public void setItem(Item item) {
        this.item = item;
    }

    public long getAmount() {return amount;}

    public void setAmount(long amount) {
        this.amount = amount;
    }
}

