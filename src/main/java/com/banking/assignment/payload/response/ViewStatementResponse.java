package com.banking.assignment.payload.response;

import java.io.Serializable;
import java.util.List;

/**
 * ViewStatementResponse, this class represents model of ViewStatementResponse
 * 
 */

@lombok.Builder
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter
public class ViewStatementResponse implements Serializable {

	private static final long serialVersionUID = 5261351764977760862L;

	private Statement statement;
	private String accountType;

	private String accountNumber;

}