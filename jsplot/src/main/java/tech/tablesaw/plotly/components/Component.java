package tech.tablesaw.plotly.components;

import static tech.tablesaw.plotly.JsonMapper.JSON_MAPPER;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UncheckedIOException;
import java.util.Map;

public abstract class Component {

  protected abstract Map<String, Object> getJSONContext();
  
  protected String asJSON() {
    StringWriter w = new StringWriter();
    try {
      JSON_MAPPER.writeValue(w, getJSONContext());
    } catch (IOException ioe) {
      throw new UncheckedIOException(ioe);
    }
    return w.toString();
  }

  @Override
  public String toString() {
    return asJSON();
  }
}
