package br.com.jonathanoliveira.currencyconverter.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.jonathanoliveira.currencyconverter.data.enums.CurrencyEnum;
import br.com.jonathanoliveira.currencyconverter.data.vos.ExchangeRatesResponseVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExchangeRatesService {

	@Value("${url.exchangeratesapi}")
	private String urlExchangeRatesAPI;

	private final RestTemplate restTemplate;

	public ExchangeRatesResponseVO searchExchangeRates(CurrencyEnum sourceCurrency, CurrencyEnum targetCurrency) {
		ParameterizedTypeReference<ExchangeRatesResponseVO> responseType = new ParameterizedTypeReference<ExchangeRatesResponseVO>() {};
		ResponseEntity<ExchangeRatesResponseVO> response = restTemplate.exchange(
				urlExchangeRatesAPI + 
				"&base=" + sourceCurrency + 
				"&symbols=" + targetCurrency, 
				HttpMethod.GET, null,
				responseType);
		return response.getBody();

	}

}
