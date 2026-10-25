package ood_assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Attendee {
    private String accountUserKey;
    private String username; 
    private String password; 
    private String fullName;
    private String email;
    private String phoneNumber; 
    private int loyaltyPointStatus;
    
    private List<TicketReservation> bookingHistory;

    public Attendee(String username, String password, String fullName, String email, String phoneNumber) {
        this.accountUserKey = "U-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        // Silently removes any spaces the user typed
        this.phoneNumber = phoneNumber.replace(" ", ""); 
        this.loyaltyPointStatus = 0; 
        this.bookingHistory = new ArrayList<>();
    }

    public void inspectUpcomingSchedule() {
        System.out.println(this.fullName + " is inspecting the upcoming event schedule...");
    }

    public void makeTicketReservation(TicketReservation reservation) {
        this.bookingHistory.add(reservation);
        int pointsEarned = (int) reservation.getFinalPrice(); 
        this.loyaltyPointStatus += pointsEarned; 
        
        System.out.println("Reservation added to account.");
        if (pointsEarned > 0) {
            System.out.println("🎉 You earned " + pointsEarned + " loyalty points! Current Balance: " + this.loyaltyPointStatus);
        }
    }
    
    public void deductLoyaltyPoints(int points) {
        if (this.loyaltyPointStatus >= points) {
            this.loyaltyPointStatus -= points;
            System.out.println("Loyalty points deducted. Remaining balance: " + this.loyaltyPointStatus);
        }
    }

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

    // --- NEW SETTERS FOR PROFILE UPDATES ---
    public void setEmail(String email) {
        this.email = email;
        System.out.println("✅ Email updated successfully.");
    }

    public void setPhoneNumber(String phoneNumber) {
        // Silently removes spaces on update as well
        this.phoneNumber = phoneNumber.replace(" ", "");
        System.out.println("✅ Phone number updated successfully.");
    }

    public String getAccountUserKey() { return accountUserKey; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public int getLoyaltyPointStatus() { return loyaltyPointStatus; }
    public List<TicketReservation> getBookingHistory() { return bookingHistory; }
}