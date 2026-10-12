package support;

import servicecenter.IServiceCenter;
import transport.ITransport;

public class FakeServiceCenter implements IServiceCenter {
    private final boolean accepted;

    public FakeServiceCenter(boolean accepted) {
        this.accepted = accepted;
    }

    @Override
    public boolean inspectTransport(ITransport transport) {
        return accepted;
    }
}
