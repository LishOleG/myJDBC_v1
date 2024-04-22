package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {

        UserService myUD_JDBC = new UserServiceImpl();

        myUD_JDBC.createUsersTable();

        myUD_JDBC.saveUser("Oleg", "Lish",(byte)34);
        myUD_JDBC.saveUser("Adam", "Lish", (byte) 12);
        myUD_JDBC.saveUser("Alice", "Lishtayeva", (byte) 8);
        myUD_JDBC.saveUser("Danya", "Lishtayev", (byte) 5);

        myUD_JDBC.removeUserById(1);
        myUD_JDBC.getAllUsers();

        myUD_JDBC.cleanUsersTable();
        myUD_JDBC.dropUsersTable();
    }
}

