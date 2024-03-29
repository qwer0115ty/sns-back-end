package com.boot.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class NoHandlerFoundExceptionHandler {
	@ExceptionHandler(NoHandlerFoundException.class)
	public String handleError404()   {
		return "main";
    }
}
