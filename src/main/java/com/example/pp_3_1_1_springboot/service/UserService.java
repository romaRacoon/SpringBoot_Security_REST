package com.example.pp_3_1_1_springboot.service;



import com.example.pp_3_1_1_springboot.models.User;

import java.util.List;

public interface UserService {


    List<User> index ();

    User showUser (int id);

    void save (User user);

    void update (User userUpdated);

    void deleteUser (int id);
}
