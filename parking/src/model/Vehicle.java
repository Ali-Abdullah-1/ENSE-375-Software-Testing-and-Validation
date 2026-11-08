package model;
import java.util.regex.Pattern;

public class Vehicle {
  public static final Pattern PLATE_PATTERN = Pattern.compile("^[A-Z0-9]{3,7}$");
  private final String licensePlate;

  public Vehicle(String licensePlate) {
    if(!isValidPlate(licensePlate)) {
      throw new IllegalArgumentException("Invalid license plate format.");
    }
    this.licensePlate = licensePlate.toUpperCase();
  }

  public boolean isValidPlate(String plate) {
    if (plate == null) return false;
    return PLATE_PATTERN.matcher(plate.trim().toUpperCase()).matches();
  }

  public String getLicensePlate() {
    return licensePlate;
  }
}