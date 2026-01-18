package com.apk.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.apk.demo.model.Transfer;


public interface TransferRepository extends JpaRepository<Transfer,Long>
{
	List<Transfer> findByFromAccountOrToAccountOrderByDateDesc(Long fromAccount, Long toAccount);
}
