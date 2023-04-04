package com.csmis.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.csmis.dao.StaffDetailsRepository;
import com.csmis.entity.Authorities;
import com.csmis.entity.Staff;
import com.csmis.entity.StaffDetails;
import com.csmis.entity.StaffDetailsDTO;
import com.csmis.service.AuthoritiesService;
import com.csmis.service.ExcelService;
import com.csmis.service.PdfService;
import com.csmis.service.StaffDetailsService;
import com.csmis.service_interface.StaffDetailsServiceInterface;
import com.csmis.service_interface.StaffServiceInterface;
import com.itextpdf.text.DocumentException;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@Controller
@RequestMapping("/admin")
public class Import_Controller {

	@Autowired
	PdfService pdfService;
	@Autowired
	ExcelService excelService;

	StaffDetailsService staffDetailsService;
	AuthoritiesService authoritiesService;

	StaffDetailsRepository staffDetailsRepository;
	StaffDetailsServiceInterface staffDetailsServiceInterface;
	StaffServiceInterface thestaffService;

	@Autowired
	public Import_Controller(StaffServiceInterface staffService, StaffDetailsService staffDetailsService,
			StaffDetailsServiceInterface theStaffDetailsService, AuthoritiesService authoritiesService) {
		thestaffService = staffService;
		this.staffDetailsService = staffDetailsService;
		staffDetailsServiceInterface = theStaffDetailsService;
		this.authoritiesService = authoritiesService;

	}

	// Mapping For Import Menu Pdf
	@PostMapping("/import_menu")
	public String import_menu(@RequestParam("pdfFile") MultipartFile pdfFile, Model model, Authentication auth)
			throws IOException, DocumentException {

		// get the name of the imported file
		String thisweek_pdfFileName = "ThisWeek.pdf";
		String nextweek_pdfFileName = "NextWeek.pdf";

		// save pdf file to resources/pdf
		pdfService.storePdf(pdfFile);

		// convert pdf from resource/pdfs to byte string
		String thisweek_encodedPdf = pdfService.getPdfAsByteString(thisweek_pdfFileName);
		String nextweek_encodedPdf = pdfService.getPdfAsByteString(nextweek_pdfFileName);
		Staff loginStaff = thestaffService.findByID(auth.getName());

		model.addAttribute("userName",loginStaff.getName());
		model.addAttribute("pdf", thisweek_encodedPdf);
		model.addAttribute("npdf", nextweek_encodedPdf);
		return "admin/admin_menu";
	}

	// Mapping for Import Staff.csv
	@PostMapping("/import_staff")
	public String StaffList(@RequestParam("staff_file") MultipartFile file, Model model, Authentication auth) {
		boolean status = false;
		String message = "";
		// validate file
		List<Staff> staffs = null;
		if (file.isEmpty()) {
			message = "Please select the Staff CSV file to import.";
		} else {
			String extension = FilenameUtils.getExtension(file.getOriginalFilename());
			if (extension.equalsIgnoreCase("xlsx") || extension.equalsIgnoreCase("xls")) {
				try {
					file = ExcelService.convertExcelToCsv(file);
					extension = "csv";
				} catch (IOException e) {
					message = "Failed to convert Excel file to CSV file.";
				}
			}

			// parse CSV file to create a list of `User` objects
			try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

				// create csv bean reader
				CsvToBean<Staff> csvToBean = new CsvToBeanBuilder<Staff>(reader).withType(Staff.class)
						.withIgnoreLeadingWhiteSpace(true).build();

				List<Staff> originStaffList = thestaffService.findAll();
				List<Staff> updatedStaffList = new ArrayList<>();
				// convert `CsvToBean` object to list of users
				staffs = csvToBean.parse();

				for (Staff staff : originStaffList) {
					String id1 = staff.getId();
					boolean checker = false;
					for (Staff s : staffs) {
						String id2 = s.getId();
						if (id1.equals(id2)) {
							checker = true;
							staff.setStatus("Active");
							staffs.remove(s);
							break;
						}else staff.setStatus("Inactive");
					}
				}
				for(Staff staff: staffs) {
					staff.setStatus("Active");
					originStaffList.add(staff);
				}

				// save users in DB?
				thestaffService.saveStaffs(originStaffList);

				staffs = thestaffService.findAll();

				List<StaffDetailsDTO> staffDetailsDTOList = new ArrayList<>();

				// staff details
				List<StaffDetails> staffDetailsList = staffDetailsService.getStaffDetails();

				for (Staff s : staffs) {
					StaffDetailsDTO staffDetailsDTO = new StaffDetailsDTO();
					boolean checker = false;
					for (StaffDetails staffDetail : staffDetailsList) {
						if (staffDetail.getId().equals(s.getId())) {
							checker = true;
							break;
						}
					}
					if (!checker) {
						staffDetailsDTO.setId(s.getId());
						staffDetailsDTO.setPassword(s.getId());
						staffDetailsDTO.setDescription("");
						staffDetailsDTO.setEnabled("1");
						staffDetailsDTO.setEmail_status("0");
						staffDetailsDTOList.add(staffDetailsDTO);
					}
				}
				List<Authorities> newAuthoritiesList = new ArrayList<>();

				staffDetailsService.saveStaffDetails(staffDetailsDTOList);
				// authorities
				List<Authorities> AuthoritiesList = authoritiesService.getAuthorities();

				// author
				for (Staff st2 : staffs) {
					Authorities authorities = new Authorities();
					boolean checker2 = false;
					for (Authorities Authorities2 : AuthoritiesList) {
						if (Authorities2.getId().equals(st2.getId())) {
							checker2 = true;
							AuthoritiesList.remove(Authorities2);
							break;
						}
					}
					if (!checker2) {
						authorities.setId(st2.getId());
						authorities.setAuthority("ROLE_EMPLOYEE");

						newAuthoritiesList.add(authorities);
					}
					authoritiesService.saveAuthorities(newAuthoritiesList);
				}
				status = true;
			} catch (Exception ex) {
				message = "An error occurred while processing the CSV file.";
			}
		}
		staffs = thestaffService.findAll();
		Staff loginStaff = thestaffService.findByID(auth.getName());

		// add staffs attributes into model for show data
		model.addAttribute("userName",loginStaff.getName());
		model.addAttribute("staff", staffs);
		model.addAttribute("status", status);
		model.addAttribute("message", message);
		return "admin/employee-list/stafflist";
	}

	@PostMapping("/saveStaff")
	public String saveStaff(@ModelAttribute("staff") Staff thestaff, Model theModel, Authentication auth) {

		thestaffService.save(thestaff);

		Authorities authorities = new Authorities();

		StaffDetails staffDetails = null;
		try {
			staffDetails = staffDetailsService.getByID(thestaff.getId());
		} catch (Exception e1) {
		}

		if (staffDetails == null) {
			staffDetails = new StaffDetails();
			staffDetails.setId(thestaff.getId());
			staffDetails.setPassword(staffDetailsService.encodedPassword(thestaff.getId()));
			staffDetails.setDescription("");
			staffDetails.setEmail_status("0");
			staffDetails.setEnabled("1");
			staffDetailsService.save(staffDetails);

			authorities.setId(thestaff.getId());
			authorities.setAuthority("ROLE_EMPLOYEE");
			authoritiesService.save(authorities);

		}
		return "redirect:/admin/staff_list";
	}

}
