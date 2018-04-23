package com.romantupikov.simpleapp.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

abstract class AbstractDAO {

    @PersistenceContext(unitName = "persistence-unit")
    EntityManager em;
}
