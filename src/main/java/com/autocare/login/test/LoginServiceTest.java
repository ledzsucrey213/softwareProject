package com.autocare.login.test;

import com.autocare.user.Role;
import com.autocare.user.User;
import com.autocare.login.service.LoginService;
import com.autocare.user.dao.UserDAO;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class LoginServiceTest {

    @Test public void testAuthenticate() {
        LoginService
                loginServiceSuccess
                = new LoginService(() -> new UserDAO() {
            @Override
            public void saveUser(String name, String surname, String username
                    , Role role, String password)
            throws SQLException {
            }

            @Override public Optional<User> loadUser(String username,
            String password)
            throws SQLException {
                return Optional.of(new User(1, "", Role.ADMIN, "", ""));
            }

            @Override
            public List<User> loadUsers() throws SQLException {
                return List.of();
            }

            @Override
            public void deleteUser(long userId) throws SQLException {

            }

            @Override
            public void updateUser(User user) throws SQLException {

            }
        });

        LoginService loginServiceFail = new LoginService(() -> new UserDAO() {
            @Override
            public void saveUser(String name, String surname, String username
                    , Role role, String password)
            throws SQLException {
            }

            @Override
            public Optional<User> loadUser(String username, String password)
            throws SQLException {
                return Optional.empty();
            }

            @Override
            public List<User> loadUsers() throws SQLException {
                return List.of();
            }

            @Override
            public void deleteUser(long userId) throws SQLException {

            }

            @Override
            public void updateUser(User user) throws SQLException {

            }
        });

        LoginService
                loginServiceActualPass
                = new LoginService(() -> new UserDAO() {
            @Override
            public void saveUser(String name, String surname, String username
                    , Role role, String password)
            throws SQLException {
            }

            @Override
            public Optional<User> loadUser(String username, String password)
            throws SQLException {
                if (username.equals("hi") && password.equals("pass")) {
                    return Optional.of(new User(1, "", Role.ADMIN, "", ""));
                }
                return Optional.empty();
            }

            @Override
            public List<User> loadUsers() throws SQLException {
                return List.of();
            }

            @Override
            public void deleteUser(long userId) throws SQLException {

            }

            @Override
            public void updateUser(User user) throws SQLException {

            }
        });

        try {
            Assert.assertTrue("AuthenticationSuccess Failed",
                              loginServiceSuccess.authenticate("", "")
            );

            Assert.assertFalse("AuthenticationFailure Failed",
                               loginServiceFail.authenticate("", "")
            );

            Assert.assertTrue("AuthenticationActualPassSuccess Failed",
                              loginServiceActualPass.authenticate("hi", "pass")
            );

            Assert.assertFalse("AuthenticationActualPassFailure Failed",
                               loginServiceActualPass.authenticate("", "")
            );

            Assert.assertFalse("AuthenticationActualPassFailure Failed 2",
                               loginServiceActualPass.authenticate("hi", "")
            );

            Assert.assertFalse("AuthenticationActualPassFailure Failed 3",
                               loginServiceActualPass.authenticate("", "pass")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
