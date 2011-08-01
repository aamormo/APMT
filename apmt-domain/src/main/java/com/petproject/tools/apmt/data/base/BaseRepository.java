package com.petproject.tools.apmt.data.base;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface BaseRepository<Model, Identifier extends Serializable> extends JpaRepository<Model, Identifier>, QueryDslPredicateExecutor<Model> {

}
