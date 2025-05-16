package com.example.PassProtect.Service;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

@Service
public class EncryptionService {

    public SecretKeySpec getAESKey(String password, byte[] salt) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
        byte[] keyBytes = factory.generateSecret(spec).getEncoded();
        return new SecretKeySpec(Arrays.copyOf(keyBytes, 16), "AES");
    }

    public String[] encrypt(String plaintext, String password) throws Exception {
        byte[] ivBytes = new byte[16];
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(ivBytes);
        new SecureRandom().nextBytes(salt);

        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        SecretKeySpec keySpec = getAESKey(password, salt);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] encrypted = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));

        return new String[] {
            Base64.getEncoder().encodeToString(encrypted),
            Base64.getEncoder().encodeToString(ivBytes),
            Base64.getEncoder().encodeToString(salt)
        };
    }

    public String decrypt(String encryptedBase64, String password, String ivBase64, String saltBase64) throws Exception {
        byte[] encrypted = Base64.getDecoder().decode(encryptedBase64);
        byte[] iv = Base64.getDecoder().decode(ivBase64);
        byte[] salt = Base64.getDecoder().decode(saltBase64);

        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        SecretKeySpec keySpec = getAESKey(password, salt);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] decrypted = cipher.doFinal(encrypted);

        return new String(decrypted, StandardCharsets.UTF_8);
    }
}
