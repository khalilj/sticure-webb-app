package com.sticure;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sticure.model.SecurityEvent;
import com.sticure.model.SecurityEvents;
@Component
@Scope(value = "prototype")
public class SticureDB {
	private static final Logger log = LoggerFactory.getLogger(SticureDB.class);
	@Value("${sticure.json.path}")
	private String restJsonPath;
	
	private File sticureJsonFile = null;
	@Autowired
	private void init() {
		sticureJsonFile = new File(restJsonPath);
	}

	public SecurityEvents loadData() throws FileNotFoundException, IOException {
		synchronized (log) {
			return privateLoadData();
		}
	}
	private SecurityEvents privateLoadData() throws IOException, FileNotFoundException {
		log.info("Reading file: " + sticureJsonFile);
		if (! sticureJsonFile.exists()){
			return new SecurityEvents();
		}
		
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(sticureJsonFile), StandardCharsets.UTF_8));) {
			Gson gson = new Gson();
			SecurityEvents sticurityEvents = gson.fromJson(reader, SecurityEvents.class);
			if (sticurityEvents == null){
				sticurityEvents = new SecurityEvents();
			}
			return sticurityEvents;
		}
	}
	public void saveData(SecurityEvent event) throws IOException {
		synchronized (log) {
			privateSaveData(event);
		}
	}
	private void privateSaveData(SecurityEvent event) throws IOException {
		log.info("Writing to file: " + sticureJsonFile);
		SecurityEvents securityEvents = privateLoadData();
		securityEvents.getEvents().add(event);
		try (Writer writer = new FileWriter(sticureJsonFile)) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(securityEvents, writer);
		}
	}

	public void setEventChecked(String eventId) throws FileNotFoundException, IOException {
		synchronized (log) {
			SecurityEvents events = privateLoadData();
			for (SecurityEvent event : events.getEvents()){
				if (event.getId() == Integer.valueOf(eventId)){
					event.setChecked(true);
					break;
				}
			}
			
			privateSaveData(events);
		}
	}

	private void privateSaveData(SecurityEvents events) throws IOException {
		try (Writer writer = new FileWriter(sticureJsonFile)) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(events, writer);
		}
	}
}