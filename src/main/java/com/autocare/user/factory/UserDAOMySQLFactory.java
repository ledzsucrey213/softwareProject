package com.autocare.user.factory;

import com.autocare.user.dao.UserDAO;
import com.autocare.user.dao.UserMySqlDAO;

public class UserDAOMySQLFactory implements UserDAOFactory {
    @Override public UserDAO createUserDAO() {
        return new UserMySqlDAO();
    }
}
