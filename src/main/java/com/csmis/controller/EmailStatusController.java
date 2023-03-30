package com.csmis.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.csmis.service.EmailStatusService;

@Controller
public class EmailStatusController {
	private EmailStatusService emailStatusService;

	@Autowired
	public EmailStatusController(EmailStatusService theEmailStatusService) {
		emailStatusService = theEmailStatusService;
	}

	@RequestMapping("/toggle-switch")
	@ResponseBody
	public void handleToggleSwitch(@RequestParam("isChecked") boolean isChecked,Authentication auth) {
		String staffId = auth.getName();
//		System.out.println("staff id is >>>>>>"+staffId);
//		System.out.println("Toggle switch is " + (isChecked ? "on" : "off"));
		// JDBC or an ORM to save the value of the toggle switch to the database
		// Example using JDBC:
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/csmis", "CSMIS", "CSMIS")) {
			String sql = "UPDATE staff_detail SET email_status = ? WHERE id=?";

			PreparedStatement statement = conn.prepareStatement(sql);
			 statement.setBoolean(1, isChecked);
			 statement.setString(2, staffId);

			 int rowsUpdated = statement.executeUpdate();
			 //System.out.println(rowsUpdated + " rows updated successfully.");



		} catch (SQLException e) {

			System.out.println("error");
			e.printStackTrace();
		}
	}
}
