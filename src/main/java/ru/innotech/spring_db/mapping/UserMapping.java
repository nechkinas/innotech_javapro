package ru.innotech.spring_db.mapping;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.innotech.spring_db.dto.ProductDto;
import ru.innotech.spring_db.dto.UserDto;
import ru.innotech.spring_db.entity.Product;
import ru.innotech.spring_db.entity.User;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserMapping {
    private final ProductMapping productMapping;

    public UserDto map(User user) {
//        Set<ProductDto> products = new HashSet<>();
//        for (Product product : user.getProducts()) {
//            products.add(productMapping.map(product));
//        }
        return new UserDto(user.getId(), user.getUserName());
    }
}
