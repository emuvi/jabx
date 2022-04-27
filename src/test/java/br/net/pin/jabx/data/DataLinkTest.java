package br.net.pin.jabx.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DataLinkTest {
  @Test
  void testNew() {
    var object = new DataLink();
    Assertions.assertNotNull(object, "object should be constructed");
  }

  @Test
  void testConnect() throws Exception {
    var object = new DataLink(DataBase.SQLiteMemory);
    object.connect();
  }
}
