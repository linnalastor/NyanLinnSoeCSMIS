package com.csmis.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
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
import com.csmis.entity.Paymentmethod;
import com.csmis.entity.Restaurant;
import com.csmis.entity.Staff;
import com.csmis.entity.StaffDetails;
import com.csmis.service.StaffService;
import com.csmis.service_interface.InvoiceApprovedByServiceInterface;
import com.csmis.service_interface.InvoiceCashierServiceInterface;
import com.csmis.service_interface.InvoiceReceiveByServiceInterface;
import com.csmis.service_interface.InvoiceServiceInterface;
import com.csmis.service_interface.PaidVoucherServiceInterface;
import com.csmis.service_interface.PaymentmethodServiceInterface;
import com.csmis.service_interface.RestaurantServiceInterface;
import com.csmis.service_interface.StaffDetailsServiceInterface;

@Controller
@RequestMapping("/admin")
public class InvoiceController {
	@Autowired
	private StaffService staffService;


	private InvoiceServiceInterface dailyInvoiceService;
	private InvoiceCashierServiceInterface cashierSerivice;
	private InvoiceReceiveByServiceInterface receivedService;
	private InvoiceApprovedByServiceInterface approvedByServie;
	private RestaurantServiceInterface resturantService;
	private PaidVoucherServiceInterface paidVoucherServiceInterface;
	private StaffDetailsServiceInterface staffDetailsServiceInterface;
	private PaymentmethodServiceInterface paymentmethodServiceInterface;

	@Autowired
	public InvoiceController(InvoiceServiceInterface theInvoiceService,
			InvoiceCashierServiceInterface theCashierSerivice, InvoiceReceiveByServiceInterface theReceivedService,
			InvoiceApprovedByServiceInterface theApprovedByServie, RestaurantServiceInterface theResturantService,
			PaidVoucherServiceInterface thePaidVoucherServiceInterface,
			StaffDetailsServiceInterface theStaffDetailsServiceInterface,PaymentmethodServiceInterface thePaymentmethodServiceInterface) {

		dailyInvoiceService = theInvoiceService;
		cashierSerivice = theCashierSerivice;
		receivedService = theReceivedService;
		approvedByServie = theApprovedByServie;
		resturantService = theResturantService;
		paidVoucherServiceInterface = thePaidVoucherServiceInterface;
		staffDetailsServiceInterface = theStaffDetailsServiceInterface;
		paymentmethodServiceInterface = thePaymentmethodServiceInterface;

	}

	@GetMapping("/invoice")
	public String DailyInvoice(Model model, Authentication auth) {

		LocalDate date = LocalDate.now();
		LocalDate firstDate = LocalDate.now();
		LocalDate startDate = null;
		LocalDate endDate = null;

		List<InvoiceCashier> cashier = cashierSerivice.findAll();
		List<InvoiceReceiveBy> received = receivedService.findAll();
		List<InvoiceApprovedBy> approve = approvedByServie.findAll();
		List<Restaurant> resturant = resturantService.findAll();
		List<Paymentmethod> paymentMethod = paymentmethodServiceInterface.findAll();
		StaffDetails staffDetail = staffDetailsServiceInterface.getStaffDetailByID(auth.getName());
		// System.out.println(staffDetail);
		String staffPassword = staffDetail.getPassword();
		LocalDate lastDate = LocalDate.now();

		String tempLatestDate = null;
		try {
			tempLatestDate = paidVoucherServiceInterface.getLastDate();
			String latestDate = tempLatestDate.substring(tempLatestDate.length() - 8);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

			startDate = LocalDate.parse(latestDate, formatter).plusDays(3);
			endDate = startDate.plusDays(4);
		} catch (Exception e) {
		}
		if (tempLatestDate == null) {
			firstDate = dailyInvoiceService.getFirstDate();

			if (firstDate.getDayOfWeek().getValue() < DayOfWeek.FRIDAY.getValue()) {
				while (firstDate.getDayOfWeek() != DayOfWeek.MONDAY) {
					firstDate = firstDate.minusDays(1);
				}
			} else {
				while (firstDate.getDayOfWeek() != DayOfWeek.MONDAY) {
					firstDate = firstDate.plusDays(1);
				}
			}
			startDate = firstDate;
			endDate = startDate.plusDays(4);
		}

//		boolean isFirstTime = true;
		int Ctotal = 0;
		int Stotal = 0;
		Staff loginStaff = staffService.findByID(auth.getName());

		model.addAttribute("userName",loginStaff.getName());
		model.addAttribute("CTotal", Ctotal);
		model.addAttribute("Stotal", Stotal);
		model.addAttribute("cashier", cashier);
		model.addAttribute("received", received);
		model.addAttribute("approve", approve);
		model.addAttribute("resturant", resturant);
		model.addAttribute("staffPassword", staffPassword);
		model.addAttribute("status", false);

		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("paymentDate", date);
		model.addAttribute("lastDate", lastDate);
		model.addAttribute("paymentMethod",paymentMethod);

		return "admin/invoice/invoice";
	}

