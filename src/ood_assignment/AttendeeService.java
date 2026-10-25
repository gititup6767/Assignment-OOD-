package ood_assignment;

import java.util.HashMap;
import java.util.Map;

public class AttendeeService {
    // Database now maps the Username to the Attendee object
    private Map<String, Attendee> attendeeDatabase;

    public AttendeeService() {
        this.attendeeDatabase = new HashMap<>();
    }

    // Handles user registration
    public Attendee registerAttendee(String username, String password, String fullName, String email, String phoneNumber) {
        if (attendeeDatabase.containsKey(username)) {
            System.out.println("❌ Registration failed. Username '" + username + "' is already taken.");
            return null;
        }
        
        Attendee newAttendee = new Attendee(username, password, fullName, email, phoneNumber);
        attendeeDatabase.put(username, newAttendee);
        
        System.out.println("✅ Registration successful! You can now log in with your username.");
        return newAttendee;
    }

    // Handles user sign-in
    public Attendee loginAttendee(String username, String password) {
        Attendee attendee = attendeeDatabase.get(username);
        
        if (attendee != null && attendee.getPassword().equals(password)) {
            System.out.println("✅ Login successful! Welcome back, " + attendee.getFullName() + ".");
            return attendee;
        } else {
            System.out.println("❌ Login failed. Incorrect username or password.");
            return null;
        }
    }
}