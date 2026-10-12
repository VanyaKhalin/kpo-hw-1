package equipment;

public class ElectricEquipment extends Equipment {
    private final double useEnergy;

    public ElectricEquipment(double useEnergy) {
        super();
        if (!Double.isFinite(useEnergy) || useEnergy < 0) {
            throw new IllegalArgumentException("Расход энергии должен быть неотрицательным конечным числом");
        }
        this.useEnergy = useEnergy;
    }

    public double getUseEnergy() {
        return useEnergy;
    }
}
