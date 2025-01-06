package com.autocare.user.factory;

import com.autocare.user.dao.UserDAO;

public interface UserDAOFactory {
    public UserDAO createUserDAO();
}
