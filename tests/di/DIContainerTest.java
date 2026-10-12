package di;

import inventory.InventoryService;
import org.junit.jupiter.api.Test;
import servicecenter.IServiceCenter;
import support.FakeServiceCenter;
import transport.Bicycle;
import warehouse.IWarehouse;
import warehouse.Warehouse;

import static org.junit.jupiter.api.Assertions.*;

class DIContainerTest {
    @Test
    void returnsTheSameWarehouseOnRepeatedRequests() {
        DIContainer container = new DIContainer();
        container.register(IWarehouse.class, () -> new Warehouse());

        IWarehouse first = container.get(IWarehouse.class);
        IWarehouse second = container.get(IWarehouse.class);

        assertSame(first, second);
    }

    @Test
    void rejectsUnregisteredDependency() {
        DIContainer container = new DIContainer();

        assertThrows(IllegalArgumentException.class, () -> container.get(IWarehouse.class));
    }

    @Test
    void usesFakeInspectionRegisteredInContainer() {
        DIContainer container = new DIContainer();
        container.register(IWarehouse.class, () -> new Warehouse());
        container.register(IServiceCenter.class, () -> new FakeServiceCenter(false));
        container.register(InventoryService.class, () -> new InventoryService(
                container.get(IWarehouse.class), container.get(IServiceCenter.class)));
        InventoryService inventory = container.get(InventoryService.class);

        assertFalse(inventory.addTransport(new Bicycle(7, "зеленый", 3)));
        assertTrue(container.get(IWarehouse.class).getTransports().isEmpty());
    }
}
