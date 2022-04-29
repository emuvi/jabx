package br.net.pin.jabx.data;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HelperTest {
    @Test
    void testNew() {
        var object = new Helper() {
        };
        Assertions.assertNotNull(object, "object should be constructed");
    }

    @Test
    void testCreate() throws Exception {
        var link = new DataLink(DataBase.SQLiteMemory);
        var conn = link.connect();
        link.base.helper.create(conn,
                new Table(new Registry("test"),
                        Arrays.asList(new Field("col1", Nature.CHARS)),
                        Arrays.asList("col1")));
    }

    @Test
    void testInsert() throws Exception {
        var link = new DataLink(DataBase.SQLiteMemory);
        var conn = link.connect();
        link.base.helper.create(conn,
                new Table(new Registry("test"),
                        Arrays.asList(
                                new Field("col1", Nature.CHARS)),
                        Arrays.asList("col1")));
        link.base.helper.insert(conn,
                new Insert(new Registry("test"),
                        Arrays.asList(
                                new Valued("col1", "This is a test"))));
    }

    @Test
    void testSelect() throws Exception {
        var link = new DataLink(DataBase.SQLiteMemory);
        var conn = link.connect();
        link.base.helper.create(conn,
                new Table(new Registry("test"),
                        Arrays.asList(
                                new Field("col1", Nature.CHARS)),
                        Arrays.asList("col1")));
        link.base.helper.insert(conn,
                new Insert(new Registry("test"),
                        Arrays.asList(
                                new Valued("col1", "This is a test"))));
        var rst = link.base.helper.select(conn, new Select(new Registry("test")));
        rst.next();
        var got = rst.getString(1);
        Assertions.assertEquals("This is a test", got);
    }

    @Test
    void testUpdate() throws Exception {
        var link = new DataLink(DataBase.SQLiteMemory);
        var conn = link.connect();
        link.base.helper.create(conn,
                new Table(new Registry("test"),
                        Arrays.asList(
                                new Field("col1", Nature.CHARS)),
                        Arrays.asList("col1")));
        link.base.helper.insert(conn,
                new Insert(new Registry("test"),
                        Arrays.asList(
                                new Valued("col1", "This is a test"))));
        var rstInsert = link.base.helper.select(conn,
                new Select(new Registry("test")));
        rstInsert.next();
        var gotInsert = rstInsert.getString(1);
        Assertions.assertEquals("This is a test", gotInsert);
        link.base.helper.update(conn,
                new Update(new Registry("test"),
                        Arrays.asList(
                                new Valued("col1", "This is a update"))));
        var rstUpdate = link.base.helper.select(conn,
                new Select(new Registry("test")));
        rstUpdate.next();
        var got = rstUpdate.getString(1);
        Assertions.assertEquals("This is a update", got);
    }
}
