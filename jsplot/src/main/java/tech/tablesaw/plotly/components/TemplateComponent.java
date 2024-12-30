package tech.tablesaw.plotly.components;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UncheckedIOException;
import java.io.Writer;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.error.PebbleException;
import io.pebbletemplates.pebble.template.PebbleTemplate;

public abstract class TemplateComponent extends Component {

  @JsonIgnore
  private final PebbleEngine engine = TemplateUtils.getNewEngine();

  protected PebbleEngine getEngine() {
    return engine;
  }

  public abstract String asJavascript();

  protected String asJavascript(String filename) {
    Writer writer = new StringWriter();
    PebbleTemplate compiledTemplate;
    try {
      compiledTemplate = getEngine().getTemplate(filename);
      compiledTemplate.evaluate(writer, getJSONContext());
    } catch (PebbleException e) {
      throw new IllegalStateException(e);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
    return writer.toString();
  }

}
