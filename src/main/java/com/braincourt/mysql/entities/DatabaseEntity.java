package com.braincourt.mysql.entities;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class DatabaseEntity {

    public abstract Long getId();
}
