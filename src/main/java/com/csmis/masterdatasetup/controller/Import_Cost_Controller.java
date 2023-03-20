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
		costService=theCostService;
	}
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

        return "/admin/admin_datasetup";
    }
	

	
}
