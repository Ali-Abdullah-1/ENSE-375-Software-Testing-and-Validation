package model;
public class ParkingSlot {
  public enum ZoneType { underground, surface, economy, plugin };
  public enum SlotState { empty, occupied, reserved, payment_pending };

  private final String slotId;
  private final ZoneType zoneType;
  private SlotState state;
  private final boolean hasElectricalPlug;
  private final int maxDurationHours;

  public ParkingSlot(String slotId, ZoneType zoneType, boolean hasElectricalPlug, int maxDurationHours) {
    this.slotId = slotId;
    this.zoneType = zoneType;
    this.state = SlotState.empty;
    this.hasElectricalPlug = hasElectricalPlug;
    this.maxDurationHours = maxDurationHours;
  }

  public String getSlotId() {
    return slotId;
  }

  public ZoneType getZoneType() {
    return zoneType;
  }

  public SlotState getState() {
    return state;
  }

  public void setState(SlotState state) {
    this.state = state;
  }

  public boolean hasElectricalPlug() {
    return hasElectricalPlug;
  }

  public int getMaxDurationHours() {
    return maxDurationHours;
  }
}