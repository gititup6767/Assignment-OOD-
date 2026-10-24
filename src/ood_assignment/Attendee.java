package ood_assignment;

import java.util.ArrayList;
import java.util.List;

public class Attendee {
    private String accountUserKey;
    private String fullName;
    private String email;
    private String phoneNumber; // Added based on your text requirement
    private int loyaltyPointStatus;
    
    // Aggregation: Attendee holds a history of their reservations
    private List<TicketReservation> bookingHistory;

    public Attendee(String accountUserKey, String fullName, String email, String phoneNumber) {
        this.accountUserKey = accountUserKey;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.loyaltyPointStatus = 0; 
        this.bookingHistory = new ArrayList<>();
    }

    // Matches UML: inspectUpcomingSchedule(): void
    public void inspectUpcomingSchedule() {
        System.out.println(this.fullName + " is inspecting the upcoming event schedule...");
        // The actual display logic will be handled by the UI connecting to your partner's Event system.
    }

    // Matches UML: makeTicketReservation(): void
    // Note: Modified slightly to accept the generated reservation to link it to the user and yes it is a suggestion from AI 
    public void makeTicketReservation(TicketReservation reservation) {
        this.bookingHistory.add(reservation);
        this.loyaltyPointStatus += 10; // Reward points for completing a booking
        System.out.println("Reservation added to account. Current Loyalty Points: " + this.loyaltyPointStatus);
    }

    // Matches UML: reviewBookingHistory(): void
    public void reviewBookingHistory() {
        System.out.println("=== Booking History for " + this.fullName + " ===");
        if (bookingHistory.isEmpty()) {
            System.out.println("No past reservations found.");
        } else {
            for (TicketReservation res : bookingHistory) {
                System.out.println("Reservation ID: " + res.getReservationID() + 
                                   " | Paid: MYR " + String.format("%.2f", res.getFinalPrice()) + 
                                   " | Booked on: " + res.getBookingTimestamp());
            }
        }
        System.out.println("=====================================");
    }

    // Getters for external access
    public String getAccountUserKey() { return accountUserKey; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public int getLoyaltyPointStatus() { return loyaltyPointStatus; }
    public List<TicketReservation> getBookingHistory() { return bookingHistory; }
}