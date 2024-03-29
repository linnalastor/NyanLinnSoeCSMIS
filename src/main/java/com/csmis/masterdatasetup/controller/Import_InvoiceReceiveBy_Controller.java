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

import com.csmis.entity.InvoiceReceiveBy;
import com.csmis.service_interface.InvoiceReceiveByServiceInterface;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@Controller
@RequestMapping("/admin")
public class Import_InvoiceReceiveBy_Controller {

	InvoiceReceiveByServiceInterface invoiceReceiveByService;

	@Autowired
	public  Import_InvoiceReceiveBy_Controller(InvoiceReceiveByServiceInterface theInvoiceReceiveByService) {

		invoiceReceiveByService=theInvoiceReceiveByService;
	}

	@GetMapping("/show_invoiceReceiveBy")
	public String showFormForUpdate( Model model) {
		List<InvoiceReceiveBy> invoiceReceiveBy =invoiceReceiveByService.findAll();
		  model.addAttribute("invoiceReceiveBy", invoiceReceiveBy);
	        model.addAttribute("status", true);
			return	"/admin/InvoiceReceiveBy_Show_List";
	}



	@PostMapping("/import_invoiceReceiveBy")
	public String import_InvoiceReceiveBy(@RequestParam("invoiceReceiveBy_file") MultipartFile file, Model model) {

		  if (file.isEmpty()) {
	            model.addAttribute("message", "Please select the InvoiceReceiveBy CSV file to import.");
	            model.addAttribute("status", false);
	        } else {


	        	  // parse CSV file to create a list of `User` objects
	            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

	            	  // create csv bean reader
	            	CsvToBean<InvoiceReceiveBy> csvToBean = new CsvToBeanBuilder<InvoiceReceiveBy>(reader)
	            			.withType(InvoiceReceiveBy.class)
	            			.withIgnoreLeadingWhiteSpace(true)
	                        .build();

	                // convert `CsvToBean` object to list of users
	                List<InvoiceReceiveBy> invoiceReceiveBy = csvToBean.parse();

	                invoiceReceiveByService.saveInvoiceReceiveBys(invoiceReceiveBy);
	           invoiceReceiveBy=invoiceReceiveByService.findAll();
	            	  model.addAttribute("invoiceReceiveBy", invoiceReceiveBy);
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
		return	"/admin/InvoiceReceiveBy_Show_List";
	}
	@GetMapping("/InvoiceReceiveByFormAdd")
	public String showFormForAdd(Model theModel) {

		// create model attribute to bind form data
		InvoiceReceiveBy invoiceReceiveBy = new InvoiceReceiveBy();

		theModel.addAttribute("invoiceReceiveBy", invoiceReceiveBy);

		return "/admin/InvoiceReceiveBy_Update_List";
	}
	@PostMapping("/saveInvoiceReceiveBy")
	public String saveInvoiceReceiveBy(@ModelAttribute("invoiceReceiveBy") InvoiceReceiveBy theinvoiceReceiveBy,Model theModel) {

		invoiceReceiveByService.save(theinvoiceReceiveBy);

			List<InvoiceReceiveBy> invoiceReceiveBy =invoiceReceiveByService.findAll();
			theModel.addAttribute("invoiceReceiveBy", invoiceReceiveBy);
			theModel.addAttribute("status", true);

        return "/admin/InvoiceReceiveBy_Show_List";
	}
	@GetMapping("/invoiceReceiveByRemove")
	public String removeInvoiceReceiveBy(@ModelAttribute("invoiceReceiveBy") String name) {
		InvoiceReceiveBy invoiceReceiveBy = invoiceReceiveByService.findByName(name);
		invoiceReceiveByService.delete(invoiceReceiveBy);
		return "redirect:/admin/show_invoiceReceiveBy";
	}

}
