package com.csmis.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.csmis.entity.Staff;
import com.csmis.service.PdfService;
import com.csmis.service_interface.StaffServiceInterface;
import com.itextpdf.text.DocumentException;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@Controller
@RequestMapping("/admin")
public class Import_Controller {

	@Autowired
	PdfService pdfService;

	StaffServiceInterface thestaffService;
	@Autowired
	public Import_Controller(StaffServiceInterface staffService) {
		thestaffService=staffService;
	}

	//Mapping For Import Menu Pdf
	@PostMapping("/import_menu")
	public String import_menu(@RequestParam("pdfFile") MultipartFile pdfFile,Model model) throws IOException,DocumentException {
		//save pdf file to resources/pdf
		pdfService.storePdf(pdfFile);

		//convert pdf from resource/pdfs to byte string
		String encodedPdf =pdfService.getPdfAsByteString("ThisWeek.pdf");

		String nextweek_encodedPdf =pdfService.getPdfAsByteString("NextWeek.pdf");

        model.addAttribute("pdf", encodedPdf);
        model.addAttribute("npdf", nextweek_encodedPdf);
        return "admin/admin_menu";
	}

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
              

               // save users in DB?
               thestaffService.saveStaffs(staff);
               staff=thestaffService.findAll();

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
}
