import controller.ParkingController;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.ParkingSlot;
import model.ParkingSlot.ZoneType;
import model.Ticket;
import model.Vehicle;
import view.TerminalView;

/**
 * Entry point and interactive command-line interface for the Smart Parking
 * Billing System. Connects user terminal input to the controller and
 * view layers while maintaining in-memory parking slots and active tickets.
 */
public class Main {
  // Single shared scanner for System.in so closing it only happens on application exit
  private static final Scanner scanner = new Scanner(System.in);
  private static final List<ParkingSlot> slots = new ArrayList<>();
  private static final List<Ticket> activeTickets = new ArrayList<>();
  private static final TerminalView view = new TerminalView();
  private static final ParkingController controller = new ParkingController();

  /**
   * Starts the terminal application and runs the main menu loop until exit.
   *
   * @param args command-line arguments (unused)
   */
  public static void main(String[] args) {
    initializeSlots();
    view.displayWelcomeBanner();

    boolean running = true;
    while (running) {
      System.out.println("\n==========================================");
      System.out.println("               MAIN MENU                  ");
      System.out.println("==========================================");
      System.out.println("1. Issue a New Parking Ticket");
      System.out.println("2. Extend an Existing Ticket (By Plate)");
      System.out.println("3. View All Parking Slots & Statuses");
      System.out.println("4. Exit System");
      System.out.print("Choose an option (1-4): ");

      String choice = scanner.nextLine().trim();

      switch (choice) {
        case "1" -> issueNewTicket();
        case "2" -> extendTicketByPlate();
        case "3" -> viewAllSlots();
        case "4" -> {
          running = false;
          System.out.println("\nExiting Smart Parking Billing System. Goodbye!");
        }
        default -> view.displayError("Invalid menu choice. Please select an option between 1 and 4.");
      }
    }

    scanner.close();
  }

  /**
   * Populates the parking facility with predefined slots across underground,
   * surface, economy, and plug-in zones with zone-specific maximum durations.
   */
  private static void initializeSlots() {
    // Underground: protected from weather, no electrical plug-ins (max 4h)
    for (int i = 1; i <= 6; i++) {
      slots.add(new ParkingSlot(String.format("UG-%02d", i), ZoneType.underground, false, 4));
    }
    // Surface lot (max 8h)
    for (int i = 1; i <= 8; i++) {
      slots.add(new ParkingSlot(String.format("SF-%02d", i), ZoneType.surface, false, 8));
    }
    // Economy outer lot (max 12h)
    for (int i = 1; i <= 10; i++) {
      slots.add(new ParkingSlot(String.format("EC-%02d", i), ZoneType.economy, false, 12));
    }
    // Dedicated plug-in stalls (max 6h)
    for (int i = 1; i <= 6; i++) {
      slots.add(new ParkingSlot(String.format("PL-%02d", i), ZoneType.plugin, true, 6));
    }
  }

  /**
   * Prompts the user for a license plate, parking slot, and duration, then issues
   * a ticket through {@link ParkingController} and displays the receipt.
   */
  private static void issueNewTicket() {
    try {
      System.out.print("\nEnter License Plate (3-7 alphanumeric characters): ");
      String plateInput = scanner.nextLine();
      Vehicle vehicle = new Vehicle(plateInput);

      System.out.println("\n--- Parking Slots ---");
      for (int i = 0; i < slots.size(); i++) {
        ParkingSlot s = slots.get(i);
        System.out.println((i + 1) + ". ID: " + s.getSlotId() +
                           " | Zone: " + s.getZoneType() +
                           " | Plug-in: " + (s.hasElectricalPlug() ? "Yes" : "No") +
                           " | Max Dur: " + s.getMaxDurationHours() + "h" +
                           " | Status: " + s.getState());
      }

      System.out.print("Select slot number (1-" + slots.size() + "): ");
      // Convert 1-based user menu selection to 0-based list index
      int slotIdx = Integer.parseInt(scanner.nextLine().trim()) - 1;
      if (slotIdx < 0 || slotIdx >= slots.size()) {
        throw new IllegalArgumentException("Invalid slot selection index.");
      }
      ParkingSlot selectedSlot = slots.get(slotIdx);

      System.out.print("Enter parking duration in hours: ");
      int duration = Integer.parseInt(scanner.nextLine().trim());

      Ticket ticket = controller.issueTicket(vehicle, selectedSlot, duration);
      activeTickets.add(ticket);
      view.displayReceipt(ticket);

    } catch (NumberFormatException e) {
      view.displayError("Please enter a valid numeric value.");
    } catch (Exception e) {
      view.displayError(e.getMessage());
    }
  }

  /**
   * Looks up an active ticket by license plate to support cross-zone extensions
   * without scanning through a long list of active tickets, then adds hours if valid.
   */
  private static void extendTicketByPlate() {
    if (activeTickets.isEmpty()) {
      view.displayError("No active parking tickets found in the system.");
      return;
    }

    try {
      System.out.print("\nEnter your License Plate to locate active ticket: ");
      String plateQuery = scanner.nextLine().trim().toUpperCase();

      Ticket matchedTicket = null;
      for (Ticket t : activeTickets) {
        if (t.getVehicle().getLicensePlate().equalsIgnoreCase(plateQuery)) {
          matchedTicket = t;
          break;
        }
      }

      if (matchedTicket == null) {
        view.displayError("No active ticket found for license plate: " + plateQuery);
        return;
      }

      System.out.println("Found active ticket [" + matchedTicket.getTicketId() + "] " +
                         "in slot [" + matchedTicket.getSlot().getSlotId() + "] " +
                         "(Current duration: " + matchedTicket.getDurationHours() + "h, " +
                         "Max allowed: " + matchedTicket.getSlot().getMaxDurationHours() + "h).");

      System.out.print("Enter additional hours to add: ");
      int addHours = Integer.parseInt(scanner.nextLine().trim());

      controller.extendTicket(matchedTicket, addHours);
      System.out.println("\n[SUCCESS] Ticket extended successfully!");
      view.displayReceipt(matchedTicket);

    } catch (NumberFormatException e) {
      view.displayError("Please enter a valid numeric value.");
    } catch (Exception e) {
      view.displayError(e.getMessage());
    }
  }

  /**
   * Prints the current zone, plug-in availability, maximum duration, and state
   * for every parking slot in the system.
   */
  private static void viewAllSlots() {
    System.out.println("\n--- CURRENT PARKING SLOTS STATUS ---");
    for (ParkingSlot s : slots) {
      System.out.println("Slot ID: " + s.getSlotId() +
                         " | Zone: " + s.getZoneType() +
                         " | Plug-in: " + (s.hasElectricalPlug() ? "Yes" : "No") +
                         " | Max Limit: " + s.getMaxDurationHours() + "h" +
                         " | Status: " + s.getState());
    }
  }
}