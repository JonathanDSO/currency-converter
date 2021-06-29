package br.com.jonathanoliveira.currencyconverter.facades;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.com.jonathanoliveira.currencyconverter.data.entities.Transaction;
import br.com.jonathanoliveira.currencyconverter.data.enums.CurrencyEnum;
import br.com.jonathanoliveira.currencyconverter.data.vos.ExchangeRatesResponseVO;
import br.com.jonathanoliveira.currencyconverter.data.vos.TransactionRequestVO;
import br.com.jonathanoliveira.currencyconverter.data.vos.TransactionResponseVO;
import br.com.jonathanoliveira.currencyconverter.services.ExchangeRatesService;
import br.com.jonathanoliveira.currencyconverter.services.TransactionService;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = CurrencyConverterFacade.class)
public class CurrencyConverterFacadeTest {

	@InjectMocks
	private CurrencyConverterFacade currencyConverterFacade;

	@Mock
	private TransactionService transactionService;

	@Mock
	private ExchangeRatesService exchangeRatesService;

	@Test
	public void convertCurrency() throws ParseException {
		TransactionRequestVO transactionRequestVO = new TransactionRequestVO();
		when(transactionService.save(ArgumentMatchers.any())).thenReturn(payload_Transaction());
		when(exchangeRatesService.searchExchangeRates(any(String.class), ArgumentMatchers.any(), ArgumentMatchers.any()))
				.thenReturn(payload_ExchangeRatesResponseVO());
		TransactionResponseVO result = currencyConverterFacade.convertCurrency("accessKey", transactionRequestVO);
		assertEquals(payload_TransactionResponseVO(), result);
	}

	@Test
	public void findAll_Pageable() throws ParseException {
		Pageable pageable = PageRequest.of(0, 10);
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(payload_Transaction());
		when(transactionService.findAll(pageable)).thenReturn(transactions);
		List<TransactionResponseVO> result = currencyConverterFacade.findAll(pageable);
		List<TransactionResponseVO> transactionResponseVOs = new ArrayList<>();
		transactionResponseVOs.add(payload_TransactionResponseVO());
		assertEquals(transactionResponseVOs, result);
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

	private Transaction payload_Transaction() throws ParseException {
		final Transaction transaction = new Transaction();
		transaction.setTransactionId(1);
		transaction.setUserId(1);
		transaction.setSourceCurrency(CurrencyEnum.EUR);
		transaction.setSourceValue(1.0);
		transaction.setTargetCurrency(CurrencyEnum.BRL);
		transaction.setConversionRateUsed(5.904754);
		SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
		transaction.setDateTime(f.parse("28/06/2021"));
		return transaction;
	}

	private ExchangeRatesResponseVO payload_ExchangeRatesResponseVO() throws ParseException {
		final ExchangeRatesResponseVO exchangeRatesResponseVO = new ExchangeRatesResponseVO();
		exchangeRatesResponseVO.setSuccess(true);
		exchangeRatesResponseVO.setTimestamp(new Timestamp(0));
		exchangeRatesResponseVO.setBase(CurrencyEnum.EUR);
		exchangeRatesResponseVO.setDate(new Date());
		Map<CurrencyEnum, Double> rates = new HashMap<>();
		rates.put(CurrencyEnum.BRL, 5.904754);
		exchangeRatesResponseVO.setRates(rates);
		return exchangeRatesResponseVO;
	}

}
