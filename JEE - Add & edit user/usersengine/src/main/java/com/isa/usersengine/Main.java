package com.isa.usersengine;

import com.isa.usersengine.dao.UsersRepositoryDao;
import com.isa.usersengine.dao.UsersRepositoryDaoBean;
import com.isa.usersengine.domain.User;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        UsersRepositoryDao usersRepositoryDao = new UsersRepositoryDaoBean();
        for (User user : usersRepositoryDao.getUsersList()) {
            System.out.println("Name: " + user.getName());
        }
    }
}
