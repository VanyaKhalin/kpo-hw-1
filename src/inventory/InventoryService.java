package inventory;

import equipment.ElectricEquipment;
import equipment.IEquipment;
import servicecenter.IServiceCenter;
import servicecenter.RejectionReason;
import transport.ElectricTransport;
import transport.ITransport;
import warehouse.IWarehouse;

public class InventoryService {
    private final IWarehouse warehouse;
    private final IServiceCenter serviceCenter;

    public InventoryService(IWarehouse warehouse, IServiceCenter serviceCenter) {
        if (warehouse == null) {
            throw new IllegalArgumentException("Склад не должен быть null");
        }
        if (serviceCenter == null) {
            throw new IllegalArgumentException("Сервис техосмотра не должен быть null");
        }
        this.warehouse = warehouse;
        this.serviceCenter = serviceCenter;
    }

    public boolean addTransport(ITransport transport) {
        if (serviceCenter.inspectTransport(transport)) {
            warehouse.addTransport(transport);
            return true;
        }
        return false;
    }

    public RejectionReason getTransportRejectionReason(ITransport transport) {
        return serviceCenter.getRejectionReason(transport);
    }

    public void addEquipment(IEquipment item) {
        warehouse.addEquipment(item);
    }

    public int getEquipmentCount() {
        return warehouse.getEquipment().size();
    }

    public String getEquipmentReport() {
        StringBuilder report = new StringBuilder();
        for (IEquipment item : warehouse.getEquipment()) {
            report.append(item.getEquipmentID() + " "
                    + item.getClass().getSimpleName() + "\n");
        }
        return report.toString();
    }

    public String getInventoryReport() {
        StringBuilder report = new StringBuilder();
        for (ITransport transport : warehouse.getTransports()) {
            report.append(transport.getTransportID() + " "
                    + transport.getClass().getSimpleName() + "\n");
        }
        report.append(getEquipmentReport());
        return report.toString();
    }

    public int getTransportCount() {
        return warehouse.getTransports().size();
    }

    public String getTransportReport() {
        StringBuilder report = new StringBuilder();
        for (ITransport transport : warehouse.getTransports()) {
            report.append(transport.getTransportID() + " "
                    + transport.getClass().getSimpleName() + " "
                    + transport.getTransportVersion() + " "
                    + transport.getTransportColor() + "\n");
        }
        return report.toString();
    }

    public int getBeginnerTransportCount() {
        int count = 0;
        for (ITransport transport : warehouse.getTransports()) {
            if (transport.getTransportEaseOfUse() >= 6) {
                count++;
            }
        }
        return count;
    }

    public String getBeginnerTransportReport() {
        StringBuilder report = new StringBuilder();
        for (ITransport transport : warehouse.getTransports()) {
            if (transport.getTransportEaseOfUse() >= 6) {
                report.append(transport.getTransportID() + " "
                        + transport.getClass().getSimpleName() + " "
                        + transport.getTransportVersion() + " "
                        + transport.getTransportColor() + "\n");
            }
        }
        return report.toString();
    }

    public double getTotalEnergyConsumption() {
        double totalEnergy = 0;
        for (ITransport transport : warehouse.getTransports()) {
            if (transport instanceof ElectricTransport) {
                ElectricTransport electricTransport = (ElectricTransport) transport;
                totalEnergy += electricTransport.getUseEnergy();
            }
        }
        for (IEquipment item : warehouse.getEquipment()) {
            if (item instanceof ElectricEquipment) {
                ElectricEquipment electricEquipment = (ElectricEquipment) item;
                totalEnergy += electricEquipment.getUseEnergy();
            }
        }
        return totalEnergy;
    }
}
