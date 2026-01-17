package com.apk.demo.controller;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.apk.demo.model.Bank;
import com.apk.demo.service.BankService;

import jakarta.servlet.http.HttpSession;

@Controller
public class BankController 
{
	@Autowired 
	private BankService bankService;
	
	//Withdraw
	@GetMapping("/withdraw/{id}")
	public String withdraw(@PathVariable Long id, Model model)
	{
		Bank bank = bankService.findById(id);
		model.addAttribute("bank", bank);
		return "withdraw";
	}
	
	@PostMapping("/withdraw/{id}")
	public String withdraw(@RequestParam BigDecimal amount, @PathVariable Long id, HttpSession session)
	{
		Bank updatedBank = bankService.withdraw(amount, id);
		session.setAttribute("LoggedIn", updatedBank);
		return "redirect:/home";
	}
	
	//Deposit
	@GetMapping("/deposit/{id}")
	public String deposit(@PathVariable Long id, Model model)
	{
		Bank bank = bankService.findById(id);
		model.addAttribute("bank", bank);
		return "deposit";
	}
	
	@PostMapping("/deposit/{id}")
	public String deposit(@RequestParam BigDecimal amount, @PathVariable Long id, HttpSession session)
	{
		Bank updatedBank = bankService.deposite(amount, id);
		session.setAttribute("LoggedIn", updatedBank);
		return "redirect:/home";
	}
	
	
	
	
	
}
