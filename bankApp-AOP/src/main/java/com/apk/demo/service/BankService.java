package com.apk.demo.service;

import java.math.BigDecimal;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apk.demo.repository.*;
import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.apk.demo.model.*;

@Service
@RequiredArgsConstructor
@Transactional

public class BankService 
{
	@Autowired
	private BankRepository bankRepo;
	
	@Autowired
	private TransferRepository transferRepo;
	
	public Bank deposite(BigDecimal amount,Long id)
	{
		Bank bank = bankRepo.findById(id)
				.orElseThrow( () -> new RuntimeException("Account Not Found"));
		
		bank.setAmount(bank.getAmount().add(amount));
		bankRepo.save(bank);

		saveTransaction(null, id, "Deposite", amount);
		return bank;
	}
	
	public Bank withdraw(BigDecimal amount,Long id)
	{
		Bank bank = bankRepo.findById(id)			
				.orElseThrow( () -> new RuntimeException("AccountNotFound"));
		bank.setAmount(bank.getAmount().subtract(amount));
		bankRepo.save(bank);
		
		saveTransaction(id, null, "Withdraw", amount);
		return bank;
	}
	
	public Bank transfer(Long fromId, Long toId, BigDecimal amount)
	{
		if (amount.compareTo(BigDecimal.ZERO) <= 0) {
	        throw new IllegalArgumentException("Transfer amount isn't valid");
	    }
		if (fromId.equals(toId)) {
			throw new IllegalArgumentException("Cannont transfer to same Account!");
		}
		
		Bank fromBank = bankRepo.findById(fromId)
				.orElseThrow( () -> new RuntimeException("AccountNotFound"));
		Bank toBank = bankRepo.findById(toId)
				.orElseThrow( () -> new RuntimeException("AccountNotFound"));
		
		if(amount.compareTo(fromBank.getAmount()) > 0)
		{         
			throw new RuntimeException("Not Enough Balance");
		}
			
		fromBank.setAmount(fromBank.getAmount().subtract(amount));	
		toBank.setAmount(toBank.getAmount().add(amount));
	
		bankRepo.save(fromBank);
		bankRepo.save(toBank);
		
		saveTransaction(fromId, toId, "Transfer", amount);
		return fromBank;
	}
	
	private void saveTransaction(Long fromId,Long toId, String type,BigDecimal amount)
	{
		
		Transfer transfer = new Transfer(null, type, fromId, toId, amount, new Date(System.currentTimeMillis()));
		transferRepo.save(transfer);
	}
	
	public List<Bank> findAllBanks()
	{
		return bankRepo.findAll();
	}
	
	public List<Transfer> getHistory(Long id)
	{
		return transferRepo.findByFromAccountOrToAccountOrderByDateDesc(id, id);
	}
	
	public Bank findById(Long id)
	{
		return bankRepo.findById(id).get();
	}
	
	public Bank login(String name)
	{
		return bankRepo.findByNameIgnoreCase(name)
				.orElseThrow( () -> new RuntimeException("User Not Found"));
	}
	
	public Bank createAccount(String name)
	{
		Bank bank = new Bank();
		bank.setName(name);
		return bankRepo.save(bank);
	}
	
}
 