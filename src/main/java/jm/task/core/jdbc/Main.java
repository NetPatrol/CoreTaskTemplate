package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.*;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        Util util = new Util();
        Connection connection = util.connect();
        util.printConnectionInfo(connection);

        UserService service = new UserServiceImpl();

        System.out.println("Создаем таблицу пользователей...");
        service.createUsersTable();


        System.out.println("\nДобавляем пользователей...");
        service.saveUser("Тимур", "Батрутдинов", (byte) 38);
        service.saveUser("Гарик", "Мартиросян", (byte) 44);
        service.saveUser("Гарик", "Мартиросян", (byte) 44);
        service.saveUser("Павел", "Воля", (byte) 40);
        service.saveUser("Гарик", "Харламов", (byte) 44);
        service.saveUser("Семен", "Слепаков", (byte) 41);




        if (service.getAllUsers().size() == 0) {
            System.out.printf("\n\nТекущее количество пользователей пользователей: %d\n", service.getAllUsers().size());
        } else {
            System.out.printf("\nТекущее количество пользователей пользователей: %d\n", service.getAllUsers().size());
            System.out.printf("%-8s%s\n", "","ТАБЛИЦА ПОЛЬЗОВАТЕЛЕЙ");
            System.out.printf("%-6s%-9s%-19s%s\n", "ID", "ИМЯ", "ФАМИЛИЯ", "ВОЗРАСТ");
            service.getAllUsers().stream()
                    .sorted((u1, u2) -> u1.getName().compareToIgnoreCase(u2.getName()))
                    .forEach(System.out::println);
        }

        service.removeUserById(6);

        if (service.getAllUsers().size() == 0) {
            System.out.printf("\n\nТекущее количество пользователей пользователей: %d\n", service.getAllUsers().size());
        } else {
            System.out.printf("\nТекущее количество пользователей пользователей: %d\n", service.getAllUsers().size());
            System.out.printf("%-8s%s\n", "","ТАБЛИЦА ПОЛЬЗОВАТЕЛЕЙ");
            System.out.printf("%-6s%-9s%-19s%s\n", "ID", "ИМЯ", "ФАМИЛИЯ", "ВОЗРАСТ");
            service.getAllUsers().stream()
                    .sorted((u1, u2) -> u1.getName().compareToIgnoreCase(u2.getName()))
                    .forEach(System.out::println);
        }
//        System.out.println("\nОчищаем и удаляем таблицу пользователей");
//        service.cleanUsersTable();
//        service.dropUsersTable();
    }
}
