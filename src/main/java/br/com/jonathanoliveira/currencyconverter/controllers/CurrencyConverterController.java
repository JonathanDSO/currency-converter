package br.com.jonathanoliveira.currencyconverter.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.jonathanoliveira.currencyconverter.data.vos.TransactionRequestVO;
import br.com.jonathanoliveira.currencyconverter.data.vos.TransactionResponseVO;
import br.com.jonathanoliveira.currencyconverter.facades.CurrencyConverterFacade;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/currency-converter")
@RequiredArgsConstructor
public class CurrencyConverterController {
	private final CurrencyConverterFacade currencyConverterFacade;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TransactionResponseVO> convertCurrency(@RequestParam(value = "accessKey", required = true) String accesskey,
			@Valid @RequestBody TransactionRequestVO transactionRequestVO) {
		TransactionResponseVO transactionResponseVO = this.currencyConverterFacade.convertCurrency(accesskey,
				transactionRequestVO);
		return new ResponseEntity<>(transactionResponseVO, HttpStatus.CREATED);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TransactionResponseVO>> findAll(
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page, size);
		List<TransactionResponseVO> transactionResponseVOList = this.currencyConverterFacade.findAll(pageable);
		return ResponseEntity.ok(transactionResponseVOList);
	}

}
