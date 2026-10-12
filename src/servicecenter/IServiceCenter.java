package servicecenter;

import transport.ITransport;

public interface IServiceCenter {
    boolean inspectTransport(ITransport transport);

    default RejectionReason getRejectionReason(ITransport transport) {
        return RejectionReason.OTHER;
    }
}
