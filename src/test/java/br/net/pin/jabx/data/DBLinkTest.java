package br.net.pin.jabx.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DBLinkTest {
  @Test
  void testNew() {
    var object = new DBLink();
    Assertions.assertNotNull(object, "object should be constructed");
  }

  @Test
  void testConnect() throws Exception {
    var object = new DBLink(DataBase.SQLiteMemory);
    object.connect();
  }
}
