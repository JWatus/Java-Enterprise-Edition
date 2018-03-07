package com.isa.usersengine.cdi;

import com.isa.usersengine.dao.UsersRepositoryDao;
import com.isa.usersengine.domain.User;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import java.util.Random;

@ApplicationScoped
public class RandomUserCDIApplicationDaoBean implements RandomUserCDIApplicationDao {

    int index = Math.abs(new Random().nextInt()) % 3;

    @EJB
    UsersRepositoryDao usersRepositoryDao;

    @Override
    public User getRandomUser() {
        return usersRepositoryDao.getUsersList().get(index);
    }
}
