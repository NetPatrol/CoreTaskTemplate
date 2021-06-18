package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.*;
import jm.task.core.jdbc.model.User;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDao query = new UserDaoJDBCImpl();

    public void createUsersTable() throws SQLException {
        query.createUsersTable();
    }

    public void dropUsersTable() throws SQLException {
        query.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        query.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) throws SQLException {
        query.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return query.getAllUsers();
    }

    @Override
    public void updateUser(long id, String name, String lastName, byte age) {
        query.updateUser(id, name, lastName, age);
    }

    public void cleanUsersTable() throws SQLException {
        query.cleanUsersTable();
    }
}
