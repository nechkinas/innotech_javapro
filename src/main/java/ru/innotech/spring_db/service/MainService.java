package ru.innotech.spring_db.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import ru.innotech.spring_db.entity.User;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainService implements CommandLineRunner {
    private final UserService userService;

    @Override
    public void run(String... args) {
        String alisaName = "Alisa";
        String bobName = "Bob";

        userService.create(alisaName);
        userService.create(bobName);

        List<User> list = userService.getAll();
        log.info(list.toString());
        log.info("------------");

        User alisa = list.stream().filter(user -> user.getUserName().equals(alisaName)).findFirst().get();
        userService.remove(alisa.getId());

        User bob = list.stream().filter(user -> user.getUserName().equals(bobName)).findFirst().get();
        userService.remove(bob.getId());

        list = userService.getAll();
        log.info(list.toString());

    }
}
