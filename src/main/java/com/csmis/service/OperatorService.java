package com.csmis.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmis.dao.ConsumerListRepository;
import com.csmis.entity.ConsumerList;
import com.csmis.service_interface.OperatorServiceInterface;

@Service
public class OperatorService implements OperatorServiceInterface{

	boolean isweekend;
	ConsumerListRepository consumerListRepository;

	@Autowired
	public OperatorService(ConsumerListRepository consumerListRepository) {
		this.consumerListRepository=consumerListRepository;
	}

	@Override
	public void saveConsumerMonthlyRegistration(ConsumerList consumerList) {
		// TODO Auto-generated method stub

	}
	public List<String> getConsumerList() {
		ZoneId zone = ZoneId.systemDefault();
		LocalDate today = LocalDate.now(zone);

//		System.out.println(today.lengthOfMonth()-6);

		LocalDate day = today.withDayOfMonth(1).plusMonths(3);
		List<String> days = new ArrayList<>();
		Integer temp;

		//input first week of month
				if(day.getDayOfWeek() == DayOfWeek.SATURDAY || day.getDayOfWeek() == DayOfWeek.SUNDAY) isweekend=true;
				if (!isweekend) {
					if(day.getDayOfWeek()==DayOfWeek.MONDAY) {
						temp=day.getDayOfMonth();
						days.add(temp.toString());
						day = day.plusDays(1);
					}
					else {
						days.add("00");
					}

					if(day.getDayOfWeek()==DayOfWeek.TUESDAY) {
						temp=day.getDayOfMonth();
						days.add(temp.toString());
						day = day.plusDays(1);
					}
					else {
						days.add("00");
					}
					if(day.getDayOfWeek()==DayOfWeek.WEDNESDAY) {
						temp=day.getDayOfMonth();
						days.add(temp.toString());
						day = day.plusDays(1);
					}
					else {
						days.add("00");
					}
					if(day.getDayOfWeek()==DayOfWeek.THURSDAY) {
						temp=day.getDayOfMonth();
						days.add(temp.toString());
						day = day.plusDays(1);
					}
					else {
						days.add("00");
					}
					if(day.getDayOfWeek()==DayOfWeek.FRIDAY) {
						temp=day.getDayOfMonth();
						days.add(temp.toString());
						day = day.plusDays(1);
					}
					else {
						days.add("00");
					}
				}

				while (day.getMonthValue() == today.getMonthValue() + 3) {
					isweekend=false;
					if(day.getDayOfWeek() == DayOfWeek.SATURDAY || day.getDayOfWeek() == DayOfWeek.SUNDAY) isweekend=true;
					if(!isweekend) {
						temp=day.getDayOfMonth();
						days.add(temp.toString());
					}
					day = day.plusDays(1);
				}
				for(int i=0;i<days.size();i++) {
					if(days.get(i).length()<2) days.set(i, "0"+days.get(i));
				}
				return days;
			}

	public String getConfirmation(List<String> list) {

		String confirmation="";
		boolean checker;
		ZoneId zone = ZoneId.systemDefault();
		LocalDate today = LocalDate.now(zone);

		LocalDate day = today.withDayOfMonth(1).plusMonths(1);
		while (day.getMonthValue() == today.getMonthValue() + 1) {
			System.out.println(day.getDayOfMonth());
			isweekend=false;
			if(day.getDayOfWeek() == DayOfWeek.SATURDAY || day.getDayOfWeek() == DayOfWeek.SUNDAY) isweekend=true;
			if(!isweekend) {
				checker=false;
				for(String s: list) {
					if(Integer.parseInt(s)==day.getDayOfMonth()) checker=true;
				}

				if(checker) confirmation+="x";
				else confirmation+="1";
			}
			day = day.plusDays(1);
		}
		System.out.println(confirmation);

		return confirmation;
	}

}
