package transport;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransportTest {
    @Test
    void savesTransportCharacteristics() {
        Bicycle bicycle = new Bicycle(6, "синий", 3);

        assertEquals(6, bicycle.getTransportEaseOfUse());
        assertEquals("синий", bicycle.getTransportColor());
        assertEquals(3, bicycle.getTransportVersion());
        assertTrue(bicycle.getTransportID().startsWith("T-"));
    }

    @Test
    void rejectsEaseOfUseOutsideAllowedRange() {
        assertThrows(IllegalArgumentException.class, () -> new Bicycle(0, "синий", 3));
        assertThrows(IllegalArgumentException.class, () -> new Bicycle(11, "синий", 3));
    }

    @Test
    void rejectsEmptyColor() {
        assertThrows(IllegalArgumentException.class, () -> new Bicycle(7, "", 3));
    }

    @Test
    void rejectsNonPositiveVersion() {
        assertThrows(IllegalArgumentException.class, () -> new Bicycle(7, "синий", 0));
    }

    @Test
    void assignsDifferentIdsToDifferentTransports() {
        Bicycle first = new Bicycle(7, "синий", 3);
        Bicycle second = new Bicycle(7, "синий", 3);

        assertNotEquals(first.getTransportID(), second.getTransportID());
    }

    @Test
    void rejectsNegativeEnergyConsumption() {
        assertThrows(IllegalArgumentException.class,
                () -> new ElectricScooter(7, -1.0, "зеленый", 3));
    }
}
