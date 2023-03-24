package com.csmis.controller;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.csmis.DTO.DailyInvoiceDTO;
import com.csmis.entity.DailyInvoice;
import com.csmis.entity.InvoiceApprovedBy;
import com.csmis.entity.InvoiceCashier;
import com.csmis.entity.InvoiceReceiveBy;
import com.csmis.entity.PaymentVoucher;
import com.csmis.entity.Restaurant;
import com.csmis.service_interface.InvoiceApprovedByServiceInterface;
import com.csmis.service_interface.InvoiceCashierServiceInterface;
import com.csmis.service_interface.InvoiceReceiveByServiceInterface;
import com.csmis.service_interface.InvoiceServiceInterface;
import com.csmis.service_interface.PaidVoucherServiceInterface;
import com.csmis.service_interface.RestaurantServiceInterface;

@Controller
@RequestMapping("/admin")
public class InvoiceController {
	private InvoiceServiceInterface dailyInvoiceService;
	private InvoiceCashierServiceInterface cashierSerivice;
	private InvoiceReceiveByServiceInterface receivedService;
	private InvoiceApprovedByServiceInterface approvedByServie;
	private RestaurantServiceInterface resturantService;
	private PaidVoucherServiceInterface paidVoucherServiceInterface;
	

	@Autowired
	public InvoiceController(InvoiceServiceInterface theInvoiceService, InvoiceCashierServiceInterface theCashierSerivice,
			InvoiceReceiveByServiceInterface theReceivedService, InvoiceApprovedByServiceInterface theApprovedByServie,
			RestaurantServiceInterface theResturantService,PaidVoucherServiceInterface thePaidVoucherServiceInterface) {
		dailyInvoiceService = theInvoiceService;
		cashierSerivice = theCashierSerivice;
		receivedService = theReceivedService;
		approvedByServie = theApprovedByServie;
		resturantService = theResturantService;
		paidVoucherServiceInterface=thePaidVoucherServiceInterface;
	}

	@GetMapping("/invoice")
	public String DailyInvoice(Model model) {

		List<InvoiceCashier> cashier = cashierSerivice.findAll();
		List<InvoiceReceiveBy> received = receivedService.findAll();
		List<InvoiceApprovedBy> approve = approvedByServie.findAll();
		List<Restaurant> resturant = resturantService.findAll();
		
		
		
		String tempLatestDate = paidVoucherServiceInterface.getLastDate();
		System.out.println("Hello mother fucker------------"+tempLatestDate);
		String latestDate = tempLatestDate.substring(tempLatestDate.length()-8);
		System.out.println("Hello mother fucker------------"+latestDate);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd"); 
		LocalDate startDate = LocalDate.parse(latestDate,formatter).plusDays(3);
		LocalDate endDate = startDate.plusDays(4);
		System.out.println("Hello mother fucker------------"+startDate);
		
		int Ctotal = 0;
		int Stotal = 0;
		model.addAttribute("CTotal", Ctotal);
		model.addAttribute("Stotal", Stotal);
		model.addAttribute("cashier", cashier);
		model.addAttribute("received", received);
		model.addAttribute("approve", approve);
		model.addAttribute("resturant", resturant);
		model.addAttribute("status",false);
		model.addAttribute("startDate",startDate);
		model.addAttribute("endDate",endDate);
		

		return "admin/invoice/invoice";
	}

