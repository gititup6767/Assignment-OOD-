package ood_assignment;

public class ConferenceEvent extends Event {
    private String scheduleType;
    private String keynoteSpeaker;

    public ConferenceEvent(String eventID, String title, String eventDate, String venueName, double baseTicketPrice, int maxCapacity, boolean allowsLoyaltyRedemption, int pointsRequiredForRedemption, boolean hasVipTickets, double vipTicketPrice, String scheduleType, String keynoteSpeaker){
        super(eventID, title, eventDate, venueName, baseTicketPrice, maxCapacity, allowsLoyaltyRedemption, pointsRequiredForRedemption, hasVipTickets, vipTicketPrice);
        this.scheduleType = scheduleType;
        this.keynoteSpeaker = keynoteSpeaker;
    }

    @Override
    public void displayDesc(){
        System.out.println("<--- Conference Event --->");
        super.displayDesc();
        System.out.println("Schedule Type: " + scheduleType + " | Keynote Speaker: " + keynoteSpeaker);
    }

    public String getScheduleType() { return scheduleType; }
    public void setScheduleType(String scheduleType) { this.scheduleType = scheduleType; }

    public String getKeynoteSpeaker() { return keynoteSpeaker; }
    public void setKeynoteSpeaker(String keynoteSpeaker) { this.keynoteSpeaker = keynoteSpeaker; }
}