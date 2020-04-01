package com.example.favorite.codegenerator.exception;

import java.text.MessageFormat;

public class ApplicationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ApplicationException(String message) {
		super(message);
	}

	public ApplicationException(String message, String... args) {
		super(MessageFormat.format(message, args));
	}

	public ApplicationException(Throwable e) {
		super(e);
	}

	public ApplicationException(String message, Throwable e, String... args) {
		super(MessageFormat.format(message, args), e);
	}
}
