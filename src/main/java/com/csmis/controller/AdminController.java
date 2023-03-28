package com.csmis.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.csmis.entity.Staff;
import com.csmis.service.PdfService;
import com.csmis.service_interface.StaffServiceInterface;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	PdfService pdfService;
	
	
	StaffServiceInterface staffService;
	
	@Autowired
	public AdminController(StaffServiceInterface staffService) {
		this.staffService=staffService;
	}
	
	
	//Mapping for importing master data setup files page
	@GetMapping("/master_data_setup")
    public String index() {
        return "admin/admin_datasetup";
    }

	

	/* Dashboard */
	@GetMapping("/dashboard")
	public String dashboard() {
		return "admin/admin_dashboard";
	}

	/* Menu */
	@GetMapping("/menu")
	public String menu(Model model) throws IOException {
		String pdf="ThisWeek.pdf";
		String encodedPdf =pdfService.getPdfAsByteString(pdf);
        model.addAttribute("pdf", encodedPdf);
        
        String next_pdf="NextWeek.pdf";
		String next_encodedPdf =pdfService.getPdfAsByteString(next_pdf);
        model.addAttribute("npdf", next_encodedPdf);

		model.addAttribute(model);
		return "admin/admin_menu";
	}

	/* ConsumerList */
//	@GetMapping("/registered_list/today")
//	public String consumerByDays() {
//		return "admin/consumer-list/consumer_bydays";
//	}
//
//	@GetMapping("/consumer_list/by_month")
//	public String consumerMonthly() {
//		return "admin/consumer-list/consumer_monthly";
//	}
//
//	@GetMapping("/consumer_list/by_week")
//	public String consumerWeekly() {
//		return "admin/consumer-list/consumer_weekly";
//	}
//
//	@GetMapping("/consumer_list/today")
//	public String consumerToday() {
//		return "admin/consumer-list/consumer_today";
//	}

	/* Report List by day */
	@GetMapping("/report/by_days")
	public String reportList() {
		return "admin/report-list/byday-report-list/report";
	}

	@GetMapping("/report/by_days/nonregistered")
	public String nonRegister() {
		return "admin/report-list/byday-report-list/nonregister";
	}

	@GetMapping("/report/by_days/not_picked")
	public String nonConsumer() {
		return "admin/report-list/byday-report-list/nonconsumer";
	}

	/* Report List Monthly */
	@GetMapping("/report/by_month/")
	public String reportListMonth() {
		return "admin/report-list/month-report-list/report";
	}

	@GetMapping("/report/by_month/nonregisteredr")
	public String reportMonthNoRegister() {
		return "admin/report-list/month-report-list/monthnonregister";
	}

	@GetMapping("/report/by_month/not_picked")
	public String reportMonthNoConsumer() {
		return "admin/report-list/month-report-list/monthnoconsumer";
	}

	/* Report List Weekly */
	@GetMapping("/report/by_week")
	public String reportListWeek() {
		return "admin/report-list/week-report-list/weekreport";
	}

	@GetMapping("/report/by_week/nonregistered")
	public String reportWeekNoRegister() {
		return "admin/report-list/week-report-list/weeknoregister";
	}

	@GetMapping("/report/by_week/not_picked")
	public String reportWeekNoConsumer() {
		return "admin/report-list/week-report-list/weeknoconsumer";
	}

	/* Report List Today */
	@GetMapping("/report/today")
	public String reportToday() {
		return "admin/report-list/today-report-list/todayreport";
	}

	@GetMapping("/report/today/nonregistered")
	public String reportTodayNoRegister() {
		return "admin/report-list/today-report-list/todaynoregister";
	}

	@GetMapping("/report/today/not_picked")
	public String reportTodayNoConsumer() {
		return "admin/report-list/today-report-list/todaynoconsumer";
	}

	/* EmployeeList */
	@GetMapping("/staff_list")
	public String staffList(Model model) {
		List<Staff> staff =staffService.findAll();

        // save users list on model
        model.addAttribute("staff", staff);
        model.addAttribute("status", true); 
		return "admin/employee-list/stafflist";
	}

	@GetMapping("/staff_list/update")
	public String updatStaff( @RequestParam("staff") String id,
			Model theModel){
		

		Staff staff = staffService.findByID(id);
		
		// set employee as a model attribute to pre-populate the form
		theModel.addAttribute("staff", staff);
			
		
		return "admin/employee-list/updatestafflist";
	}
	
	

	@GetMapping("/StaffFormAdd")
	public String StaffFormForAdd(Model theModel) {
		
		// create model attribute to bind form data
		Staff staff = new Staff();
		
		theModel.addAttribute("staff", staff);
		
		return "admin/employee-list/updatestafflist";

	}

//	/* Invoice */
//	@GetMapping("/invoice")
//	public String invoice() {
//		return "admin/invoice/invoice";
//	}

	@GetMapping("/invoice/paid")
	public String paidInvoice() {
		return "admin/invoice/paidinvoice";
	}

}
