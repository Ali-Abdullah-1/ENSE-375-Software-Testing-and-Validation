package model;
import java.time.LocalDateTime;

public class Ticket {
  private final String ticketId;
  private final Vehicle vehicle;
  private final ParkingSlot slot;
  private final LocalDateTime startTime;
  private int durationHours;
  private double calculatedFee;

  public Ticket(String ticketId, Vehicle vehicle, ParkingSlot slot, int durationHours, double calculatedFee) {
    this.ticketId = ticketId;
    this.vehicle = vehicle;
    this.slot = slot;
    this.startTime = LocalDateTime.now();
    this.durationHours = durationHours;
    this.calculatedFee = calculatedFee;
  }

  public String getTicketId() {
    return ticketId;
  }

  public Vehicle getVehicle() {
    return vehicle;
  }

  public ParkingSlot getSlot() {
    return slot;
  }

  public LocalDateTime getStartTime() {
    return startTime;
  }

  public int getDurationHours() {
    return durationHours;
  }

  public void setDurationHours(int durationHours) {
    this.durationHours = durationHours;
  }

  public double getCalculatedFee() {
    return calculatedFee;
  }

  public void setCalculatedFee(double calculatedFee) {
    this.calculatedFee = calculatedFee;
  }
}