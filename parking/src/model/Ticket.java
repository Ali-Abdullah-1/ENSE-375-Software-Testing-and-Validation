package model;

import java.time.LocalDateTime;

/**
 * Model component representing a parking ticket in the Smart Parking Billing
 * System. Stores ticket identification, associated vehicle and parking
 * slot references, start timestamp, parking duration, and calculated fee.
 */
public class Ticket {

  private final String ticketId;
  private final Vehicle vehicle;
  private final ParkingSlot slot;
  private final LocalDateTime startTime;
  private int durationHours;
  private double calculatedFee;

  /**
   * Constructs a new Ticket with its start time set to the current timestamp.
   *
   * @param ticketId      unique identifier for the ticket
   * @param vehicle       vehicle associated with this ticket
   * @param slot          parking slot assigned to the vehicle
   * @param durationHours initial parking duration in hours
   * @param calculatedFee initial calculated parking fee in dollars
   */
  public Ticket(String ticketId, Vehicle vehicle, ParkingSlot slot, int durationHours, double calculatedFee) {
    this.ticketId = ticketId;
    this.vehicle = vehicle;
    this.slot = slot;
    this.startTime = LocalDateTime.now();
    this.durationHours = durationHours;
    this.calculatedFee = calculatedFee;
  }

  /**
   * Returns the unique ticket ID.
   *
   * @return the ticket ID
   */
  public String getTicketId() {
    return ticketId;
  }

  /**
   * Returns the vehicle associated with this ticket.
   *
   * @return the {@link Vehicle} object
   */
  public Vehicle getVehicle() {
    return vehicle;
  }

  /**
   * Returns the parking slot assigned to this ticket.
   *
   * @return the {@link ParkingSlot} object
   */
  public ParkingSlot getSlot() {
    return slot;
  }

  /**
   * Returns the timestamp when this ticket was created.
   *
   * @return the start time as a {@link LocalDateTime}
   */
  public LocalDateTime getStartTime() {
    return startTime;
  }

  /**
   * Returns the current parking duration for this ticket in hours.
   *
   * @return duration in hours
   */
  public int getDurationHours() {
    return durationHours;
  }

  /**
   * Updates the parking duration for this ticket in hours.
   *
   * @param durationHours the new duration in hours
   */
  public void setDurationHours(int durationHours) {
    this.durationHours = durationHours;
  }

  /**
   * Returns the total calculated fee for this ticket in dollars.
   *
   * @return calculated fee
   */
  public double getCalculatedFee() {
    return calculatedFee;
  }

  /**
   * Updates the total calculated fee for this ticket in dollars.
   *
   * @param calculatedFee the updated fee amount
   */
  public void setCalculatedFee(double calculatedFee) {
    this.calculatedFee = calculatedFee;
  }
}