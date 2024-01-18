package com.itbank.simpleboard.component;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class HashComponent {
    public String getRandomSalt() {
        String salt = null;
        SecureRandom ran = new SecureRandom();	// 보안에 활용할 수 있는 수준의 랜덤
        byte[] arr = new byte[16];
        ran.nextBytes(arr);
        salt = new String(Base64.getEncoder().encode(arr));
        return salt;
    }

    public String getHash(String source, String salt) {
        String hash = null;
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes());
            md.update(source.getBytes());
            hash = String.format("%0128X", new BigInteger(1, md.digest()));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash;
    }
}
