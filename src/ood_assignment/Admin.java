package ood_assignment;

import java.util.UUID;

public class Admin {
    private String adminID;
    private String username; 
    private String password; 
    private String fullName; 
    private String roleLevel;
    private String department;

    public Admin(String username, String password, String fullName, String roleLevel, String department) {
        this.adminID = "ADMIN-" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.roleLevel = roleLevel;
        this.department = department;
    }
    
    public Admin(String specificID, String username, String password, String fullName, String roleLevel, String department) {
        this.adminID = specificID;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.roleLevel = roleLevel;
        this.department = department;
    }

    public void addEvent(Event newEvent, EventService eventServices) {
        System.out.println("Admin " + this.username + " is adding a new event...");
        boolean isSaved = eventServices.addEventToDatabase(newEvent);
        if (isSaved) {
            System.out.println("✅ Event '" + newEvent.title + "' successfully saved to the system!");
        } else {
            System.out.println("❌ Error: Event ID '" + newEvent.eventID + "' already exists. Overwrite prevented.");
        }
    }

    public void logEventUpdate(String eventID) {
        System.out.println("✅ Admin " + this.username + " successfully updated details for event: " + eventID);
    }

    public void deleteEvent(String eventID, EventService eventServices) {
        System.out.println("Admin " + this.username + " is attempting to delete event: " + eventID);
        if (eventServices.deleteEvent(eventID)) {
            System.out.println("✅ Event '" + eventID + "' successfully deleted.");
        } else {
            System.out.println("❌ Error: Event not found.");
        }
    }

    public String getAdminID() { return adminID; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getFullName() { return fullName; }
    public String getRoleLevel() { return roleLevel; }
    public String getDepartment() { return department; }
}