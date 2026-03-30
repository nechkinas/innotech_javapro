package ru.innotech.spring_db;

import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
class UserDao {
    private final DataSource dataSource;

    public UserDao(DataSource dataSource) { this.dataSource = dataSource; }

    public void save(String username) throws SQLException {
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement("INSERT INTO users (username) VALUES (?)")) {
            ps.setString(1, username);
            ps.executeUpdate();
        }
    }

    public List<User> findAll() throws SQLException {
        List<User> users = new ArrayList<>();
        try (Connection c = dataSource.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery("SELECT * FROM users")) {
            while (rs.next()) users.add(new User(rs.getLong("id"), rs.getString("username")));
        }
        return users;
    }

    public User findById(Long id) throws SQLException {
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT * FROM users WHERE id = ?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? new User(rs.getLong("id"), rs.getString("username")) : null;
        }
    }

    public void delete(Long id) throws SQLException {
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM users WHERE id = ?")) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }
}