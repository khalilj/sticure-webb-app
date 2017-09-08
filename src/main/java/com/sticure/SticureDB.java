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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amdocs.tenbis.model.SticureEvents;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

	public SticureEvents loadData() throws FileNotFoundException, IOException {
		synchronized (log) {
			return privateLoadData();
		}
	}
	private SticureEvents privateLoadData() throws IOException, FileNotFoundException {
		log.info("Reading file: " + sticureJsonFile);
		if (! sticureJsonFile.exists()){
			return new SticureEvents();
		}
		
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(sticureJsonFile), StandardCharsets.UTF_8));) {
			Gson gson = new Gson();
			SticureEvents sticurityEvents = gson.fromJson(reader, SticureEvents.class);
			if (sticurityEvents == null){
				sticurityEvents = new SticureEvents();
			}
			return sticurityEvents;
		}
	}
	public void saveData(SticureEvents sticureEvents) throws IOException {
		synchronized (log) {
			privateSaveData(sticureEvents);
		}
	}
	private void privateSaveData(SticureEvents sticureEvents) throws IOException {
		log.info("Writing to file: " + sticureJsonFile);
		try (Writer writer = new FileWriter(sticureJsonFile)) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(sticureEvents, writer);
		}
	}
//	public void updateResturantDelivered(String resturantName) throws FileNotFoundException, IOException {
//		synchronized (log) {
//			log.info("Updating returant deliverred for resturant: " + resturantName);
//			OrdersSummary ordersSummary = privateLoadData();
//			ordersSummary.getResturantsOrders().get(resturantName).setDeliveryArrived(true);
//			ordersSummary.getResturantsOrders().get(resturantName).setDeliveryArrivedDate(new Date());
//			privateSaveData(ordersSummary);
//		}
//	}
}