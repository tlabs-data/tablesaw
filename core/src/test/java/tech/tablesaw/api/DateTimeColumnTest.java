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

package tech.tablesaw.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.tablesaw.columns.datetimes.DateTimeParser;
import tech.tablesaw.columns.strings.StringColumnType;

class DateTimeColumnTest {

  private DateTimeColumn column1;

  @BeforeEach
  void setUp() {
    Table table = Table.create("Test");
    column1 = DateTimeColumn.create("Game date");
    table.addColumns(column1);
  }

  @Test
  void testAppendCell() {
    column1.appendCell("1923-10-20T10:15:30");
    column1.appendCell("1924-12-10T10:15:30");
    column1.appendCell("2015-12-05T10:15:30");
    column1.appendCell("2015-12-20T10:15:30");
    assertEquals(4, column1.size());
    LocalDateTime date = LocalDateTime.now();
    column1.append(date);
    assertEquals(5, column1.size());
  }

  @Test
  void testAppendCell2() {
    column1.appendCell("10/12/2016 12:18:03 AM");
    column1.appendCell("10/2/2016 8:18:03 AM");
    column1.appendCell("10/12/2016 12:18:03 AM");
    assertEquals(3, column1.size());
  }

  @Test
  void testCustomParser() {
    // Just do enough to ensure the parser is wired up correctly
    DateTimeParser customParser = new DateTimeParser(ColumnType.LOCAL_DATE_TIME);
    customParser.setMissingValueStrings(Arrays.asList("not here"));
    column1.setParser(customParser);

    column1.appendCell("not here");
    assertTrue(column1.isMissing(column1.size() - 1));
    column1.appendCell("1923-10-20T10:15:30");
    assertFalse(column1.isMissing(column1.size() - 1));
  }

  @Test
  void testConvertMillisSinceEpoch() {
    long millis = 1503952123189L;
    LongColumn dc = LongColumn.create("test");
    dc.append(millis);
    DateTimeColumn column2 = dc.asDateTimes(ZoneOffset.UTC);

    assertEquals(1, column2.size());
    assertEquals(2017, column2.get(0).getYear());
    assertEquals(8, column2.get(0).getMonthValue());
    assertEquals(28, column2.get(0).getDayOfMonth());
    assertEquals(20, column2.get(0).getHour());

    long[] millisArr = column2.asEpochMillisArray();
    assertEquals(1, millisArr.length);
    assertEquals(millis, millisArr[0]);
  }

  @Test
  void testAfter() {
    Table t = Table.create("test");
    t.addColumns(column1);
    column1.appendCell("2015-12-03T10:15:30");
    column1.appendCell("2015-01-03T10:15:30");
    Table result =
        t.where(t.dateTimeColumn("Game date").isAfter(LocalDateTime.of(2015, 2, 2, 0, 0)));
    assertEquals(1, result.rowCount());
  }

  @Test
  void testNull() {
    DateTimeColumn col = DateTimeColumn.create("Game date");
    col.appendCell(null);
    assertNull(col.get(0));
  }

  @Test
  void testCountUnique() {
    column1.append(LocalDateTime.of(2000, 1, 1, 0, 0));
    column1.append(LocalDateTime.of(2000, 1, 1, 0, 0));
    column1.append(LocalDateTime.of(2000, 2, 1, 0, 0));
    column1.appendMissing();

    assertEquals(3, column1.countUnique());
  }

  @Test
  void testFormatter() {
    column1.setPrintFormatter(DateTimeFormatter.ISO_LOCAL_DATE_TIME, "NaT");
    column1.append(LocalDateTime.of(2000, 1, 1, 0, 0));
    column1.appendMissing();
    assertEquals("2000-01-01T00:00:00", column1.getString(0));
    assertEquals("NaT", column1.getString(1));
  }

