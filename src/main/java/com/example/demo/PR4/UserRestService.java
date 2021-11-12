package com.example.demo.PR4;

import com.example.demo.Model.User;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Roles.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

@Service
public class UserRestService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    public void addNewUser(User user) {
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long id) {
        User user = userRepository.findUserById(id);
        if (user == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "пользователя с таким id не существует");
        return user;
    }

    public User updateUser(Long id, User user) {
        User currentUser = getUser(id);
        currentUser.setUsername(user.getUsername());
        currentUser.setEmail(user.getEmail());
        currentUser.setPhoneNumber(user.getPhoneNumber());
        currentUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(currentUser);
        return currentUser;
    }

    public void deleteUser(Long id) {
        User currentUser = getUser(id);
        userRepository.delete(currentUser);
    }
}
