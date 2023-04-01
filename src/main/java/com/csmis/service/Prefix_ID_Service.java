package com.csmis.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

@Service
public class Prefix_ID_Service {

	//get prefix ID for register and report for lunch
	public String getPrefix_ID(LocalDate date) {

		int month_value = date.getMonthValue();
		String month = Integer.toString(month_value);
		if (month.length() < 2)
			month = "0" + month;
		String prefix_id = month + "/" + date.getYear();
		return prefix_id;
	}

}
