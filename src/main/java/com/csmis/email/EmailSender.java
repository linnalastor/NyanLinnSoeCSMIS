package com.csmis.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSender implements CommandLineRunner{

	@Autowired
    private JavaMailSender javaMailSender;


	public void sendEmail(String subject,String message,String[] to) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);

        msg.setSubject(subject);
        msg.setText(message);

        javaMailSender.send(msg);
    }

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub

	}

}
