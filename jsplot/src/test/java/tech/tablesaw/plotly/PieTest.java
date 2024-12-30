package tech.tablesaw.plotly;

import java.io.File;
import java.nio.file.Paths;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import tech.tablesaw.plotly.components.Figure;
import tech.tablesaw.plotly.traces.PieTrace;

@Disabled
class PieTest {

  private final Object[] x = {"sheep", "cows", "fish", "tree sloths"};
  private final double[] y = {1, 4, 9, 16};

  @Test
  void testAsJavascript() {
    PieTrace trace = PieTrace.builder(x, y).build();
    System.out.println(trace.asJavascript(1));
  }

  @Test
  void show() {
    PieTrace trace = PieTrace.builder(x, y).build();
    Figure figure = new Figure(trace);
    File outputFile = Paths.get("testoutput/output.html").toFile();
    Plot.show(figure, "target", outputFile);
  }
}
