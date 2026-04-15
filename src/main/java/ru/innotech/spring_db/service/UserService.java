package ru.innotech.spring_db.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.innotech.spring_db.entity.User;
import ru.innotech.spring_db.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void create(String name) {
        User user = new User();
        user.setUserName(name);
        userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void remove(Long id) {
        userRepository.deleteById(id);
    }

}