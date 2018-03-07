package com.isa;

import com.isa.usersengine.dao.UsersRepositoryDaoBean;

public class Main {

    public static void main(String[] args) {

        UsersRepositoryDaoBean user = new UsersRepositoryDaoBean();

        user.getUsersList().stream()
                .map(u -> u.getName())
                .forEach(System.out::println);
    }
}
