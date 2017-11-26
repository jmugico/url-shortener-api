package com.jm.urlshortener.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * URL not found exception. API will return status code 404 (NO FOUND) when this exception is thrown.
 * @author jmugico
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class URLNotFoundException  extends Exception {

	public URLNotFoundException(String key) {
		super("Short URL with key: " + key + " not found");
	}
}