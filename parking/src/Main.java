import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controller.ParkingController;
import model.ParkingSlot;
import model.ParkingSlot.ZoneType;
import model.Ticket;
import model.Vehicle;
import view.TerminalView;

public class Main 
{
  private static List<ParkingSlot> slots = new ArrayList<>();
  private static List<Ticket> activeTickets = new ArrayList<>();
  private static TerminalView view = new TerminalView();
  private static ParkingController controller = new ParkingController();
  
  public static void main( String[] args ) {
    Scanner scanner = new Scanner(System.in);

    slots.add(new ParkingSlot("UG-01", ZoneType.underground, false, 4));
    slots.add(new ParkingSlot("UG-02", ZoneType.underground, true, 4));
    slots.add(new ParkingSlot("SF-01", ZoneType.surface, false, 8));
    slots.add(new ParkingSlot("EC-01", ZoneType.economy, false, 12));
    slots.add(new ParkingSlot("PL-01", ZoneType.plugin, true, 6));


    view.displayWelcomeBanner();

    boolean running = true;
    while (running) {
      System.out.println("\n==========================================");
      System.out.println("               MAIN MENU                  ");
      System.out.println("==========================================");
      System.out.println("1. Issue a New Parking Ticket");
      System.out.println("2. Extend an Existing Ticket (Cross-Zone)");
      System.out.println("3. Release Slot / Checkout");
      System.out.println("4. View All Parking Slots & Statuses");
      System.out.println("5. Exit System");
      System.out.print("Choose an option (1-5): ");

      String choice = scanner.nextLine().trim();

      if (choice.equals("1")) {
        issueNewTicket();
      } else if (choice.equals("2")) {
        extendTicket();
      } else if (choice.equals("3")) {
        checkOut();
      } else if (choice.equals("4")) {
        viewAllSlots();
      } else if (choice.equals("5")) {
        running = false;
        System.out.println("\nExiting Smart Parking Billing System. Goodbye!");
      } else {
        view.displayError("Invalid menu choice. Please select an option between 1 and 5.");
      }
    }

    scanner.close();
  }

  private static void issueNewTicket() {
    Scanner scanner = new Scanner(System.in);

    System.out.print("\nEnter License Plate (3-7 alphanumeric characters): ");
    String plateInput = scanner.nextLine();
    Vehicle vehicle = new Vehicle(plateInput);

    try {
      System.out.println("\n--- Available Parking Slots ---");
      for (int i = 0; i < slots.size(); i++) {
        ParkingSlot s = slots.get(i);
        System.out.println((i + 1) + ". ID: " + s.getSlotId() +
                                     " | Zone: " + s.getZoneType() +
                                     " | Plug-in: " + (s.hasElectricalPlug() ? "Yes" : "No") +
                                     " | Max Dur: " + s.getMaxDurationHours() + "h" +
                                     " | Status: " + s.getState());
      }
  
      System.out.print("Select slot number (1-" + slots.size() + "): ");
      int slotIdx = Integer.parseInt(scanner.nextLine()) - 1;
      if (slotIdx < 0 || slotIdx > slots.size()) {
        scanner.close();
        throw new IllegalArgumentException("Invalid slot selection index.");
      }
      ParkingSlot selectedSlot = slots.get(slotIdx);
  
      System.out.println("Enter parking duration in hours: ");
      int duration = Integer.parseInt(scanner.nextLine());
  
      boolean usePlug = false;
      if (selectedSlot.hasElectricalPlug()) {
        System.out.print("This stall has an electrical plug-in. Do you want to use it? (y/n): ");
        String plugAns = scanner.nextLine().trim().toLowerCase();
        usePlug = plugAns.equals("y") || plugAns.equals("yes");
      }
  
      Ticket ticket = controller.issueTicket(vehicle, selectedSlot, duration, usePlug);
      activeTickets.add(ticket);
      view.displayReceipt(ticket);
    } catch (NumberFormatException e) {
      view.displayError("Please enter a valid numeric value for numbers.");
    } catch (Exception e) {
      view.displayError(e.getMessage());
    }

    scanner.close();
  }

  private static void extendTicket() {
    Scanner scanner = new Scanner(System.in);

    if (activeTickets.isEmpty()) {
      view.displayError("No active parking tickets found to extend.");
    }

    try {
      System.out.println("\n--- Active Tickets ---");
      for (int i = 0; i < activeTickets.size(); i++) {
        Ticket t = activeTickets.get(i);
        System.out.println((i + 1) + ". Ticket ID: " + t.getTicketId() +
                                     " | Plate: " + t.getVehicle().getLicensePlate() +
                                     " | Slot: " + t.getSlot().getSlotId() +
                                     " | Current Duration: " + t.getDurationHours() + "h");
      }

      System.out.print("Select ticket number to extend: ");
      int tIdx = Integer.parseInt(scanner.nextLine()) - 1;
      if (tIdx < 0 || tIdx >= activeTickets.size()) {
        scanner.close();
        throw new IllegalArgumentException("Invalid ticket selection index.");
      }
      Ticket selectedTicket = activeTickets.get(tIdx);

      System.out.print("Enter additional hours to add: ");
      int addHours = Integer.parseInt(scanner.nextLine());

      controller.extendTicket(selectedTicket, addHours);
      System.out.println("\n[SUCCESS] Ticket extended successfully!");
      view.displayReceipt(selectedTicket);

    } catch (NumberFormatException e) {
      view.displayError("Please enter a valid numeric value.");
    } catch (Exception e) {
      view.displayError(e.getMessage());
    }

    scanner.close();
  }
}
