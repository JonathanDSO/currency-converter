package br.com.jonathanoliveira.currencyconverter.handlers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<ApiSubError> apiSubErrors = new ArrayList<>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			String object = ex.getParameter().getParameterType().getSimpleName();
			String field = error.getField();
			Object rejectedValue = error.getRejectedValue();
			String message = error.getDefaultMessage();
			apiSubErrors.add(new ApiValidationError(object, field, rejectedValue, message));
		}
		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			String object = error.getObjectName();
			String message = error.getDefaultMessage();
			apiSubErrors.add(new ApiValidationError(object, message));
		}
		ApiError apiError = new ApiError(status, "Validation errors", ex);
		apiError.setSubErrors(apiSubErrors);
		return buildResponseEntity(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = "Malformed JSON request";
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
	}

	@ExceptionHandler(HttpClientErrorException.class)
	protected ResponseEntity<Object> handleHttpClientErrorException(HttpClientErrorException ex) {
		ApiError apiError = new ApiError(ex.getStatusCode());
		apiError.setMessage(ex.getMessage());
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	protected ResponseEntity<Object> illegalArgumentException(IllegalArgumentException ex) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		apiError.setMessage(ex.getMessage());
		return buildResponseEntity(apiError);
	}

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

}
