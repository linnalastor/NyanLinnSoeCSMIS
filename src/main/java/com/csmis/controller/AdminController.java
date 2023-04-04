package com.csmis.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    public String index(Authentication auth, Model theModel) {
		Staff loginStaff = staffService.findByID(auth.getName());

		theModel.addAttribute("userName",loginStaff.getName());
        return "admin/admin_datasetup";
    }



	/* Dashboard */
	@GetMapping("/dashboard")
	public String dashboard(Authentication auth, Model theModel) {
		Staff loginStaff = staffService.findByID(auth.getName());

		theModel.addAttribute("userName",loginStaff.getName());
		return "admin/admin_dashboard";
	}

	/* Menu */
	@GetMapping("/menu")
	public String menu(Model model, Authentication auth) throws IOException {
		String pdf="ThisWeek.pdf";
		String encodedPdf =pdfService.getPdfAsByteString(pdf);
        model.addAttribute("pdf", encodedPdf);

        String next_pdf="NextWeek.pdf";
		String next_encodedPdf =pdfService.getPdfAsByteString(next_pdf);
		Staff loginStaff = staffService.findByID(auth.getName());

		model.addAttribute("userName",loginStaff.getName());
        model.addAttribute("npdf", next_encodedPdf);
		model.addAttribute(model);
		return "admin/admin_menu";
	}

	/* EmployeeList */
	@GetMapping("/staff_list")
	public String staffList(Model model, Authentication auth) {
		List<Staff> staff =staffService.findAll();

		Staff loginStaff = staffService.findByID(auth.getName());

		model.addAttribute("userName",loginStaff.getName());
        model.addAttribute("staff", staff);
        model.addAttribute("status", true);
		return "admin/employee-list/stafflist";
	}

	@GetMapping("/staff_list/update")
	public String updatStaff( @RequestParam("staff") String id, Model theModel, Authentication auth){


		Staff staff = staffService.findByID(id);
		Staff loginStaff = staffService.findByID(auth.getName());
		theModel.addAttribute("userName",loginStaff.getName());
		theModel.addAttribute("staff", staff);


		return "admin/employee-list/updatestafflist";
	}



	@GetMapping("/StaffFormAdd")
	public String StaffFormForAdd(Model theModel, Authentication auth) {

		// create model attribute to bind form data
		Staff staff = new Staff();
		Staff loginStaff = staffService.findByID(auth.getName());

		theModel.addAttribute("userName",loginStaff.getName());
		theModel.addAttribute("staff", staff);

		return "admin/employee-list/updatestafflist";

	}

	@GetMapping("/invoice/paid")
	public String paidInvoice() {
		return "admin/invoice/paidinvoice";
	}

}
