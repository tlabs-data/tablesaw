package tech.tablesaw.components;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import tech.tablesaw.plotly.components.Axis;
import tech.tablesaw.plotly.components.Font;

public class AxisTest {

  @Test
  public void testAsJSON() {
    Axis x =
        Axis.builder()
            .title("x Axis 1")
            .visible(true)
            .type(Axis.Type.DEFAULT)
            .titleFont(Font.builder().family(Font.Family.ARIAL).size(8).color("red").build())
            .build();
    String expected = "{\n"
      + "  title : \"x Axis 1\",\n"
      + "  titlefont : {\n"
      + "    color : \"red\",\n"
      + "    family : \"arial\",\n"
      + "    size : 8\n"
      + "  }\n"
      + "}";
    assertEquals(expected, x.asJSON());
  }
}
