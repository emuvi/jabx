package br.net.pin;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JabxTest {
    @Test
    void constructed() {
        var constructed = new Jabx();
        assertNotNull(constructed, "command should be constructed");
    }
}
