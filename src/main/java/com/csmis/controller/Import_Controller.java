package com.csmis.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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
	
	// for authorities
	AuthoritiesService authoritiesService;

	StaffDetailsRepository staffDetailsRepository;

	StaffDetailsService staffDetailsService;
	@Autowired
	PdfService pdfService;
	StaffDetailsServiceInterface staffDetailsServiceInterface;
	StaffServiceInterface thestaffService;
	@Autowired
	public Import_Controller(StaffServiceInterface staffService,StaffDetailsService staffDetailsService,StaffDetailsServiceInterface theStaffDetailsService, AuthoritiesService authoritiesService) {
		thestaffService=staffService;
		this.staffDetailsService=staffDetailsService;
		staffDetailsServiceInterface=theStaffDetailsService;
		this.authoritiesService = authoritiesService;

	}

	//Mapping For Import Menu Pdf
		@PostMapping("/import_menu")
		public String import_menu(@RequestParam("pdfFile") MultipartFile pdfFile,Model model) throws IOException,DocumentException {

			//get the name of the imported file
			String thisweek_pdfFileName="ThisWeek.pdf";
			String nextweek_pdfFileName="NextWeek.pdf";

			//save pdf file to resources/pdf
			pdfService.storePdf(pdfFile);

			//convert pdf from resource/pdfs to byte string
			String thisweek_encodedPdf =pdfService.getPdfAsByteString(thisweek_pdfFileName);
			String nextweek_encodedPdf =pdfService.getPdfAsByteString(nextweek_pdfFileName);


	        model.addAttribute("pdf", thisweek_encodedPdf);
	        model.addAttribute("npdf", nextweek_encodedPdf);
	        return "admin/admin_menu";
		}

	//Mapping for Import Staff.csv
		//Mapping for Import Staff.csv
		@PostMapping("/import_staff")
		public String StaffList(@RequestParam("staff_file") MultipartFile file, Model model) {
			 // validate file
	       if (file.isEmpty()) {
	           model.addAttribute("message", "Please select the Staff CSV file to import.");
	           model.addAttribute("status", false);
	       } else {

	           // parse CSV file to create a list of `User` objects
	           try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

	               // create csv bean reader
	           	CsvToBean<Staff> csvToBean = new CsvToBeanBuilder<Staff>(reader)
	           			.withType(Staff.class)
	           			.withIgnoreLeadingWhiteSpace(true)
	                       .build();

	               // convert `CsvToBean` object to list of users
	               List<Staff> staff = csvToBean.parse();

	               System.out.println(staff);
	               // save users in DB?
	               thestaffService.saveStaffs(staff);

	               staff=thestaffService.findAll();


	             List  <StaffDetailsDTO> staffDetailsDTOList=new ArrayList<>();
	               StaffDetailsDTO staffDetailsDTO= new StaffDetailsDTO();

	               List<StaffDetails> staffDetailsList=staffDetailsService.getStaffDetails();

	           	// authorities

					List<Authorities> AuthoritiesList = authoritiesService.getAuthorities();

					Authorities authorities = new Authorities();

	               for(Staff s:staff) {
	                   boolean checker=false;
	               	for(StaffDetails staffDetail:staffDetailsList) {
	               		if(staffDetail.getId().equals(s.getId())) checker=true;
	               	}
	               	if(!checker) {

	               		staffDetailsDTO.setId(s.getId());
	               		staffDetailsDTO.setPassword(staffDetailsService.encodedPassword(s.getId()));
	               		staffDetailsDTO.setEnabled("1");
	               		staffDetailsDTOList.add(staffDetailsDTO);

	               	}

	               }


	           	staffDetailsService.saveStaffDetails(staffDetailsDTOList);

	        	// author
				for (Staff st2 : staff) {
					boolean checker2 = false;
					for (Authorities Authorities2 : AuthoritiesList) {
						if (Authorities2.getId().equals(st2.getId())) {
							checker2 = true;
						break;
					}
					}
					if (!checker2) {
						authorities.setId(st2.getId());
						authorities.setAuthority("ROLE_EMPLOYEE");

						AuthoritiesList.add(authorities);
//						 authoritiesService.getAuthorities();

					}
					authoritiesService.saveAuthorities(AuthoritiesList);

				}
	           	
	           	
	               // save users list on model
	               model.addAttribute("staff", staff);
	               model.addAttribute("status", true);

	               try {
	               }catch(Exception ex) {
	               	 model.addAttribute("message", "An error occurred while saving the CSV file.");
	                    model.addAttribute("status", false);
	               }

	           } catch (Exception ex) {
	               model.addAttribute("message", "An error occurred while processing the CSV file.");
	               model.addAttribute("status", false);
	           }
	       }
			return "admin/employee-list/stafflist";
	    }

		@PostMapping("/saveStaff")
		public String saveStaff(@ModelAttribute("staff") Staff thestaff, Model theModel) {

			thestaffService.save(thestaff);
			List<Staff> staff = thestaffService.findAll();

			List<StaffDetailsDTO> staffDetailsDTOList = new ArrayList<>();
			StaffDetailsDTO staffDetailsDTO = new StaffDetailsDTO();

			List<StaffDetails> staffDetailsList = staffDetailsService.getStaffDetails();

			for (Staff s : staff) {
				boolean checker = false;
				for (StaffDetails staffDetail : staffDetailsList) {
					if (staffDetail.getId().equals(s.getId())) {
						checker = true;
						break;
					}
				}

				if (!checker) {
					staffDetailsDTO.setId(s.getId());
					staffDetailsDTO.setPassword(staffDetailsService.encodedPassword(s.getId()));
					staffDetailsDTO.setEnabled("1");
					staffDetailsDTOList.add(staffDetailsDTO);

					try {
						staffDetailsService.saveStaffDetails(staffDetailsDTOList);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}

			List<Authorities> AuthoritiesList =authoritiesService.getAuthorities();

			Authorities authorities = new Authorities();

			// author
			for (Staff st2 : staff) {
				boolean checker2 = false;
				for (Authorities Authorities2 : AuthoritiesList) {
					if (Authorities2.getId().equals(st2.getId())) {
						checker2 = true;
						break;
					}

				}
				if (!checker2) {
					authorities.setId(st2.getId());
					authorities.setAuthority("ROLE_EMPLOYEE");

					AuthoritiesList.add(authorities);
//					 authoritiesService.getAuthorities();
					authoritiesService.saveAuthorities(AuthoritiesList);

				}
			}

			theModel.addAttribute("staff", staff);
			theModel.addAttribute("status", true);

			return "admin/employee-list/stafflist";
		}

	}
