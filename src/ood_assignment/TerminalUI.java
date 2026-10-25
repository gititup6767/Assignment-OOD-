package ood_assignment;

import java.util.Scanner;

public class TerminalUI {
    private Scanner scanner;
    private AttendeeService attendeeService;
    private AdminService adminService;
    private EventService eventService;

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
                case "1": handleAttendeeFlow(); break;
                case "2": handleAdminFlow(); break;
                case "3":
                    System.out.println("Shutting down the system. Goodbye!");
                    running = false;
                    break;
                default: System.out.println("❌ Invalid choice.");
            }
        }
    }

    // ==========================================
    // ATTENDEE PORTAL
    // ==========================================
    private void handleAttendeeFlow() {
        System.out.println("\n<=== ATTENDEE PORTAL ===>");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Go Back");
        System.out.print("Select option: ");
        String authChoice = scanner.nextLine().trim();
        
        Attendee loggedInUser = null;
        
        if (authChoice.equals("1")) {
            System.out.print("Enter Username: ");
            String username = scanner.nextLine().trim();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine().trim();
            loggedInUser = attendeeService.loginAttendee(username, password);
        } else if (authChoice.equals("2")) {
            System.out.print("Create Username: ");
            String username = scanner.nextLine().trim();
            System.out.print("Create Password: ");
            String password = scanner.nextLine().trim();
            System.out.print("Enter Full Name: ");
            String name = scanner.nextLine().trim();
            System.out.print("Enter Email: ");
            String email = scanner.nextLine().trim();
            System.out.print("Enter Phone Number: ");
            String phone = scanner.nextLine().trim();
            loggedInUser = attendeeService.registerAttendee(username, password, name, email, phone);
        } else if (authChoice.equals("3")) {
            return;
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
            System.out.println("4. Update Profile");
            System.out.println("5. Logout");
            System.out.print("Select option: ");
            
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    System.out.println("\n<=== UPCOMING EVENTS ===>");
                    if (eventService.getAllEvents().isEmpty()) {
                        System.out.println("❌ There are currently no upcoming events.");
                    } else {
                        for (Event e : eventService.getAllEvents()){
                            e.displayDesc();
                            System.out.println("<======================>");
                        }
                    }
                    user.inspectUpcomingSchedule();
                    break;
                case "2": processMockReservation(user); break;
                case "3": user.reviewBookingHistory(); break;
                case "4": handleProfileUpdate(user); break;
                case "5": inDashboard = false; break;
                default: System.out.println("❌ Invalid option.");
            }
        }
    }

    private void handleProfileUpdate(Attendee user) {
        System.out.println("\n--- UPDATE PROFILE ---");
        System.out.println("1. Update Email (Current: " + user.getEmail() + ")");
        System.out.println("2. Update Phone Number (Current: " + user.getPhoneNumber() + ")");
        System.out.println("3. Go Back");
        System.out.print("Select option: ");
        String choice = scanner.nextLine().trim();
        
        if (choice.equals("1")) {
            System.out.print("Enter new Email: ");
            user.setEmail(scanner.nextLine().trim());
        } else if (choice.equals("2")) {
            System.out.print("Enter new Phone Number: ");
            user.setPhoneNumber(scanner.nextLine().trim());
        } else if (choice.equals("3")) {
            return;
        } else {
            System.out.println("❌ Invalid option.");
        }
    }

    private void processMockReservation(Attendee user) {
        System.out.println("\n--- TICKET RESERVATION ---");
        System.out.println("Enter Event ID to book (or type '0' to go back): ");
        String eID = scanner.nextLine().trim();
        if (eID.equals("0")) return;
        
        Event selectedEvent = eventService.getEvent(eID);
        if (selectedEvent == null){
            System.out.println("Event not Found.");
            return;
        }
        
        double selectedPrice = selectedEvent.getBaseTicketPrice();
        if (selectedEvent.hasVipTickets()) {
            System.out.println("This event offers VIP tickets.");
            System.out.println("1. Base Ticket (MYR " + selectedEvent.getBaseTicketPrice() + ")");
            System.out.println("2. VIP Ticket (MYR " + selectedEvent.getVipTicketPrice() + ")");
            System.out.print("Select ticket type (1 or 2): ");
            String type = scanner.nextLine().trim();
            if (type.equals("2")) {
                selectedPrice = selectedEvent.getVipTicketPrice();
                System.out.println("VIP Ticket selected.");
            } else {
                System.out.println("Base Ticket selected.");
            }
        }
        
        System.out.print("How many seats would you like to book? (e.g., 2): ");
        int seatCount;
        try {
            seatCount = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid number.");
            return;
        }

        String[] seats = new String[seatCount];
        for (int i = 0; i < seatCount; i++) { seats[i] = "SEAT-" + (i + 1); }
        TicketReservation reservation = new TicketReservation(user, selectedEvent, seats);
        
        int freeTicketsRedeemed = 0;
        
        if (selectedEvent.isLoyaltyRedemptionAllowed()) {
            int pointCostPerTicket = selectedEvent.getPointsRequiredForRedemption();
            System.out.println("\n🌟 LOYALTY PROGRAM 🌟");
            System.out.println("Points needed for 1 Free Ticket: " + pointCostPerTicket + " points.");
            System.out.println("Your Current Points: " + user.getLoyaltyPointStatus());
            
            if (user.getLoyaltyPointStatus() >= pointCostPerTicket) {
                int maxPossibleRedeems = Math.min(seatCount, user.getLoyaltyPointStatus() / pointCostPerTicket);
                System.out.print("Would you like to exchange points for tickets? Enter amount to redeem up to " + maxPossibleRedeems + " (or 0 to skip): ");
                try {
                    freeTicketsRedeemed = Integer.parseInt(scanner.nextLine().trim());
                    if (freeTicketsRedeemed > maxPossibleRedeems) freeTicketsRedeemed = maxPossibleRedeems;
                    if (freeTicketsRedeemed < 0) freeTicketsRedeemed = 0;
                } catch (Exception e) {
                    freeTicketsRedeemed = 0;
                }
            } else {
                System.out.println("You do not have enough points to exchange for a free ticket at this time.");
            }
        }
        
        double finalPrice = reservation.calculateFinalPrice(selectedPrice, 0.10, freeTicketsRedeemed); 
        
        System.out.println("\nTotal Price: MYR " + String.format("%.2f", finalPrice));
        System.out.print("Proceed to payment? (Y/N): ");
        String confirm = scanner.nextLine().trim().toUpperCase();
        
        if (confirm.equals("Y")) {
            String method = "Points Only"; 
            if (finalPrice > 0) {
                System.out.print("Enter Payment Method (e.g., Visa, Cash): ");
                method = scanner.nextLine().trim();
            }
            
            if (reservation.confirmPayment(method, finalPrice)) {
                System.out.println("✅ Payment Successful!");
                if (freeTicketsRedeemed > 0) {
                    user.deductLoyaltyPoints(freeTicketsRedeemed * selectedEvent.getPointsRequiredForRedemption());
                }
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
    // ADMIN PORTAL
    // ==========================================
    private void handleAdminFlow() {
        System.out.println("\n--- ADMIN PORTAL ---");
        System.out.print("Enter Username (or type '0' to go back): ");
        String username = scanner.nextLine().trim();
        if (username.equals("0")) return;
        
        System.out.print("Enter Password: ");
        String password = scanner.nextLine().trim();
        
        Admin loggedInAdmin = adminService.loginAdmin(username, password);
        if (loggedInAdmin != null) adminDashboard(loggedInAdmin);
    }

    private void adminDashboard(Admin admin) {
        boolean inDashboard = true;
        while (inDashboard) {
            System.out.println("\n--- ADMIN DASHBOARD (" + admin.getFullName() + ") ---");
            System.out.println("1. Add New Event");
            System.out.println("2. Update Event");
            System.out.println("3. Delete Event");
            System.out.println("4. Register New Admin");
            System.out.println("5. View All Events");
            System.out.println("6. View & Delete Admins");
            System.out.println("7. Logout");
            System.out.print("Select option: ");
            
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    System.out.println("--- SELECT EVENT TYPE ---");
                    System.out.println("1. Concert");
                    System.out.println("2. Conference");
                    System.out.print("Choose (1 or 2): ");
                    String eventType = scanner.nextLine().trim();
                    
                    if (!eventType.equals("1") && !eventType.equals("2")) {
                        System.out.println("❌ Invalid event type selected.");
                        break;
                    }

                    System.out.print("Enter Event ID (e.g., EVT-002): ");
                    String eId = scanner.nextLine().trim();
                    System.out.print("Enter Title: ");
                    String title = scanner.nextLine().trim();
                    System.out.print("Enter Date (e.g., 2026-11-15): ");
                    String eventDate = scanner.nextLine().trim();
                    System.out.print("Enter Venue Name: ");
                    String venue = scanner.nextLine().trim();
                    System.out.print("Enter Base Ticket Price: ");
                    double price = Double.parseDouble(scanner.nextLine().trim());
                    System.out.print("Enter Max Capacity: ");
                    int cap = Integer.parseInt(scanner.nextLine().trim());
                    
                    System.out.print("Allow Loyalty Point Redemption? (true/false): ");
                    boolean allowsRedemption = Boolean.parseBoolean(scanner.nextLine().trim());
                    int pointsRequired = 0;
                    if (allowsRedemption) {
                        System.out.print("Enter loyalty points required for 1 free ticket: ");
                        pointsRequired = Integer.parseInt(scanner.nextLine().trim());
                    }

                    System.out.print("Does this event have VIP tickets? (true/false): ");
                    boolean hasVip = Boolean.parseBoolean(scanner.nextLine().trim());
                    double vipPrice = 0.0;
                    if (hasVip) {
                        System.out.print("Enter VIP Ticket Price: ");
                        vipPrice = Double.parseDouble(scanner.nextLine().trim());
                    }

                    if (eventType.equals("1")) {
                        System.out.print("Enter Artist Name: ");
                        String artist = scanner.nextLine().trim();
                        System.out.print("Enter VIP Config: ");
                        String vip = scanner.nextLine().trim();
                        ConcertEvent newConcert = new ConcertEvent(eId, title, eventDate, venue, price, cap, allowsRedemption, pointsRequired, hasVip, vipPrice, vip, artist);
                        admin.addEvent(newConcert, eventService);
                    } else {
                        System.out.print("Enter Keynote Speaker Name: ");
                        String speaker = scanner.nextLine().trim();
                        System.out.print("Enter Schedule Type (e.g., Full-Day): ");
                        String schedule = scanner.nextLine().trim();
                        ConferenceEvent newConference = new ConferenceEvent(eId, title, eventDate, venue, price, cap, allowsRedemption, pointsRequired, hasVip, vipPrice, schedule, speaker);
                        admin.addEvent(newConference, eventService);
                    }
                    break;
                case "2":
                    handleEventUpdateFlow(admin);
                    break;
                case "3":
                    System.out.print("Enter Event ID to delete (or type '0' to go back): ");
                    String delID = scanner.nextLine().trim();
                    if (!delID.equals("0")) {
                        admin.deleteEvent(delID, eventService);
                    }
                    break;
                case "4":
                    System.out.print("Create Admin Username: ");
                    String newUsername = scanner.nextLine().trim();
                    System.out.print("Create Password: ");
                    String newPassword = scanner.nextLine().trim();
                    System.out.print("Enter Full Name: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Enter Role Level (e.g., Manager): ");
                    String role = scanner.nextLine().trim();
                    System.out.print("Enter Department: ");
                    String dept = scanner.nextLine().trim();
                    adminService.registerNewAdmin(admin, newUsername, newPassword, name, role, dept);
                    break;
                case "5":
                    System.out.println("\n<=== ALL SYSTEM EVENTS ===>");
                    if (eventService.getAllEvents().isEmpty()) {
                        System.out.println("❌ There are currently no events registered in the system.");
                    } else {
                        for (Event e : eventService.getAllEvents()){
                            e.displayDesc();
                            System.out.println("<======================>");
                        }
                    }
                    break;
                case "6":
                    System.out.println("\n<=== ALL REGISTERED ADMINS ===>");
                    for (Admin a : adminService.getAllAdmins()) {
                        System.out.println("Username: " + a.getUsername() + " | Name: " + a.getFullName() + " | Role: " + a.getRoleLevel());
                    }
                    System.out.println("<==============================>");
                    System.out.print("Enter Admin Username to delete (or type '0' to go back): ");
                    String targetUsername = scanner.nextLine().trim();
                    if (targetUsername.equals("0")) break; 
                    
                    adminService.deleteAdmin(targetUsername, admin.getUsername());
                    break;
                case "7": 
                    inDashboard = false; 
                    break;
                default: 
                    System.out.println("❌ Invalid option.");
            }
        }
    }

    // --- UPDATED INTERACTIVE EVENT UPDATE MENU ---
    private void handleEventUpdateFlow(Admin admin) {
        System.out.print("Enter Event ID to update (or type '0' to go back): ");
        String targetID = scanner.nextLine().trim();
        if (targetID.equals("0")) return;
        
        Event ev = eventService.getEvent(targetID);
        if (ev == null) {
            System.out.println("❌ Event not found.");
            return;
        }

        boolean updating = true;
        while (updating) {
            System.out.println("\n--- UPDATING EVENT: " + ev.getTitle() + " ---");
            System.out.println("1. Update Title (Current: " + ev.getTitle() + ")");
            System.out.println("2. Update Date (Current: " + ev.getEventDate() + ")");
            System.out.println("3. Update Venue (Current: " + ev.getVenueName() + ")");
            System.out.println("4. Update Base Price (Current: MYR " + ev.getBaseTicketPrice() + ")");
            System.out.println("5. Update Capacity (Current: " + ev.getMaxCapacity() + " total seats)");
            
            if (ev instanceof ConcertEvent) {
                System.out.println("6. Update Artist Name (Current: " + ((ConcertEvent) ev).getArtistName() + ")");
            } else if (ev instanceof ConferenceEvent) {
                System.out.println("6. Update Keynote Speaker (Current: " + ((ConferenceEvent) ev).getKeynoteSpeaker() + ")");
                System.out.println("7. Update Schedule Type (Current: " + ((ConferenceEvent) ev).getScheduleType() + ")");
            }
            
            System.out.println("0. Done Updating");
            System.out.print("Select field to update: ");
            
            String updateChoice = scanner.nextLine().trim();
            switch (updateChoice) {
                case "1":
                    System.out.print("Enter new Title: ");
                    ev.setTitle(scanner.nextLine().trim());
                    break;
                case "2":
                    System.out.print("Enter new Date (e.g., 2026-12-01): ");
                    ev.setEventDate(scanner.nextLine().trim());
                    break;
                case "3":
                    System.out.print("Enter new Venue: ");
                    ev.setVenueName(scanner.nextLine().trim());
                    break;
                case "4":
                    System.out.print("Enter new Base Price: ");
                    try { ev.setBaseTicketPrice(Double.parseDouble(scanner.nextLine().trim())); } 
                    catch (Exception e) { System.out.println("❌ Invalid price format."); }
                    break;
                case "5":
                    System.out.print("Enter new Total Capacity: ");
                    try { ev.setMaxCapacity(Integer.parseInt(scanner.nextLine().trim())); } 
                    catch (Exception e) { System.out.println("❌ Invalid capacity format."); }
                    break;
                case "6":
                    if (ev instanceof ConcertEvent) {
                        System.out.print("Enter new Artist Name: ");
                        ((ConcertEvent) ev).setArtistName(scanner.nextLine().trim());
                    } else if (ev instanceof ConferenceEvent) {
                        System.out.print("Enter new Keynote Speaker: ");
                        ((ConferenceEvent) ev).setKeynoteSpeaker(scanner.nextLine().trim());
                    }
                    break;
                case "7":
                    if (ev instanceof ConferenceEvent) {
                        System.out.print("Enter new Schedule Type: ");
                        ((ConferenceEvent) ev).setScheduleType(scanner.nextLine().trim());
                    } else {
                        System.out.println("❌ Invalid option.");
                    }
                    break;
                case "0":
                    updating = false;
                    admin.logEventUpdate(targetID); 
                    break;
                default:
                    System.out.println("❌ Invalid option.");
            }
        }
    }
}