package br.com.jonathanoliveira.currencyconverter.data.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import br.com.jonathanoliveira.currencyconverter.data.enums.CurrencyEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer transactionId;

	@Column(name = "user_id", nullable = false)
	private Integer userId;

	@Column(name = "source_currency", nullable = false)
	private CurrencyEnum sourceCurrency;

	@Column(name = "source_value", nullable = false)
	private Double sourceValue;

	@Column(name = "target_currency", nullable = false)
	private CurrencyEnum targetCurrency;

	@Column(name = "conversion_rate_used", nullable = false)
	private Double conversionRateUsed;

	@CreationTimestamp
	@Column(name = "date_time", nullable = false)
	private Date dateTime;

}
