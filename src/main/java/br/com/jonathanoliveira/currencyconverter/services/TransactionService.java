package br.com.jonathanoliveira.currencyconverter.services;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.com.jonathanoliveira.currencyconverter.data.entities.Transaction;
import br.com.jonathanoliveira.currencyconverter.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {
	private final TransactionRepository transactionRepository;

	public Transaction save(Transaction transaction) {
		Assert.notNull(transaction, "transaction cannot be null.");
		return this.transactionRepository.save(transaction);
	}

	public List<Transaction> findAll(Pageable pageable) {
		Assert.notNull(pageable, "pageable cannot be null.");
		return this.transactionRepository.findAll(pageable).getContent();
	}

}
