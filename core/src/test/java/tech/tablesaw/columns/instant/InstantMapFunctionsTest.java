/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tech.tablesaw.columns.instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;
import tech.tablesaw.api.InstantColumn;
import tech.tablesaw.api.LongColumn;

/** Tests for TemporalMapFunctions with Instants */
public class InstantMapFunctionsTest {

  private InstantColumn startCol = InstantColumn.create("start");
  private InstantColumn stopCol = InstantColumn.create("stop");
  private Instant start = Instant.now();

  @Test
  public void testDifferenceInMilliseconds() {
    startCol.append(start);
    stopCol = startCol.plusMillis(100_000L);
    LongColumn result = startCol.differenceInMilliseconds(stopCol);
    assertEquals(100_000L, result.getLong(0), "Wrong difference in millis");
  }

  @Test
  public void testDifferenceInSeconds() {
    startCol.append(start);
    stopCol = startCol.plusSeconds(100_000L);
    LongColumn result = startCol.differenceInSeconds(stopCol);
    assertEquals(100_000L, result.getLong(0), "Wrong difference in seconds");
  }

  @Test
  public void testDifferenceInMinutes() {
    startCol.append(start);
    stopCol = startCol.plusMinutes(100_000L);
    LongColumn result = startCol.differenceInMinutes(stopCol);
    assertEquals(100_000L, result.getLong(0), "Wrong difference in minutes");
  }

  @Test
  public void testDifferenceInHours() {
    startCol.append(start);
    stopCol = startCol.plusHours(100_000L);
    LongColumn result = startCol.differenceInHours(stopCol);
    assertEquals(100_000L, result.getLong(0), "Wrong difference in hours");
  }

