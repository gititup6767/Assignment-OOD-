package ood_assignment;

import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

public class EventService {
    private Map<String, Event> eventDatabase;
    
    public EventService(){
        this.eventDatabase = new HashMap<>();
        
        // Added the date "2026-12-31" to the default event
        ConcertEvent sampleConcert = new ConcertEvent("EVT-001", "Rock Concert", "2026-12-31", "Stadium Merdeka", 150.00, 500, true, 150, true, 300.00, "VIP Rock Zone", "Muse");
        eventDatabase.put(sampleConcert.eventID, sampleConcert);
    }

    public Event getEvent(String eventID){ return eventDatabase.get(eventID); }
    public Collection<Event> getAllEvents(){ return eventDatabase.values(); }

    public boolean addEventToDatabase(Event event) {
        if (eventDatabase.containsKey(event.eventID)) return false; 
        eventDatabase.put(event.eventID, event);
        return true;
    }

    public boolean deleteEvent(String eventID) {
        if (eventDatabase.containsKey(eventID)) {
            eventDatabase.remove(eventID);
            return true;
        }
        return false;
    }
}