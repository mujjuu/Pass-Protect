package com.example.PassProtect.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.PassProtect.Entity.User;
import com.example.PassProtect.ResponseStructure.ResponseStructure;
import com.example.PassProtect.Service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	UserService userService;

	@PostMapping("/add")
	public ResponseEntity<ResponseStructure<User>> addUser(@RequestBody User user){
		return userService.addUser(user);
	}
	
	@GetMapping("/getByEmail/{email}")
	public ResponseEntity<ResponseStructure<User>> findByEmail(@PathVariable String email){
		return userService.findByEmail(email);
	}
	
	@PutMapping("/update/{email}")
	public ResponseEntity<ResponseStructure<User>> updateUser(@RequestBody User updatedUserDetails,@PathVariable String email){
		return userService.updateUserByEmail(email, updatedUserDetails);
	}
	
	@DeleteMapping("/delete/{email}")
	public ResponseEntity<ResponseStructure<User>> deleteUserByEmail(@PathVariable String email){
		return userService.deleteUserByEmail(email);
	}
}
