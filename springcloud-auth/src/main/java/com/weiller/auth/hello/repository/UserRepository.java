package com.weiller.auth.hello.repository;

import com.weiller.auth.hello.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {

}

