package com.csmis.masterdatasetup.controller;


import java.io.BufferedReader;
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

import com.csmis.entity.InvoiceCashier;
import com.csmis.service_interface.InvoiceCashierServiceInterface;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@Controller
@RequestMapping("/admin")
public class Import_InvoiceCashier_Controller {
	
	
	InvoiceCashierServiceInterface invoiceCashierService;
	@Autowired
	public Import_InvoiceCashier_Controller(InvoiceCashierServiceInterface theInvoiceCashierService) {
		invoiceCashierService=theInvoiceCashierService;
	}
	
	@PostMapping("/import_invoiceCashier")
	public String import_InvoiceCashier(@RequestParam("invoiceCashier_file") MultipartFile file, Model model) {
	
		  if (file.isEmpty()) {
	            model.addAttribute("message", "Please select the InvoiceCashier CSV file to import.");
	            model.addAttribute("status", false);
	        } else {
	        	
	        	
	        	  // parse CSV file to create a list of `User` objects
	            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
	            	
	            	  // create csv bean reader
	            	CsvToBean<InvoiceCashier> csvToBean = new CsvToBeanBuilder<InvoiceCashier>(reader)
	            			.withType(InvoiceCashier.class)
	            			.withIgnoreLeadingWhiteSpace(true)
	                        .build();

	                // convert `CsvToBean` object to list of users
	                List<InvoiceCashier> invoiceCashier = csvToBean.parse();
	            	
	                invoiceCashierService.saveInvoiceCashiers(invoiceCashier);
	           
	            	  model.addAttribute("invoiceCashier", invoiceCashier);
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
		  return "/admin/admin_datasetup";  
	}
	
	
	
}
