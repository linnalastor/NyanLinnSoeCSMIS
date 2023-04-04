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

	@GetMapping("/invoice/paid")
	public String paidInvoice() {
		return "admin/invoice/paidinvoice";
	}

}
