package com.example.pp_3_1_1_springboot.service;

import com.example.pp_3_1_1_springboot.dao.UserDao;
import com.example.pp_3_1_1_springboot.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDaoImpl;


    @Override
    @Transactional
    public List<User> index() {
        return userDaoImpl.index();
    }

    @Override
    @Transactional
    public User showUser(int id) {
        return userDaoImpl.showUser(id);
    }

    @Override
    @Transactional
    public void save(User user) {
        userDaoImpl.save(user);
    }

    @Override
    @Transactional
    public void update(User userUpdated) {
        userDaoImpl.update(userUpdated);
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        userDaoImpl.deleteUser(id);
    }
}
