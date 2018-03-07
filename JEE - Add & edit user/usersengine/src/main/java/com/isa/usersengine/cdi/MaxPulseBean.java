package com.isa.usersengine.cdi;

import com.isa.usersengine.domain.Gender;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class MaxPulseBean implements MaxPulse {

    @Override
    public double getMaxPulse(Gender gender, int age) {
        if (gender.equals(Gender.MAN)) {
            return 220 - (0.55 * age);
        } else {
            return 220 - (1.09 * age);
        }
    }
}
