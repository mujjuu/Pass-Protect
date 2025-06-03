package com.example.PassProtect.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.PassProtect.Entity.User;

import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);
	
	 @Modifying
	 @Transactional
	 @Query("DELETE FROM User u WHERE u.email = :email")
	 void deleteUserByEmail(String email);
}
