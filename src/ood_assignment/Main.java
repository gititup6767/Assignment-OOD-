package ood_assignment;

public class Main {
    public static void main(String[] args) {
        // 1. Initialize our in-memory database service
        AttendeeService attendeeService = new AttendeeService();

        System.out.println("=== 1. TESTING REGISTRATION ===");
        // Register a new attendee with legal name, email, and phone number
        Attendee newAttendee = attendeeService.registerAttendee("Ali bin Abu", "ali@example.com", "012-3456789");

        System.out.println("\n=== 2. TESTING LOGIN ===");
        // Simulate the user logging in with the key they just received
        Attendee loggedInUser = attendeeService.loginAttendee(newAttendee.getAccountUserKey());

        if (loggedInUser != null) {
            System.out.println("\n=== 3. SIMULATING TICKET RESERVATION ===");
            
            // Mocking Event class
            String[] chosenSeats = {"CAT1-A01", "CAT1-A02"}; // User selects 2 Category 1 seats
            double basePrice = 200.00; // Base price is 200 MYR per seat
            double earlyBirdDiscount = 0.15; // 15% discount for early bird
            
            // Step A: Initialize the Reservation
            TicketReservation reservation = new TicketReservation(loggedInUser, chosenSeats);
            
            // Step B: Calculate Price
            double finalAmount = reservation.calculateFinalPrice(basePrice, earlyBirdDiscount);
            System.out.println("Total price after 15% discount for " + chosenSeats.length + " seats: MYR " + String.format("%.2f", finalAmount));
            
            // Step C: Process Payment (This automatically generates the DigitalTickets inside)
            System.out.println("\nProcessing transaction...");
            boolean isSuccess = reservation.confirmPayment("Visa ending in 9999", finalAmount);
            
            if (isSuccess) {
                // Step D: Print Receipt and save to Attendee profile
                reservation.getPayment().generateReceipt();
                loggedInUser.makeTicketReservation(reservation); // Awards loyalty points
                
                System.out.println("\n=== 4. VERIFYING GENERATED TICKETS ===");
                for (DigitalTicket ticket : reservation.getDigitalTickets()) {
                    System.out.println("Ticket Code: " + ticket.getTicketCode() + " | Barcode Data: " + ticket.getBarcodeData());
                }
            }
            
            System.out.println("\n=== 5. TESTING BOOKING HISTORY ===");
            // Verify that the attendee can view their past invoices
            loggedInUser.reviewBookingHistory();
        }
    }
}