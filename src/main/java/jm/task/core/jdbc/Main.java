package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.*;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserService service = new UserServiceImpl();
        System.out.println("=================================================================");
        System.out.println("Создаем таблицу пользователей...");
        service.createUsersTable();
        System.out.println("=================================================================");
        System.out.println("Добавляем пользователей...");
        service.saveUser("Тимур", "Батрутдинов", (byte) 38);
        service.saveUser("Гарик", "Мартиросян", (byte) 44);
        service.saveUser("Павел", "Воля", (byte) 40);
        service.saveUser("Семен", "Слепаков", (byte) 41);
        System.out.println("=================================================================");
        System.out.println("Выводим список всех пользователей...");
        for (User user : service.getAllUsers()) {
            System.out.println(user);
        }
        System.out.println("=================================================================");
        System.out.println("Очищаем и удаляем таблицу пользователей");
        service.cleanUsersTable();
        service.dropUsersTable();
    }
}
