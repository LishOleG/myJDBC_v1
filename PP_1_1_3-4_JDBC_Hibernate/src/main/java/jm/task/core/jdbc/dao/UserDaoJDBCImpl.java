package jm.task.core.jdbc.dao;

import com.sun.xml.bind.v2.model.core.ID;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import net.bytebuddy.description.NamedElement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.time.chrono.HijrahChronology.INSTANCE;

public class UserDaoJDBCImpl extends Util implements UserDao {

    private final Util INSTANCE = new Util();

    private static final Connection conn;

    static {
        conn = Util.getConnection();
        System.out.println("connection from UD_JDBCImpl ..it's OK!");
    }

    public Util getInstance() {
        return INSTANCE;
    }

    public UserDaoJDBCImpl() {
    }

    @Override
    public void createUsersTable() {
        String mySQL = """
                CREATE TABLE IF NOT EXISTS `mydbtest`.`user` (
                  `id` INT(11) NOT NULL AUTO_INCREMENT,
                  `name` VARCHAR(45) NOT NULL,
                  `lastName` VARCHAR(45) NOT NULL,
                  `age` INT(3) NOT NULL,
                  PRIMARY KEY (`id`))
                ENGINE = InnoDB 
                DEFAULT CHARACTER SET = utf8;""";

        try (PreparedStatement state = conn.prepareStatement(mySQL)) {
            conn.setAutoCommit(false);
            state.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void dropUsersTable() {
        String mySQL = "TRUNCATE TABLE USER";

        try (Statement state = conn.createStatement()) {
            conn.setAutoCommit(false);
            state.executeUpdate(mySQL);
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        String mySQL = "INSERT INTO USER (name, lastName, age) VALUES (?, ?, ?)";

        try (PreparedStatement state = conn.prepareStatement(mySQL)) {
            state.setString(1, name);
            state.setString(2, lastName);
            state.setByte(3, Byte.parseByte(String.valueOf(age)));
            conn.setAutoCommit(false);
            state.executeUpdate();
            System.out.println("User с именем — " + name + " добавлен в базу данных");
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void removeUserById(long id) {

        String mySQL = "DELETE FROM USER WHERE ID=?";

        try (PreparedStatement state = conn.prepareStatement(mySQL)) {
            state.setLong(1, id);

            conn.setAutoCommit(false);
            state.executeUpdate();
            System.out.println("User " + id + " удален из таблицы.");
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        String mySQL = "SELECT  id, name, lastName, age FROM USER ";

        try (Statement statement = conn.createStatement()) {
            conn.setAutoCommit(false);
            ResultSet resultSet = statement.executeQuery(mySQL);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(resultSet.getByte("AGE"));

                userList.add(user);
                conn.commit();
            }

        } catch (SQLException e) {
            e.printStackTrace();

            for (User users : userList) {
                System.out.println("инфо строка из БД таблицы о: " + users);
            }
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        }
        return userList;
    }


    @Override
    public void cleanUsersTable() {

        String mySQL = "DELETE FROM USER";

        try (Statement statement = conn.createStatement()) {
            conn.setAutoCommit(false);
            statement.executeUpdate(mySQL);
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
