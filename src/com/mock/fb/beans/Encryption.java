package com.mock.fb.beans;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;


public class Encryption {
	public String encrypt(String password) throws NoSuchAlgorithmException{
		MessageDigest sha = MessageDigest.getInstance("SHA-1");
		sha.update(password.getBytes());
		byte byteData[] = sha.digest();

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();

	}
}
