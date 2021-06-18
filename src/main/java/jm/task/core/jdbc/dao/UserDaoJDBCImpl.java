package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.SqlQuery;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static Util util = new Util();
    private static Connection connection;

    String CREATE_TABLE = SqlQuery.CREATE.toString();
    String DROP_TABLE = SqlQuery.DROP.toString();
    String TRUNCATE_TABLE = SqlQuery.TRUNCATE.toString();
    String SELECT_ALL = SqlQuery.SELECT.toString();
    String SELECT_USER = SqlQuery.SELECT_USER.toString();
    String SELECT_USER_ID = SqlQuery.SELECT_BY_ID.toString();
    String INSERT_USER = SqlQuery.INSERT.toString();
    String DELETE_USER = SqlQuery.DELETE.toString();
                           
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        connection = util.connect();
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_TABLE);
            connection.close();
        } catch (SQLException e) {
            e.getSQLState();
        }
    }

    public void dropUsersTable() {
        connection = util.connect();
        try (Statement statement = connection.createStatement()) {
            statement.execute(DROP_TABLE);
            connection.close();
        } catch (SQLException e) {
            e.getSQLState();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        connection = util.connect();
        PreparedStatement pstm = null;
        try {
            pstm = connection.prepareStatement(SELECT_USER);
            pstm.setString(1, name);
            pstm.setString(2, lastName);
            pstm.setByte(3, age);
            ResultSet result = pstm.executeQuery();
            if (result.next()) {
                System.out.println("Такой пользователь уже существует.");
                pstm.close();
            } else {
                pstm = connection.prepareStatement(INSERT_USER);
                pstm.setString(1, name);
                pstm.setString(2, lastName);
                pstm.setByte(3, age);
                int i = pstm.executeUpdate();
                if (i > 0) {
                    System.out.printf("\nПользователь  %s %s %d добавлен в таблицу.", lastName, name, i);
                } else {
                    System.out.println("\nНе удалось добавить пользователя.");
                }
                pstm.close();
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        public void removeUserById(long id) {
        connection = util.connect();
        PreparedStatement pstm = null;
        try {
            pstm = connection.prepareStatement(SELECT_USER_ID);
            pstm.setLong(1, id);
            ResultSet result = pstm.executeQuery();
            if (result.next()) {
                String name = result.getString("name");
                String lastName = result.getString("lastName");
                pstm = connection.prepareStatement(DELETE_USER);
                pstm.setLong(1, id);
                int i = pstm.executeUpdate();
                if (i > 0) {
                    System.out.printf("Пользователь %s %s успешно удален", lastName, name);
                }
            } else {
                System.out.println("Пользователя с таким ID не существует.");
            }
            connection.close();
        } catch (SQLException e) {
            e.getSQLState();
        }
    }

    public List<User> getAllUsers() {
        List<User> all = new ArrayList<>();
        connection = util.connect();
        try (Statement statement = connection.createStatement()) {
            ResultSet response = statement.executeQuery(SELECT_ALL);
            while (response.next()) {
                User user = new User();
                user.setId(response.getLong("id"));
                user.setName(response.getString("name"));
                user.setLastName(response.getString("lastName"));
                user.setAge(response.getByte("age"));
                all.add(user);
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.getSQLState();
        }
        return all;
    }

    public void cleanUsersTable() {
        connection = util.connect();
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(TRUNCATE_TABLE);
            connection.close();
        } catch (SQLException e) {
            e.getSQLState();
        }
    }
}