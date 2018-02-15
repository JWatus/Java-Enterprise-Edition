package com.isa.usersengine.dao;

import com.isa.usersengine.domain.User;
import com.isa.usersengine.repository.UsersRepository;

import java.util.List;

public class UsersRepositoryDaoBean implements UsersRepositoryDao {

    @Override
    public void addUser(User user) {

        UsersRepository.getRepository().add(user);
    }

    @Override
    public User getUserById(int id) {

        for (User user : UsersRepository.getRepository()) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User getUserByLogin(String login) {
        for (User user : UsersRepository.getRepository()) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<User> getUsersList() {
        return UsersRepository.getRepository();
    }
}

