package br.com.jonathanoliveira.currencyconverter.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.com.jonathanoliveira.currencyconverter.data.entities.Transaction;
import br.com.jonathanoliveira.currencyconverter.data.enums.CurrencyEnum;
import br.com.jonathanoliveira.currencyconverter.repositories.TransactionRepository;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = TransactionService.class)
public class TransactionServiceTest {

	@InjectMocks
	private TransactionService transactionService;

	@Mock
	private TransactionRepository transactionRepository;

	@Test
	public void save() throws ParseException {
		when(transactionRepository.save(any(Transaction.class))).thenReturn(payload_Transaction());

		Transaction result = this.transactionService.save(new Transaction());
		Assert.assertNotNull(result);
		assertEquals(payload_Transaction(), result);
	}

	@Test(expected = IllegalArgumentException.class)
	public void save_null() {
		this.transactionService.save(null);
	}

	@Test
	public void findAll_Pageable() throws ParseException {
		Pageable pageable = PageRequest.of(0, 10);
		final List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(payload_Transaction());
		Page<Transaction> pageTransaction = new PageImpl<Transaction>(transactions);
		when(transactionRepository.findAll(pageable)).thenReturn(pageTransaction);
		List<Transaction> result = transactionService.findAll(pageable);

		Assert.assertNotNull(result);
		assertEquals(payload_Transaction(), result.get(0));
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
		transaction.setDate(f.parse("28/06/2021"));
		return transaction;
	}

}
