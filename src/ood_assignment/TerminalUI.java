package ood_assignment;

import java.util.Scanner;

public class TerminalUI {
    private Scanner scanner;
    private AttendeeService attendeeService;
    private AdminService adminService;
    private EventService eventService;

    // Injecting our database services into the UI
    public TerminalUI(AttendeeService attendeeService, AdminService adminService, EventService eventService) {
        this.scanner = new Scanner(System.in);
        this.attendeeService = attendeeService;
        this.adminService = adminService;
        this.eventService = eventService;
    }

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. Attendee Portal");
            System.out.println("2. Admin Portal");
            System.out.println("3. Exit System");
            System.out.print("Select your role: ");
            
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    System.out.println("\n<=== UPCOMING EVENTS ===>");
                    for (Event e : eventService.getAllEvents()){
                        e.displayDesc();
                        System.out.println("<======================>");
                    }
                    break;
                case "2":
                    handleAttendeeFlow();
                    break;
                case "3":
                    handleAdminFlow();
                    break;
                case "4":
                    System.out.println("Shutting down the system. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("❌ Invalid choice. Please enter 1, 2, or 3.");
            }
        }
    }

    // ==========================================
    // ATTENDEE PORTAL LOGIC
    // ==========================================
    private void handleAttendeeFlow() {
        System.out.println("\n<=== ATTENDEE PORTAL ===>");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.print("Select option: ");
        String authChoice = scanner.nextLine().trim();
        
        Attendee loggedInUser = null;
        
        if (authChoice.equals("1")) {
            System.out.print("Enter your Account User Key: ");
            String key = scanner.nextLine().trim();
            loggedInUser = attendeeService.loginAttendee(key);
        } else if (authChoice.equals("2")) {
            System.out.print("Enter Full Name: ");
            String name = scanner.nextLine().trim();
            System.out.print("Enter Email: ");
            String email = scanner.nextLine().trim();
            System.out.print("Enter Phone Number: ");
            String phone = scanner.nextLine().trim();
            loggedInUser = attendeeService.registerAttendee(name, email, phone);
        } else {
            System.out.println("❌ Invalid option.");
            return;
        }

        if (loggedInUser != null) {
            attendeeDashboard(loggedInUser);
        }
    }

    private void attendeeDashboard(Attendee user) {
        boolean inDashboard = true;
        while (inDashboard) {
            System.out.println("\n--- ATTENDEE DASHBOARD (" + user.getFullName() + ") ---");
            System.out.println("Loyalty Points: " + user.getLoyaltyPointStatus());
            System.out.println("1. Browse Upcoming Events");
            System.out.println("2. Reserve Tickets");
            System.out.println("3. Review Booking History");
            System.out.println("4. Logout");
            System.out.print("Select option: ");
            
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    user.inspectUpcomingSchedule();
                    break;
                case "2":
                    processMockReservation(user);
                    break;
                case "3":
                    user.reviewBookingHistory();
                    break;
                case "4":
                    inDashboard = false;
                    break;
                default:
                    System.out.println("❌ Invalid option.");
            }
        }
    }

    private void processMockReservation(Attendee user) {
        System.out.println("\n--- TICKET RESERVATION ---");
        System.out.println("Enter Event ID to book (e.g., EVT-001): ");
        String eID = scanner.nextLine().trim();
        Event selectedEvent = eventService.getEvent(eID);

        if (selectedEvent == null){
            System.out.println("Event not Found.");
            return;
        }
        // Mocking the event selection process until your partner builds the Event class
        System.out.print("How many seats would you like to book? (e.g., 2): ");
        int seatCount;
        try {
            seatCount = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid number. Please enter digits only.");
            return;
        }

        String[] seats = new String[seatCount];
        for (int i = 0; i < seatCount; i++) {
            seats[i] = "SEAT-" + (i + 1);
        }
        
        TicketReservation reservation = new TicketReservation(user, selectedEvent, seats);
        double finalPrice = reservation.calculateFinalPrice(selectedEvent.baseTicketPrice, 0.10); // Base: 150 MYR, 10% discount
        
        System.out.println("Total Price: MYR " + String.format("%.2f", finalPrice));
        System.out.print("Proceed to payment? (Y/N): ");
        String confirm = scanner.nextLine().trim().toUpperCase();
        
        if (confirm.equals("Y")) {
            System.out.print("Enter Payment Method (e.g., Visa, Cash): ");
            String method = scanner.nextLine().trim();
            
            if (reservation.confirmPayment(method, finalPrice)) {
                System.out.println("✅ Payment Successful!");
                reservation.getPayment().generateReceipt();
                user.makeTicketReservation(reservation);
            } else {
                System.out.println("❌ Payment Failed.");
            }
        } else {
            System.out.println("Reservation cancelled.");
        }
    }

    // ==========================================
    // ADMIN PORTAL LOGIC
    // ==========================================
    private void handleAdminFlow() {
        System.out.println("\n--- ADMIN PORTAL ---");
        System.out.print("Enter your Admin ID: ");
        String adminID = scanner.nextLine().trim();
        
        Admin loggedInAdmin = adminService.loginAdmin(adminID);
        
        if (loggedInAdmin != null) {
            adminDashboard(loggedInAdmin);
        }
    }

    private void adminDashboard(Admin admin) {
        boolean inDashboard = true;
        while (inDashboard) {
            System.out.println("\n--- ADMIN DASHBOARD (" + admin.getFullName() + ") ---");
            System.out.println("1. Add New Event");
            System.out.println("2. Update Event");
            System.out.println("3. Manage Ticket Allocations");
            System.out.println("4. Register New Admin");
            System.out.println("5. Logout");
            System.out.print("Select option: ");
            
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    System.out.print("Enter Event ID (e.g., EVT-002): ");
                    String eId = scanner.nextLine().trim();
                    System.out.print("Enter Title: ");
                    String title = scanner.nextLine().trim();
                    System.out.print("Enter Venue Name: ");
                    String venue = scanner.nextLine().trim();
                    System.out.print("Enter Base Ticket Price: ");
                    double price = Double.parseDouble(scanner.nextLine().trim());
                    System.out.print("Enter Max Capacity: ");
                    int cap = Integer.parseInt(scanner.nextLine().trim());

                    System.out.print("Enter Artist Name: ");
                    String artist = scanner.nextLine().trim();
                    System.out.print("Enter VIP Config: ");
                    String vip = scanner.nextLine().trim();
                
                    ConcertEvent newConcert = new ConcertEvent(eId, title, venue, price, cap, vip, artist);
                    admin.addEvent(newConcert, eventService);
                    break;
                case "2":
                    System.out.print("Enter Event ID to update: ");
                    admin.updateEvent(scanner.nextLine().trim(), eventService);
                    break;
                case "3":
                    System.out.print("Enter Event ID to manage: ");
                    String targeteID = scanner.nextLine().trim();
                    System.out.print("Enter new capacity: ");
                    try {
                        int newCap = Integer.parseInt(scanner.nextLine().trim());
                        admin.manageTicketAllocations(targeteID, newCap, eventService);
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Invalid capacity. Please enter numbers only.");
                    }
                    break;
                case "4":
                    System.out.print("Enter new admin's Full Name: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Enter Role Level (e.g., Manager): ");
                    String role = scanner.nextLine().trim();
                    System.out.print("Enter Department: ");
                    String dept = scanner.nextLine().trim();
                    adminService.registerNewAdmin(admin, name, role, dept);
                    break;
                case "5":
                    inDashboard = false;
                    break;
                default:
                    System.out.println("❌ Invalid option.");
            }
        }
    }
}