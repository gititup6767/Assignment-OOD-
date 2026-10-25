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
    private Event event;

    private List<DigitalTicket> digitalTickets;
    private Payment payment;
    private Attendee attendee;

    public TicketReservation(Attendee attendee, Event event, String[] uniqueSeatAssignments) {
        this.reservationID = "RES-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.attendee = attendee;
        this.event = event;
        this.uniqueSeatAssignments = uniqueSeatAssignments;
        this.bookingTimestamp = LocalDateTime.now();
        this.digitalTickets = new ArrayList<>();
    }

    public Event getEvent(){ return event; }

    // Updated to accept the specific price (Base or VIP) chosen by the user
    public double calculateFinalPrice(double pricePerTicket, double earlyBirdDiscount, int freeTicketsRedeemed) {
        int payableSeats = Math.max(0, uniqueSeatAssignments.length - freeTicketsRedeemed);
        double totalBase = pricePerTicket * payableSeats;
        
        this.finalPrice = totalBase - (totalBase * earlyBirdDiscount);
        return this.finalPrice;
    }

    public boolean confirmPayment(String paymentDetails, double amountPaid) {
        this.payment = new Payment(this.reservationID, amountPaid, paymentDetails);
        
        if (this.payment.processTransaction() || amountPaid == 0.0) {
            generateTickets(); 
            this.event.updateAvailableSeatCount(this.event.getAvailableSeats() - uniqueSeatAssignments.length);
            return true;
        }
        return false;
    }

    private void generateTickets() {
        for (String seat : uniqueSeatAssignments) {
            digitalTickets.add(new DigitalTicket(this.reservationID, seat));
        }
    }

    public String getReservationID() { return reservationID; }
    public String[] getUniqueSeatAssignments() { return uniqueSeatAssignments; }
    public LocalDateTime getBookingTimestamp() { return bookingTimestamp; }
    public double getFinalPrice() { return finalPrice; }
    public List<DigitalTicket> getDigitalTickets() { return digitalTickets; }
    public Payment getPayment() { return payment; }
    public Attendee getAttendee() { return attendee; }
}