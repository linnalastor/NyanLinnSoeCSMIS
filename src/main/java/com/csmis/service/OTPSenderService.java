package com.csmis.service;

import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Properties;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public class OTPSenderService {


	private static final int DIGITS=6;
	private static final int TIME_STEP_SECONDS = 30;
	private static final String HMAC_ALGORITHM ="HmacSHA1";

	private SecretKey secretKey;

	public OTPSenderService() throws NoSuchAlgorithmException {

		KeyGenerator keyGeneretor = KeyGenerator.getInstance(HMAC_ALGORITHM);

		secretKey = keyGeneretor.generateKey() ;

	}

	public String generateOTP() throws InvalidKeyException, NoSuchAlgorithmException {
        long counter = Instant.now().getEpochSecond() / TIME_STEP_SECONDS;

        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(counter);
        byte[] counterBytes = buffer.array();

        Mac mac = Mac.getInstance(HMAC_ALGORITHM);
        mac.init(secretKey);
        byte[] hashBytes = mac.doFinal(counterBytes);

        int offset = hashBytes[hashBytes.length - 1] & 0xf;
        int truncatedHash = ((hashBytes[offset] & 0x7f) << 24) | ((hashBytes[offset + 1] & 0xff) << 16) | ((hashBytes[offset + 2] & 0xff) << 8) | (hashBytes[offset + 3] & 0xff);
        truncatedHash %= Math.pow(10, DIGITS);

        String otp = String.format("%0" + DIGITS + "d", truncatedHash);

        return otp;
    }

	public void sendOTP(String recipientEmail, String otp) {
		String subject = "Your OTP for forget password";
		String messageText = "Your OTP is " + otp;

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("swamhtetnaing.cu@gmail.com", "jassqinajcvwjcrj");
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("swamhtetnaing.cu@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
			message.setSubject(subject);
			message.setText(messageText);
			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}


}