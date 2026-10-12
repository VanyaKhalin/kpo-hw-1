package transport;

public class ElectricTransport extends Transport {
    private final double useEnergy;

    public ElectricTransport(int transportEaseOfUse, double useEnergy, String transportColor, int transportVersion) {
        super(transportEaseOfUse, transportColor, transportVersion);
        if (!Double.isFinite(useEnergy) || useEnergy < 0) {
            throw new IllegalArgumentException("Расход энергии должен быть неотрицательным конечным числом");
        }
        this.useEnergy = useEnergy;
    }

    public double getUseEnergy() {
        return useEnergy;
    }
}
