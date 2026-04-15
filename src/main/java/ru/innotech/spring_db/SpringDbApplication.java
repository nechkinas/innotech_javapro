package ru.innotech.spring_db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SpringDbApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDbApplication.class);
        /*
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(SpringDbApplication.class);

        UserService service = context.getBean(UserService.class);

        service.create("Alice");
        service.create("Bob");

        System.out.println("All users: " + service.getAll());
        System.out.println("User 1: " + service.getOne(1L));
        System.out.println("User 2: " + service.getOne(2L));

        service.remove(1L);
        System.out.println("After delete: " + service.getAll());

        context.close();
         */
    }

}
