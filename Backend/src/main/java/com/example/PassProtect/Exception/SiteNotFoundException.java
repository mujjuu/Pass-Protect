package com.example.PassProtect.Exception;

public class SiteNotFoundException extends RuntimeException {

	public SiteNotFoundException(String siteName) {
		super("Site With Name : "+siteName+" Not Found");
	}
}
