package ood_assignment;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AttendeeService {
    // In-memory database mapping the Account User Key to the Attendee object
    private Map<String, Attendee> attendeeDatabase;

    public AttendeeService() {
        this.attendeeDatabase = new HashMap<>();
    }

    // Handles user registration
    public Attendee registerAttendee(String fullName, String email, String phoneNumber) {
        // Generate a unique user key (e.g., U-A1B2C3)
        String newAccountUserKey = "U-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        
        Attendee newAttendee = new Attendee(newAccountUserKey, fullName, email, phoneNumber);
        
        // Save to our in-memory database
        attendeeDatabase.put(newAccountUserKey, newAttendee);
        
        System.out.println("✅ Registration successful!");
        System.out.println("IMPORTANT: Your Account User Key is: " + newAccountUserKey);
        
        return newAttendee;
    }

    // Handles user sign-in
    public Attendee loginAttendee(String accountUserKey) {
        if (attendeeDatabase.containsKey(accountUserKey)) {
            System.out.println("✅ Login successful! Welcome back.");
            return attendeeDatabase.get(accountUserKey);
        } else {
            System.out.println("❌ Login failed. Account User Key not found.");
            return null;
        }
    }
}