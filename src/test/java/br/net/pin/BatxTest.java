package br.net.pin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class JabxTest {
  @Test
  void testNew() {
    var object = new Jabx();
    Assertions.assertNotNull(object, "object should be constructed");
  }
}
