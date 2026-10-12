package equipment;

public class Equipment implements IEquipment {
    private static int counter = 1;
    private final String equipmentID;

    public Equipment() {
        this.equipmentID = "E-" + counter++;
    }

    @Override
    public String getEquipmentID() {
        return equipmentID;
    }
}
