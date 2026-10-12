package warehouse;

import equipment.IEquipment;
import transport.ITransport;

import java.util.List;

public interface IWarehouse {
    void addTransport(ITransport transport);
    void addEquipment(IEquipment item);
    List<ITransport> getTransports();
    List<IEquipment> getEquipment();
}
