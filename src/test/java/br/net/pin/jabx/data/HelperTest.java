package br.net.pin.jabx.data;

import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HelperTest {
  @Test
  void testNew() {
    var object = new Helper() {};
    Assertions.assertNotNull(object, "object should be constructed");
  }

  @Test
  void testCreate() throws Exception {
    var link = new DBLink(DataBase.SQLiteMemory);
    var conn = link.connect();
    link.base.helper.create(conn,
        new Table(new Head("test"),
            Arrays.asList(new Field("col1", Nature.CHARS)),
            Arrays.asList("col1")));
  }

  @Test
  void testInsert() throws Exception {
    var link = new DBLink(DataBase.SQLiteMemory);
    var conn = link.connect();
    link.base.helper.create(conn,
        new Table(new Head("test"),
            Arrays.asList(
                new Field("col1", Nature.CHARS)),
            Arrays.asList("col1")));
    link.base.helper.insert(conn,
        new Insert(new Head("test"),
            Arrays.asList(
                new Valued("col1", "This is a test"))));
  }

  @Test
  void testSelect() throws Exception {
    var link = new DBLink(DataBase.SQLiteMemory);
    var conn = link.connect();
    link.base.helper.create(conn,
        new Table(new Head("test"),
            Arrays.asList(
                new Field("col1", Nature.CHARS)),
            Arrays.asList("col1")));
    link.base.helper.insert(conn,
        new Insert(new Head("test"),
            Arrays.asList(
                new Valued("col1", "This is a test"))));
    var rst = link.base.helper.select(conn, new Select(new Head("test")));
    rst.next();
    var got = rst.getString(1);
    Assertions.assertEquals("This is a test", got);
  }

  @Test
  void testUpdate() throws Exception {
    var link = new DBLink(DataBase.SQLiteMemory);
    var conn = link.connect();
    link.base.helper.create(conn,
        new Table(new Head("test"),
            Arrays.asList(
                new Field("col1", Nature.CHARS)),
            Arrays.asList("col1")));
    link.base.helper.insert(conn,
        new Insert(new Head("test"),
            Arrays.asList(
                new Valued("col1", "This is a test"))));
    var rstInsert = link.base.helper.select(conn,
        new Select(new Head("test")));
    rstInsert.next();
    var gotInsert = rstInsert.getString(1);
    Assertions.assertEquals("This is a test", gotInsert);
    link.base.helper.update(conn,
        new Update(new Head("test"),
            Arrays.asList(
                new Valued("col1", "This is a update"))));
    var rstUpdate = link.base.helper.select(conn,
        new Select(new Head("test")));
    rstUpdate.next();
    var got = rstUpdate.getString(1);
    Assertions.assertEquals("This is a update", got);
  }
}
