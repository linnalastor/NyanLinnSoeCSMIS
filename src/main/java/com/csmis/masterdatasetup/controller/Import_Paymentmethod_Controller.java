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

import com.csmis.entity.Paymentmethod;
import com.csmis.service_interface.PaymentmethodServiceInterface;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@Controller
@RequestMapping("/admin")
public class Import_Paymentmethod_Controller {

	PaymentmethodServiceInterface paymentmethodService;

	@Autowired
	public Import_Paymentmethod_Controller(PaymentmethodServiceInterface thePaymentmethodService) {
		paymentmethodService = thePaymentmethodService;
	}

	@GetMapping("/show_Paymentmethod")
	public String showFormForm(Model model) {

		List<Paymentmethod> paymentmethod = paymentmethodService.findAll();
		System.out.println(paymentmethod);
		model.addAttribute("paymentmethod", paymentmethod);
		model.addAttribute("status", true);
		return "/admin/Paymentmethod_Show_List";
	}

	@PostMapping("/import_paymentmethod")
	public String ImportpaymentmethodController(@RequestParam("paymentmethod_file") MultipartFile file, Model model) {

		if (file.isEmpty()) {
			model.addAttribute("message", "Please select the InvoiceReceiveBy CSV file to import.");
			model.addAttribute("status", false);
		} else {

			// parse CSV file to create a list of `User` objects
			try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

				// create csv bean reader
				CsvToBean<Paymentmethod> csvToBean = new CsvToBeanBuilder<Paymentmethod>(reader)
						.withType(Paymentmethod.class).withIgnoreLeadingWhiteSpace(true).build();

				// convert `CsvToBean` object to list of users
				List<Paymentmethod> paymentmethod = csvToBean.parse();

				paymentmethodService.savePaymentmethod(paymentmethod);
				paymentmethod = paymentmethodService.findAll();
				model.addAttribute("paymentmethod", paymentmethod);
				model.addAttribute("status", true);

			} catch (Exception ex) {
				model.addAttribute("message", "An error occurred while processing the CSV file.");
				model.addAttribute("status", false);
			}

		}
		return "/admin/Paymentmethod_Show_List";
	}

	@GetMapping("/PaymentmethodFormAdd")
	public String showFormForAdd(Model theModel) {

		// create model attribute to bind form data
		Paymentmethod paymentmethod = new Paymentmethod();

		theModel.addAttribute("paymentmethod", paymentmethod);

		return "/admin/Paymentmethod_Update_List";
	}

	@PostMapping("/savePaymentmethod")
	public String savePaymentmethod(@ModelAttribute("paymentmethod") Paymentmethod thepaymentmethod, Model theModel) {

		paymentmethodService.save(thepaymentmethod);

		List<Paymentmethod> paymentmethod = paymentmethodService.findAll();
		theModel.addAttribute("paymentmethod", paymentmethod);
		theModel.addAttribute("status", true);

		return "/admin/Paymentmethod_Show_List";
	}

	@GetMapping("/paymentmethodRemove")
	public String removePaymentmethod(@ModelAttribute("paymentmethod") String name) {

		Paymentmethod paymentmethod = paymentmethodService.findByName(name);
		System.out.println(paymentmethod.getName());
		paymentmethodService.delete(paymentmethod);
		return "redirect:/admin/show_Paymentmethod";
	}
}
