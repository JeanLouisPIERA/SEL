package com.microseluser.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.microseluser.entities.User;

@Repository
public interface IUserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

}
