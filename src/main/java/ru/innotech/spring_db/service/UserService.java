package ru.innotech.spring_db.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.innotech.spring_db.dto.UserDto;
import ru.innotech.spring_db.entity.Product;
import ru.innotech.spring_db.entity.User;
import ru.innotech.spring_db.mapping.UserMapping;
import ru.innotech.spring_db.repository.UserRepository;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapping userMapping;

    public User create(String name) {
        User user = new User();
        user.setUserName(name);
        user.setProducts(new HashSet<>());
        return userRepository.save(user);
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

    public void addProduct(User user, Product product) {
        user.getProducts().add(product);
        userRepository.save(user);
    }

    @Transactional
    public UserDto getDtoById(Long id) {
        return userMapping.map(getById(id));
    }

    public UserDto createDto(String userName) {
        return userMapping.map(create(userName));
    }
}