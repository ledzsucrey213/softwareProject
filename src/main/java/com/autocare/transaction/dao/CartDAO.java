package com.autocare.transaction.dao;

import com.autocare.transaction.Cart;
import com.autocare.user.User;

import java.sql.SQLException;
import java.util.Optional;

public interface CartDAO {
    Cart insertCart(Cart cart) throws SQLException;
    boolean removeCart(Cart cart) throws SQLException;
    Optional<Cart> getCartOfUser(long userId) throws SQLException;
}
