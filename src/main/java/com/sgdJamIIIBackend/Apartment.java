package com.sgdJamIIIBackend;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Apartments")
public class Apartment {

	
	@Id
	private String id;
	private String description;
	
	public Apartment(String id) {
		this.id = id;
		this.description = "";
	}
	
	public Apartment(String id, String description) {
		this.id = id;
		this.description = description;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String _id) {
		id = _id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
