package br.com.jonathanoliveira.currencyconverter.data.vos;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import br.com.jonathanoliveira.currencyconverter.data.enums.CurrencyEnum;
import lombok.Data;

@Data
public class ExchangeRatesResponseVO {

	private boolean success;
	private Timestamp timestamp;
	private CurrencyEnum base;
	private Date date;
	private Map<CurrencyEnum, Double> rates;

}
