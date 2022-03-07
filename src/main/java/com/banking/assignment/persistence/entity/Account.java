package com.banking.assignment.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

/**
 * This is a model class represents Account entity
 */

@Entity
@Table(name = "account")
@lombok.NoArgsConstructor
@lombok.Setter
@lombok.Getter
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;

	@Column(name = "account_type")
	private String accountType;

	@Column(name = "account_number")
	private String accountNumber;
	
	@OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "account")
	@JsonBackReference
	private List<Statement> statements;

}
