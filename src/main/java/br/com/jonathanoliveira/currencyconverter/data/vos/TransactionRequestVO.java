package br.com.jonathanoliveira.currencyconverter.data.vos;

import javax.validation.constraints.NotNull;

import br.com.jonathanoliveira.currencyconverter.data.enums.CurrencyEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionRequestVO {

	@NotNull
	private Integer userId;

	@NotNull
	private CurrencyEnum sourceCurrency;

	@NotNull
	private Double sourceValue;

	@NotNull
	private CurrencyEnum targetCurrency;

}
