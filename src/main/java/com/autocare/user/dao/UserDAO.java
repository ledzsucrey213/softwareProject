package com.autocare.user.dao;

import com.autocare.user.Role;
import com.autocare.user.User;

import java.sql.SQLException;
import java.util.Optional;
import java.util.List;

public interface UserDAO {
    void saveUser(String name, String surname, String username, Role role,
                  String password
    ) throws SQLException;

    Optional<User> loadUser(String username, String password)
    throws SQLException;

    List<User> loadUsers() throws SQLException;

    void deleteUser(long userId) throws SQLException;

    void updateUser(User user) throws SQLException;

}