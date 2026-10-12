package servicecenter;

import transport.ITransport;

public class ServiceCenter implements IServiceCenter {
    @Override
    public boolean inspectTransport(ITransport transport) {
        return getRejectionReason(transport) == RejectionReason.NONE;
    }

    @Override
    public RejectionReason getRejectionReason(ITransport transport) {
        if (transport == null) {
            throw new IllegalArgumentException("Транспорт не должен быть null\n");
        }
        
        String color = transport.getTransportColor();
        if (color.equals("желтый")) {
            return RejectionReason.YELLOW_COLOR;
        }
        if (color.equals("черный")) {
            return RejectionReason.BLACK_COLOR;
        }
        if (transport.getTransportVersion() < 3) {
            return RejectionReason.OUTDATED_VERSION;
        }

        return RejectionReason.NONE;
    }
}
