package com.apk.demo.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Bank 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(
	        nullable = false,
	        precision = 15,
	        scale = 2,
	        columnDefinition = "DECIMAL(15,2) DEFAULT 3000.00"
	    )
	private BigDecimal amount = new BigDecimal("3000.00");
	
}
