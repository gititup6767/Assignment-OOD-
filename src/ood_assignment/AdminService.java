package ood_assignment;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AdminService {
    // Database now maps Username to the Admin object
    private Map<String, Admin> adminDatabase;

    public AdminService() {
        this.adminDatabase = new HashMap<>();
        createDefaultAdmin(); 
    }

    // Default master admin credentials: Username = "admin", Password = "adminpassword"
    private void createDefaultAdmin() {
        Admin defaultAdmin = new Admin("ADMIN-MASTER", "admin", "adminpassword", "System Administrator", "SuperAdmin", "IT");
        adminDatabase.put(defaultAdmin.getUsername(), defaultAdmin);
    }

    public Admin loginAdmin(String username, String password) {
        Admin admin = adminDatabase.get(username);
        if (admin != null && admin.getPassword().equals(password)) {
            System.out.println("✅ Admin login successful! Welcome, " + admin.getFullName());
            return admin;
        } else {
            System.out.println("❌ Admin login failed. Incorrect username or password.");
            return null;
        }
    }

    public Admin registerNewAdmin(Admin currentAdmin, String username, String password, String fullName, String roleLevel, String department) {
        if (currentAdmin == null || !adminDatabase.containsKey(currentAdmin.getUsername())) {
            System.out.println("❌ Unauthorized. Only verified admins can register new admins.");
            return null;
        }
        if (adminDatabase.containsKey(username)) {
            System.out.println("❌ Registration failed. Username '" + username + "' is already taken.");
            return null;
        }
        
        Admin newAdmin = new Admin(username, password, fullName, roleLevel, department);
        adminDatabase.put(username, newAdmin);
        
        System.out.println("✅ New admin registered successfully by " + currentAdmin.getFullName() + ".");
        return newAdmin;
    }

    public Collection<Admin> getAllAdmins() {
        return adminDatabase.values();
    }

    public void deleteAdmin(String targetUsername, String currentUsername) {
        Admin target = adminDatabase.get(targetUsername);
        if (target == null) {
            System.out.println("❌ Admin not found.");
            return;
        }
        if (target.getAdminID().equals("ADMIN-MASTER")) {
            System.out.println("❌ Permission Denied: Cannot delete the Master SuperAdmin.");
            return;
        }
        if (targetUsername.equals(currentUsername)) {
            System.out.println("❌ Error: You cannot delete your own active account.");
            return;
        }
        
        adminDatabase.remove(targetUsername);
        System.out.println("✅ Admin '" + targetUsername + "' successfully deleted from the system.");
    }
}