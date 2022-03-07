package com.banking.assignment.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@Schema(description = "Request model for view statement")
public class ViewStatementRequest implements Serializable {

	private static final long serialVersionUID = 3177975971789264385L;

	@NotBlank
	@Pattern(regexp = "[\\d]{13}")
	@Schema(name = "accountId", example = "0000210229876")
	private String accountId;

	@Schema(name = "fromDate", example = "18.05.2021")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
	private LocalDate fromDate;

	@Schema(name = "toDate", example = "07.11.2022")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
	private LocalDate toDate;

	@Digits(integer = 12, fraction = 12)
	@Schema(name = "fromAmount", example = "10")
	private BigDecimal fromAmount;

	@Digits(integer = 12, fraction = 12)
	@Schema(name = "toAmount", example = "40000")
	private BigDecimal toAmount;

}