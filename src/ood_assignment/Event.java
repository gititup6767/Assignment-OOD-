package ood_assignment;

public abstract class Event {
    protected String eventID;
    protected String title;
    protected String venueName;
    protected double baseTicketPrice;
    protected int maxCapacity;
    protected int availableSeats;

    public Event(String eventID, String title, String venueName, double baseTicketPrice, int maxCapacity){
        this.eventID = eventID;
        this.title = title;
        this.venueName = venueName;
        this.baseTicketPrice = baseTicketPrice;
        this.maxCapacity = maxCapacity;
        this.availableSeats = maxCapacity;
    }

    public void displayDesc(){
        System.out.println("Event ID: " + eventID + "| Title: " + title + "at " + venueName + ".");
        System.out.println("Base Price: " + baseTicketPrice + "| Available Seats: " + availableSeats + "/"  + maxCapacity + ".");
    }

    public void updateAvailableSeatCount(int seats){
        this.availableSeats = seats;
    } 

    public int getAvailableSeats(){
        return this.availableSeats;
    }
}
