package controller;

import java.util.UUID;
import model.ParkingSlot;
import model.Ticket;
import model.Vehicle;

public class ParkingController {
  /**
   * Calculates the parking fee based on zone, number of hours and plug usage
   * 
   * @param zoneType the parking zone tier (underground, surface, plugin, economy, plugin)
   * @param durationHours total duration in hours (must be > 0)
   * @param usePlug true if using electrical plug
   * 
   * @return total calculated fee in dollars
   * 
   * @throws IllegalArgumentException if duration is less than or equal to 0
   */
  public double calculateFee(ParkingSlot.ZoneType zoneType, int durationHours, boolean usePlug) {
    if (durationHours <= 0) {
      throw new IllegalArgumentException("Duration must be greater than zero hours.");
    }

    double baseRatePerHour = switch (zoneType) {
      case underground -> 3.50;
      case surface -> 2.50;
      case economy -> 1.50;
      case plugin -> 3.00;
    };

    double total = baseRatePerHour * durationHours;

    if (usePlug) {
      total += 1.00 * durationHours;
    }

    return total;
  }

  /**
   * Creates new ticket for the given vehicle for a period of time and in the specified slot
   * 
   * @param vehicle the vehicle to be parked
   * @param slot the slot where the vehicle will be parked
   * @param durationHours the duration the ticket is to last in hours (must be > 0)
   * 
   * @return a new ticket object
   * 
   * @throws IllegalStateException if slot is not empty
   * @throws IllegalArgumentException if duration is greater than the slot's max duration hours
   */
  public Ticket issueTicket(Vehicle vehicle, ParkingSlot slot, int durationHours) {
    if (slot.getState() != ParkingSlot.SlotState.empty) {
      throw new IllegalStateException("Parking slot is not available.");
    }

    if (durationHours > slot.getMaxDurationHours()) {
      throw new IllegalArgumentException("Duration exceeds the maximum limit for this zone (" + slot.getMaxDurationHours() + " hours).");
    }

    boolean usePlug = slot.hasElectricalPlug();
    double fee = calculateFee(slot.getZoneType(), durationHours, usePlug);
    slot.setState(ParkingSlot.SlotState.occupied);

    String ticketId = "TKT-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    return new Ticket(ticketId, vehicle, slot, durationHours, fee);
  }

  /**
   * Extends the duration of an existing ticket by specified number of additional hours
   * 
   * @param ticket the ticket for which the duration is to be extended
   * @param additionalHours the number of hours to extend by (must be > 0)
   * 
   * @return void
   * 
   * @throws IllegalArgumentException if additional hours <= 0 or if new total duration > slot's max duration
   */
  public void extendTicket(Ticket ticket, int additionalHours) {
    if (additionalHours <= 0) {
      throw new IllegalArgumentException("Additional hours must be greater than zero.");
    }

    ParkingSlot slot = ticket.getSlot();
    int newTotalDuration = ticket.getDurationHours() + additionalHours;

    if (newTotalDuration > slot.getMaxDurationHours()) {
      throw new IllegalArgumentException("Extension exceeds maximum allowable duration for zone.");
    }

    ticket.setDurationHours(newTotalDuration);
    double updatedFee = calculateFee(slot.getZoneType(), newTotalDuration, slot.hasElectricalPlug());
    ticket.setCalculatedFee(updatedFee);
  }

  /**
   * Sets the state of the given slot to empty
   * @param slot the slot to release
   */
  public void releaseSlot(ParkingSlot slot) {
    slot.setState(ParkingSlot.SlotState.empty);
  }
}