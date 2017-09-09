package com.sticure.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.sticure.SticureDB;
import com.sticure.model.SecurityEvent;
import com.sticure.model.SecurityEvents;

@RestController
public class SecurityController {
	@Autowired
    private ApplicationContext applicationContext;
	
	
	@RequestMapping("/getSecurityEvents")
	public String getEvents() throws FileNotFoundException, IOException {
		SticureDB db = applicationContext.getBean(SticureDB.class);
		Gson gson = new Gson();
		return gson.toJson(db.loadData(), SecurityEvents.class);
	}
	
	@RequestMapping(value = "/reportEvent", method = RequestMethod.POST)
	public void notifyOrderRecieved(@RequestBody SecurityEvent securityEvent)
			throws FileNotFoundException, IOException {
		SticureDB db = applicationContext.getBean(SticureDB.class);
		db.saveData(securityEvent);
	}
}
