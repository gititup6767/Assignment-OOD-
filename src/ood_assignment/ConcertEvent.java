package ood_assignment;

public class ConcertEvent extends Event {
    private String vipConfig;
    private String artistName;

    public ConcertEvent(String eventID, String title, String eventDate, String venueName, double baseTicketPrice, int maxCapacity, boolean allowsLoyaltyRedemption, int pointsRequiredForRedemption, boolean hasVipTickets, double vipTicketPrice, String vipConfig, String artistName){
        super(eventID, title, eventDate, venueName, baseTicketPrice, maxCapacity, allowsLoyaltyRedemption, pointsRequiredForRedemption, hasVipTickets, vipTicketPrice);
        this.vipConfig = vipConfig;
        this.artistName = artistName;
    }

    @Override
    public void displayDesc(){
        System.out.println("<--- Concert Event --->");
        super.displayDesc();
        System.out.println("Artist: " + artistName + " | VIP Config: " + vipConfig);
    }

    public String getArtistName() { return artistName; }
    public void setArtistName(String artistName) { this.artistName = artistName; }
}