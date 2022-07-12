package com.example.test.service;



import com.example.test.models.User;

import java.util.List;

public interface UserService {


    List<User> index ();

    User showUser (int id);

    void save (User user);

    void update (User userUpdated);

    void deleteUser (int id);
}
