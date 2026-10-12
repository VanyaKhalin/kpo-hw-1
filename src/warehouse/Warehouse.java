package warehouse;

import equipment.IEquipment;
import transport.ITransport;

import java.util.ArrayList;
import java.util.List;

public class Warehouse implements IWarehouse {
    private final List<ITransport> transports = new ArrayList<>();
    private final List<IEquipment> equipment = new ArrayList<>();

    @Override
    public void addTransport(ITransport transport) {
        if (transport == null) {
            throw new IllegalArgumentException("Транспорт не должен быть null");
        }
        for (ITransport existingTransport : transports) {
            if (existingTransport.getTransportID().equals(transport.getTransportID())) {
                throw new IllegalArgumentException("Транспорт с таким ID уже есть на складе");
            }
        }
        transports.add(transport);
    }

    @Override
    public void addEquipment(IEquipment item) {
        if (item == null) {
            throw new IllegalArgumentException("Оборудование не должно быть null");
        }
        for (IEquipment existingEquipment : equipment) {
            if (existingEquipment.getEquipmentID().equals(item.getEquipmentID())) {
                throw new IllegalArgumentException("Оборудование с таким ID уже есть на складе");
            }
        }
        equipment.add(item);
    }

    @Override
    public List<ITransport> getTransports() {
        return transports;
    }

    @Override
    public List<IEquipment> getEquipment() {
        return equipment;
    }
}
