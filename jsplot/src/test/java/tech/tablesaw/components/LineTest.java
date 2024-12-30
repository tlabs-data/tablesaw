package tech.tablesaw.components;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import tech.tablesaw.plotly.components.Line;
import tech.tablesaw.plotly.components.Marker;

class LineTest {

    @Test
    void testLineAndMarker() {
        Line line = Line.builder().build();
        final String lineAsJSON = line.asJSON();
        assertTrue(lineAsJSON.contains("dash : \"solid\","));
        assertTrue(lineAsJSON.contains("shape : \"linear\","));

        Marker marker = Marker.builder().line(Line.builder().build()).build();
        final String markerAsJSON = marker.asJSON();
        assertTrue(markerAsJSON.contains("  line : {\n"
          + "    dash : \"solid\","));
        assertTrue(markerAsJSON.contains(" size : 6.0"));
    }
}
