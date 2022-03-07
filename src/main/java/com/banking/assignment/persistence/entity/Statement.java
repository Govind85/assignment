package com.banking.assignment.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "statement")
@JsonIgnoreProperties(ignoreUnknown = true)
@lombok.NoArgsConstructor
@lombok.Setter
@lombok.Getter
public class Statement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "account_id")
	@JsonManagedReference
	private Account account;

	@Column(name = "datefield")
	private String date;

	@Column(name = "amount")
	private BigDecimal amount;

}
