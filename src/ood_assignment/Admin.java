package ood_assignment;

public class Admin {
    private String adminID;
    private String fullName; // Added so the admin has a display name
    private String roleLevel;
    private String department;

    public Admin(String adminID, String fullName, String roleLevel, String department) {
        this.adminID = adminID;
        this.fullName = fullName;
        this.roleLevel = roleLevel;
        this.department = department;
    }

    // Matches UML: addEvent(newEvent: Event): void[cite: 5]
    // Note: Using 'Object' as a placeholder until your partner creates the 'Event' class.
    public void addEvent(Object newEvent) {
        System.out.println("Admin " + this.adminID + " is preparing to add a new event...");
        //  replace 'Object' with 'Event' and add their saving logic here
    }

    // Matches UML: updateEvent(eventID: String): void[cite: 5]
    public void updateEvent(String eventID) {
        System.out.println("Admin " + this.adminID + " is accessing event: " + eventID + " for updates...");
        //  add their event lookup and modification logic here
    }

    // Matches UML: manageTicketAllocations(eventID: String, capacity: int): void[cite: 5]
    public void manageTicketAllocations(String eventID, int capacity) {
        System.out.println("Admin " + this.adminID + " is updating ticket capacity for " + eventID + " to " + capacity + " seats.");
        //  will add the logic to adjust maximum capacity here
    }

    // Getters for external access
    public String getAdminID() { return adminID; }
    public String getFullName() { return fullName; }
    public String getRoleLevel() { return roleLevel; }
    public String getDepartment() { return department; }
}