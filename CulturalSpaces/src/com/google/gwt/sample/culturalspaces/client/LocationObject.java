package com.google.gwt.sample.culturalspaces.client;

import javax.jdo.annotations.Persistent;

public class LocationObject {


	private String name;
	private String type;
	private String address;
	private String neighbourhood;
	private String lon;
	private String lat;



	public LocationObject() {
	}

	public LocationObject(String name, String address, String type, String neighbourhood, String lon, String lat) {
		this.name = name;
		this.address = address;
		this.type = type;
		this.neighbourhood = neighbourhood;
		this.lon = lon;
		this.lat = lat;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNeighbourhood() {
		return neighbourhood;
	}

	public void setNeighbourhood(String neighbourhood) {
		this.neighbourhood = neighbourhood;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

}
