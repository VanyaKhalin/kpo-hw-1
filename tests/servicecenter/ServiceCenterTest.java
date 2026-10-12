package servicecenter;

import org.junit.jupiter.api.Test;
import transport.Bicycle;

import static org.junit.jupiter.api.Assertions.*;

class ServiceCenterTest {
    @Test
    void rejectsNullTransport() {
        ServiceCenter center = new ServiceCenter();

        assertThrows(IllegalArgumentException.class, () -> center.inspectTransport(null));
    }

    @Test
    void acceptsGreenTransportOfVersionThree() {
        ServiceCenter center = new ServiceCenter();
        Bicycle bicycle = new Bicycle(7, "зеленый", 3);

        assertTrue(center.inspectTransport(bicycle));
    }

    @Test
    void rejectsYellowTransport() {
        ServiceCenter center = new ServiceCenter();
        Bicycle bicycle = new Bicycle(7, "желтый", 3);

        assertFalse(center.inspectTransport(bicycle));
        assertEquals(RejectionReason.YELLOW_COLOR, center.getRejectionReason(bicycle));
    }

    @Test
    void rejectsBlackTransport() {
        ServiceCenter center = new ServiceCenter();
        Bicycle bicycle = new Bicycle(7, "черный", 3);

        assertFalse(center.inspectTransport(bicycle));
        assertEquals(RejectionReason.BLACK_COLOR, center.getRejectionReason(bicycle));
    }

    @Test
    void rejectsVersionBelowThree() {
        ServiceCenter center = new ServiceCenter();
        Bicycle bicycle = new Bicycle(7, "зеленый", 2);

        assertFalse(center.inspectTransport(bicycle));
        assertEquals(RejectionReason.OUTDATED_VERSION, center.getRejectionReason(bicycle));
    }
}
