package ru.innotech.spring_db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "type_product", length = 255, nullable = false)
    private String type;

    @Column(name = "account", length = 20, nullable = false)
    private String account;

    @Column(name = "balance")
    private Long balance;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", user=" + user.getUserName() +
                ", type='" + type + '\'' +
                ", account='" + account + '\'' +
                ", balance=" + balance +
                '}';
    }
}