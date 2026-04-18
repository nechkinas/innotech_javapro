package ru.innotech.spring_db.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import ru.innotech.spring_db.entity.Product;
import ru.innotech.spring_db.entity.User;
import ru.innotech.spring_db.enums.ProductType;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainService implements CommandLineRunner {
    private final UserService userService;
    private final ProductService productService;

    @Transactional
    @Override
    public void run(String... args) {
        String alisaName = "Alisa";
        String bobName   = "Bob";

        User alisaUser = userService.create(alisaName);
        User bobUser   = userService.create(bobName);

        productService.create(ProductType.ACCOUNT, alisaUser);
        productService.create(ProductType.CARD, alisaUser);
        productService.create(ProductType.ACCOUNT, alisaUser);

        productService.create(ProductType.CARD, bobUser);
        productService.create(ProductType.ACCOUNT, bobUser);
        productService.create(ProductType.CARD, bobUser);

        List<User> list = userService.getAll();
        log.info(list.toString());
        log.info("------------");

        log.info("----- Alisa products: -------");
        Set<Product> alisaProductList = productService.getByUserId(alisaUser.getId());
        if(alisaProductList != null ) {
            log.info(alisaProductList.toString());
        }
        log.info("------------");

        log.info("----- Bob products: -------");
        Set<Product> bobProductList = productService.getByUserId(alisaUser.getId());
        if(bobProductList != null ) {
            log.info(bobProductList.toString());
        }
        log.info("------------");

        //User alisa = list.stream().filter(user -> user.getUserName().equals(alisaName)).findFirst().get();
        userService.remove(alisaUser.getId());

        //User bob = list.stream().filter(user -> user.getUserName().equals(bobName)).findFirst().get();
        userService.remove(bobUser.getId());

        list = userService.getAll();
        log.info(list.toString());
    }
}
