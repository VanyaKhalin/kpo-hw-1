package warehouse;

import equipment.Helmet;
import org.junit.jupiter.api.Test;
import transport.Bicycle;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseTest {
    private final Warehouse warehouse = new Warehouse();

    @Test
    void startsWithNoInventory() {
        assertTrue(warehouse.getTransports().isEmpty());
        assertTrue(warehouse.getEquipment().isEmpty());
    }

    @Test
    void storesDistinctTransportsWithSameCharacteristics() {
        Bicycle first = new Bicycle(7, "зеленый", 3);
        Bicycle second = new Bicycle(7, "зеленый", 3);

        warehouse.addTransport(first);
        warehouse.addTransport(second);

        assertEquals(List.of(first, second), warehouse.getTransports());
    }

    @Test
    void doesNotStoreTheSameTransportTwice() {
        Bicycle bicycle = new Bicycle(7, "зеленый", 3);
        warehouse.addTransport(bicycle);

        assertThrows(IllegalArgumentException.class, () -> warehouse.addTransport(bicycle));
        assertEquals(List.of(bicycle), warehouse.getTransports());
    }

    @Test
    void storesDistinctEquipment() {
        Helmet first = new Helmet();
        Helmet second = new Helmet();

        warehouse.addEquipment(first);
        warehouse.addEquipment(second);

        assertEquals(List.of(first, second), warehouse.getEquipment());
    }

    @Test
    void doesNotStoreTheSameEquipmentTwice() {
        Helmet helmet = new Helmet();
        warehouse.addEquipment(helmet);

        assertThrows(IllegalArgumentException.class, () -> warehouse.addEquipment(helmet));
        assertEquals(List.of(helmet), warehouse.getEquipment());
    }

    @Test
    void rejectsNullInventoryWithoutChangingContents() {
        assertThrows(IllegalArgumentException.class, () -> warehouse.addTransport(null));
        assertThrows(IllegalArgumentException.class, () -> warehouse.addEquipment(null));
        assertTrue(warehouse.getTransports().isEmpty());
        assertTrue(warehouse.getEquipment().isEmpty());
    }
}
