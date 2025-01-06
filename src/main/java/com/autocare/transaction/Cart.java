package com.autocare.transaction;

import com.autocare.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Cart {
    private final List<Transaction> cartContent;
    private       Optional<Long>    id;
    private       User              user;

    public Cart(long id, User user) {
        this.id = Optional.of(id);
        this.user = user;
        cartContent = new ArrayList<>();
    }

    public Cart(User user) {
        this.user = user;
        cartContent = new ArrayList<>();
    }

    public Optional<Long> getId() {
        return id;
    }

    public void setId(long id) {
        this.id = Optional.of(id);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Transaction> getCartContent() {
        return cartContent;
    }

    public void addItem(Item item) {
        for (Transaction transaction : cartContent) {
            if (transaction.getItem().equals(item)) {
                transaction.setAmount(transaction.getAmount() + 1);
                return;
            }
        }

        cartContent.add(new Transaction(this, item, 1));
    }

    public void addTransaction(Transaction transaction) {
        cartContent.add(transaction);
    }

    public void removeItem(Item item) {
        boolean found = false;
        for (Transaction transaction : cartContent) {
            if (transaction.getItem().equals(item)) {
                transaction.setAmount(transaction.getAmount() - 1);
                found = true;
                break;
            }
        }

        if (!found) {
            throw new IllegalArgumentException("Item not found");
        }

        cartContent.removeIf(transaction -> transaction.getAmount() == 0);
    }
}