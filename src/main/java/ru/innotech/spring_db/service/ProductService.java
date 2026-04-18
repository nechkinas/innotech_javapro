package ru.innotech.spring_db.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.innotech.spring_db.dto.ProductDto;
import ru.innotech.spring_db.dto.UserDto;
import ru.innotech.spring_db.entity.Product;
import ru.innotech.spring_db.entity.User;
import ru.innotech.spring_db.enums.ProductType;
import ru.innotech.spring_db.mapping.ProductMapping;
import ru.innotech.spring_db.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UserService userService;
    private final ProductMapping productMapping;

    public Product create(ProductType type, User user) {
        Product product = new Product();
        product.setType(type.name());
        product.setAccount(generateAccountNumber());
        product.setBalance(0L);
        product.setUser(user);
        Product tmpProduct = productRepository.save(product);
        userService.addProduct(user, product);

        return tmpProduct;
    }

    @Transactional
    public ProductDto createDto(String type, Long id) {
        return productMapping.map( create(ProductType.valueOf(type.toUpperCase()), userService.getById(id)) );
    }

    @Transactional
    public List<ProductDto> getDtoByUserId(Long id) {
        User user = userService.getById(id);
        List<ProductDto> listProducts = new ArrayList<>();
        for(Product product:user.getProducts()){
            listProducts.add(productMapping.map(product));
        }
        return listProducts;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Set<Product> getByUserId(Long userId) {
        User user = userService.getById(userId);
        if (user != null) {
            return user.getProducts();
        }
        return Set.of();
    }

    public Product getById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Transactional
    public ProductDto getDtoById(Long id) {
        return productMapping.map(getById(id));
    }

    public void remove(Long id) {
        productRepository.deleteById(id);
    }

    private String generateAccountNumber() {
        StringBuilder sb = new StringBuilder(20);
        sb.append(ThreadLocalRandom.current().nextInt(1, 10));
        for (int i = 0; i < 19; i++) {
            sb.append(ThreadLocalRandom.current().nextInt(10));
        }
        return sb.toString();
    }



}