  @Test
  public void testDifferenceInDays() {
    startCol.append(start);
    stopCol = startCol.plusDays(100_000L);
    LongColumn result = startCol.differenceInDays(stopCol);
    assertEquals(100_000L, result.getLong(0), "Wrong difference in days");
  }

@Test
  public void testDifferenceInYears() {
    startCol.append(start);
    stopCol = startCol.plusDays(2L * 365);
    LongColumn result = startCol.differenceInYears(stopCol);
    assertEquals(2L, result.getLong(0), "Wrong difference in years");
  }

/*
  @Test
  public void testHour() {
    startCol.append(LocalDateTime.of(1984, 12, 12, 7, 30));
    IntColumn hour = startCol.hour();
    assertEquals(7, hour.getInt(0), 0.0001);
  }

  @Test
  public void testYear() {
    startCol.append(LocalDateTime.of(1984, 12, 12, 7, 30));
    IntColumn year = startCol.year();
    assertEquals(1984, year.getInt(0), 0.0001);
  }

  @Test
  public void testDayOfYear() {
    startCol.append(LocalDateTime.of(1984, 1, 5, 7, 30));
    IntColumn dayOfYear = startCol.dayOfYear();
    assertEquals(5, dayOfYear.getInt(0), 0.0001);
  }

  @Test
  public void testDayOfMonth() {
    startCol.append(LocalDateTime.of(1984, 1, 22, 7, 30));
    IntColumn dayOfMonth = startCol.dayOfMonth();
    assertEquals(22, dayOfMonth.getInt(0), 0.0001);
  }

  @Test
  public void testMinute() {
    startCol.append(LocalDateTime.of(1984, 1, 22, 7, 30));
    IntColumn minute = startCol.minute();
    assertEquals(30, minute.getInt(0), 0.0001);
  }

  @Test
  public void testDayOfWeekValue() {
    startCol.append(LocalDateTime.of(2018, 4, 10, 7, 30));
    IntColumn dayOfWeekValue = startCol.dayOfWeekValue();
    assertEquals(2, dayOfWeekValue.getInt(0), 0.0001);
  }

  @Test
  public void testDayOfWeek() {
    startCol.append(LocalDateTime.of(2018, 4, 10, 7, 30));
    StringColumn dayOfWeek = startCol.dayOfWeek();
    assertEquals("TUESDAY", dayOfWeek.get(0));
  }

  @Test
  public void testHourMinute() {
    startCol.append(LocalDateTime.of(2018, 4, 10, 7, 30));
    StringColumn hourMinute = startCol.hourMinute();
    assertEquals("07:30", hourMinute.get(0));
  }

  @Test
  public void testYearMonth() {
    startCol.append(LocalDateTime.of(2018, 4, 10, 7, 30));
    StringColumn yearMonth = startCol.yearMonth();
    assertEquals("2018-04", yearMonth.get(0));
  }

  @Test
  public void testYearDay() {
    LocalDateTime dateTime = LocalDateTime.of(2018, 4, 10, 7, 30);
    startCol.append(dateTime);
    StringColumn yearDay = startCol.yearDay();
    assertEquals(
        "2018-" + Strings.padStart(String.valueOf(dateTime.getDayOfYear()), 3, '0'),
        yearDay.get(0));
  }

  @Test
  public void testYearWeek() {
    LocalDateTime dateTime = LocalDateTime.of(2018, 4, 10, 7, 30);
    startCol.append(dateTime);
    StringColumn yearWeek = startCol.yearWeek();
    TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
    assertEquals(
        "2018-" + Strings.padStart(String.valueOf(dateTime.get(woy)), 2, '0'), yearWeek.get(0));
  }

  @Test
  public void testYearQuarter() {
    LocalDateTime dateTime = LocalDateTime.of(2018, 4, 10, 7, 30);
    startCol.append(dateTime);
    StringColumn yearQuarter = startCol.yearQuarter();
    assertEquals("2018-2", yearQuarter.get(0));
  }

  @Test
  public void testMonth() {
    LocalDateTime dateTime = LocalDateTime.of(2018, 4, 10, 7, 30);
    startCol.append(dateTime);
    StringColumn month = startCol.month();
    assertEquals("APRIL", month.get(0));
  }

  @Test
  public void testMonthValue() {
    LocalDateTime dateTime = LocalDateTime.of(2018, 4, 10, 7, 30);
    startCol.append(dateTime);
    IntColumn month = startCol.monthValue();
    assertEquals(4, month.get(0), 0.0001);
  }

  @Test
  public void testMinuteOfDay() {
    LocalDateTime dateTime = LocalDateTime.of(2018, 4, 10, 7, 30);
    startCol.append(dateTime);
    IntColumn minuteOfDay = startCol.minuteOfDay();
    assertEquals((7 * 60) + 30, minuteOfDay.get(0), 0.0001);
  }

  @Test
  public void testSecondOfDay() {
    LocalDateTime dateTime = LocalDateTime.of(2018, 4, 10, 7, 30);
    startCol.append(dateTime);
    IntColumn secondOfDay = startCol.secondOfDay();
    assertEquals(dateTime.get(ChronoField.SECOND_OF_DAY), secondOfDay.get(0), 0.0001);
  }

  @Test
  public void testDate() {
    LocalDateTime dateTime = LocalDateTime.of(2018, 4, 10, 7, 30);
    startCol.append(dateTime);
    DateColumn date = startCol.date();
    assertEquals(dateTime.toLocalDate(), date.get(0));
  }

  @Test
  public void testTime() {
    LocalDateTime dateTime = LocalDateTime.of(2018, 4, 10, 7, 30);
    startCol.append(dateTime);
    TimeColumn time = startCol.time();
    assertEquals(dateTime.toLocalTime(), time.get(0));
  }

  @Test
  public void testTimeWindow() {
    LocalDateTime dateTime = LocalDateTime.of(2018, 4, 10, 7, 30);
    startCol.append(dateTime);
    for (int i = 0; i < 49; i++) {
      dateTime = dateTime.plusDays(1);
      startCol.append(dateTime);
    }
    LongColumn timeWindows = startCol.timeWindow(ChronoUnit.DAYS, 5);
    assertEquals(0, timeWindows.get(0), 0.0001);
    assertEquals(9, timeWindows.max(), 0.0001);

    timeWindows = startCol.timeWindow(ChronoUnit.WEEKS, 1);
    assertEquals(0, timeWindows.get(0), 0.0001);
    assertEquals(7, timeWindows.max(), 0.0001);

    timeWindows = startCol.timeWindow(ChronoUnit.MONTHS, 1);
    assertEquals(0, timeWindows.get(0), 0.0001);
    assertEquals(1, timeWindows.max(), 0.0001);

    timeWindows = startCol.timeWindow(ChronoUnit.YEARS, 1);
    assertEquals(0, timeWindows.get(0), 0.0001);
    assertEquals(0, timeWindows.max(), 0.0001);
  }
*/
  
  @Test
  public void testLeadAndLag() {
    Instant instant1 = LocalDateTime.of(2018, 4, 10, 7, 30).toInstant(ZoneOffset.UTC);
    Instant instant2 = LocalDateTime.of(2018, 5, 10, 7, 30).toInstant(ZoneOffset.UTC);
    Instant instant3 = LocalDateTime.of(2018, 5, 10, 7, 30).toInstant(ZoneOffset.UTC);
    startCol.append(instant1);
    startCol.append(instant2);
    startCol.append(instant3);
    InstantColumn lead = startCol.lead(1);
    InstantColumn lag = startCol.lag(1);
    assertEquals(startCol.get(0), lag.get(1));
    assertEquals(InstantColumnType.missingValueIndicator(), lag.getLongInternal(0));
    assertEquals(startCol.get(1), lead.get(0));
    assertEquals(InstantColumnType.missingValueIndicator(), lead.getLongInternal(2));
  }
}
