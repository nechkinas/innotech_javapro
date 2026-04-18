package ru.innotech.spring_db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.innotech.spring_db.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
