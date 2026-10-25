package ood_assignment;

public class Main {
    public static void main(String[] args) {
        // 1. Initialize our in-memory database service
        AttendeeService attendeeService = new AttendeeService();
        AdminService adminService = new AdminService();
        EventService eventService = new EventService();

        TerminalUI ui = new TerminalUI(attendeeService, adminService, eventService);

        ui.start();
    }
}