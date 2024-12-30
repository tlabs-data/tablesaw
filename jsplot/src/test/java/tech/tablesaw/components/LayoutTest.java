package tech.tablesaw.components;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import tech.tablesaw.plotly.components.Axis;
import tech.tablesaw.plotly.components.Grid;
import tech.tablesaw.plotly.components.Layout;
import tech.tablesaw.plotly.components.Margin;

public class LayoutTest {

  private static final String LINE_END = System.lineSeparator();

  @Test
  public void asJavascript() {

    Axis x = Axis.builder().title("x axis").build();
    Axis y = Axis.builder().title("y axis").build();

    Layout layout =
        Layout.builder()
            .title("foobar")
            .xAxis(x)
            .yAxis(y)
            .showLegend(true)
            .margin(Margin.builder().top(100).bottom(100).left(200).right(200).build())
            .build();
    final String asJavascript = layout.asJavascript();
    assertTrue(asJavascript.startsWith("var layout = {"));
    assertTrue(asJavascript.contains("title : \"foobar\""));
    assertTrue(asJavascript.contains("  margin : {" + LINE_END
      + "    autoexpand : true"));
  }

  @Test
  public void asJavascriptForGrid() {

    Axis x = Axis.builder().title("x axis").build();
    Axis y = Axis.builder().title("y axis").build();
    Grid grid = Grid.builder().rows(2).columns(2).build();
    Layout layout =
        Layout.builder()
            .title("foobar")
            .xAxis(x)
            .yAxis(y)
            .grid(grid)
            .showLegend(true)
            .margin(Margin.builder().top(100).bottom(100).left(200).right(200).build())
            .build();
    String asJavascript = layout.asJavascript();
    assertTrue(asJavascript.contains("rows"));
    assertTrue(asJavascript.contains("columns"));
    assertTrue(asJavascript.contains("rows"));
    assertTrue(asJavascript.contains("xaxis"));
  }

  @Test
  public void testAutosize() {
      Layout layout = Layout.builder().autosize(true).build();
      assertEquals(
        "var layout = {"
            + LINE_END
            + "  autosize : true"
            + LINE_END
            + "};"
            + LINE_END,
        layout.asJavascript());
  }
  
  @Test
  public void testAutosizeAndWidth() {
      Layout layout = Layout.builder().autosize(true).width(800).build();
      assertEquals(
        "var layout = {"
          + LINE_END
          + "  autosize : true,"
          + LINE_END
          + "  width : 800"
          + LINE_END
          + "};"
          + LINE_END,
        layout.asJavascript());
    }

  @Test
  public void testAutosizeAndHeightWidth() {
      Layout layout = Layout.builder().autosize(true).height(600).width(800).build();
      assertEquals(
        "var layout = {"
          + LINE_END
          + "  autosize : true,"
          + LINE_END
          + "  height : 600,"
          + LINE_END
          + "  width : 800"
          + LINE_END
          + "};"
          + LINE_END,
        layout.asJavascript());
    }
  @Test
  public void testAutosizeAndHeight() {
      Layout layout = Layout.builder().autosize(false).height(600).build();
      assertEquals(
        "var layout = {"
            + LINE_END
            + "  height : 600"
            + LINE_END
            + "};"
            + LINE_END,
        layout.asJavascript());
  }
}
