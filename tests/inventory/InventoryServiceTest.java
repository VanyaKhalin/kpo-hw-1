package inventory;

import equipment.ChargingCabinet;
import equipment.DockingStation;
import equipment.Helmet;
import org.junit.jupiter.api.Test;
import support.FakeServiceCenter;
import transport.Bicycle;
import transport.ElectricBicycle;
import transport.ElectricScooter;
import warehouse.Warehouse;

import static org.junit.jupiter.api.Assertions.*;

class InventoryServiceTest {
    @Test
    void rejectsMissingWarehouseOrInspection() {
        assertThrows(IllegalArgumentException.class,
                () -> new InventoryService(null, new FakeServiceCenter(true)));
        assertThrows(IllegalArgumentException.class,
                () -> new InventoryService(new Warehouse(), null));
    }

    @Test
    void addsTransportWhenFakeInspectionApproves() {
        Warehouse warehouse = new Warehouse();
        InventoryService inventory = new InventoryService(warehouse, new FakeServiceCenter(true));
        Bicycle bicycle = new Bicycle(7, "желтый", 1);

        assertTrue(inventory.addTransport(bicycle));
        assertEquals(1, inventory.getTransportCount());
        assertSame(bicycle, warehouse.getTransports().get(0));
    }

    @Test
    void doesNotAddTransportWhenFakeInspectionRejects() {
        Warehouse warehouse = new Warehouse();
        InventoryService inventory = new InventoryService(warehouse, new FakeServiceCenter(false));
        Bicycle bicycle = new Bicycle(7, "зеленый", 3);

        assertFalse(inventory.addTransport(bicycle));
        assertEquals(0, inventory.getTransportCount());
    }

    @Test
    void countsTransportForBeginnersStartingAtEaseSix() {
        InventoryService inventory = new InventoryService(new Warehouse(), new FakeServiceCenter(true));
        Bicycle difficult = new Bicycle(5, "синий", 3);
        Bicycle beginner = new Bicycle(6, "зеленый", 3);
        inventory.addTransport(difficult);
        inventory.addTransport(beginner);

        assertEquals(1, inventory.getBeginnerTransportCount());
        assertEquals(beginner.getTransportID() + " Bicycle 3 зеленый\n",
                inventory.getBeginnerTransportReport());
    }

    @Test
    void sumsEnergyOfTransportAndEquipment() {
        InventoryService inventory = new InventoryService(new Warehouse(), new FakeServiceCenter(true));
        inventory.addTransport(new Bicycle(7, "зеленый", 3));
        inventory.addTransport(new ElectricBicycle(7, 1.5, "синий", 3));
        inventory.addTransport(new ElectricScooter(7, 2.5, "белый", 3));
        inventory.addEquipment(new Helmet());
        inventory.addEquipment(new ChargingCabinet(3.0));
        inventory.addEquipment(new DockingStation(1.0));

        assertEquals(8.0, inventory.getTotalEnergyConsumption(), 0.000001);
    }

    @Test
    void combinesTransportAndEquipmentInReport() {
        InventoryService inventory = new InventoryService(new Warehouse(), new FakeServiceCenter(true));
        Bicycle bicycle = new Bicycle(7, "зеленый", 3);
        Helmet helmet = new Helmet();
        inventory.addTransport(bicycle);
        inventory.addEquipment(helmet);

        assertEquals(bicycle.getTransportID() + " Bicycle\n"
                + helmet.getEquipmentID() + " Helmet\n", inventory.getInventoryReport());
    }

    @Test
    void includesVersionAndColorInTransportReport() {
        InventoryService inventory = new InventoryService(new Warehouse(), new FakeServiceCenter(true));
        Bicycle bicycle = new Bicycle(7, "синий", 4);
        inventory.addTransport(bicycle);

        assertEquals(bicycle.getTransportID() + " Bicycle 4 синий\n", inventory.getTransportReport());
    }

    @Test
    void reportsZeroEnergyForEmptyWarehouse() {
        InventoryService inventory = new InventoryService(new Warehouse(), new FakeServiceCenter(true));

        assertEquals(0.0, inventory.getTotalEnergyConsumption());
        assertEquals(0, inventory.getTransportCount());
        assertEquals(0, inventory.getEquipmentCount());
    }
}
