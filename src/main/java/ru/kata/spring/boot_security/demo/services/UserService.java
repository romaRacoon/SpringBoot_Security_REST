package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User getBy(Integer id) {
        return userRepository.findById(id).get();
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void deleteBy(Integer id) {
        userRepository.deleteById(id);
    }

    public void edit(Integer id, User user) {
        System.out.println("edit");
        userRepository.save(user);
        //userRepository.update(id, user.getPassword(), user.getUsername(), user.getAge(), user.getName(),
        //        user.getLastName());
    }

    public String getAllUserRoles(Principal principal) {
        User user = findByUsername(principal.getName());
        List<String> newRoles = new ArrayList<>();
        List<String> roles = new ArrayList<>();
        Iterator<? extends GrantedAuthority> iterator = user.getAuthorities().iterator();

        while (iterator.hasNext()) {
            roles.add(String.valueOf(iterator.next()));
        }

        for (int i = 0; i < roles.size(); i++) {
            int index = roles.get(i).indexOf('_');
            int endPoint = roles.get(i).indexOf(')');
            String role = "";

            for (int j = index + 1; j < endPoint; j++) {
                role += roles.get(i).charAt(j);
            }
            newRoles.add(role);
        }

        if (newRoles.size() > 1) {
            String allRoles = "";
            for (int i = 0; i < newRoles.size(); i++) {
                allRoles += newRoles.get(i) + " ";
            }
            return allRoles;
        } else {
            return newRoles.get(0);
        }
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), user.getAuthorities());
    }
}
