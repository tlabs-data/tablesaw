package tech.tablesaw.components;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import tech.tablesaw.plotly.components.Grid;

class GridTest {

  @Test
  void testAsJSON() {
    Grid x =
        Grid.builder()
            .rows(10)
            .columns(5)
            .rowOrder(Grid.RowOrder.BOTTOM_TO_TOP)
            .pattern(Grid.Pattern.INDEPENDENT)
            .build();

    String asJSON = x.asJSON();
    assertTrue(asJSON.contains("rows"));
    assertTrue(asJSON.contains("columns"));
    assertTrue(asJSON.contains("roworder"));
    assertTrue(asJSON.contains("pattern"));
  }
}
