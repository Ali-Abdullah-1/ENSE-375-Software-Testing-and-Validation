package view;
import model.Ticket;

public class TerminalView {
  public void displayWelcomeBanner() {
    System.out.println("==========================================");
    System.out.println("   Smart Parking Billing System (SPBS)    ");
    System.out.println("==========================================");
  }

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

  public void displayError(String message) {
    System.out.println("[ERROR] " + message);
  }
}