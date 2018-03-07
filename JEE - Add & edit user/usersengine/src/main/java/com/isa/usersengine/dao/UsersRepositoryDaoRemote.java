package com.isa.usersengine.dao;

import com.isa.usersengine.domain.User;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface UsersRepositoryDaoRemote {
    List<String> getUsersNames();
}
