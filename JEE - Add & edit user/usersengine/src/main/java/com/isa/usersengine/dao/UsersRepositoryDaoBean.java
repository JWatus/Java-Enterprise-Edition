package com.isa.usersengine.dao;

import com.isa.usersengine.domain.User;
import com.isa.usersengine.interceptors.AddUserInterceptor;
import com.isa.usersengine.interceptors.AddUserSetGenderInterceptor;
import com.isa.usersengine.repository.UsersRepository;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class UsersRepositoryDaoBean implements UsersRepositoryDao, UsersRepositoryDaoRemote {

    @Override
    @Interceptors({AddUserSetGenderInterceptor.class, AddUserInterceptor.class})
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

    @Override
    public List<String> getUsersNames() {
        List<String> names = new ArrayList<>();
        for (User user : UsersRepository.getRepository()) {
            names.add(user.getName());
        }
        return names;
    }
}