	@PostMapping("/confirm")
	public String showData(Model model,
			@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
		
		List<DailyInvoice> data = dailyInvoiceService.findByDateBetween(startDate, endDate);
		List<InvoiceCashier> cashier = cashierSerivice.findAll();
		List<InvoiceReceiveBy> received = receivedService.findAll();
		List<InvoiceApprovedBy> approve = approvedByServie.findAll();
		List<Restaurant> resturant = resturantService.findAll();
		List<DailyInvoiceDTO> dto = new ArrayList<>();
		int Ctotal = 0;
		int Stotal = 0;
		int numOfPax=0;
		int amount=0;
		int price=0;

		for (DailyInvoice dailyInvoice : data) {
			DailyInvoiceDTO temp = new DailyInvoiceDTO();
			temp.setActualHeadCount(dailyInvoice.getActualHeadCount());
			temp.setRegisterHeadCount(dailyInvoice.getRegisterHeadCount());
			temp.setDate(dailyInvoice.getDate());
			temp.setCompanyCost(dailyInvoice.getCompanyCost());
			temp.setStaffCost(dailyInvoice.getStaffCost());
			temp.setDifference(dailyInvoice.getRegisterHeadCount() - dailyInvoice.getActualHeadCount());
			
			if (dailyInvoice.getActualHeadCount() > dailyInvoice.getRegisterHeadCount()) {
				temp.setCamount(dailyInvoice.getActualHeadCount() * dailyInvoice.getCompanyCost());
			} else {
				temp.setCamount(dailyInvoice.getRegisterHeadCount() * dailyInvoice.getCompanyCost());
			}
			if (dailyInvoice.getActualHeadCount() > dailyInvoice.getRegisterHeadCount()) {
				temp.setSamount(dailyInvoice.getActualHeadCount() * dailyInvoice.getStaffCost());
			} else {
				temp.setSamount(dailyInvoice.getRegisterHeadCount() * dailyInvoice.getStaffCost());
			}
			if (dailyInvoice.getActualHeadCount() > dailyInvoice.getRegisterHeadCount()) {
				numOfPax += temp.getActualHeadCount();
			
			} else {
				numOfPax += temp.getRegisterHeadCount();
				
			}
			Ctotal += temp.getCamount();
			Stotal += temp.getSamount();
			price = temp.getStaffCost()+ temp.getCompanyCost();
			amount = numOfPax * price;
			dto.add(temp);
		}
		model.addAttribute("invoices", dto);
		model.addAttribute("CTotal", Ctotal);
		model.addAttribute("Stotal", Stotal);
		model.addAttribute("numOfPax", numOfPax);
		model.addAttribute("amount", amount);
		model.addAttribute("price", price);
		
		model.addAttribute("status",true);

		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("cashier", cashier);
		model.addAttribute("received", received);
		model.addAttribute("approve", approve);
		model.addAttribute("resturant", resturant);
		return "admin/invoice/invoice";
	}
	
	@GetMapping("/monthlyInvoice")
	public String MonthlyInvoice(Model model) {
		List<PaymentVoucher> monthlyInvoice =paidVoucherServiceInterface.findAll();
		model.addAttribute("monthlyInvoice",monthlyInvoice);
		return "admin/invoice/paidinvoice";
		
	}
	@PostMapping("/save")
	public String MonthlyInvoice(@ModelAttribute("Month}") PaymentVoucher thePaymentVoucher ) {
		
		
		paidVoucherServiceInterface.save(thePaymentVoucher);
		
		
		return "redirect:/admin/monthlyInvoice";
		
	}
	
	

	@GetMapping("/detailInvoice")
	public String DetailInvoice(Model model) {
		List<DailyInvoice> theInvoices = dailyInvoiceService.findAll();
		List<DailyInvoiceDTO> dto = new ArrayList<>();
		
		for (DailyInvoice dailyInvoice : theInvoices) {
			DailyInvoiceDTO temp = new DailyInvoiceDTO();
			temp.setActualHeadCount(dailyInvoice.getActualHeadCount());
			temp.setRegisterHeadCount(dailyInvoice.getRegisterHeadCount());
			temp.setDate(dailyInvoice.getDate());
			temp.setCompanyCost(dailyInvoice.getCompanyCost());
			temp.setStaffCost(dailyInvoice.getStaffCost());
			temp.setDifference(dailyInvoice.getRegisterHeadCount() - dailyInvoice.getActualHeadCount());
			if (dailyInvoice.getActualHeadCount() > dailyInvoice.getRegisterHeadCount()) {
				temp.setCamount(dailyInvoice.getActualHeadCount() * dailyInvoice.getCompanyCost());
			} else {
				temp.setCamount(dailyInvoice.getRegisterHeadCount() * dailyInvoice.getCompanyCost());
			}
			if (dailyInvoice.getActualHeadCount() > dailyInvoice.getRegisterHeadCount()) {
				temp.setSamount(dailyInvoice.getActualHeadCount() * dailyInvoice.getStaffCost());
			} else {
				temp.setSamount(dailyInvoice.getRegisterHeadCount() * dailyInvoice.getStaffCost());
			}
			dto.add(temp);
		}
		// add to the spring model
		model.addAttribute("invoices", dto);	
		
		
		return "admin/invoice/detailInvoice";
	}

}
