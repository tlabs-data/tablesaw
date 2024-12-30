package tech.tablesaw.components;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import tech.tablesaw.plotly.components.Font;
import tech.tablesaw.plotly.components.HoverLabel;

class HoverLabelTest {

  @Test
  void testAsJSON() {
    HoverLabel hoverLabel =
        HoverLabel.builder()
            .nameLength(10)
            .bgColor("blue")
            .borderColor("green")
            .font(Font.builder().family(Font.Family.ARIAL).size(8).color("red").build())
            .build();

    final String asJSON = hoverLabel.asJSON();
    assertTrue(asJSON.contains("bgcolor : \"blue\""));
    assertTrue(asJSON.contains("bordercolor : \"green\""));
    assertTrue(asJSON.contains("namelength : 10"));
  }
}
