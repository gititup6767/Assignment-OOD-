package ood_assignment;

public class ConferenceEvent extends Event{
    private String scheduleType;
    private String keynoteSpeaker;

    public ConferenceEvent(String eventID, String title, String venueName, double baseTicketPrice, int maxCapacity, String scheduleType, String keynoteSpeaker){
        super(eventID, title, venueName, baseTicketPrice, maxCapacity);
        this.scheduleType = scheduleType;
        this.keynoteSpeaker = keynoteSpeaker;
    }

    @Override
    public void displayDesc(){
        System.out.println("<--- Conference Event --->");
        super.displayDesc();
        System.out.println("Schedule Type: " + scheduleType + " | Keynote Speaker: " + keynoteSpeaker + "." );
    }

    public void setKeynoteSpeaker(String speaker){
        this.keynoteSpeaker = speaker;
        System.out.println("Keynote Speaker updated to: " + speaker + ".");
    }
}
