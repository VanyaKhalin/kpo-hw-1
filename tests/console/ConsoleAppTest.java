package console;

import inventory.InventoryService;
import org.junit.jupiter.api.Test;
import servicecenter.ServiceCenter;
import warehouse.Warehouse;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleAppTest {
    @Test
    void addsHelmetThroughConsoleMenu() {
        InventoryService inventory = new InventoryService(new Warehouse(), new ServiceCenter());
        // 2 — добавить оборудование, 1 — шлем, 0 — выйти.
        try (Scanner scanner = new Scanner("2\n1\n0\n")) {
            new ConsoleApp(inventory, scanner).run();
        }

        assertEquals(1, inventory.getEquipmentCount());
        assertEquals(0, inventory.getTransportCount());
    }

    @Test
    void showsReportsForEmptyWarehouseWithoutErrors() {
        InventoryService inventory = new InventoryService(new Warehouse(), new ServiceCenter());
        // Пункты 3–7 — отчёты; 0 — выйти.
        try (Scanner scanner = new Scanner("3\n4\n5\n6\n7\n0\n")) {
            ConsoleApp app = new ConsoleApp(inventory, scanner);
            assertDoesNotThrow(() -> app.run());
        }

        assertEquals(0, inventory.getTransportCount());
        assertEquals(0, inventory.getEquipmentCount());
    }
}
