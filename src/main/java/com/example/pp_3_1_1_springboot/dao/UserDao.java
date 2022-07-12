package com.example.pp_3_1_1_springboot.dao;


import com.example.pp_3_1_1_springboot.models.User;
import java.util.List;

public interface UserDao {

    List<User> index ();

    User showUser (int id);

    void save (User user);

    void update (User userUpdated);

    void deleteUser (int id);
}