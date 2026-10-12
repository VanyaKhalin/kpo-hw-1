package transport;

public class Transport implements ITransport {
    private static int counter = 1;
    private final String transportID;
    private final int transportEaseOfUse;
    private final String transportColor;
    private final int transportVersion;

    public Transport(int transportEaseOfUse, String transportColor, int transportVersion) {
        if (transportEaseOfUse < 1 || transportEaseOfUse > 10) {
            throw new IllegalArgumentException("Простота использования должна быть от 1 до 10");
        }
        if (transportColor == null || transportColor.isBlank()) {
            throw new IllegalArgumentException("Цвет транспорта не должен быть пустым");
        }
        if (transportVersion <= 0) {
            throw new IllegalArgumentException("Версия транспорта должна быть больше нуля");
        }
        this.transportID = "T-" + counter++;
        this.transportEaseOfUse = transportEaseOfUse;
        this.transportColor = transportColor;
        this.transportVersion = transportVersion;
    }

    @Override
    public String getTransportID() {
        return transportID;
    }

    @Override
    public int getTransportEaseOfUse() {
        return transportEaseOfUse;
    }

    @Override
    public String getTransportColor() {
        return transportColor;
    }

    @Override
    public int getTransportVersion() {
        return transportVersion;
    }
}
