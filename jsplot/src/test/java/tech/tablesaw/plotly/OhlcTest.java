package tech.tablesaw.plotly;

import org.junit.jupiter.api.Test;

import tech.tablesaw.api.DateColumn;
import tech.tablesaw.api.DateTimeColumn;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.InstantColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.plotly.api.OHLCPlot;
import tech.tablesaw.plotly.components.Figure;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OhlcTest {

    private static final String TIME_TITLE = "time";
    private static final String OPEN_TITLE = "open";
    private static final String CLOSE_TITLE = "close";
    private static final String HIGH_TITLE = "high";
    private static final String LOW_TITLE = "low";
    private static final String GRAPH_TITLE = "title";
    private static final DoubleColumn OPEN_COLUMN = DoubleColumn.create(OPEN_TITLE, Arrays.asList(1d, 2d, 3d));
    private static final DoubleColumn CLOSE_COLUMN = DoubleColumn.create(CLOSE_TITLE, Arrays.asList(1d, 2d, 3d));
    private static final DoubleColumn HIGH_COLUMN = DoubleColumn.create(HIGH_TITLE, Arrays.asList(1d, 2d, 3d));
    private static final DoubleColumn LOW_COLUMN = DoubleColumn.create(LOW_TITLE, Arrays.asList(1d, 2d, 3d));

    @Test
    void ohlcPlotDoesNotThrowIllegalArgumentExceptionUsingDateTime() {
        LocalDateTime now = LocalDateTime.now();
        List<LocalDateTime> time = Arrays.asList(now, now.plusSeconds(5), now.plusSeconds(10));
        DateTimeColumn dateTimeColumn = DateTimeColumn.create(TIME_TITLE, time);
        Table priceTable = Table.create(dateTimeColumn, OPEN_COLUMN, CLOSE_COLUMN, HIGH_COLUMN, LOW_COLUMN);
        Figure figure = OHLCPlot.create(GRAPH_TITLE, priceTable, TIME_TITLE, OPEN_TITLE, HIGH_TITLE, LOW_TITLE, CLOSE_TITLE);

        assertNotNull(figure);
        assertDoesNotThrow(() -> IllegalArgumentException.class);
    }

    @Test
    void ohlcPlotDoesNotThrowIllegalArgumentExceptionUsingLocalDate() {
        LocalDate now = LocalDate.now();
        List<LocalDate> time = Arrays.asList(now, now.plusDays(5), now.plusDays(10));
        DateColumn dateColumn = DateColumn.create(TIME_TITLE, time);
        Table priceTable = Table.create(dateColumn, OPEN_COLUMN, CLOSE_COLUMN, HIGH_COLUMN, LOW_COLUMN);
        Figure figure = OHLCPlot.create(GRAPH_TITLE, priceTable, TIME_TITLE, OPEN_TITLE, HIGH_TITLE, LOW_TITLE, CLOSE_TITLE);

        assertNotNull(figure);
        assertDoesNotThrow(() -> IllegalArgumentException.class);
    }

    @Test
    void ohlcPlotDoesNotThrowIllegalArgumentExceptionUsingInstant() {
        Instant now = Instant.now();
        List<Instant> time = Arrays.asList(now, now.plusSeconds(5), now.plusSeconds(10));
        InstantColumn instantColumn = InstantColumn.create(TIME_TITLE, time);
        Table priceTable = Table.create(instantColumn, OPEN_COLUMN, CLOSE_COLUMN, HIGH_COLUMN, LOW_COLUMN);
        Figure figure = OHLCPlot.create(GRAPH_TITLE, priceTable, TIME_TITLE, OPEN_TITLE, HIGH_TITLE, LOW_TITLE, CLOSE_TITLE);

        assertNotNull(figure);
        assertDoesNotThrow(() -> IllegalArgumentException.class);
    }
}
