package com.sgdJamIIIBackend;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface ApartmentRepository extends MongoRepository<Apartment, String> {
	
	@Override
	public List<Apartment> findAll();
}
