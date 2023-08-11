package pl.coderslab.entity;

import pl.coderslab.utils.DbUtil;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class UserDao {
    private static final String CREATE_USER_QUERY =
            "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";
    private static final String READ_USER_QUERY =
            "SELECT * FROM users WHERE id = ?";
    private static final String UPDATE_USER_QUERY =
            "UPDATE users SET username = ?, email = ?, password = ? WHERE id = ?";
    private static final String DELETE_USER_QUERY =
            "DELETE FROM users WHERE id = ?";
    private static final String PRINTALL_USER_QUERY =
            "SELECT * FROM users";

    public User createUser(User user) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(CREATE_USER_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            preStmt.setString(1, user.getUserName());
            preStmt.setString(2, user.getEmail());
            preStmt.setString(3, hashPassword(user.getPassword()));
            preStmt.executeUpdate();
            //Pobieramy wstawiony do bazy identyfikator, a następnie ustawiamy id obiektu user.
            ResultSet resultSet = preStmt.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User readUser(int userId) {
        boolean hasUser = false;
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(READ_USER_QUERY);
            preStmt.setString(1, Integer.toString(userId));
            ResultSet resultSet = preStmt.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt(1));
                user.setPassword(resultSet.getString(2));
                user.setEmail(resultSet.getString(3));
                user.setUserName(resultSet.getString(4));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(User user) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(UPDATE_USER_QUERY);
            preStmt.setString(1, user.getUserName());
            preStmt.setString(2, user.getEmail());
            preStmt.setString(3, hashPassword(user.getPassword()));
            preStmt.setString(4, Integer.toString(user.getId()));
            preStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int userId) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(DELETE_USER_QUERY);
            preStmt.setString(1, Integer.toString(userId));
            preStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public User[] findAll(){
        try(Connection conn = DbUtil.getConnection()){
            PreparedStatement preStmt = conn.prepareStatement(PRINTALL_USER_QUERY);
            ResultSet rs = preStmt.executeQuery();
            User[] userTab = new User[0];
            while (rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                userTab = addToArray(user, userTab);
            }
            return userTab;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    private User[] addToArray(User u, User[] users) {
        User[] tmpUsers = Arrays.copyOf(users, users.length + 1); // Tworzymy kopię tablicy powiększoną o 1.
        tmpUsers[users.length] = u; // Dodajemy obiekt na ostatniej pozycji.
        return tmpUsers; // Zwracamy nową tablicę.
    }
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }



}
