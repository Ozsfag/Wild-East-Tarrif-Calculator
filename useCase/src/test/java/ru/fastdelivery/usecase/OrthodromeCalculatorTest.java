package ru.fastdelivery.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class OrthodromeCalculatorTest {

  @Test
  public void test_orthodrome_distance_calculation() {

    // New York coordinates
    double lat1 = 40.7128;
    double lon1 = -74.0060;

    // London coordinates
    double lat2 = 51.5074;
    double lon2 = -0.1278;

    // Expected distance in kilometers (approximate)
    double expectedDistance = 5570.0;

    // Assuming the calculator has a method to calculate distance
    double actualDistance =
        OrthodromeCalculator.calculateDistanceByCoordinates(lat1, lon1, lat2, lon2);

    // Assert with a reasonable delta for floating point comparison
    assertEquals(expectedDistance, actualDistance, 10.0);
  }
}
