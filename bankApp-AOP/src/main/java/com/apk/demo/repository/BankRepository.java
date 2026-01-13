package com.apk.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.apk.demo.model.Bank;

public interface BankRepository extends JpaRepository<Bank,Long>
{

}
