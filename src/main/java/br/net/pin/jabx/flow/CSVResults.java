package br.net.pin.jabx.flow;

import java.sql.ResultSet;

import br.net.pin.jabx.data.Nature;
import br.net.pin.jabx.mage.WizData;

public class CSVResults {
  private final ResultSet results;
  private final Nature[] natures;

  public CSVResults(ResultSet results, Nature[] natures) throws Exception {
    this.results = results;
    if (natures != null) {
      this.natures = natures;
    } else {
      var metaData = this.results.getMetaData();
      var columnCount = metaData.getColumnCount();
      this.natures = new Nature[columnCount];
      for (int i = 1; i <= columnCount; i++) {
        this.natures[(i - 1)] = WizData.getNatureOfSQL(metaData.getColumnType(i));
      }
    }
  }

  public String[] makeLine() throws Exception {
    var result = new String[this.natures.length];
    for (int i = 1; i <= this.natures.length; i++) {
      result[(i - 1)] = WizData.formatValue(this.natures[(i - 1)], this.results.getObject(i));
    }
    return result;
  }
}
