package jm.task.core.jdbc.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static Connection connect;
     
    public Connection connect() {
        Properties property = new Properties();

        try (FileInputStream input = new FileInputStream("src/main/Resources/mysql_config")) {

            property.load(input);

            String user = property.getProperty("db.login");
            String password = property.getProperty("db.password");

            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = property.getProperty("db.driver") +
                    property.getProperty("db.type") +
                    property.getProperty("db.host") +
                    property.getProperty("db.port") +
                    property.getProperty("db.base");
            
            connect = DriverManager.getConnection(url, user, password);
            System.out.printf("\nподключились к БД: %s", property.getProperty("db.base"));
        } catch (SQLException | IOException | ClassNotFoundException e) {
            System.err.format("Ошибка подключения к базе данных %s", property.getProperty("db.base"));
            e.printStackTrace();
        }
        return connect;
    }
}