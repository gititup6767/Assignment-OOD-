package ood_assignment;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AdminService {
    // In-memory database mapping the Admin ID to the Admin object
    private Map<String, Admin> adminDatabase;

    public AdminService() {
        this.adminDatabase = new HashMap<>();
        createDefaultAdmin(); // Automatically create the master admin on startup
    }

    // Generates the required default admin
    private void createDefaultAdmin() {
        Admin defaultAdmin = new Admin("ADMIN-MASTER", "System Administrator", "SuperAdmin", "IT");
        adminDatabase.put(defaultAdmin.getAdminID(), defaultAdmin);
    }

    // Handles admin sign-in
    public Admin loginAdmin(String adminID) {
        if (adminDatabase.containsKey(adminID)) {
            System.out.println("✅ Admin login successful! Welcome, " + adminDatabase.get(adminID).getFullName());
            return adminDatabase.get(adminID);
        } else {
            System.out.println("❌ Admin login failed. Invalid Admin ID.");
            return null;
        }
    }

    // Restricts registration: A valid 'currentAdmin' must be passed in to authorize the creation
    public Admin registerNewAdmin(Admin currentAdmin, String fullName, String roleLevel, String department) {
        // Security check
        if (currentAdmin == null || !adminDatabase.containsKey(currentAdmin.getAdminID())) {
            System.out.println("❌ Unauthorized. Only verified admins can register new admins.");
            return null;
        }
        
        // Generate a new Admin ID
        String newAdminID = "ADMIN-" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        Admin newAdmin = new Admin(newAdminID, fullName, roleLevel, department);
        
        // Save to database
        adminDatabase.put(newAdminID, newAdmin);
        
        System.out.println("✅ New admin registered successfully by " + currentAdmin.getFullName() + ".");
        System.out.println("IMPORTANT: The new Admin ID is: " + newAdminID);
        
        return newAdmin;
    }
}