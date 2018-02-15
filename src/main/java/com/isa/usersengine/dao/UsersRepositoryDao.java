package com.isa.usersengine.dao;

import com.isa.usersengine.domain.User;

import java.util.List;

public interface UsersRepositoryDao {

    void addUser(User user);

    User getUserById(int id);

    User getUserByLogin(String login);

    List<User> getUsersList();

}
