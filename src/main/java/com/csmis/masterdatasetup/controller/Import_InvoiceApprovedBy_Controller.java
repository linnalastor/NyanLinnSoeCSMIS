package com.csmis.masterdatasetup.controller;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.csmis.entity.Cost;
import com.csmis.entity.InvoiceApprovedBy;
import com.csmis.entity.Restaurant;
import com.csmis.service_interface.InvoiceApprovedByServiceInterface;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
@Controller
@RequestMapping("/admin")
public class Import_InvoiceApprovedBy_Controller {
	
	InvoiceApprovedByServiceInterface invoiceApprovedByService;
	@Autowired
	public  Import_InvoiceApprovedBy_Controller(InvoiceApprovedByServiceInterface theInvoiceApprovedByService)
	{	
		invoiceApprovedByService=theInvoiceApprovedByService;
	}
	@GetMapping("/show_invoiceApprovedBy")
	public String showFormForUpdate( Model model) {
		List<InvoiceApprovedBy> invoiceApprovedBy =invoiceApprovedByService.findAll();
		  model.addAttribute("invoiceApprovedBy", invoiceApprovedBy);
	        model.addAttribute("status", true); 
		  return "/admin/InvoiceApprovedBy_Show_List";			
	}
	
	
	
	@PostMapping("/import_invoiceApprovedBy")
	public String import_InvoiceApprovedBy(@RequestParam("invoiceApprovedBy_file") MultipartFile file, Model model) {
	
		  if (file.isEmpty()) {
	            model.addAttribute("message", "Please select the InvoiceApprovedBy CSV file to import.");
	            model.addAttribute("status", false);
	        } else {
	        	
	        	
	        	  // parse CSV file to create a list of `User` objects
	            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
	            	
	            	  // create csv bean reader
	            	CsvToBean<InvoiceApprovedBy> csvToBean = new CsvToBeanBuilder<InvoiceApprovedBy>(reader)
	            			.withType(InvoiceApprovedBy.class)
	            			.withIgnoreLeadingWhiteSpace(true)
	                        .build();

	                // convert `CsvToBean` object to list of users
	                List<InvoiceApprovedBy> invoiceApprovedBy = csvToBean.parse();
	            	
	            	invoiceApprovedByService.saveInvoiceApprovedBys(invoiceApprovedBy);
	                        invoiceApprovedBy=invoiceApprovedByService.findAll();	            	  model.addAttribute("invoiceApprovedBy", invoiceApprovedBy);
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
		 	 return "/admin/InvoiceApprovedBy_Show_List";  
	}

	@GetMapping("/InvoiceApprovedByFormForUpdate")
	public String showFormForUpdate(@RequestParam("invoiceApprovedBy_file") String name,
									Model theModel) {
	    
		
		InvoiceApprovedBy invoiceApprovedBy = invoiceApprovedByService.findByName(name);
		
		// set employee as a model attribute to pre-populate the form
		theModel.addAttribute("invoiceApprovedBy", invoiceApprovedBy);
		
		// send over to our form
		return "/admin/InvoiceApprovedBy_Update_List";			
	}

	@PostMapping("/saveInvoiceApprovedBy")
	public String saveInvoiceApprovedBy(@ModelAttribute("invoiceApprovedBy") InvoiceApprovedBy theinvoiceApprovedBy,Model theModel) {
		
		invoiceApprovedByService.save(theinvoiceApprovedBy);
	
			List<InvoiceApprovedBy> invoiceApprovedBy =invoiceApprovedByService.findAll();
			theModel.addAttribute("invoiceApprovedBy", invoiceApprovedBy);
			theModel.addAttribute("status", true); 
		   
        return "/admin/invoiceApprovedBy_Show_List";
	}

	@GetMapping("/InvoiceApprovedByFormAdd")
	public String showFormForAdd(Model theModel) {
		
		// create model attribute to bind form data
		InvoiceApprovedBy invoiceApprovedBy = new InvoiceApprovedBy();
		
		theModel.addAttribute("invoiceApprovedBy", invoiceApprovedBy);
		
		return "/admin/InvoiceApprovedBy_Update_List";
	}
	@GetMapping("/invoiceApprovedByRemove")
	public String removeInvoiceApprovedBy(@ModelAttribute("invoiceApprovedBy") String name) {
		InvoiceApprovedBy invoiceApprovedBy = invoiceApprovedByService.findByName(name);
		System.out.println(invoiceApprovedBy.getName());
		invoiceApprovedByService.delete(invoiceApprovedBy);
		return "redirect:/admin/show_invoiceApprovedBy";
	}
}
