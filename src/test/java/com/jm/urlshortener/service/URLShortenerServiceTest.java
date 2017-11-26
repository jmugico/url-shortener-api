package com.jm.urlshortener.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.jm.urlshortener.exceptions.URLFormatException;
import com.jm.urlshortener.exceptions.URLNotFoundException;
import com.jm.urlshortener.model.URLMapping;
import com.jm.urlshortener.repository.URLMappingRepository;
import com.jm.urlshortener.service.URLShortenerService;
import com.jm.urlshortener.utils.KeyGenerator;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;

public class URLShortenerServiceTest {
    
	URLShortenerService service;
    
	@Mock
	URLMappingRepository repo;

	@Before
	public void init() {

		MockitoAnnotations.initMocks(this);
		service = new URLShortenerService();
		service.repo = this.repo;
	}
    
	@Test(expected = URLFormatException.class)
	public void getURLKeyMalFormed() throws Exception {
	
		service.getURLKey("this-is-not-a-valid-url");
	}
    
	@Test
	public void getURLKeyAlreadyExists() throws Exception {
	
		String url = "http://www.marca.com";
		String key = "abcdef";
		URLMapping mapping = new URLMapping(key, url); 
	
		when(service.repo.findByUrl(url)).thenReturn(mapping);
	
		String actualKey = service.getURLKey(url);
	
		assertEquals(key, actualKey);
	}
    
	@Test
	public void getURLKeyNew() throws Exception {
	
		String url = "http://dn.se";
	
		when(service.repo.findByUrl(url)).thenReturn(null);
		when(service.repo.findByKey(any())).thenReturn(null);
	
		String actualKey = service.getURLKey(url);
		assertEquals(KeyGenerator.KEY_LENGTH, actualKey.length());
	
		verify(service.repo, times(1)).save(any(URLMapping.class));
	}
    
	@Test(expected = URLNotFoundException.class)
	public void getURLNotFound() throws Exception {
	
		String key = "abcdef";
	
		when(service.repo.findByKey(key)).thenReturn(null);
		service.getURL(key);
	}
    
	@Test
	public void getURL() throws Exception {
	
		String key = "abcdef";
		String url = "http://dn.se";
		URLMapping mapping = new URLMapping(key, url);
	
		when(service.repo.findByKey(key)).thenReturn(mapping);
	
		String actualUrl = service.getURL(key);
	
		assertEquals(url, actualUrl);
	}
}