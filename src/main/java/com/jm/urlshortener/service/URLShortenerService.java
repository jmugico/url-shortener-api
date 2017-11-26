package com.jm.urlshortener.service;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jm.urlshortener.exceptions.URLFormatException;
import com.jm.urlshortener.exceptions.URLNotFoundException;
import com.jm.urlshortener.model.URLMapping;
import com.jm.urlshortener.repository.URLMappingRepository;
import com.jm.urlshortener.utils.KeyGenerator;
/**
 * URL shortener service
 * @author jmugico
 */
@Service
public class URLShortenerService {
    
	@Autowired
	URLMappingRepository repo;

	/**
	 * Returns a key for a given long URL. 
	 * If the long URL does not exists in the system, a new mapping key-url will be created.
	 * @param long URL
	 * @return URL key for the given long URL.
	 * @throws URLFormatException if the URL is malformed.
	 */
	public String getURLKey(String url) throws URLFormatException {

		if(!validateURL(url)) {
			throw new URLFormatException(url);
		}
	
		String result = "";
	
		URLMapping mapping = repo.findByUrl(url);
		if(mapping == null) {
			result = generateNewKey();
			mapping = new URLMapping(result, url);
			repo.save(mapping);
		} else {
			result = mapping.getKey();
		}
	
		return result;
	}
    
	/**
	 * Returns the URL for a given key.
	 * @param key
	 * @return long URL
	 * @throws URLNotFoundException if the key is not found.
	 */
	public String getURL(String key) throws URLNotFoundException {
	
		URLMapping mapping = repo.findByKey(key);
	
		if(mapping == null) {
			throw new URLNotFoundException(key);
		}
	
		return mapping.getUrl();
	}
    
	/**
	 * Generates a new key which does not exists in the system yet.
	 * @return new key
	 */
	private String generateNewKey() {
	
		String key = null;
		boolean valid = false;
		while (!valid) {
			key = KeyGenerator.generateKey();
			if (repo.findByKey(key) == null) {
				valid = true;
			}
		}
		return key;
	}
    
	/**
	 * Validate that a URL is well-formed.
	 * @param _url
	 * @return true is the given URL is valid, false if not.
	 */
	private boolean validateURL(String url) {
	
		try {
			new URL(url);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}