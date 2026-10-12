package equipment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EquipmentTest {
    @Test
    void assignsDifferentIdsToEquipment() {
        Helmet first = new Helmet();
        Helmet second = new Helmet();

        assertTrue(first.getEquipmentID().startsWith("E-"));
        assertNotEquals(first.getEquipmentID(), second.getEquipmentID());
    }

    @Test
    void rejectsNegativeEnergyConsumption() {
        assertThrows(IllegalArgumentException.class, () -> new ChargingCabinet(-1.0));
    }
}
