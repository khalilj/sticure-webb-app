package com.sticure.model;

import java.util.ArrayList;
import java.util.List;

public class SecurityEvents {
	private List<SecurityEvent> events = new ArrayList<SecurityEvent>();

	public List<SecurityEvent> getEvents() {
		return events;
	}

	public void setEvents(List<SecurityEvent> events) {
		this.events = events;
	}
}
