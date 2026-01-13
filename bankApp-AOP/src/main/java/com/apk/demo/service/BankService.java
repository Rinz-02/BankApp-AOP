package com.apk.demo.service;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apk.demo.repository.*;
import java.util.List;

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
	
	public void deposite(Double amount,Long id)
	{
		Bank bank = bankRepo.findById(id)
				.orElseThrow( () -> new RuntimeException("Account Not Found"));
		
		bank.setAmount(bank.getAmount()+ amount);
		bankRepo.save(bank);
		
		saveTransaction(id, null, "Deposite", amount);
	}
	
	public void withdraw(Double amount,Long id)
	{
		Bank bank = bankRepo.findById(id)			
				.orElseThrow( () -> new RuntimeException("AccountNotFound"));
		bank.setAmount(bank.getAmount() + amount);
		bankRepo.save(bank);
		
		saveTransaction(id, null, "Withdraw", amount);
	}
	
	public void transfer(long fromId, Long toId, Double amount)
	{
		Bank fromBank = bankRepo.findById(fromId)
				.orElseThrow( () -> new RuntimeException("AccountNotFound"));
		Bank toBank = bankRepo.findById(toId)
				.orElseThrow( () -> new RuntimeException("AccountNotFound"));
		
		if(amount > fromBank.getAmount())
		{
			throw new RuntimeException("Not Enough Balance");
		}
			
		fromBank.setAmount(fromBank.getAmount() - amount);	
		toBank.setAmount(toBank.getAmount() + amount);
	
		bankRepo.save(fromBank);
		bankRepo.save(toBank);
		
		saveTransaction(fromId, toId, "Transfer", amount);
	}
	
	private void saveTransaction(long fromId,Long toId, String type,Double amount)
	{
		//Error at id null value argument //quick fix type cast for null value
		Transfer transfer = new Transfer((Long) null, type, fromId, toId, amount, new Date(System.currentTimeMillis()));
		transferRepo.save(transfer);
	}
	
	public List<Transfer> getHistory()
	{
		return transferRepo.findAll();
	}
	
}
 