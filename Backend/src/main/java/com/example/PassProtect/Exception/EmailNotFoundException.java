package com.example.PassProtect.Exception;

public class EmailNotFoundException extends RuntimeException {

	public EmailNotFoundException(String email) {
		super("Student with Email : "+email+ " , Not Found ");
	}
}

