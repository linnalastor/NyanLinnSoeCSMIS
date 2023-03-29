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
import com.csmis.entity.Restaurant;
import com.csmis.service_interface.RestaurantServiceInterface;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@Controller
@RequestMapping("/admin")
public class Import_Restaurant_Controller {
	
	RestaurantServiceInterface restaurantService;
	@Autowired
	public Import_Restaurant_Controller(RestaurantServiceInterface theRestaurantService) {
		restaurantService=theRestaurantService;
	}
	@GetMapping("/show_restaurant")
	public String showFormForUpdate( Model model) {
		List<Restaurant> restaurant =restaurantService.findAll();
		  model.addAttribute("restaurant", restaurant);
	        model.addAttribute("status", true); 
	        return "/admin/Restaurant_Show_List";  		
	}
	
	@GetMapping("/restaurantRemove")
	public String removeRestaurant(@ModelAttribute("restaurant") String name) {
		Restaurant restaurant = restaurantService.findByName(name);
		System.out.println(restaurant.getName());
		restaurantService.delete(restaurant);
		return "redirect:/admin/show_restaurant";
	}
	
	@PostMapping("/import_restaurant")
	  public String import_restaurant(@RequestParam("restaurant_file") MultipartFile file, Model model) {
	
		  if (file.isEmpty()) {
	            model.addAttribute("message", "Please select the Restaurant CSV file to import.");
	            model.addAttribute("status", false);
	        } else {
	        	 // parse CSV file to create a list of `User` objects
	            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

	                // create csv bean reader
	            	CsvToBean<Restaurant> csvToBean = new CsvToBeanBuilder<Restaurant>(reader)
	            			.withType(Restaurant.class)
	            			.withIgnoreLeadingWhiteSpace(true)
	                        .build();

	                // convert `CsvToBean` object to list of users
	                List<Restaurant> restaurant = csvToBean.parse();
	                System.out.println(restaurant);
	               

	                // save users in DB?
	                restaurantService.saveRestaurants(restaurant);
                          restaurant=restaurantService.findAll();
	                // save users list on model
	                model.addAttribute("restaurant", restaurant);
	                model.addAttribute("status", true); 

	                try {
	                }catch(Exception ex) {
	                	 model.addAttribute("message", "An error occurred while saving the CSV file.");
	                     model.addAttribute("status", false); 
	                }

	            } catch (Exception ex) {
	                model.addAttribute("message", "An Error occurred while processing the CSV file.");
	                model.addAttribute("status", false);
	            }
	        	
	        }
		
		 	 return "/admin/Restaurant_Show_List"; 
		 	 
}
	
	

	@GetMapping("/RestaurantFormForUpdate")
	public String showFormForUpdate(@RequestParam("restaurant_file") String name,
									Model theModel) {
	    
		
		Restaurant 		restaurant = restaurantService.findByName(name);
		
		// set employee as a model attribute to pre-populate the form
		theModel.addAttribute("restaurant", restaurant);
		
		// send over to our form
		return "/admin/Restaurant_Update_List";			
	}
	

	@PostMapping("/saveRestaurant")
	public String saveCost(@ModelAttribute("restaurant") Restaurant theRestaurant,Model theModel) {
		
		restaurantService.save(theRestaurant);
	
			List<Restaurant> restaurant =restaurantService.findAll();
			theModel.addAttribute("restaurant", restaurant);
			theModel.addAttribute("status", true); 
		   
		 	 return "/admin/Restaurant_Show_List";  
	}
		
	
	@GetMapping("/RestaurantFormAdd")
	public String showFormForAdd(Model theModel) {
		
		// create model attribute to bind form data
		Restaurant restaurant = new Restaurant();


		theModel.addAttribute("restaurant", restaurant);
		
		return "/admin/Restaurant_Update_List";
	}
	}

