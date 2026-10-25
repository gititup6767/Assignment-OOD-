package ood_assignment;

import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

public class EventService {
    private Map<String, Event> eventDatabase;
    
    public EventService(){
        this.eventDatabase = new HashMap<>();

        ConcertEvent sampleConcert = new ConcertEvent("EVT-001", "Rock Concert", "Stadium Merdeka", 150.00, 500, "VIP Rock Zone", "Muse");
        eventDatabase.put(sampleConcert.eventID, sampleConcert);
    }

    public Event getEvent(String eventID){
        return eventDatabase.get(eventID);
    }

    public Collection<Event> getAllEvents(){
        return eventDatabase.values();
    }

    public void updateEventCapacity(String eventID, int newCapacity){
        Event event = eventDatabase.get(eventID);
        if (event != null) {
            event.updateAvailableSeatCount(newCapacity);
            System.out.println("Event '" + event.title + "' Added Successfully");
        }
        else {
            System.out.println("Event not found.");
        }
    }
}
