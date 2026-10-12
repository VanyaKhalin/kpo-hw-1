import console.ConsoleApp;
import di.DIContainer;
import inventory.InventoryService;
import servicecenter.IServiceCenter;
import servicecenter.ServiceCenter;
import warehouse.IWarehouse;
import warehouse.Warehouse;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            DIContainer container = new DIContainer();
            container.register(IWarehouse.class, () -> new Warehouse());
            container.register(IServiceCenter.class, () -> new ServiceCenter());
            container.register(Scanner.class, () -> scanner);

            container.register(InventoryService.class, () -> new InventoryService(
                    container.get(IWarehouse.class),
                    container.get(IServiceCenter.class)
            ));
            container.register(ConsoleApp.class, () -> new ConsoleApp(
                    container.get(InventoryService.class),
                    container.get(Scanner.class)
            ));

            ConsoleApp app = container.get(ConsoleApp.class);
            app.run();
        }
    }
}
