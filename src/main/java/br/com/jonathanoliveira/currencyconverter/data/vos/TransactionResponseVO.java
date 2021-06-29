package br.com.jonathanoliveira.currencyconverter.data.vos;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.jonathanoliveira.currencyconverter.data.enums.CurrencyEnum;
import lombok.Data;

@Data
public class TransactionResponseVO {

	private Integer transactionId;
	private Integer userId;
	private CurrencyEnum sourceCurrency;
	private Double sourceValue;
	private CurrencyEnum targetCurrency;
	private Double targetValue;
	private Double conversionRateUsed;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	private Date dateTime;

	public Double getTargetValue() {
		targetValue = sourceValue * conversionRateUsed;
		return targetValue;
	}

}
