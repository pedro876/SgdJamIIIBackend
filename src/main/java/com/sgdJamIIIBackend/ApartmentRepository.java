package com.sgdJamIIIBackend;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Component;

@Component
public interface ApartmentRepository extends MongoRepository<Apartment, String> {
	
	@Override
	public List<Apartment> findAll();
	
	/*@Query{"{$and :[{bathroomState: {$lt: 2}},{kitchenState: {$lt: 2}},{bedroomState: {$lt: 2}},{livingRoomState: {$lt: 2}}]}"}
	public List<Apartment> getUnfinishedApartments();*/
	
	@Query("{$or :[{bathroomState: 0},{kitchenState: 0},{bedroomState: 0},{livingRoomState: 0}]}")
	public List<Apartment> findUnfinishedApartments();
	
	@Query("{$and :[{bathroomState: 2},{kitchenState: 2},{bedroomState: 2},{livingRoomState: 2}]}")
	public List<Apartment> findFinishedApartments();
	
	@Query(value = "{$and :[{bathroomState: 2},{kitchenState: 2},{bedroomState: 2},{livingRoomState: 2}]}", sort = "{stars:-1}")
	public List<Apartment> findRanking(int count);
}
