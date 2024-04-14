package jm.task.core.jdbc.dao;

import com.sun.xml.bind.v2.model.core.ID;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import net.bytebuddy.description.NamedElement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends UserServiceImpl implements UserDao {

    private static final UserDaoJDBCImpl INSTANCE = new UserDaoJDBCImpl();
    private static final Connection conn;

    static {
        conn = Util.getConnection();
        System.out.println("...connection it's OK!");
    }

    public static UserDaoJDBCImpl getInstance() {
        return INSTANCE;
    }

    public UserDaoJDBCImpl() {
    }

    @Override
    public void createUsersTable() {
        String mySQL = """
        CREATE TABLE IF NOT EXISTS `mydbtest`.`users` (
          `id` INT(11) NOT NULL AUTO_INCREMENT,
          `name` VARCHAR(45) NOT NULL,
          `lastName` VARCHAR(45) NOT NULL,
          `age` INT(3) NOT NULL,
          PRIMARY KEY (`id`))
        ENGINE = InnoDB 
        DEFAULT CHARACTER SET = utf8;""";

        try {

            PreparedStatement state = conn.prepareStatement(mySQL);
            {
                state.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        String mySQL = "TRUNCATE TABLE USERS";

        try (Statement state = conn.createStatement()) {

            state.executeUpdate(mySQL);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        String mySQL = "INSERT INTO USERS (name, lastName, age) VALUES (?, ?, ?)";
        //User user = new User();

        try (PreparedStatement state = conn.prepareStatement(mySQL)) {
            state.setString(1, name);
            state.setString(2, lastName);
            state.setByte(3, Byte.parseByte(String.valueOf(age)));

            state.executeUpdate();
            System.out.println("User с именем — " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {

        String mySQL = "DELETE FROM USERS WHERE ID=?";

        //User users = new User();

        try (PreparedStatement state = conn.prepareStatement(mySQL)) {
            state.setLong(1, id);
            state.executeUpdate();
            System.out.println("User " + id + " удален из таблицы.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        String mySQL = "SELECT  id, name, lastName, age FROM USERS ";

        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(mySQL);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(resultSet.getByte("AGE"));

                userList.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (User users : userList) {
            System.out.println("инфо строка из БД таблицы о: " + users);
        }
        return userList;
    }


    @Override
    public void cleanUsersTable() {

        String mySQL = "DELETE FROM USERS";

        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(mySQL);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
