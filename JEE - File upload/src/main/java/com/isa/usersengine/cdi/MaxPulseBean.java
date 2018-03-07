package com.isa.usersengine.cdi;

import com.isa.usersengine.domain.User;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class MaxPulseBean {

    public double maxPulseMan(User user) {
        return 202 - (0.55 * user.getAge());
    }

    public double maxPulseWoman(User user) {
        return 216 - (1.09 * user.getAge());
    }
}
