package tech.tablesaw.plotly.components;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import tech.tablesaw.plotly.components.change.ChangeLine;
import tech.tablesaw.plotly.components.change.Increasing;

public class ChangeTest {

  @Test
  public void testAsJSON() {

    Increasing increasing =
        Increasing.builder()
            .changeLine(ChangeLine.builder().width(3).color("blue").build())
            .fillColor("444")
            .build();

    final String asJSON = increasing.asJSON();
    assertTrue(asJSON.contains("line"));
    assertTrue(asJSON.contains("color"));
    assertTrue(asJSON.contains("width"));
    assertTrue(asJSON.contains("fillcolor"));
  }

  @Test
  public void testChangeLine() {

    ChangeLine line = ChangeLine.builder().width(4).color("444").build();

    final String asJSON = line.asJSON();
    assertTrue(asJSON.contains("color"));
    assertTrue(asJSON.contains("width"));
  }
}
