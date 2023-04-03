package com.csmis.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
	public String import_menu(@RequestParam("pdfFile") MultipartFile pdfFile, Model model)
			throws IOException, DocumentException {

		// get the name of the imported file
		String thisweek_pdfFileName = "ThisWeek.pdf";
		String nextweek_pdfFileName = "NextWeek.pdf";

		// save pdf file to resources/pdf
		pdfService.storePdf(pdfFile);

		// convert pdf from resource/pdfs to byte string
		String thisweek_encodedPdf = pdfService.getPdfAsByteString(thisweek_pdfFileName);
		String nextweek_encodedPdf = pdfService.getPdfAsByteString(nextweek_pdfFileName);

		model.addAttribute("pdf", thisweek_encodedPdf);
		model.addAttribute("npdf", nextweek_encodedPdf);
		return "admin/admin_menu";
	}

	// Mapping for Import Staff.csv
	// Mapping for Import Staff.csv
	@PostMapping("/import_staff")
	public String StaffList(@RequestParam("staff_file") MultipartFile file, Model model) {
		boolean status = false;
		String message="";
		// validate file
		List<Staff> staff = null;
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

				// convert `CsvToBean` object to list of users
				staff = csvToBean.parse();

				// save users in DB?
				thestaffService.saveStaffs(staff);

				staff = thestaffService.findAll();

				List<StaffDetailsDTO> staffDetailsDTOList = new ArrayList<>();

				// staff details
				List<StaffDetails> staffDetailsList = staffDetailsService.getStaffDetails();

				for (Staff s : staff) {
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
						staffDetailsDTO.setDescription(" ");
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
				for (Staff st2 : staff) {
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
		staff = thestaffService.findAll();
		// add staffs attributes into model for show data
		model.addAttribute("staff", staff);
		model.addAttribute("status", status);
		model.addAttribute("message",message);
		return "admin/employee-list/stafflist";
	}

	@PostMapping("/saveStaff")
	public String saveStaff(@ModelAttribute("staff") Staff thestaff, Model theModel) {

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
			staffDetails.setDescription(" ");
			staffDetails.setEmail_status("0");
			staffDetails.setEnabled("1");
			staffDetailsService.save(staffDetails);

			authorities.setId(thestaff.getId());
			authorities.setAuthority("ROLE_EMPLOYEE");
			authoritiesService.save(authorities);

		}
		return "redirect:/admin/staff_list";
	}

//	private void startLoading() {
//	    Notiflix.Loading.dots('Loading.....');
//	}
//
//	private void stopLoading() {
//	    Notiflix.Loading.remove();
//	}

}
