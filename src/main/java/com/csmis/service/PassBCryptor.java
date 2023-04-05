package com.csmis.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PassBCryptor {

	public void BCryptEncoderFunction() {
		String rawPass = "123";
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPass = encoder.encode(rawPass);
	}

}
