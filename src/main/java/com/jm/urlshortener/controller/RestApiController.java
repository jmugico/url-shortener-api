package com.jm.urlshortener.controller;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.jm.urlshortener.exceptions.URLFormatException;
import com.jm.urlshortener.exceptions.URLNotFoundException;
import com.jm.urlshortener.model.URLMapping;
import com.jm.urlshortener.service.URLShortenerService;

/**
 * API to handle URL requests
 * @author jmugico
 */

@RestController
public class RestApiController {
     
	@Autowired URLShortenerService service;
    
	private static Logger LOGGER = Logger.getLogger(RestApiController.class.getName());
    
	@RequestMapping(value = "/shortener", method = RequestMethod.POST)
	public ResponseEntity<?> shortUrl(@RequestBody URLMapping mapping, UriComponentsBuilder ucBuilder) throws URLFormatException {
	
		String shortURLKey = service.getURLKey(mapping.getUrl());
	
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/{shortURLKey}").buildAndExpand(shortURLKey).toUri());
	
		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "{key}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.SEE_OTHER)
	public void redirect(@PathVariable("key") String key, HttpServletResponse response) throws IOException, URLNotFoundException {
	
		String longURL = service.getURL(key);

		LOGGER.info("Redirect to: " + longURL);

		response.sendRedirect(longURL);
	}
}