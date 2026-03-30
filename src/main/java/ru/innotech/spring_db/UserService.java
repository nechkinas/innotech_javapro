package ru.innotech.spring_db;

import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
class UserService {
    private final UserDao userDao;
    public UserService(UserDao userDao) { this.userDao = userDao; }

    public void create(String name) { try { userDao.save(name); } catch (SQLException e) { e.printStackTrace(); } }
    public List<User> getAll() { try { return userDao.findAll(); } catch (SQLException e) { return List.of(); } }
    public User getOne(Long id) { try { return userDao.findById(id); } catch (SQLException e) { return null; } }
    public void remove(Long id) { try { userDao.delete(id); } catch (SQLException e) { e.printStackTrace(); } }
}