package com.csmis.masterdatasetup.controller;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.time.LocalDate;
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

  	@PostMapping("/saveHoliday")
  	public String save(@ModelAttribute("holiday")HolidayDTO holidaydto) throws ParseException {
  		Holiday holiday=new Holiday();
  		holiday.HolidayDTO(holidaydto.getDate(), holidaydto.getDescription());
  		holidayService.saveHoliday(holiday);
  		return "redirect:/admin/show_holiday";
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

    		  CsvToBean<HolidayDTO> csvToBean=new  CsvToBeanBuilder<HolidayDTO>(reader)
    				  .withType(HolidayDTO.class)
          			.withIgnoreLeadingWhiteSpace(true)
                      .build();
    		  // convert `CsvToBean` object to list of users
    		  List<HolidayDTO> holiday=csvToBean.parse();
    		  // save users in DB?
    		  holidayService.deleteAll();

    		  holidayService.saveHolidays(holiday);
    		  

    	  }catch (Exception ex) {
                model.addAttribute("message", "An error is occurred while processing the CSV file.");
                model.addAttribute("status", false);
            }
      }
  	 return "redirect:/admin/show_holiday";  	}



	@GetMapping("/HolidayFormForUpdate")
	public String showFormForUpdate(@RequestParam("holiday_date") String thedate,
									Model theModel) {
	    LocalDate date = LocalDate.parse(thedate);
	    System.out.println("Parsed Date"+date);

		Holiday holiday = holidayService.findByDate(date);
		System.out.println("here");
		HolidayDTO hdto= new HolidayDTO();
		hdto.setDate(holiday.getDate().toString());
		hdto.setDescription(holiday.getDescription());

		theModel.addAttribute("holiday", hdto);

		return "/admin/Holiday_Update_List";
	}
 	  	}