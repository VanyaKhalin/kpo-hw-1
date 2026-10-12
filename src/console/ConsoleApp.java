package console;

import equipment.ChargingCabinet;
import equipment.DockingStation;
import equipment.Helmet;
import equipment.IEquipment;
import inventory.InventoryService;
import servicecenter.RejectionReason;
import transport.Bicycle;
import transport.ElectricBicycle;
import transport.ElectricScooter;
import transport.ITransport;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConsoleApp {
    private final InventoryService inventoryService;
    private final Scanner scanner;

    public ConsoleApp(InventoryService inventoryService, Scanner scanner) {
        if (inventoryService == null) {
            throw new IllegalArgumentException("Сервис учёта не должен быть null");
        }
        if (scanner == null) {
            throw new IllegalArgumentException("Средство ввода не должно быть null");
        }
        this.inventoryService = inventoryService;
        this.scanner = scanner;
    }

    public void run() {
        System.out.println("Учёт имущества сервиса ВышКат");
        while (true) {
            showMenu();
            try {
                int choice = readInt("Выберите действие: ", 0, 7);
                System.out.println();
                switch (choice) {
                    case 1:
                        addTransport();
                        break;
                    case 2:
                        addEquipment();
                        break;
                    case 3:
                        showStatistics();
                        break;
                    case 4:
                        showTransport();
                        break;
                    case 5:
                        showBeginnerTransport();
                        break;
                    case 6:
                        showEquipment();
                        break;
                    case 7:
                        showInventory();
                        break;
                    case 0:
                        System.out.println("Работа приложения завершена");
                        return;
                }
            } catch (NoSuchElementException e) {
                System.out.println("Ввод завершён. Работа приложения завершена");
                return;
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    private void showMenu() {
        System.out.println();
        System.out.println("1. Добавить транспорт");
        System.out.println("2. Добавить оборудование");
        System.out.println("3. Показать количество транспорта и расход энергии");
        System.out.println("4. Показать весь транспорт");
        System.out.println("5. Показать количество и список транспорта для новичков");
        System.out.println("6. Показать всё оборудование");
        System.out.println("7. Показать всё имущество (транспорт и оборудование)");
        System.out.println("0. Выйти");
    }

    private void addTransport() {
        System.out.println("1. Велосипед");
        System.out.println("2. Электровелосипед");
        System.out.println("3. Электросамокат");
        System.out.println("0. Вернуться в главное меню");
        int choice = readInt("Выберите транспорт: ", 0, 3);
        System.out.println();
        if (choice == 0) {
            return;
        }

        int version = readInt("Версия транспорта (больше нуля): ", 1, Integer.MAX_VALUE);
        String color = readColor();
        int easeOfUse = readInt("Простота использования (от 1 до 10): ", 1, 10);

        ITransport transport;
        if (choice == 1) {
            transport = new Bicycle(easeOfUse, color, version);
        } else if (choice == 2) {
            transport = new ElectricBicycle(easeOfUse, readEnergy(), color, version);
        } else {
            transport = new ElectricScooter(easeOfUse, readEnergy(), color, version);
        }

        System.out.println();
        if (inventoryService.addTransport(transport)) {
            System.out.println("Транспорт добавлен. ID: " + transport.getTransportID());
        } else {
            showTransportRejection(transport);
        }
    }

    private void showTransportRejection(ITransport transport) {
        RejectionReason reason = inventoryService.getTransportRejectionReason(transport);
        switch (reason) {
            case YELLOW_COLOR:
                System.out.println("желтый цвет не подходит, мы же не яндекс!\n");
                break;
            case BLACK_COLOR:
                System.out.println("черный цвет не подходит, выглядит не привлекательно!\n");
                break;
            case OUTDATED_VERSION:
                System.out.println("Версия " + transport.getTransportVersion()
                        + " устаревшая. Необходимая минимальная версия 3\n");
                break;
            default:
                System.out.println("Транспорт не прошёл техосмотр");
                break;
        }
    }

    private String readColor() {
        System.out.println();
        System.out.println("1. Жёлтый");
        System.out.println("2. Зелёный");
        System.out.println("3. Синий");
        System.out.println("4. Чёрный");
        System.out.println("5. Белый");
        System.out.println("6. Красный");
        int choice = readInt("Выберите цвет: ", 1, 6);
        System.out.println();
        switch (choice) {
            case 1:
                return "желтый";
            case 2:
                return "зеленый";
            case 3:
                return "синий";
            case 4:
                return "черный";
            case 5:
                return "белый";
            case 6:
                return "красный";
            default:
                throw new IllegalArgumentException("Неизвестный цвет");
        }
    }

    private void addEquipment() {
        System.out.println("1. Шлем");
        System.out.println("2. Зарядный шкаф");
        System.out.println("3. Док-станция");
        System.out.println("0. Вернуться в главное меню");
        int choice = readInt("Выберите оборудование: ", 0, 3);
        if (choice == 0) {
            return;
        }

        IEquipment item;
        if (choice == 1) {
            item = new Helmet();
        } else if (choice == 2) {
            item = new ChargingCabinet(readEnergy());
        } else {
            item = new DockingStation(readEnergy());
        }
        inventoryService.addEquipment(item);
        System.out.println("Оборудование добавлено. ID: " + item.getEquipmentID());
    }

    private void showStatistics() {
        System.out.println("Количество транспорта: " + inventoryService.getTransportCount());
        System.out.println("Суммарный суточный расход энергии: " + inventoryService.getTotalEnergyConsumption() + " кВт*ч");
    }

    private void showTransport() {
        String report = inventoryService.getTransportReport();
        if (report.isEmpty()) {
            System.out.println("На складе нет транспорта");
        } else {
            System.out.println("ID Вид Версия Цвет");
            System.out.print(report);
        }
    }

    private void showBeginnerTransport() {
        System.out.println("Количество транспорта для новичков: " + inventoryService.getBeginnerTransportCount());
        String report = inventoryService.getBeginnerTransportReport();
        if (report.isEmpty()) {
            System.out.println("Транспорта для новичков нет");
        } else {
            System.out.println("ID Вид Версия Цвет");
            System.out.print(report);
        }
    }

    private void showEquipment() {
        System.out.println("Количество оборудования: " + inventoryService.getEquipmentCount());
        String report = inventoryService.getEquipmentReport();
        if (report.isEmpty()) {
            System.out.println("На складе нет оборудования");
        } else {
            System.out.println("ID Вид");
            System.out.print(report);
        }
    }

    private void showInventory() {
        String report = inventoryService.getInventoryReport();
        if (report.isEmpty()) {
            System.out.println("На складе нет имущества");
        } else {
            System.out.println("ID Наименование");
            System.out.print(report);
        }
    }

    private int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                int value = Integer.parseInt(input.trim());
                if (value < min || value > max) {
                    System.out.println("Введите число от " + min + " до " + max + ".");
                } else {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println("Введите целое число");
            }
        }
    }

    private double readEnergy() {
        while (true) {
            System.out.print("Суточный расход энергии (кВт*ч): ");
            String input = scanner.nextLine();
            try {
                double value = Double.parseDouble(input.trim().replace(',', '.'));
                if (!Double.isFinite(value) || value < 0) {
                    System.out.println("Введите неотрицательное конечное число");
                } else {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println("Введите число, например 2.5 или 2,5");
            }
        }
    }
}
