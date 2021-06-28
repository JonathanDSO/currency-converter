package br.com.jonathanoliveira.currencyconverter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ExchangeRatesApiException extends RuntimeException {

	private static final long serialVersionUID = 5751650821615471402L;

	public ExchangeRatesApiException() {
		super();
	}

	public ExchangeRatesApiException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExchangeRatesApiException(String message) {
		super(message);
	}

	public ExchangeRatesApiException(Throwable cause) {
		super(cause);
	}
}
