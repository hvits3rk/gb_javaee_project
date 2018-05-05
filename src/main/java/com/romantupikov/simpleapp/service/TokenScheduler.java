package com.romantupikov.simpleapp.service;

import com.romantupikov.simpleapp.service.TokenService;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
public class TokenScheduler {

    @PersistenceContext(unitName = "webapp-persistence-unit")
    EntityManager em;

    @EJB
    TokenService tokenService;

    @Schedule(dayOfWeek = "*", hour = "*", minute = "0", second = "0", persistent = false)
    public void hourly() {
        this.tokenService.removeExpired();
    }
}
