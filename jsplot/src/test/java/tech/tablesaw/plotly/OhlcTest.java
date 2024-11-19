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

public class OhlcTest {

    private static final String timeTitle = "time";
    private static final String openTitle = "open";
    private static final String closeTitle = "close";
    private static final String highTitle = "high";
    private static final String lowTitle = "low";
    private static final String graphTitle = "title";
    private static final List<Double> open = Arrays.asList(1d, 2d, 3d);
    private static final List<Double> close = Arrays.asList(1d, 2d, 3d);
    private static final List<Double> high = Arrays.asList(1d, 2d, 3d);
    private static final List<Double> low = Arrays.asList(1d, 2d, 3d);
    private static final DoubleColumn openColumn = DoubleColumn.create(openTitle, open);
    private static final DoubleColumn closeColumn = DoubleColumn.create(closeTitle, close);
    private static final DoubleColumn highColumn = DoubleColumn.create(highTitle, high);
    private static final DoubleColumn lowColumn = DoubleColumn.create(lowTitle, low);

    @Test
    void ohlcPlotDoesNotThrowIllegalArgumentExceptionUsingDateTime() {
        LocalDateTime now = LocalDateTime.now();
        List<LocalDateTime> time = Arrays.asList(now, now.plusSeconds(5), now.plusSeconds(10));
        DateTimeColumn dateTimeColumn = DateTimeColumn.create(timeTitle, time);
        Table priceTable = Table.create(dateTimeColumn, openColumn, closeColumn, highColumn, lowColumn);
        Figure figure = OHLCPlot.create(graphTitle, priceTable, timeTitle, openTitle, highTitle, lowTitle, closeTitle);

        assertNotNull(figure);
        assertDoesNotThrow(() -> IllegalArgumentException.class);
    }

    @Test
    void ohlcPlotDoesNotThrowIllegalArgumentExceptionUsingLocalDate() {
        LocalDate now = LocalDate.now();
        List<LocalDate> time = Arrays.asList(now, now.plusDays(5), now.plusDays(10));
        DateColumn dateColumn = DateColumn.create(timeTitle, time);
        Table priceTable = Table.create(dateColumn, openColumn, closeColumn, highColumn, lowColumn);
        Figure figure = OHLCPlot.create(graphTitle, priceTable, timeTitle, openTitle, highTitle, lowTitle, closeTitle);

        assertNotNull(figure);
        assertDoesNotThrow(() -> IllegalArgumentException.class);
    }

    @Test
    void ohlcPlotDoesNotThrowIllegalArgumentExceptionUsingInstant() {
        Instant now = Instant.now();
        List<Instant> time = Arrays.asList(now, now.plusSeconds(5), now.plusSeconds(10));
        InstantColumn instantColumn = InstantColumn.create(timeTitle, time);
        Table priceTable = Table.create(instantColumn, openColumn, closeColumn, highColumn, lowColumn);
        Figure figure = OHLCPlot.create(graphTitle, priceTable, timeTitle, openTitle, highTitle, lowTitle, closeTitle);

        assertNotNull(figure);
        assertDoesNotThrow(() -> IllegalArgumentException.class);
    }
}
