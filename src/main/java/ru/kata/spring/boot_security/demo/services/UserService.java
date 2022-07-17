package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            throw new UsernameNotFoundException("Username not found");
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), user.getAuthorities());
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User getBy(Integer id) {
        return userRepository.getById(id);
    }

    public void save(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        String result = encoder.encode(user.getPassword());
        user.setPassword(result);
        userRepository.save(user);
    }

    public void deleteBy(Integer id) {
        userRepository.deleteById(id);
    }

    public void edit(Integer id, User user) {
        userRepository.update(id, user.getPassword(), user.getUsername());
    }
}
