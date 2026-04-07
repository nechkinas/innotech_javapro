package ru.innotech.spring_db;

import com.sun.tools.javac.Main;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.innotech.spring_db.service.UserService;


@ComponentScan
public class SpringDbApplication {

    public static void main(String[] args) {
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
    }

}
