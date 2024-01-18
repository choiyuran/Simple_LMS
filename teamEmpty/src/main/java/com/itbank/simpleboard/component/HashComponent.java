package com.itbank.simpleboard.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Component
@Slf4j
public class HashComponent {
    private Random random  =  new Random();
    private String sample = "ABCDEFGHIJKLMNOPQRSTUVWXYNZ0123456789";

    public String getRandomSalt() {
//		String salt = UUID.randomUUID().toString().substring(0,8);
        String salt = "";
        for(int i = 0; i < 12; i++) {
            salt += sample.charAt(random.nextInt(sample.length()));
        }
        return salt;
    }

    public String getHash(String source, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");	// 여기작성한 알고리즘 규칙이 없는걸수도 있어서 예외처리
            md.update(salt.getBytes());
            md.update(source.getBytes());
            String hash = String.format("%0128X", new BigInteger(1, md.digest()));// 1은 양수를 의미
            return hash;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }



}
