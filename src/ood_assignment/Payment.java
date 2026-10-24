package ood_assignment;

import java.time.LocalDateTime;
import java.util.UUID;

public class Payment {
    private String transactionID;
    private double paymentAmount;
    private LocalDateTime paymentDate;
    private String paymentMethod;
    private String paymentStatus;

    // Package-private constructor enforces Composition.
    // Only TicketReservation can instantiate a Payment.
    Payment(String reservationID, double paymentAmount, String paymentMethod) {
        this.transactionID = "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.paymentAmount = paymentAmount;
        this.paymentDate = LocalDateTime.now();
        this.paymentMethod = paymentMethod;
        this.paymentStatus = "PENDING";
    }

    // Matches UML: processTransaction(): boolean
    public boolean processTransaction() {
        // For our automated, in-memory system, we approve valid amounts automatically.
        if (this.paymentAmount > 0) {
            this.paymentStatus = "APPROVED";
            return true;
        } else {
            this.paymentStatus = "FAILED";
            return false;
        }
    }

    // Matches UML: generateReceipt(): void
    public void generateReceipt() {
        System.out.println("=== Payment Receipt ===");
        System.out.println("Transaction ID: " + transactionID);
        System.out.println("Date: " + paymentDate);
        System.out.println("Amount Paid: MYR " + String.format("%.2f", paymentAmount));
        System.out.println("Method: " + paymentMethod);
        System.out.println("Status: " + paymentStatus);
        System.out.println("=======================");
    }

    // Getters for external access
    public String getTransactionID() { return transactionID; }
    public double getPaymentAmount() { return paymentAmount; }
    public LocalDateTime getPaymentDate() { return paymentDate; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getPaymentStatus() { return paymentStatus; }
    
    // Setter for admins to revoke/refund payments later if needed
    public void setPaymentStatus(String status) { this.paymentStatus = status; }
}
