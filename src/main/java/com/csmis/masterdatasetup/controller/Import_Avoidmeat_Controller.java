package com.csmis.masterdatasetup.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
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

import com.csmis.entity.Avoidmeat;
import com.csmis.entity.Staff;
import com.csmis.service.StaffService;
import com.csmis.service_interface.AvoidmeatServiceInterface;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@Controller
@RequestMapping("/admin")
public class Import_Avoidmeat_Controller {

	AvoidmeatServiceInterface avoidmeatService;
	StaffService staffService;

	@Autowired
	public Import_Avoidmeat_Controller(AvoidmeatServiceInterface theAvoidmeatService, StaffService staffService) {
		this.staffService = staffService;
		avoidmeatService = theAvoidmeatService;
	}

	@GetMapping("/show_avoidmeat")
	public String showFormForm(Model model, Authentication auth) {
		List<Avoidmeat> avoidmeat = avoidmeatService.findAll();
		model.addAttribute("avoidmeat", avoidmeat);
		model.addAttribute("status", true);
		return "/admin/Avoidmeat_Show_List";
	}

	@PostMapping("/import_avoidmeat")
	public String ImportAvoidmeat_Controller(@RequestParam("avoidmeat_file") MultipartFile file, Model model,
			Authentication auth) {

		if (file.isEmpty()) {
			model.addAttribute("message", "Please select the InvoiceReceiveBy CSV file to import.");
			model.addAttribute("status", false);
		} else {

			// parse CSV file to create a list of `User` objects
			try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

				// create csv bean reader
				CsvToBean<Avoidmeat> csvToBean = new CsvToBeanBuilder<Avoidmeat>(reader).withType(Avoidmeat.class)
						.withIgnoreLeadingWhiteSpace(true).build();

				// convert `CsvToBean` object to list of users
				List<Avoidmeat> avoidmeat = csvToBean.parse();

				avoidmeatService.saveAvoidmeat(avoidmeat);
				avoidmeat = avoidmeatService.findAll();
				model.addAttribute("avoidmeat", avoidmeat);
				model.addAttribute("status", true);

				try {
				} catch (Exception ex) {
					model.addAttribute("message", "An error occurred while saving the CSV file.");
					model.addAttribute("status", false);
				}

			} catch (Exception ex) {
				model.addAttribute("message", "An error occurred while processing the CSV file.");
				model.addAttribute("status", false);
			}

		}
		Staff loginStaff = staffService.findByID(auth.getName());
		model.addAttribute("userName", loginStaff.getName());
		return "/admin/Avoidmeat_Show_List";
	}

	@GetMapping("/AvoidmeatFormAdd")
	public String showFormForAdd(Model theModel, Authentication auth) {

		// create model attribute to bind form data
		Avoidmeat avoidmeat = new Avoidmeat();
		Staff loginStaff = staffService.findByID(auth.getName());

		theModel.addAttribute("userName", loginStaff.getName());
		theModel.addAttribute("avoidmeat", avoidmeat);

		return "/admin/Avoidmeat_Update_List";
	}

	@PostMapping("/saveAvoidmeat")
	public String saveAvoidmeat(@ModelAttribute("avoidmeat") Avoidmeat theavoidmeat, Model theModel,
			Authentication auth) {

		avoidmeatService.save(theavoidmeat);

		List<Avoidmeat> avoidmeat = avoidmeatService.findAll();
		Staff loginStaff = staffService.findByID(auth.getName());

		theModel.addAttribute("userName",loginStaff.getName());
		theModel.addAttribute("avoidmeat", avoidmeat);
		theModel.addAttribute("status", true);

		return "/admin/Avoidmeat_Show_List";
	}

	@GetMapping("/avoidmeatRemove")
	public String removeAvoidmeat(@ModelAttribute("avoidmeat") String name) {

		Avoidmeat avoidmeat = avoidmeatService.findByName(name);
		avoidmeatService.delete(avoidmeat);
		return "redirect:/admin/show_avoidmeat";
	}
}
