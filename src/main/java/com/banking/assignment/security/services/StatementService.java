package com.banking.assignment.security.services;

import com.banking.assignment.payload.request.ViewStatementRequest;
import com.banking.assignment.payload.response.ViewStatementResponse;

import java.util.List;

public interface StatementService {

	List<ViewStatementResponse> fetchStatements(ViewStatementRequest request);
	List<ViewStatementResponse> fetchStatements(String accountNumber);

}
