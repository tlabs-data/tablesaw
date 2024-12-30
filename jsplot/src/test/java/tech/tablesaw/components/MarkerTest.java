package tech.tablesaw.components;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import tech.tablesaw.plotly.components.Marker;
import tech.tablesaw.plotly.components.Symbol;

public class MarkerTest {

  @Test
  public void testAsJson() {
    Marker marker = Marker.builder().size(12.0).symbol(Symbol.DIAMOND_TALL).color("#c68486").build();

    final String asJSON = marker.asJSON();
    assertTrue(asJSON.contains("color"));
    assertTrue(asJSON.contains("symbol"));
    assertTrue(asJSON.contains("size"));
  }
}
