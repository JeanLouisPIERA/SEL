package com.microseladherent.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microseladherent.entities.Role;
import com.microseladherent.entities.RoleEnum;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long>{
	
	Role findByName(RoleEnum name);

}
