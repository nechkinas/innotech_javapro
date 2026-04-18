package ru.innotech.spring_db.mapping;

import org.springframework.stereotype.Service;
import ru.innotech.spring_db.dto.ProductDto;
import ru.innotech.spring_db.dto.UserDto;
import ru.innotech.spring_db.entity.Product;
import ru.innotech.spring_db.entity.User;

@Service
public class ProductMapping {
    public ProductDto map(Product product) {
        return new ProductDto(product.getId(), product.getType(), product.getAccount(), product.getBalance());
    }
}
