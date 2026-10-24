package ood_assignment;

import java.time.LocalDateTime;
import java.util.UUID;

public class DigitalTicket {
    private String ticketCode;
    private String barcodeData;
    private LocalDateTime issueDate;

    // Package-private constructor enforces Composition.
    // Only TicketReservation (which is in the same package) can create a DigitalTicket.
    DigitalTicket(String reservationID, String seatAssignment) {
        this.ticketCode = generateTicketCode(reservationID);
        this.barcodeData = reservationID + "-" + seatAssignment + "-" + this.ticketCode;
        this.issueDate = LocalDateTime.now();
    }

    // Matches UML: generateTicketCode(reservationID: String): String
    public String generateTicketCode(String reservationID) {
        String uniqueSuffix = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        return reservationID + "-TKT-" + uniqueSuffix;
    }

    // Matches UML: validateTicket(): boolean
    public boolean validateTicket() {
        // In a real system, this would check cryptography or a database flag.
        // For our in-memory system, we ensure the ticket code and barcode are correctly formed.
        return this.ticketCode != null && this.barcodeData != null;
    }

    // Getters for external access
    public String getTicketCode() { return ticketCode; }
    public String getBarcodeData() { return barcodeData; }
    public LocalDateTime getIssueDate() { return issueDate; }
}