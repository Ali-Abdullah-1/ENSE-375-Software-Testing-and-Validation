package controller;
import java.util.UUID;

import model.ParkingSlot;
import model.Ticket;
import model.Vehicle;

public class ParkingController {
  public double calculatedFee(ParkingSlot.ZoneType zonetype, int durationHours, boolean usePlug) {
    if (durationHours <= 0) {
      throw new IllegalArgumentException("Duration must be greater than zero hours.");
    } 

    double baseRatePerHour = switch (zonetype) {
      case underground -> 3.50;
      case surface -> 2.50;
      case economy -> 1.50;
      case plugin -> 3.00;
    };

    double total = baseRatePerHour * durationHours;

    if (usePlug) total += 1.00 * durationHours;

    return total;
  }

  public Ticket issueTicket(Vehicle vehicle, ParkingSlot slot, int durationHours, boolean usePlug) {
    if (slot.getState() != ParkingSlot.SlotState.empty) {
      throw new IllegalArgumentException("Parking slot is not available.");
    }

    if (durationHours > slot.getMaxDurationHours()) {
      throw new IllegalArgumentException("Duration exceeds the maximum limit for this zone(" + slot.getMaxDurationHours() + " hours).");
    }

    if (usePlug && !slot.hasElectricalPlug()) {
      throw new IllegalArgumentException("Selected stall does not support electrical plug-in usage.");
    }

    double fee = calculatedFee(slot.getZoneType(), durationHours, usePlug);
    slot.setState(ParkingSlot.SlotState.occupied);

    String ticketId = "TKT-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    return new Ticket(ticketId, vehicle, slot, durationHours, fee);
  }

  public void extendTicket(Ticket ticket, int additionalHours) {
    ParkingSlot slot = ticket.getSlot();
    int newTotalDuration = ticket.getDurationHours() + additionalHours;

    if (newTotalDuration > slot.getMaxDurationHours()) {
      throw new IllegalArgumentException("Extension exceeds maximum allowable duration for zone.");
    }

    ticket.setDurationHours(newTotalDuration);
    double updatedFee = calculatedFee(slot.getZoneType(), newTotalDuration, slot.hasElectricalPlug());
    ticket.setCalculatedFee(updatedFee);
  }

  public void releaseSlot(ParkingSlot slot) {
    slot.setState(ParkingSlot.SlotState.empty);
  }
}