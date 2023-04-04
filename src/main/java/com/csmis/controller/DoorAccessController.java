package com.csmis.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.csmis.entity.Cost;
import com.csmis.entity.DailyInvoice;
import com.csmis.entity.HeadCount;
import com.csmis.entity.Lunch_Report;
import com.csmis.entity.Staff;
import com.csmis.service.DoorAccessService;
import com.csmis.service.ExcelService;
import com.csmis.service_interface.CostServiceInterface;
import com.csmis.service_interface.HeadCountServiceInterface;
import com.csmis.service_interface.InvoiceServiceInterface;
import com.csmis.service_interface.OperatorReportServiceInterface;
import com.csmis.service_interface.StaffServiceInterface;

@Controller
@RequestMapping("/admin")
public class DoorAccessController {

	@Autowired
	private ExcelService excelImporter;
	@Autowired
	DoorAccessService door_Access_Service;
	@Autowired
	StaffServiceInterface staffService;
	@Autowired
	InvoiceServiceInterface invoiceService;
	@Autowired
	CostServiceInterface costService;
	@Autowired
	HeadCountServiceInterface headCountService;
	@Autowired
	OperatorReportServiceInterface operatorReportService;

	@GetMapping("/door_access")
	public String door_access(Model model,@ModelAttribute(name = "message") String message,@ModelAttribute(name = "status") String status, Authentication auth) {
		List<HeadCount> headCount = headCountService.findAllDesc();
		Staff loginStaff = staffService.findByID(auth.getName());
		model.addAttribute("userName",loginStaff.getName());
		model.addAttribute("headCountList",headCount);
		model.addAttribute("message",message);
		if(status == "true")
			model.addAttribute("status",false);
		else 
			model.addAttribute("status",true);
		return "admin/door_access";
	}

	@PostMapping("/door_access/validation")
	public String DoorAccessValidation(@RequestParam("file") MultipartFile file,
			@RequestParam("date") String dateString, RedirectAttributes redirectAttributes) {

		LocalDate date = LocalDate.parse(dateString);
		LocalDate today = LocalDate.now();
		HeadCount headCount = headCountService.find_by_id(dateString);

		

		//if the selected date hasn't reached yet, go back to dooraccess upload file page with error message
		if(date.getYear()>today.getYear() && date.getMonthValue()>today.getMonthValue() && date.getDayOfMonth()>today.getDayOfMonth() ) {
			redirectAttributes.addFlashAttribute("message", "Selected date hasn't reached yet!");
			redirectAttributes.addFlashAttribute("status", "true");
			return "redirect:/admin/door_access";
		}else if (headCount == null) {

			// create file for incoming xlsx file
			File tempFile = null;
			try {
				tempFile = File.createTempFile("temp", null);
				tempFile.deleteOnExit();
				file.transferTo(tempFile);
			} catch (IOException e) {
			}

			redirectAttributes.addFlashAttribute("file", tempFile);

			return "redirect:/admin/door_access/import?date=" + dateString;
		}
		//if the selected date is already uploaded, go back to dooraccess upload file page with error message
		else {
			redirectAttributes.addFlashAttribute("message", "Selected date hasn already imported!");
			redirectAttributes.addFlashAttribute("status", "true");
			return "redirect:/admin/door_access";
		}
	}

	@GetMapping("/door_access/import")
	public String upload(Model model, @RequestParam("date") String dateString, RedirectAttributes redirectAttributes)
			throws IOException, ParseException {
		String[] dateArray = dateString.split(",");
		dateString = dateArray[0];

		File tempFile = (File) model.getAttribute("file");

		LocalDate date = LocalDate.parse(dateString);

		List<String> door_access_list = new ArrayList<>();
		List<String> staff_list = new ArrayList<>();
		List<Lunch_Report> lunch_report = new ArrayList<>();
		List<Cost> cost_list = costService.findAll();

		int count = 0;

		try {
			// set excel row by row list data into data
			List<List<String>> data = excelImporter.readExcel(tempFile);

			// pass data to extract door access IDs from xlsx file and get door_access_id
			// list
			door_access_list = door_Access_Service.door_log_list(data);
		} catch (Exception e) {
		}

		// get staff id list by door access IDs
		for (String s : door_access_list) {
			if (s != "") {
				String staff_id = staffService.findID_By_DoorAccessID(s);
				if (staff_id != null) {
					staff_list.add(staff_id);
					count++;
				}
			}
		}

		for (String id : staff_list) {
			// get lunch report of staff and add to list
			lunch_report.add(door_Access_Service.getLunchReport(dateString, id));
		}

		// save lunch_report into database
		operatorReportService.saveAll(lunch_report);

		// this list contain head counts
		// index 0 is for registered head counts
		// index 1 is for not registered head counts
		// index 2 is for not picked head counts
		List<Integer> CountList = door_Access_Service.getHeadCount(dateString);

		// set head counts
		HeadCount headCount = new HeadCount();
		headCount.setDate(date);
		headCount.setActual_head_count(count);
		headCount.setRegistered_head_count(CountList.get(0));
		headCount.setNot_registered_head_count(CountList.get(1));
		headCount.setNot_picked_head_count(CountList.get(2));
		// save head counts
		headCountService.save(headCount);

		// set today invoice
		DailyInvoice dailyInvoice = new DailyInvoice();
		dailyInvoice.setDate(date);
		dailyInvoice.setRegisterHeadCount(CountList.get(0));
		dailyInvoice.setActualHeadCount(count);
		dailyInvoice.setStaffCost(cost_list.get(1).getCost());
		dailyInvoice.setCompanyCost(cost_list.get(0).getCost());
		// save today invoice
		invoiceService.save(dailyInvoice);

		return "redirect:/admin/door_access";
	}
}
