package ood_assignment;

public abstract class Event {
    protected String eventID;
    protected String title;
    protected String eventDate; // New field
    protected String venueName;
    protected double baseTicketPrice;
    protected int maxCapacity;
    protected int availableSeats;
    
    protected boolean allowsLoyaltyRedemption; 
    protected int pointsRequiredForRedemption; 
    
    protected boolean hasVipTickets;
    protected double vipTicketPrice;

    public Event(String eventID, String title, String eventDate, String venueName, double baseTicketPrice, int maxCapacity, boolean allowsLoyaltyRedemption, int pointsRequiredForRedemption, boolean hasVipTickets, double vipTicketPrice){
        this.eventID = eventID;
        this.title = title;
        this.eventDate = eventDate;
        this.venueName = venueName;
        this.baseTicketPrice = baseTicketPrice;
        this.maxCapacity = maxCapacity;
        this.availableSeats = maxCapacity;
        this.allowsLoyaltyRedemption = allowsLoyaltyRedemption;
        this.pointsRequiredForRedemption = pointsRequiredForRedemption;
        this.hasVipTickets = hasVipTickets;
        this.vipTicketPrice = vipTicketPrice;
    }

    public void displayDesc(){
        System.out.println("Event ID: " + eventID + " | Title: " + title + " | Date: " + eventDate);
        System.out.println("Venue: " + venueName + " | Available Seats: " + availableSeats + "/"  + maxCapacity);
        System.out.println("Base Price: MYR " + baseTicketPrice);
        if (hasVipTickets) {
            System.out.println("VIP Tickets Available at MYR " + vipTicketPrice);
        }
        if (allowsLoyaltyRedemption) {
            System.out.println("Loyalty Redemption: Yes (" + pointsRequiredForRedemption + " points per free ticket)");
        } else {
            System.out.println("Loyalty Redemption: No");
        }
    }

    public void updateAvailableSeatCount(int seats){ this.availableSeats = seats; } 
    public int getAvailableSeats(){ return this.availableSeats; }
    public boolean isLoyaltyRedemptionAllowed() { return allowsLoyaltyRedemption; }
    public int getPointsRequiredForRedemption() { return pointsRequiredForRedemption; }
    public boolean hasVipTickets() { return hasVipTickets; }
    public double getVipTicketPrice() { return vipTicketPrice; }
    public double getBaseTicketPrice() { return baseTicketPrice; }
    
    public String getTitle() { return title; }
    public String getEventDate() { return eventDate; }
    public String getVenueName() { return venueName; }
    public int getMaxCapacity() { return maxCapacity; }

    public void setTitle(String title) { this.title = title; }
    public void setEventDate(String eventDate) { this.eventDate = eventDate; }
    public void setVenueName(String venueName) { this.venueName = venueName; }
    public void setBaseTicketPrice(double baseTicketPrice) { this.baseTicketPrice = baseTicketPrice; }
    
    public void setMaxCapacity(int maxCapacity) { 
        int difference = maxCapacity - this.maxCapacity;
        this.maxCapacity = maxCapacity; 
        this.availableSeats += difference;
    }
}