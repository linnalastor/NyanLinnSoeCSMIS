package com.csmis.masterdatasetup.controller;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.DateTimeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.csmis.entity.Cost;
import com.csmis.entity.Holiday;
import com.csmis.entity.HolidayDTO;
import com.csmis.service_interface.HolidayServiceInterface;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@Controller
@RequestMapping("/admin")
public class Import_Holiday_Controller {
	
  	HolidayServiceInterface holidayService;
  	@Autowired
  	public Import_Holiday_Controller(HolidayServiceInterface theholidayService) {
  		holidayService=theholidayService;
  		
  	}
  	

	@GetMapping("/show_holiday")
	public String showFormForUpdate( Model model) {
		List<Holiday> holiday =holidayService.findAll();
		  model.addAttribute("holiday", holiday);
	        model.addAttribute("status", true); 
	        return "/admin/Holiday_Show_List";  	
	}
  	
  	@PostMapping("/import_holiday")
  	public String import_holiday(@RequestParam("holiday_file")MultipartFile file, Model model) {
  	
  	  if (file.isEmpty()) {
          model.addAttribute("message", "Please select the Holiday CSV file to import.");
          model.addAttribute("status", false);
      } else {
    	  try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
    		  
  //  		  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//s  		  Date date = dateFormat.parse(import_holiday(file, model));
//    
//    		  DateTimeConverter dateConverter = new DateConverter();
//    		  dateConverter.setPattern("<>");
//    		  ConvertUtils.register(dateConverter, Holiday.class);

    		  CsvToBean<HolidayDTO> csvToBean=new  CsvToBeanBuilder<HolidayDTO>(reader)
    				  .withType(HolidayDTO.class)
          			.withIgnoreLeadingWhiteSpace(true)
                      .build();
    		  // convert `CsvToBean` object to list of users
    		  List<HolidayDTO> holiday=csvToBean.parse();
    		  // save users in DB?
    		  holidayService.saveHolidays(holiday);
    		
    		   // save users list on model
    		  model.addAttribute("holiday", holiday);
              model.addAttribute("status", true); 
              
              
            
              
           try {
                 }catch(Exception ex) {
                	 model.addAttribute("message", "An error occurred while saving the CSV file.");
                     model.addAttribute("status", false); 
                }

            } catch (Exception ex) {
                model.addAttribute("message", "An error is occurred while processing the CSV file.");
                model.addAttribute("status", false);
            } 
    	  
 
      }
  	 return "/admin/Holiday_Show_List";  	}
  	
  	

	@GetMapping("/HolidayFormForUpdate")
	public String showFormForUpdate(@RequestParam("holiday_file") Date thedate,
									Model theModel) {
	    
		
		Holiday holiday2 = holidayService.findByDate(thedate);
		
		// set Holiday as a model attribute to pre-populate the form
		theModel.addAttribute("holiday", holiday2);
		
		// send over to our form
		return "/admin/Holiday_Update_List";			
	}
 	  	}
