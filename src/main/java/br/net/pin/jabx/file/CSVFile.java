package br.net.pin.jabx.file;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import br.net.pin.jabx.mage.WizChars;

public class CSVFile implements Closeable {

  public static enum Mode {
    READ, WRITE, APPEND
  }

  private final File file;
  private final Mode mode;
  private final BufferedReader reader;
  private final PrintWriter writer;

  public CSVFile(File file, Mode mode) throws Exception {
    this.file = file;
    this.mode = mode;
    if (mode == Mode.READ) {
      this.reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8));
      this.writer = null;
    } else {
      this.reader = null;
      this.writer = new PrintWriter(new FileOutputStream(file, mode == Mode.APPEND), true,
          StandardCharsets.UTF_8);
    }
  }

  public File getFile() {
    return this.file;
  }

  public Mode getMode() {
    return this.mode;
  }

  public String[] readLine() throws Exception {
    final var line = this.reader.readLine();
    if (line == null) {
      return null;
    }
    var result = new LinkedList<>();
    var openQuotes = false;
    var builder = new StringBuilder();
    for (var i = 0; i < line.length(); i++) {
      var actual = line.charAt(i);
      var next = i < line.length() - 1 ? line.charAt(i + 1) : ' ';
      if (openQuotes) {
        if (actual == '"') {
          if (next == '"') {
            builder.append('"');
            i++;
          } else {
            openQuotes = false;
          }
        } else {
          builder.append(actual);
        }
      } else if (actual == '"') {
        openQuotes = true;
      } else if (actual == ',' || actual == ';') {
        result.add(WizChars.remakeControlFlow(builder.toString()));
        builder = new StringBuilder();
      } else {
        builder.append(actual);
      }
    }
    result.add(WizChars.remakeControlFlow(builder.toString()));
    return result.toArray(new String[result.size()]);
  }

  public void writeLine(String... columns) {
    if (columns != null) {
      for (var i = 0; i < columns.length; i++) {
        var column = WizChars.replaceControlFlow(columns[i]);
        if (WizChars.contains(column, '"', ' ', ',', ';')) {
          column = '"' + column.replace("\"", "\"\"") + '"';
        }
        if (i > 0) {
          this.writer.write(",");
        }
        if (WizChars.isNotEmpty(column)) {
          this.writer.write(column);
        }
      }
    }
    this.writer.write(System.lineSeparator());
  }

  @Override
  public void close() throws IOException {
    if (this.reader != null) {
      this.reader.close();
    }
    if (this.writer != null) {
      this.writer.close();
    }
  }

}
