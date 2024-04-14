package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь
        //Util.getConnection();

        UserServiceImpl myuserService = new UserDaoJDBCImpl();

        myuserService.createUsersTable();

        myuserService.saveUser("Oleg", "Lish",(byte)44);
        myuserService.saveUser("Adam", "Lish", (byte) 12);
        myuserService.saveUser("Alice", "Lishtayeva", (byte) 8);
        myuserService.saveUser("Danya", "Lishtayev", (byte) 5);

        myuserService.removeUserById(1);
        myuserService.getAllUsers();

        myuserService.cleanUsersTable();
        myuserService.dropUsersTable();
    }
}

