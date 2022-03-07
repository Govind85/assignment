package com.banking.assignment.security.services;

import com.banking.assignment.constants.GlobalConstants;
import com.banking.assignment.crypto.CryptoUtil;
import com.banking.assignment.exceptions.NoSuchValueException;
import com.banking.assignment.payload.request.ViewStatementRequest;
import com.banking.assignment.payload.response.ViewStatementResponse;
import com.banking.assignment.persistence.entity.Account;
import com.banking.assignment.persistence.entity.Statement;
import com.banking.assignment.persistence.repistory.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
public class StatementServiceImpl implements StatementService {

	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private CryptoUtil cryptoUtil;
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(GlobalConstants.DATE_FORMAT);


	@Override
	@Transactional
	public List<ViewStatementResponse> fetchStatements(ViewStatementRequest request) {

		Optional<Account> account = accountRepository.findByAccountNumber(request.getAccountId());

		if (!account.isPresent()) {
			log.info("Account does not exist.");
			throw new NoSuchValueException("No value present for searched criteria");
		}


		List<ViewStatementResponse> statements = account.get().getStatements().stream()
					.filter(statement -> LocalDate.parse(statement.getDate(), dateFormatter)
							.compareTo(request.getFromDate()) >= 0
							&& LocalDate.parse(statement.getDate(), dateFormatter).compareTo(request.getToDate()) <= 0
							&& (request.getToAmount() == null || (statement.getAmount().compareTo(request.getFromAmount()) >= 0
											&& statement.getAmount().compareTo(request.getToAmount()) <= 0)))
					.map(this :: convertToResponse)
					.collect(Collectors.toList());


	return statements;
	}

	@Override
	public List<ViewStatementResponse> fetchStatements(String accountNumber) {
		Optional<Account> account = accountRepository.findByAccountNumber(accountNumber);
		if(account.isPresent()) {
			LocalDate fromDate = LocalDate.now().minusMonths(3);
			List<ViewStatementResponse> statements = account.get().getStatements().stream()
					.filter(statement -> LocalDate.parse(statement.getDate(), dateFormatter).compareTo(fromDate) >= 0)
					.map(this::convertToResponse).collect(Collectors.toList());
			return statements;
		} else {
			throw new NoSuchValueException("No value present for searched account number");
		}

	}

	private ViewStatementResponse convertToResponse(Statement statement) {
		ViewStatementResponse viewStatementResponse = new ViewStatementResponse();
		com.banking.assignment.payload.response.Statement statementDTO = new com.banking.assignment.payload.response.Statement();
		mapper.map(statement, statementDTO);
		viewStatementResponse.setStatement(statementDTO);
		viewStatementResponse.setAccountNumber(cryptoUtil.encrypt(statement.getAccount().getAccountNumber()));
		viewStatementResponse.setAccountType(statement.getAccount().getAccountType());
		return viewStatementResponse;
	}


}
