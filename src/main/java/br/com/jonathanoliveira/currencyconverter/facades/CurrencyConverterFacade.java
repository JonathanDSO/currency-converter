package br.com.jonathanoliveira.currencyconverter.facades;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.com.jonathanoliveira.currencyconverter.converters.DozerConverter;
import br.com.jonathanoliveira.currencyconverter.data.entities.Transaction;
import br.com.jonathanoliveira.currencyconverter.data.vos.ExchangeRatesResponseVO;
import br.com.jonathanoliveira.currencyconverter.data.vos.TransactionRequestVO;
import br.com.jonathanoliveira.currencyconverter.data.vos.TransactionResponseVO;
import br.com.jonathanoliveira.currencyconverter.services.ExchangeRatesService;
import br.com.jonathanoliveira.currencyconverter.services.TransactionService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CurrencyConverterFacade {
	private final TransactionService transactionService;

	private final ExchangeRatesService exchangeRatesService;

	private static final String TRANSACTION_REQUEST_VO_CANNOT_BE_NULL = "transactionRequestVO cannot be null";

	public TransactionResponseVO convertCurrency(TransactionRequestVO transactionRequestVO) {
		Assert.notNull(transactionRequestVO, TRANSACTION_REQUEST_VO_CANNOT_BE_NULL);
		Transaction transaction = DozerConverter.parseObject(transactionRequestVO, Transaction.class);
		ExchangeRatesResponseVO exchangeRatesResponseVO = exchangeRatesService
				.searchExchangeRates(transaction.getSourceCurrency(), transaction.getTargetCurrency());
		transaction.setConversionRateUsed(exchangeRatesResponseVO.getRates().get(transaction.getTargetCurrency()));
		Transaction transactionSaved = transactionService.save(transaction);
		return DozerConverter.parseObject(transactionSaved, TransactionResponseVO.class);
	}

	public List<TransactionResponseVO> findAll(Pageable pageable) {
		return DozerConverter.parseListObject(transactionService.findAll(pageable), TransactionResponseVO.class);
	}

}
