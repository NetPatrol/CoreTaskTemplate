package jm.task.core.jdbc.util;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

public class Util {
    private static Connection connection;
    private static String user;

    public static Connection connect() {
        Properties property = new Properties();
        try (FileInputStream input = new FileInputStream("src/main/Resources/mysql_config")) {
            property.load(input);
            user = property.getProperty("db.login");
            String password = property.getProperty("db.password");

            Class.forName("com.mysql.cj.jdbc.Driver");
            StringBuilder url = new StringBuilder();
            url.append("jdbc:")
               .append(property.getProperty("db.driver"))
               .append(property.getProperty("db.host"))
               .append(property.getProperty("db.port"))
               .append(property.getProperty("db.base"));
            connection = DriverManager.getConnection(url.toString(), user, password);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void printConnectionInfo(Connection connection) throws SQLException {
        System.out.println(new Date());
        System.out.printf("Текущий ползователь: %s\n", user);
        System.out.println("------------------------------------------------");
        System.out.printf("Data base: %s\n", connection.getMetaData().getDatabaseProductName());
        System.out.printf("version: %s\n", connection.getMetaData().getDatabaseProductVersion());
        System.out.printf("Driver: %s\n", connection.getMetaData().getDriverName());
        System.out.printf("Autocommit: %s\n", connection.getAutoCommit());
        System.out.println("------------------------------------------------");
    }
}