package com.sticure.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@RequestMapping(value = "/dummyReportEvent/{eventType}", method = RequestMethod.POST)
	public void dummyReportEvent(@PathVariable("eventType") String eventType)
			throws FileNotFoundException, IOException {
		SticureDB db = applicationContext.getBean(SticureDB.class);
		SecurityEvent securityEvent = getDummyEvent(eventType);
		db.saveData(securityEvent);
	}
	
	@RequestMapping(value = "/setEventChecked/{eventId}", method = RequestMethod.POST)
	public void setEventChecked(@PathVariable("eventId") String eventId)
			throws FileNotFoundException, IOException {
		SticureDB db = applicationContext.getBean(SticureDB.class);
		db.setEventChecked(eventId);
	}
	
	private SecurityEvent getDummyEvent(String eventType) {
        SecurityEvent event = new SecurityEvent();
        event.setId(getRandomId());
        setPossition(event);
        event.setTime(new Date());

        switch (eventType){
            case "G":
                event.setTitle("Gun Shot At Naz");
                event.setType("Crime");
                event.setImgUrl("{ url:\u0027img/pistol.png\u0027, scaledSize:[32,40], origin: [0,0], anchor: [16,40] }");
                break;
            case "F":
                event.setTitle("Fire at Naz");
                event.setType("Fire");
                event.setImgUrl("{ url:\u0027img/fire.png\u0027, scaledSize:[32,40], origin: [0,0], anchor: [16,40] }");
                break;
        }

        return event;
    }
	
	private int getRandomId() {
        return 1000 + (int) (Math.random() * ((Integer.MAX_VALUE - 1000) + 1));
    }

    private void setPossition(SecurityEvent event) {
        List<String> positions = getPossitions();
        int randomPosInd = (int) (Math.random() * positions.size());
        String[] latLon = positions.get(randomPosInd).split(":");
        event.setLat(Double.valueOf(latLon[0]));
        event.setLon(Double.valueOf(latLon[1]));
    }

    private List<String> getPossitions() {
        List<String> list = new ArrayList<>();
        list.add("32.696002:35.301039");
        list.add("32.707126:35.301647");
        list.add("32.693525:35.303422");
        list.add("32.6960018:35.3010389");
        list.add("32.6805991:35.2921629");
        list.add("32.68475298:35.27765751");
        list.add("32.69374638:35.29950142");
        list.add("32.69327687:35.29950142");
        list.add("32.70129436:35.2958107");
        list.add("32.7106833:35.3000164");
        list.add("32.71321092:35.30662537");
        list.add("32.70685562:35.28538227");
        list.add("32.70447226:35.31198978");
        list.add("32.7085032:35.2969909");

        return list;
    }
}
