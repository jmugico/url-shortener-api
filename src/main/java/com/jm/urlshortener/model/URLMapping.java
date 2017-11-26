package com.jm.urlshortener.model;

import org.springframework.data.annotation.Id;

/**
 * URL mapping entity. 
 * @author jmugico
 */
public class URLMapping {

	@Id
	private String id;
    
	/**
	 * Identifier for the short URL. http://localhost:8080/{key}
	 */
	private String key;
    
	/**
	 * Long URL
	 */
	private String url;
    
	public URLMapping() {}
    
	public URLMapping(String key, String url) {
		this.key = key;
		this.url = url;
	}

	public String getKey() {
		return key;
	}
	public String getUrl() {
		return url;
	}

	@Override
	public String toString() {
		return String.format(
			"URLMapping[id=%s, key='%s', url='%s']",
			id, key, url);
	}
}