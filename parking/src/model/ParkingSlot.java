package model;

/**
 * Model component representing an individual parking stall in the Smart Parking
 * Billing System. Tracks slot identification, parking zone tier, current
 * occupancy state, electrical plug-in availability, and maximum allowed parking
 * duration.
 */
public class ParkingSlot {

  /**
   * Available parking zone tiers across the campus facility.
   */
  public enum ZoneType {
    underground,
    surface,
    economy,
    plugin
  }

  /**
   * Possible lifecycle states for a parking slot.
   */
  public enum SlotState {
    empty,
    occupied,
    reserved,
    payment_pending
  }

  private final String slotId;
  private final ZoneType zoneType;
  private SlotState state;
  private final boolean hasElectricalPlug;
  private final int maxDurationHours;

  /**
   * Constructs a new ParkingSlot initialized to the {@code empty} state.
   *
   * @param slotId            unique identifier for the stall (e.g., "UG-01", "PL-01")
   * @param zoneType          parking zone tier for the stall
   * @param hasElectricalPlug {@code true} if the stall provides an electrical plug-in
   * @param maxDurationHours  maximum allowable parking duration in hours for this stall
   */
  public ParkingSlot(String slotId, ZoneType zoneType, boolean hasElectricalPlug, int maxDurationHours) {
    this.slotId = slotId;
    this.zoneType = zoneType;
    this.state = SlotState.empty;
    this.hasElectricalPlug = hasElectricalPlug;
    this.maxDurationHours = maxDurationHours;
  }

  /**
   * Returns the unique identifier of this parking slot.
   *
   * @return the slot ID
   */
  public String getSlotId() {
    return slotId;
  }

  /**
   * Returns the parking zone tier assigned to this slot.
   *
   * @return the zone type
   */
  public ZoneType getZoneType() {
    return zoneType;
  }

  /**
   * Returns the current occupancy/billing state of this slot.
   *
   * @return the current slot state
   */
  public SlotState getState() {
    return state;
  }

  /**
   * Updates the current state of this parking slot.
   *
   * @param state the new slot state to assign
   */
  public void setState(SlotState state) {
    this.state = state;
  }

  /**
   * Indicates whether this parking slot has an electrical plug-in available.
   *
   * @return {@code true} if an electrical plug is available, {@code false} otherwise
   */
  public boolean hasElectricalPlug() {
    return hasElectricalPlug;
  }

  /**
   * Returns the maximum parking duration allowed for this slot in hours.
   *
   * @return maximum duration in hours
   */
  public int getMaxDurationHours() {
    return maxDurationHours;
  }
}