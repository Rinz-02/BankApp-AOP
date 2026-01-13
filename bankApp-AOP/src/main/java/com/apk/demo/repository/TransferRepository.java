package com.apk.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.apk.demo.model.Transfer;


public interface TransferRepository extends JpaRepository<Transfer,Long>
{

}
