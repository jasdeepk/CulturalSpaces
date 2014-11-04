package com.google.gwt.sample.culturalspaces.client;

import javax.jdo.annotations.Persistent;

public class LocationName {


	private String name;
	private String type;
	private String address;
	private String neighbourhood;


	public LocationName() {
	}

	public LocationName(String name, String address, String type, String neighbourhood) {
		this.name = name;
		this.address = address;
		this.type = type;
		this.neighbourhood = neighbourhood;
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

}
