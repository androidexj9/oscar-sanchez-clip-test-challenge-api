package com.java.clip.challenge.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthenticationException extends org.springframework.security.core.AuthenticationException {
	
	public AuthenticationException(String msg) {
		super(msg);
	}
}
