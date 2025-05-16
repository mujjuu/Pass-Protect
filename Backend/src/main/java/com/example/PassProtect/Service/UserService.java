package com.example.PassProtect.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.PassProtect.Dao.UserDao;
import com.example.PassProtect.Entity.User;
import com.example.PassProtect.Exception.EmailNotFoundException;
import com.example.PassProtect.ResponseStructure.ResponseStructure;

@Service
public class UserService {

	@Autowired
	UserDao userDao;
	
	public ResponseEntity<ResponseStructure<User>> addUser(User user){
		userDao.addUser(user);
		ResponseStructure<User> structure= new ResponseStructure<User>();
		structure.setStatusCode(HttpStatus.CREATED.value());
		structure.setMessage("Success");
		structure.setData(user);
		return new ResponseEntity<ResponseStructure<User>>(structure,HttpStatus.CREATED);
	}
	
	public ResponseEntity<ResponseStructure<User>> findByEmail(String email){
		Optional<User> opt= userDao.findByEmail(email);
		ResponseStructure<User> structure = new ResponseStructure<User>();
		
		if(opt.isPresent()) {
			structure.setStatusCode(HttpStatus.CREATED.value());
			structure.setMessage("Success");
			structure.setData(opt.get());
			return new ResponseEntity<ResponseStructure<User>>(structure,HttpStatus.CREATED);
		}
		else {
			throw new EmailNotFoundException(email);
		}
	}
	
	public ResponseEntity<ResponseStructure<User>> updateUserByEmail(String email, User updatedUserDetails) {
        // Find the user by email
        Optional<User> opt = userDao.findByEmail(email);
        ResponseStructure<User> structure = new ResponseStructure<User>();
 
        if (opt.isEmpty()) {
            throw new EmailNotFoundException(email);
        }
        else {
        	 
        	User existingUser = opt.get();
        	 
        	if (updatedUserDetails.getUsername() != null) {
        		    existingUser.setUsername(updatedUserDetails.getUsername());
        	}
        	if (updatedUserDetails.getEmail() != null) {
        		    existingUser.setEmail(updatedUserDetails.getEmail());
        	}
        	
            User updatedUser = userDao.updateUserByEmail(existingUser);

            structure.setStatusCode(HttpStatus.OK.value());
            structure.setMessage("User updated successfully");
            structure.setData(updatedUser);
            return new ResponseEntity<>(structure, HttpStatus.OK);
        }
        
    }
	
	public ResponseEntity<ResponseStructure<User>> deleteUserByEmail(String email){
		Optional<User> opt = userDao.findByEmail(email);
		ResponseStructure<User> structure = new ResponseStructure<User>();
		
		if(opt.isPresent()) {
			userDao.removeUserByEmail(opt.get());
			structure.setStatusCode(HttpStatus.OK.value());
			structure.setMessage("Record Deleted");
			return new ResponseEntity<ResponseStructure<User>>(structure,HttpStatus.OK);
			
		}
		else {
			throw new EmailNotFoundException(email);
		}
	}
	
}
