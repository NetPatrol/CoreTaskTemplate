package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Util util = new Util();
    Connection connection;
    PreparedStatement preparedStatement;
    private static final String CREATE_TABLE_SQL_REQUEST = "CREATE TABLE IF NOT EXISTS users " +
            "(id BIGINT AUTO_INCREMENT UNIQUE NOT NULL, " +
            "name VARCHAR(45), " +
            "lastName VARCHAR(45), " +
            "age INT, " +
            "PRIMARY KEY (id))";
    private static final String DROP_SQL_REQUEST = "DROP TABLE IF EXISTS users CASCADE";
    private static final String INSERT_SQL_REQUEST = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
    private static final String SELECT_ALL_USERS_SQL_REQUEST = "SELECT * FROM users";
    private static final String DELETE_USER_SQL_REQUEST =  "DELETE FROM users WHERE id = ?";
    private static final String TRUNCATE_TABLE_SQL_REQUEST = "TRUNCATE TABLE users";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        connection = util.connect();
        try {
            preparedStatement = connection.prepareStatement(CREATE_TABLE_SQL_REQUEST);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.getSQLState();
        }
    }

    public void dropUsersTable() {
        connection = util.connect();
        try {
            preparedStatement = connection.prepareStatement(DROP_SQL_REQUEST);
            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.getSQLState();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        connection = util.connect();
        try {
            preparedStatement = connection.prepareStatement(INSERT_SQL_REQUEST);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            System.out.printf("Пользователь %s %s успешно добавлен\n", lastName, name);
        } catch (SQLException e) {
            e.getSQLState();
        }
    }
    
    public void removeUserById(long id) {
        connection = util.connect();
        try {
            preparedStatement = connection.prepareStatement(DELETE_USER_SQL_REQUEST);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.getSQLState();
        }
    }

    public List<User> getAllUsers() {
        List<User> all = new ArrayList<>();
        connection = util.connect();
        try {
            preparedStatement = connection.prepareStatement(SELECT_ALL_USERS_SQL_REQUEST);
            ResultSet response = preparedStatement.executeQuery();
            while (response.next()) {
                User users = new User();
                users.setId(response.getLong("id"));
                users.setName(response.getString("name"));
                users.setLastName(response.getString("lastName"));
                users.setAge((byte)response.getInt("age"));
                all.add(users);
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.getSQLState();
        }
        return all;
    }

    public void cleanUsersTable() {
        connection = util.connect();
        try {
            preparedStatement = connection.prepareStatement(TRUNCATE_TABLE_SQL_REQUEST);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.getSQLState();
        }
    }
}