package com.sgdJamIIIBackend;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Apartments")
public class Apartment {

	
	@Id
	private String id;
	
	private int stars;
	
	private int bathroomState;
	private int kitchenState;
	private int bedroomState;
	private int livingRoomState;

	private String bathroomMsg;
	private String kitchenMsg;
	private String bedroomMsg;
	private String livingRoomMsg;

	private String bathroomAuthor;
	private String kitchenAuthor;
	private String bedroomAuthor;
	private String livingRoomAuthor;

	private String bathroomCode;
	private String kitchenCode;
	private String bedroomCode;
	private String livingRoomCode;

	public Apartment() {
		id = "";
		stars = 0;
		
		bathroomState = 0;
		kitchenState = 0;
		bedroomState = 0;
		livingRoomState = 0;

		bathroomMsg= "";
		kitchenMsg= "";
		bedroomMsg= "";
		livingRoomMsg= "";

		bathroomAuthor= "";
		kitchenAuthor= "";
		bedroomAuthor= "";
		livingRoomAuthor= "";

		bathroomCode= "";
		kitchenCode= "";
		bedroomCode= "";
		livingRoomCode= "";
	}

	public Apartment(String id, int stars, int bathroomState, int kitchenState, int bedroomState, int livingRoomState,
			String bathroomMsg, String kitchenMsg, String bedroomMsg, String livingRoomMsg, String bathroomAuthor,
			String kitchenAuthor, String bedroomAuthor, String livingRoomAuthor, String bathroomCode,
			String kitchenCode, String bedroomCode, String livingRoomCode) {
		super();
		this.id = id;
		this.stars = stars;
		this.bathroomState = bathroomState;
		this.kitchenState = kitchenState;
		this.bedroomState = bedroomState;
		this.livingRoomState = livingRoomState;
		this.bathroomMsg = bathroomMsg;
		this.kitchenMsg = kitchenMsg;
		this.bedroomMsg = bedroomMsg;
		this.livingRoomMsg = livingRoomMsg;
		this.bathroomAuthor = bathroomAuthor;
		this.kitchenAuthor = kitchenAuthor;
		this.bedroomAuthor = bedroomAuthor;
		this.livingRoomAuthor = livingRoomAuthor;
		this.bathroomCode = bathroomCode;
		this.kitchenCode = kitchenCode;
		this.bedroomCode = bedroomCode;
		this.livingRoomCode = livingRoomCode;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}
	
	


	public int getStars() {
		return stars;
	}


	public void setStars(int stars) {
		this.stars = stars;
	}


	public int getBathroomState() {
		return bathroomState;
	}


	public void setBathroomState(int bathroomState) {
		this.bathroomState = bathroomState;
	}


	public int getKitchenState() {
		return kitchenState;
	}


	public void setKitchenState(int kitchenState) {
		this.kitchenState = kitchenState;
	}


	public int getBedroomState() {
		return bedroomState;
	}


	public void setBedroomState(int bedroomState) {
		this.bedroomState = bedroomState;
	}


	public int getLivingRoomState() {
		return livingRoomState;
	}


	public void setLivingRoomState(int livingRoomState) {
		this.livingRoomState = livingRoomState;
	}


	public String getBathroomMsg() {
		return bathroomMsg;
	}


	public void setBathroomMsg(String bathroomMsg) {
		this.bathroomMsg = bathroomMsg;
	}


	public String getKitchenMsg() {
		return kitchenMsg;
	}


	public void setKitchenMsg(String kitchenMsg) {
		this.kitchenMsg = kitchenMsg;
	}


	public String getBedroomMsg() {
		return bedroomMsg;
	}


	public void setBedroomMsg(String bedroomMsg) {
		this.bedroomMsg = bedroomMsg;
	}


	public String getLivingRoomMsg() {
		return livingRoomMsg;
	}


	public void setLivingRoomMsg(String livingRoomMsg) {
		this.livingRoomMsg = livingRoomMsg;
	}


	public String getBathroomAuthor() {
		return bathroomAuthor;
	}


	public void setBathroomAuthor(String bathroomAuthor) {
		this.bathroomAuthor = bathroomAuthor;
	}


	public String getKitchenAuthor() {
		return kitchenAuthor;
	}


	public void setKitchenAuthor(String kitchenAuthor) {
		this.kitchenAuthor = kitchenAuthor;
	}


	public String getBedroomAuthor() {
		return bedroomAuthor;
	}


	public void setBedroomAuthor(String bedroomAuthor) {
		this.bedroomAuthor = bedroomAuthor;
	}


	public String getLivingRoomAuthor() {
		return livingRoomAuthor;
	}


	public void setLivingRoomAuthor(String livingRoomAuthor) {
		this.livingRoomAuthor = livingRoomAuthor;
	}


	public String getBathroomCode() {
		return bathroomCode;
	}


	public void setBathroomCode(String bathroomCode) {
		this.bathroomCode = bathroomCode;
	}


	public String getKitchenCode() {
		return kitchenCode;
	}


	public void setKitchenCode(String kitchenCode) {
		this.kitchenCode = kitchenCode;
	}


	public String getBedroomCode() {
		return bedroomCode;
	}


	public void setBedroomCode(String bedroomCode) {
		this.bedroomCode = bedroomCode;
	}


	public String getLivingRoomCode() {
		return livingRoomCode;
	}


	public void setLivingRoomCode(String livingRoomCode) {
		this.livingRoomCode = livingRoomCode;
	}
	
}
