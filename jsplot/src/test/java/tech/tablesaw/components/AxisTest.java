package tech.tablesaw.components;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import tech.tablesaw.plotly.components.Axis;
import tech.tablesaw.plotly.components.Font;

class AxisTest {

  private static final String LINE_END = System.lineSeparator();

  @Test
  void testAsJSON() {
    Axis x =
        Axis.builder()
            .title("x Axis 1")
            .visible(true)
            .type(Axis.Type.DEFAULT)
            .titleFont(Font.builder().family(Font.Family.ARIAL).size(8).color("red").build())
            .build();
    String expected = "{" + LINE_END
      + "  title : \"x Axis 1\"," + LINE_END
      + "  titlefont : {" + LINE_END
      + "    color : \"red\"," + LINE_END
      + "    family : \"arial\"," + LINE_END
      + "    size : 8" + LINE_END
      + "  }" + LINE_END
      + "}";
    assertEquals(expected, x.asJSON());
  }
}
