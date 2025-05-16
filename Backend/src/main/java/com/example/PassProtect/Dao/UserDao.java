package com.example.PassProtect.Dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.PassProtect.Entity.User;
import com.example.PassProtect.Repository.UserRepository;

@Repository
public class UserDao {

	@Autowired
	UserRepository userRepository;
	
	public User addUser(User user){
		return userRepository.save(user);
	}
	
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public User updateUserByEmail(User user) {
		return userRepository.save(user);
	}
	
	public void removeUserByEmail(User user) {
		userRepository.delete(user);
	}
}
