package com.microseladherent.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microseladherent.entities.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long>{

	Optional<User> findByUsername(String username);
	
	Optional<User> findByAdresseMail(String adresseMail);
}
