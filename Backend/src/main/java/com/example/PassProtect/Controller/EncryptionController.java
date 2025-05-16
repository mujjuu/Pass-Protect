package com.example.PassProtect.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.PassProtect.Service.EncryptionService;



@RestController
@RequestMapping("/api")
public class EncryptionController{
	
	@Autowired
	EncryptionService encryptionService;
	
	@PostMapping("/encrypt")
	public String encryptPassword(@RequestBody Map<String,String> request)throws Exception
	{
		String password = request.get("password");
		String key = request.get("key");
		
		return encryptionService.encrypt(password, key);
	}
	
}
