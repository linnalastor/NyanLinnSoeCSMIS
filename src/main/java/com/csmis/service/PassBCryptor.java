package com.csmis.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PassBCryptor {

	public void BCryptEncoderFunction() {
		String rawPass = "wearebombs2023";
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPass = encoder.encode(rawPass);
		System.out.println(encodedPass);
	}

}
