package com.jm.urlshortener.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * URL format exception. API will return status code 400 (BAD REQUEST) when this exception is thrown.
 * @author jmugico
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class URLFormatException extends Exception {

	public URLFormatException(String url) {
		super("URL " + url + " is malformed");
	}
}