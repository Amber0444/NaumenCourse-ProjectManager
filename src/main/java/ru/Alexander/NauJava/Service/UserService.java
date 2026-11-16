package ru.Alexander.NauJava.Service;


import ru.Alexander.NauJava.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);
    User saveUser(User user);
}