  @Test
  public void testAsStringColumn() {
    column1.appendCell("1923-10-20T10:15:30");
    column1.appendMissing();
    StringColumn sc = column1.asStringColumn();
    assertEquals("Game date strings", sc.name());
    assertEquals(2, sc.size());
    assertEquals("1923-10-20T10:15:30.000", sc.get(0));
    assertEquals(StringColumnType.missingValueIndicator(), sc.get(1));
  }
  
  @Test
  void removeMissing() {
    column1.append(LocalDateTime.of(2000, 2, 1, 0, 0));
    column1.appendMissing();
    assertEquals(1, column1.removeMissing().size());
    assertNotNull(column1.removeMissing().get(0));
  }
  
  @Test
  void testContains() {
    column1.append(LocalDateTime.of(2000, 1, 1, 0, 0));
    column1.append(LocalDateTime.of(2000, 2, 1, 0, 0));
    column1.append(LocalDateTime.of(2000, 3, 1, 0, 0));
    assertTrue(column1.contains(LocalDateTime.of(2000, 1, 1, 0, 0)));
    assertFalse(column1.contains(LocalDateTime.of(2000, 5, 1, 0, 0)));
  }

  @Test
  void setMissing() {
    column1.append(LocalDateTime.of(2000, 2, 1, 0, 0));
    column1.appendMissing();
    column1.setMissing(0);
    assertEquals(2, column1.countMissing());
  }
  
  @Test
  void testWhere() {
    column1.append(LocalDateTime.of(2000, 2, 1, 0, 0));
    column1.appendMissing();
    assertNull(column1.where(column1.isMissing()).get(0));
  }

  @Test
  void testAsInstantColumn() {
    LocalDateTime nowLdt = LocalDateTime.now().withNano(0);
    DateTimeColumn dtColumn = DateTimeColumn.create("datetime", nowLdt);
    InstantColumn instColumn = dtColumn.asInstantColumn();
    Instant nowInst = nowLdt.toInstant(ZoneOffset.UTC);
    assertTrue(instColumn.contains(nowInst));
  }

  @Test
  void testTopN() {
    column1.append(LocalDateTime.of(2000, 1, 1, 0, 0));
    column1.append(LocalDateTime.of(2000, 2, 1, 0, 0));
    column1.append(LocalDateTime.of(2000, 3, 1, 0, 0));
    column1.appendMissing();
    final List<LocalDateTime> top = column1.top(2);
    assertEquals(2, top.size());
    assertEquals(LocalDateTime.of(2000, 3, 1, 0, 0), top.get(0));
    assertEquals(LocalDateTime.of(2000, 2, 1, 0, 0), top.get(1));
  }

  @Test
  void testTopNAboveSize() {
    column1.append(LocalDateTime.of(2000, 1, 1, 0, 0));
    column1.append(LocalDateTime.of(2000, 2, 1, 0, 0));
    column1.append(LocalDateTime.of(2000, 3, 1, 0, 0));
    column1.appendMissing();
    final List<LocalDateTime> top = column1.top(5);
    assertEquals(column1.size(), top.size());
  }

  @Test
  void testBottomN() {
    column1.append(LocalDateTime.of(2000, 1, 1, 0, 0));
    column1.append(LocalDateTime.of(2000, 2, 1, 0, 0));
    column1.append(LocalDateTime.of(2000, 3, 1, 0, 0));
    column1.appendMissing();
    final List<LocalDateTime> bottom = column1.bottom(2);
    assertEquals(2, bottom.size());
    assertNull(bottom.get(0));
    assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), bottom.get(1));
  }

  @Test
  void testBottomNAboveSize() {
    column1.append(LocalDateTime.of(2000, 1, 1, 0, 0));
    column1.append(LocalDateTime.of(2000, 2, 1, 0, 0));
    column1.append(LocalDateTime.of(2000, 3, 1, 0, 0));
    column1.appendMissing();
    final List<LocalDateTime> bottom = column1.bottom(column1.size() + 2);
    assertEquals(column1.size(), bottom.size());
  }

}
