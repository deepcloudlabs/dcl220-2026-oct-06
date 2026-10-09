package com.example.cm.controller.handler;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.cm.dto.fault.ErrorResponse;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class RestControllerErrorHandler {

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public List<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
		return e.getConstraintViolations().stream()
				.map(cv -> new ErrorResponse(cv.getMessage(), cv.getConstraintDescriptor().getAnnotation().toString()))
				.toList();
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public List<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		return e.getBindingResult().getAllErrors().stream()
				.map(err -> new ErrorResponse(Arrays.stream(err.getCodes()).collect(Collectors.joining(",")), err.getObjectName())).toList();
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ErrorResponse handleIllegalArgumentException(IllegalArgumentException e) {
		return new ErrorResponse(e.getMessage(),e.getMessage());
	}
	

	@ExceptionHandler(Exception.class)
	@ResponseStatus(code = HttpStatus.BAD_GATEWAY)
	public ErrorResponse handleException(Exception e) {
		return new ErrorResponse(e.getMessage(), e.getCause().toString());
	}	
}