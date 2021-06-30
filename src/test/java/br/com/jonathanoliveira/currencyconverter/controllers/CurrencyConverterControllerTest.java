package br.com.jonathanoliveira.currencyconverter.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.type.TypeReference;

import br.com.jonathanoliveira.currencyconverter.data.entities.Transaction;
import br.com.jonathanoliveira.currencyconverter.data.enums.CurrencyEnum;
import br.com.jonathanoliveira.currencyconverter.data.vos.TransactionRequestVO;
import br.com.jonathanoliveira.currencyconverter.data.vos.TransactionResponseVO;
import br.com.jonathanoliveira.currencyconverter.facades.CurrencyConverterFacade;

public class CurrencyConverterControllerTest extends AbstractTest {

	@MockBean
	private CurrencyConverterFacade currencyConverterFacade;

	@Before
	public void init() {
		setUp();
	}

	@Test
	public void convertCurrency() throws Exception {
		String uri = "/currency-converter?accessKey=accessKey";
		String inputJson = super.mapToJson(payload_TransactionRequestVO());
		when(currencyConverterFacade.convertCurrency(any(TransactionRequestVO.class)))
				.thenReturn(payload_TransactionResponseVO());

		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
	}

	@Test
	public void convertCurrency_Unsupported_MediaType() throws Exception {
		String uri = "/currency-converter";

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.ALL)
				.content(payload_TransactionRequestVO().toString())).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(415, status);
	}

	@Test
	public void convertCurrency_Bad_Request() throws Exception {
		String uri = "/currency-converter";
		Transaction transaction = new Transaction();
		transaction.setDateTime(new Date());
		String inputJson = super.mapToJson(transaction);

		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(400, status);
	}

	@Test
	public void findAll() throws Exception {
		String uri = "/currency-converter";

		List<TransactionResponseVO> transactionResponseVOs = new ArrayList<>();
		when(currencyConverterFacade.findAll(any(Pageable.class))).thenReturn(transactionResponseVOs);

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		List<TransactionResponseVO> result = super.mapFromJson(mvcResult.getResponse().getContentAsString(),
				new TypeReference<List<TransactionResponseVO>>() {
				});
		assertNotNull(result);
		assertEquals(transactionResponseVOs, result);

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}

	private TransactionRequestVO payload_TransactionRequestVO() throws ParseException {
		final TransactionRequestVO transactionRequestVO = new TransactionRequestVO();
		transactionRequestVO.setUserId(1);
		transactionRequestVO.setSourceCurrency(CurrencyEnum.EUR);
		transactionRequestVO.setSourceValue(1.0);
		transactionRequestVO.setTargetCurrency(CurrencyEnum.BRL);
		return transactionRequestVO;
	}

	private TransactionResponseVO payload_TransactionResponseVO() throws ParseException {
		final TransactionResponseVO transactionResponseVO = new TransactionResponseVO();
		transactionResponseVO.setTransactionId(1);
		transactionResponseVO.setUserId(1);
		transactionResponseVO.setSourceCurrency(CurrencyEnum.EUR);
		transactionResponseVO.setSourceValue(1.0);
		transactionResponseVO.setTargetCurrency(CurrencyEnum.BRL);
		transactionResponseVO.setConversionRateUsed(5.904754);
		SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
		transactionResponseVO.setDateTime(f.parse("28/06/2021"));
		return transactionResponseVO;
	}

}
