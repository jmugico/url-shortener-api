package com.jm.urlshortener.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jm.urlshortener.model.URLMapping;

/**
 * URL mapping repository interface. Used to handle mappings in mongo db.
 * @author jmugico
 */
public interface URLMappingRepository extends MongoRepository<URLMapping, String> {

	public URLMapping findByKey(String key);
	public URLMapping findByUrl(String url);
}