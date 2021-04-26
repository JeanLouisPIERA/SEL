package com.microseluser.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.microseluser.dao.specs.UserSpecification;
import com.microseluser.entities.User;


@Repository
public interface IUserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User>{
	
	

}
