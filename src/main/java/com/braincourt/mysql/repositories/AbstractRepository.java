package com.braincourt.mysql.repositories;

import com.braincourt.mysql.entities.DatabaseEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AbstractRepository<T extends DatabaseEntity> extends CrudRepository<T, String> {
}