	@PostMapping("/create")
	public String showData(Model model,
			@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
			@RequestParam("paymentDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate paymentDate,
			Authentication auth) {

		List<DailyInvoice> data = dailyInvoiceService.findByDateBetween(startDate, endDate);
		List<InvoiceCashier> cashier = cashierSerivice.findAll();
		List<InvoiceReceiveBy> received = receivedService.findAll();
		List<InvoiceApprovedBy> approve = approvedByServie.findAll();
		List<Restaurant> resturant = resturantService.findAll();
		List<DailyInvoiceDTO> dto = new ArrayList<>();

		int Ctotal = 0;
		int Stotal = 0;
		int numOfPax = 0;
		int amount = 0;
		int price = 0;

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
			price = temp.getStaffCost() + temp.getCompanyCost();
			amount = numOfPax * price;
			dto.add(temp);
		}
		model.addAttribute("invoices", dto);
		model.addAttribute("CTotal", Ctotal);
		model.addAttribute("Stotal", Stotal);
		model.addAttribute("numOfPax", numOfPax);
		model.addAttribute("amount", amount);
		model.addAttribute("price", price);
		model.addAttribute("status", true);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("paymentDate", paymentDate);

		System.out.println("Hello mother fucker+++++startDate------------" + startDate);
		model.addAttribute("cashier", cashier);
		model.addAttribute("received", received);
		model.addAttribute("approve", approve);
		model.addAttribute("resturant", resturant);

		String SpaymentDate = paymentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String SstartDate = startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String SendDate = endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String pYear = SpaymentDate.substring(0, 4);
		String pMonth = SpaymentDate.substring(5, 7);
		String pDay = SpaymentDate.substring(8);
//		String voucherNo = "CSMIS-" + year + month + day + ": " + year + month + SstartDate.substring(8) + " ~" + year
//				+ month + SendDate.substring(8);
		String sYear = SstartDate.substring(0, 4);
		String sMonth = SstartDate.substring(5, 7);
		String sDay = SstartDate.substring(8);
		String eYear = SendDate.substring(0, 4);
		String eMonth = SendDate.substring(5, 7);
		String eDay = SendDate.substring(8);
		String voucherNo = "CSMIS-" + pYear + pMonth + pDay + ": " + sYear + sMonth + sDay + " ~" + eYear + eMonth
				+ eDay;
		model.addAttribute("voucherNo", voucherNo);

		Staff loginStaff = staffService.findByID(auth.getName());

		model.addAttribute("userName",loginStaff.getName());
		return "admin/invoice/invoice";
	}

	@GetMapping("/monthlyInvoice")
	public String MonthlyInvoice(Model model, Authentication auth) {
		List<PaymentVoucher> monthlyInvoice = paidVoucherServiceInterface.findAll();
		model.addAttribute("monthlyInvoice", monthlyInvoice);

		Staff loginStaff = staffService.findByID(auth.getName());

		model.addAttribute("userName",loginStaff.getName());
		return "admin/invoice/paidinvoice";

	}

	@PostMapping("/save")
	public String MonthlyInvoice(@ModelAttribute("Month}") PaymentVoucher thePaymentVoucher) {

		paidVoucherServiceInterface.save(thePaymentVoucher);

		return "redirect:/admin/invoice";

	}

	@GetMapping("/detailInvoice")
	public String DetailInvoice(Model model, Authentication auth) {
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

		model.addAttribute("status", false);
		Staff loginStaff = staffService.findByID(auth.getName());

		model.addAttribute("userName",loginStaff.getName());

		return "admin/invoice/detailInvoice";
	}

	@GetMapping("/search")
	public String Search(Model model, @RequestParam("voucherNo") String voucherNo, Authentication auth) {
		String startDateString = voucherNo.substring(voucherNo.indexOf(":") + 2, voucherNo.indexOf("~")).trim();
		String endDateString = voucherNo.substring(voucherNo.indexOf("~") + 1).trim();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDate startDate = LocalDate.parse(startDateString, formatter);
		LocalDate endDate = LocalDate.parse(endDateString, formatter);

		List<DailyInvoice> data = dailyInvoiceService.findByDateBetween(startDate, endDate);
		List<DailyInvoiceDTO> dto = new ArrayList<>();
		int Ctotal = 0;
		int Stotal = 0;

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
			Ctotal += temp.getCamount();
			Stotal += temp.getSamount();
			dto.add(temp);
		}
		Staff loginStaff = staffService.findByID(auth.getName());

		model.addAttribute("userName",loginStaff.getName());
		model.addAttribute("invoices", dto);
		model.addAttribute("CTotal", Ctotal);
		model.addAttribute("Stotal", Stotal);
		model.addAttribute("status", true);
		return "admin/invoice/detailInvoice";

	}

	@PostMapping("/SEsearch")
	public String betweenTwoDate(Model model,
			@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
			Authentication auth) {
		List<DailyInvoice> data = dailyInvoiceService.findByDateBetween(startDate, endDate);
		List<DailyInvoiceDTO> dto = new ArrayList<>();
		int Ctotal = 0;
		int Stotal = 0;

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
			Ctotal += temp.getCamount();
			Stotal += temp.getSamount();
			dto.add(temp);
		}
		Staff loginStaff = staffService.findByID(auth.getName());

		model.addAttribute("userName",loginStaff.getName());
		model.addAttribute("invoices", dto);
		model.addAttribute("CTotal", Ctotal);
		model.addAttribute("Stotal", Stotal);
		model.addAttribute("status", true);
		return "admin/invoice/detailInvoice";
	}

}
