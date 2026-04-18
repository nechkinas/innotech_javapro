package ru.innotech.spring_db.mapping;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.innotech.spring_db.dto.UserDto;
import ru.innotech.spring_db.entity.User;

@Service
@RequiredArgsConstructor
public class UserMapping {
    private final ProductMapping productMapping;

    public UserDto map(User user) {
        return new UserDto(user.getId(), user.getUserName());
    }
}
