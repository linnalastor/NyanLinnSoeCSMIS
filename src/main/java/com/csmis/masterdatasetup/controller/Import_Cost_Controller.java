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
import com.csmis.service_interface.CostServiceInterface;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@Controller
@RequestMapping("/admin")
public class Import_Cost_Controller {

	CostServiceInterface costService;

	@Autowired
	public   Import_Cost_Controller(CostServiceInterface theCostService) {
		this.costService=theCostService;
	}
	@GetMapping("/show_cost")
	public String showFormForUpdate( Model model) {
		List<Cost> cost =costService.findAll();
		  model.addAttribute("cost", cost);
	        model.addAttribute("status", true);
		  return "/admin/Cost_Show_List";
	}
//
	@PostMapping("/import_cost")
    public String import_cost(@RequestParam("cost_file") MultipartFile file, Model model) {
        // validate file
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select the Cost CSV file to import.");
            model.addAttribute("status", false);
        } else {

            // parse CSV file to create a list of `User` objects
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

                // create csv bean reader
            	CsvToBean<Cost> csvToBean = new CsvToBeanBuilder<Cost>(reader)
            			.withType(Cost.class)
            			.withIgnoreLeadingWhiteSpace(true)
                        .build();

                // convert `CsvToBean` object to list of users
                List<Cost> cost = csvToBean.parse();


                // save users in DB?
                costService.saveCosts(cost);
                   cost=costService.findAll();
                // save users list on model
                model.addAttribute("cost", cost);
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

        return "/admin/Cost_Show_List";
    }

	@GetMapping("/CostFormForUpdate")
	public String showFormForUpdate(@RequestParam("cost_file") String payer,
									Model theModel) {


		Cost cost1 = costService.findByPayer(payer);

		// set employee as a model attribute to pre-populate the form
		theModel.addAttribute("cost", cost1);

		// send over to our form
		return "/admin/Cost_Update_Form";
	}


	@PostMapping("/saveCost")
	public String saveCost(@ModelAttribute("cost") Cost thecost,Model theModel) {

		   costService.save(thecost);

			List<Cost> cost =costService.findAll();
			theModel.addAttribute("cost", cost);
			theModel.addAttribute("status", true);

        return "/admin/Cost_Show_List";
	}

	@GetMapping("/CostFormAdd")
	public String showFormForAdd(Model theModel) {

		// create model attribute to bind form data
		Cost cost = new Cost();

		theModel.addAttribute("cost", cost);

		return "/admin/Cost_Update_Form";
	}
}
