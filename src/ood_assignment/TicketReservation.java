package ood_assignment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TicketReservation {
    private String reservationID;
    private String[] uniqueSeatAssignments;
    private LocalDateTime bookingTimestamp;
    private double finalPrice;

    // Composition: One reservation owns 1 to many DigitalTickets and 1 Payment
    private List<DigitalTicket> digitalTickets;
    private Payment payment;

    // Aggregation: The reservation belongs to an Attendee
    private Attendee attendee;

    public TicketReservation(Attendee attendee, String[] uniqueSeatAssignments) {
        this.reservationID = "RES-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.attendee = attendee;
        this.uniqueSeatAssignments = uniqueSeatAssignments;
        this.bookingTimestamp = LocalDateTime.now();
        this.digitalTickets = new ArrayList<>();
    }

    public double calculateFinalPrice(double basePrice, double earlyBirdDiscount) {
        double totalBase = basePrice * uniqueSeatAssignments.length;
        this.finalPrice = totalBase - (totalBase * earlyBirdDiscount);
        return this.finalPrice;
    }

    public boolean confirmPayment(String paymentDetails, double amountPaid) {
        this.payment = new Payment(this.reservationID, amountPaid, paymentDetails);
        
        if (this.payment.processTransaction()) {
            generateTickets(); 
            return true;
        }
        return false;
    }

    private void generateTickets() {
        for (String seat : uniqueSeatAssignments) {
            digitalTickets.add(new DigitalTicket(this.reservationID, seat));
        }
    }

    // Getters for external access
    public String getReservationID() { return reservationID; }
    public String[] getUniqueSeatAssignments() { return uniqueSeatAssignments; }
    public LocalDateTime getBookingTimestamp() { return bookingTimestamp; }
    public double getFinalPrice() { return finalPrice; }
    public List<DigitalTicket> getDigitalTickets() { return digitalTickets; }
    public Payment getPayment() { return payment; }
    public Attendee getAttendee() { return attendee; }
}