package ru.innotech.spring_db.controller;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.innotech.spring_db.dto.ProductDto;
import ru.innotech.spring_db.dto.UserDto;
import ru.innotech.spring_db.entity.Product;
import ru.innotech.spring_db.enums.ProductType;
import ru.innotech.spring_db.service.ProductService;
import ru.innotech.spring_db.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductsController {
    private final ProductService productService;

    @GetMapping("/product/{id}")
    public ProductDto getById(@PathVariable Long id) {
        return productService.getDtoById(id);
    }

    @GetMapping("/user/{id}/products")
    public List<ProductDto> getDtoByUserId(@PathVariable Long id) {
        return productService.getDtoByUserId(id);
    }

    @PostMapping("/user/{id}/product/{type_product}")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto create(
            @PathVariable Long id,
            @PathVariable("type_product") String typeProduct
    ) {
        return productService.createDto(typeProduct, id);
    }

    @DeleteMapping("/product/{id}")
    public void deleteById(@PathVariable Long id) {
        productService.remove(id);
    }

}
