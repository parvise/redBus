package com.demo.busBookingApp.controller;

import java.security.Principal;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.busBookingApp.entity.BankAccount;
import com.demo.busBookingApp.entity.TfBankAccountInfo;
import com.demo.busBookingApp.service.BankAccountService;

@Controller
@RequestMapping("/bank")
public class BankAccountController {

	@Autowired
	private BankAccountService service;

	@GetMapping("/home")
	public String goToBank(Model model, Principal principal) {
		model.addAttribute("accountList", getList());
		model.addAttribute("bankAccount", new TfBankAccountInfo());
		// model.addAttribute("user", principal.getName());
		return "bankHome";
	}

	private ArrayList<BankAccount> getList() {
		ArrayList<BankAccount> list = new ArrayList<>();
		Iterable<BankAccount> itr = service.listAll();
		itr.iterator().forEachRemaining(list::add);
		return list;
	}

	@GetMapping("/sendMoney/{id}")
	public String sendMoney(@PathVariable("id") Long id, Model model) {
		BankAccount account = service.findById(id);
		model.addAttribute("bankAccount", convertEntityToBean(account));
		return "bankHome";
	}

	//@GetMapping("/sendMoney")
	// @Secured({ "ROLE_ADMIN" })
	public String sendMoney(HttpServletRequest request, Model model, Principal principal) {
		String userName = "Invalid";
		if (principal != null)
			userName = principal.getName();
		System.out.println("principal" + userName);
		BankAccount account = service.findById(1l);
		model.addAttribute("bankAccount", convertEntityToBean(account));
		model.addAttribute("user", userName);
		model.addAttribute("message", "Welcome To HDFC");
		return "bankHome";
	}

	@GetMapping("/error")
	public String goToError(Model model) {
		model.addAttribute("message", "You are not authorized to access");
		return "error";
	}

	@PostMapping("/sendMoney")
	public String sendMoney(TfBankAccountInfo info, Model model) {

		System.out.println(info);
		try {
			service.sendMoney(info);
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			BankAccount account = service.findById(info.getFromAccId());
			model.addAttribute("bankAccount", convertEntityToBean(account));
			return "bankHome";
		}
		model.addAttribute("accountList", getList());
		return "bankHome";
	}

	private TfBankAccountInfo convertEntityToBean(BankAccount account) {
		System.out.println("convertEntityToBean");
		TfBankAccountInfo info = new TfBankAccountInfo();
		info.setBalance(account.getBalance());
		info.setAmount(700);
		info.setFromAccId(account.getId());
		info.setToAccId(2l);
		BankAccount toBank = service.findById(info.getToAccId());
		info.setToVersionNo(toBank.getVersion());
		info.setVersionNo(account.getVersion());
		return info;
	}

}
