package tech.tablesaw.components;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import tech.tablesaw.plotly.components.Annotation;
import tech.tablesaw.plotly.components.Font;

class AnnotationTest {
  
  @Test
  void testYAnchor() {
    Annotation annotation = Annotation.builder().xref("paper").
        yref("paper")
        .x(0)
        .y(0)
        .yanchor(Annotation.Yanchor.BOTTOM)
        .bordercolor("#c7c7c7")
        .font(Font.builder().build())
        .text("X asis label")
        .showarrow(false).build();
    final String asJSON = annotation.asJSON();
    assertTrue(asJSON.contains("yanchor : \"bottom\""));
    assertFalse(asJSON.contains("paper"));
  }

  @Test
  void testXYRef() {
    Annotation annotation = Annotation.builder()
        .xref("x")
        .yref("y")
        .x(0)
        .y(0)
        .yanchor(Annotation.Yanchor.MIDDLE)
        .bordercolor("#c7c7c7")
        .font(Font.builder().family(Font.Family.SANS_SERIF).size(16).color("#ffffff").build())
        .text("max=5").align(Annotation.Align.CENTER).arrowhead(2).arrowsize(1).arrowwidth(2).ax(20).ay(-30)
        .font(Font.builder().color("rgba(0,0,0,0)").build())
        .showarrow(true).build();
    final String asJSON = annotation.asJSON();
    assertTrue(asJSON.contains("yanchor : \"center\""));
    assertTrue(asJSON.contains("text : \"max=5\""));
    assertTrue(asJSON.contains("xref : \"x\""));
    assertTrue(asJSON.contains("yref : \"y\""));
  }
}
