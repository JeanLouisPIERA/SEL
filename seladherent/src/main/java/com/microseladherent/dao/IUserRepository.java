package com.microseladherent.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microseladherent.entities.User;
import com.microseladherent.entities.UserStatutEnum;

@Repository
public interface IUserRepository extends JpaRepository<User, Long>{

	Optional<User> findByUsername(String username);
	
	Optional<User> findByEmail(String email);
	
	Optional<User> findByUsernameAndPassword(String username, String password);
	
	Optional<User> findByEmailAndPassword(String email, String password);
	
	Optional<List<User>> findByDateAdhesion(LocalDate dateAdhesion);
	
	Optional<User> findByIdAndStatut(Long id, UserStatutEnum userStatutEnum);

}
