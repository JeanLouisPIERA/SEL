package com.microseluser.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.microseluser.entities.Role;

@Repository
public interface IRoleRepository extends JpaRepository<Role, String> {

}
