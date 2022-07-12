package com.example.test.dao;


import com.example.test.models.User;
import java.util.List;

public interface UserDao {

    List<User> index ();

    User showUser (int id);

    void save (User user);

    void update (User userUpdated);

    void deleteUser (int id);
}