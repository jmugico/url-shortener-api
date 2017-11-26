package com.jm.urlshortener.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import com.jm.urlshortener.AppConfig;
import com.jm.urlshortener.controller.RestApiController;
import com.jm.urlshortener.exceptions.URLFormatException;
import com.jm.urlshortener.exceptions.URLNotFoundException;
import com.jm.urlshortener.model.URLMapping;
import com.jm.urlshortener.repository.URLMappingRepository;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppConfig.class)
public class RestApiControllerFunctionalTest {
    
	@Autowired
	RestApiController controller;
    
	@Autowired
	URLMappingRepository repo;
    
	@Before
	public void init() {
	
		repo.deleteAll();
	}
    
	@Test(expected = URLFormatException.class)
	public void shortUrlMalformed() throws Exception {
	
		controller.shortUrl(new URLMapping(null, "this-is-not-a-valid-url"), UriComponentsBuilder.newInstance());
	}

	@Test
	public void shortUrl() throws Exception {
	
		String url = "http://dn.se";
	
		ResponseEntity<?> response = controller.shortUrl(new URLMapping(null, url), UriComponentsBuilder.newInstance());
		String location = response.getHeaders().get("location").get(0);

		URLMapping mapping = repo.findByUrl(url);
	
		assertNotNull(mapping);
		assertEquals(url, mapping.getUrl());
		assertNotNull(location);
	}
    
	@Test
	public void shortExistingUrl() throws Exception {
	
		String url = "http://dn.se";
		String key = "123456";
	
		repo.save(new URLMapping(key, url));
	
		ResponseEntity<?> response = controller.shortUrl(new URLMapping(null, url), UriComponentsBuilder.newInstance());
		String location = response.getHeaders().get("location").get(0);
		String actualKey = location.substring(1);
	
		assertEquals(key, actualKey);

		response = controller.shortUrl(new URLMapping(null, url), UriComponentsBuilder.newInstance());
		location = response.getHeaders().get("location").get(0);
		actualKey = location.substring(1);
	
		assertEquals(key, actualKey);
	}
    
	@Test(expected = URLNotFoundException.class)
	public void redirectNotFound() throws Exception {
	
		controller.redirect("not-exits", null);
	}
    
	@Test
	public void redirect() throws Exception {
	
		String url = "http://dn.se";
		String key = "123456";
	
		repo.save(new URLMapping(key, url));

		MockHttpServletResponse response = new MockHttpServletResponse();
		controller.redirect(key, response);
	
		assertEquals(url, response.getRedirectedUrl());
	}
}