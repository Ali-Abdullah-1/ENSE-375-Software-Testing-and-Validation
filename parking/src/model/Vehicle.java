package model;

import java.util.regex.Pattern;

/**
 * Model component representing a vehicle in the Smart Parking Billing System. 
 * Stores and validates the vehicle's normalized license plate format.
 */
public class Vehicle {

  /**
   * Regular expression pattern requiring 3 to 7 alphanumeric characters.
   */
  public static final Pattern PLATE_PATTERN = Pattern.compile("^[A-Z0-9]{3,7}$");

  private final String licensePlate;

  /**
   * Constructs a new Vehicle after validating and normalizing the license plate.
   *
   * @param licensePlate raw license plate string (3-7 alphanumeric characters)
   * @throws IllegalArgumentException if the license plate format is invalid
   */
  public Vehicle(String licensePlate) {
    if (!isValidPlate(licensePlate)) {
      throw new IllegalArgumentException("Invalid license plate format.");
    }
    this.licensePlate = licensePlate.toUpperCase();
  }

  /**
   * Checks whether a given license plate string matches the valid format.
   * Trims surrounding whitespace and converts to uppercase before matching.
   *
   * @param plate the license plate string to validate
   * @return {@code true} if the plate is non-null and valid, {@code false} otherwise
   */
  public static boolean isValidPlate(String plate) {
    if (plate == null) {
      return false;
    }
    return PLATE_PATTERN.matcher(plate.trim().toUpperCase()).matches();
  }

  /**
   * Returns the normalized (uppercase) license plate of this vehicle.
   *
   * @return the license plate string
   */
  public String getLicensePlate() {
    return licensePlate;
  }
}