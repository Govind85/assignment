package com.banking.assignment.payload.response;

import java.io.Serializable;
import java.math.BigDecimal;

@lombok.Getter
@lombok.Setter
public class Statement implements Serializable {

	private static final long serialVersionUID = -232832822397379379L;

	private String date;

	private BigDecimal amount;

}
