package view;

import model.Ticket;

/**
 * View component of the Smart Parking Billing System MVC architecture.
 * Handles command-line output formatting, including the welcome banner, parking
 * ticket receipts, and error messages, without embedding business logic.
 */
public class TerminalView {

  /**
   * Prints the welcome banner for the Smart Parking Billing System to standard output.
   */
  public void displayWelcomeBanner() {
    System.out.println("==========================================");
    System.out.println("   Smart Parking Billing System (SPBS)    ");
    System.out.println("==========================================");
  }

  /**
   * Prints a formatted receipt containing ticket, vehicle, zone, slot,
   * duration, and total fee details for the given ticket.
   *
   * @param ticket the parking ticket whose details should be displayed
   */
  public void displayReceipt(Ticket ticket) {
    System.out.println("\n--- PARKING TICKET RECEIPT ---");
    System.out.println("Ticket ID     : " + ticket.getTicketId());
    System.out.println("License Plate : " + ticket.getVehicle().getLicensePlate());
    System.out.println("Zone Type     : " + ticket.getSlot().getZoneType());
    System.out.println("Slot ID       : " + ticket.getSlot().getSlotId());
    System.out.println("Duration      : " + ticket.getDurationHours() + " hours");
    System.out.println("Total Fee     : $" + String.format("%.2f", ticket.getCalculatedFee()));
    System.out.println("------------------------------\n");
  }

  /**
   * Prints a formatted error message to standard output.
   *
   * @param message the error message to display
   */
  public void displayError(String message) {
    System.out.println("[ERROR] " + message);
  }
}