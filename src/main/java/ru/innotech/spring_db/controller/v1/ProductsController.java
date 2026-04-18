package ru.innotech.spring_db.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.innotech.spring_db.dto.ProductDto;
import ru.innotech.spring_db.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductsController {
    private final ProductService productService;

    @GetMapping("/{id}")
    public ProductDto getById(@PathVariable Long id) {
        return productService.getDtoById(id);
    }

    @GetMapping("/user/{id}")
    public List<ProductDto> getDtoByUserId(@PathVariable Long id) {
        return productService.getDtoByUserId(id);
    }

    @PostMapping("/user/{id}/{type_product}")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto create(
            @PathVariable Long id,
            @PathVariable("type_product") String typeProduct
    ) {
        return productService.createDto(typeProduct, id);
    }

    @DeleteMapping("/{id}/delete")
    public void deleteById(@PathVariable("id") Long id) {
        productService.remove(id);
    }

}
