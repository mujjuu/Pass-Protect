package com.example.PassProtect.Exception;

public class DecryptionException extends RuntimeException {

	public DecryptionException(String siteName) {
	    super("Failed to decrypt credentials for site: " + siteName);
	}

}
