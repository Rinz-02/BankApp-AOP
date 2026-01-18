package com.apk.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.apk.demo.model.Bank;
import com.apk.demo.service.BankService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController 
{
	@Autowired
	private BankService bankService;
	
	@GetMapping("/index")
	public String index()
	{
		return "index";
	}
	
	//Create
	@GetMapping("/createAccount")
	public String createAccount()
	{
		return "create-account";
	}
	
	@PostMapping("/createAccount")
	public String createAccount(@RequestParam String name)
	{
		 bankService.createAccount(name);
		 return "redirect:/index";
	}
	
	//Login
	@PostMapping("/login")
	public String login(@RequestParam String name, Model model, HttpSession session)
	{
		Bank bank = bankService.login(name);
		session.setAttribute("LoggedIn", bank); //save bank object to session to get easy access for the whatever data 
		return "redirect:/home";
	}
	
	//Home
	@GetMapping("/home")
	public String home(HttpSession session, Model model)
	{
		Bank bank = (Bank) session.getAttribute("LoggedIn");
		if (bank == null)
		{
			return "redirect:/login";
		}
		 model.addAttribute("bank", bank);
		 return "home";
	}
	
	//LogOut
	@PostMapping("/logout")
	public String logOut(HttpSession session)
	{
		session.invalidate();
		return "redirect:/index";
	}
}
