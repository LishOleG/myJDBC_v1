package jm.task.core.jdbc.util;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.w3c.dom.ls.LSOutput;

import java.sql.*;
import java.util.Properties;

public class Util {

    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "04061979";
    private static final String DIALECT = "org.hibernate.dialect.MySQL5InnoDBDialect";

    private static SessionFactory sessionFactory;

    static {
        try {

            Properties properties = new Properties();

            properties.put("hibernate.connection.driver_class", DB_DRIVER);
            properties.put("hibernate.connection.url", URL);
            properties.put("hibernate.connection.username", USERNAME);
            properties.put("hibernate.connection.password", PASSWORD);
            properties.put("hibernate.dialect", DIALECT);

            properties.put(Environment.SHOW_SQL, "true");
            properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            properties.put(Environment.HBM2DDL_AUTO, "create-drop");

            Configuration configure = new Configuration();
            configure.setProperties(properties)


                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();

            ServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .applySettings(configure.getProperties())
                    .build();
            sessionFactory = configure.buildSessionFactory(registry);
            System.out.println("It's OK connection SessionFactory");

        } catch (HibernateException e) {

            System.out.println("Problem creating SessionFactory");
            e.printStackTrace();
        }
    }


    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("It's OK connection from Util");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("It's ERROR connection");
        }
        return connection;
    }
}








