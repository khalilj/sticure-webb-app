package com.amdocs.tenbis.model;

import java.util.Date;

public class SecurityEvent {
	private String type;
	private Date time;
	private double lat;
	private double lon;
	private String imgUrl;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double d) {
		this.lat = d;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double d) {
		this.lon = d;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
}
