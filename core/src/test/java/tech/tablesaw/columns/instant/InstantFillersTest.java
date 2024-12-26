package tech.tablesaw.columns.instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tech.tablesaw.columns.temporal.fillers.TemporalRangeIterable.range;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

import tech.tablesaw.api.InstantColumn;

class InstantFillersTest {

  private void assertContentEquals(Iterable<Instant> times, Instant... expected) {
    int num = 0;
    for (Instant value : times) {
      assertEquals(expected[num], value);
      num++;
    }
    assertEquals(expected.length, num);
  }

  @Test
  void testFromToByDays() {
    assertContentEquals(
      InstantColumn.create("instant", new Instant[5])
            .fillWith(
                range(
                  // year, month, day, hour, minute
                  LocalDateTime.of(2018, 3, 1, 12, 30).toInstant(ZoneOffset.UTC), 
                  LocalDateTime.of(2019, 3, 1, 12, 30).toInstant(ZoneOffset.UTC),
                  1, ChronoUnit.DAYS)),
        // year, month, day, hour, minute
        LocalDateTime.of(2018, 3, 1, 12, 30).toInstant(ZoneOffset.UTC),
        LocalDateTime.of(2018, 3, 2, 12, 30).toInstant(ZoneOffset.UTC),
        LocalDateTime.of(2018, 3, 3, 12, 30).toInstant(ZoneOffset.UTC),
        LocalDateTime.of(2018, 3, 4, 12, 30).toInstant(ZoneOffset.UTC),
        LocalDateTime.of(2018, 3, 5, 12, 30).toInstant(ZoneOffset.UTC));
  }

  @Test
  void testFromNbValuesByDays() {
    assertContentEquals(
      InstantColumn.create("instant", new Instant[5])
            .fillWith(
                range(
                  // year, month, day, hour, minute
                  LocalDateTime.of(2018, 3, 1, 12, 30).toInstant(ZoneOffset.UTC),
                  1, ChronoUnit.DAYS, 5)),
        // year, month, day, hour, minute
        LocalDateTime.of(2018, 3, 1, 12, 30).toInstant(ZoneOffset.UTC),
        LocalDateTime.of(2018, 3, 2, 12, 30).toInstant(ZoneOffset.UTC),
        LocalDateTime.of(2018, 3, 3, 12, 30).toInstant(ZoneOffset.UTC),
        LocalDateTime.of(2018, 3, 4, 12, 30).toInstant(ZoneOffset.UTC),
        LocalDateTime.of(2018, 3, 5, 12, 30).toInstant(ZoneOffset.UTC));
  }

  @Test
  void testFromToByHours() {
    assertContentEquals(
      InstantColumn.create("instant", new Instant[5])
            .fillWith(
                range(
                  // year, month, day, hour, minute
                  LocalDateTime.of(2018, 3, 1, 12, 30).toInstant(ZoneOffset.UTC),
                  LocalDateTime.of(2019, 3, 1, 12, 30).toInstant(ZoneOffset.UTC),
                  2, ChronoUnit.HOURS).iterator()),
        // year, month, day, hour, minute
        LocalDateTime.of(2018, 3, 1, 12, 30).toInstant(ZoneOffset.UTC),
        LocalDateTime.of(2018, 3, 1, 14, 30).toInstant(ZoneOffset.UTC),
        LocalDateTime.of(2018, 3, 1, 16, 30).toInstant(ZoneOffset.UTC),
        LocalDateTime.of(2018, 3, 1, 18, 30).toInstant(ZoneOffset.UTC),
        LocalDateTime.of(2018, 3, 1, 20, 30).toInstant(ZoneOffset.UTC));
  }
  
  @Test
  void testRepeatFromToByMinutes() {
    assertContentEquals(
      InstantColumn.create("instant", new Instant[5])
            .fillWith(
                range(
                  // year, month, day, hour, minute
                  LocalDateTime.of(2018, 3, 1, 12, 30).toInstant(ZoneOffset.UTC),
                  LocalDateTime.of(2018, 3, 1, 12, 32).toInstant(ZoneOffset.UTC),
                  1, ChronoUnit.MINUTES)),
        // year, month, day, hour, minute
        LocalDateTime.of(2018, 3, 1, 12, 30).toInstant(ZoneOffset.UTC),
        LocalDateTime.of(2018, 3, 1, 12, 31).toInstant(ZoneOffset.UTC),
        LocalDateTime.of(2018, 3, 1, 12, 30).toInstant(ZoneOffset.UTC),
        LocalDateTime.of(2018, 3, 1, 12, 31).toInstant(ZoneOffset.UTC),
        LocalDateTime.of(2018, 3, 1, 12, 30).toInstant(ZoneOffset.UTC));
  }

  @Test
  void testSupplier() {
    final Instant now = Instant.now().with(ChronoField.NANO_OF_SECOND, 0);
    assertContentEquals(
      InstantColumn.create("instant", new Instant[5]).fillWith(() -> now),
        new Instant[] {now, now, now, now, now});
    
  }
}
