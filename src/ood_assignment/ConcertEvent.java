package ood_assignment;

public class ConcertEvent extends Event {
    private String vipConfig;
    private String artistName;

    public ConcertEvent(String eventID, String title, String venueName, double baseTicketPrice, int maxCapacity, String vipConfig, String artistName){
        super(eventID, title, venueName, baseTicketPrice, maxCapacity);
        this.vipConfig = vipConfig;
        this.artistName = artistName;
    }

    @Override
    public void displayDesc(){
        System.out.println("<--- Concert Event --->");
        super.displayDesc();
        System.out.println("Artist: " + artistName + " | VIP Config: " + vipConfig + "." );
    }

    public void configVIPSeats(int vipCapacity){
        System.out.println("Configuring " +vipCapacity + " VIP seats for artist " + artistName + ".");
    }
}
