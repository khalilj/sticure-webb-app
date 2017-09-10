package com.sticure.model;

import java.util.Date;

public class SecurityEvent {
	private int id;
	private String type;
	private String title;
	private Date time;
	private double lat;
	private double lon;
	private String imgUrl;
	private boolean checked = false;
	
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
