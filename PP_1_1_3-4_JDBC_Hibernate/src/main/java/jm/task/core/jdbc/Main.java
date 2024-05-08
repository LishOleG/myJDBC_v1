package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
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

        //UserService myUD_JDBC = new UserServiceImpl();
        UserService UD_Hibernate = new UserServiceImpl();
        //Util.getSessionFactory();

        UD_Hibernate.createUsersTable();

        UD_Hibernate.saveUser("Oleg", "Lish",(byte)34);
        UD_Hibernate.saveUser("Adam", "Lish", (byte) 12);
        UD_Hibernate.saveUser("Alice", "Lishtayeva", (byte) 8);
        UD_Hibernate.saveUser("Danya", "Lishtayev", (byte) 5);

        UD_Hibernate.removeUserById(1);

        UD_Hibernate.getAllUsers();

        UD_Hibernate.cleanUsersTable();
        //UD_Hibernate.dropUsersTable();


    }
}

