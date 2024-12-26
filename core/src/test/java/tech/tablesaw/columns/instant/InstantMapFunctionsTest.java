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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;

import tech.tablesaw.api.BooleanColumn;
import tech.tablesaw.api.InstantColumn;
import tech.tablesaw.api.LongColumn;

/** Tests for TemporalMapFunctions with Instants */
class InstantMapFunctionsTest {

  private InstantColumn startCol = InstantColumn.create("start");
  private InstantColumn stopCol = InstantColumn.create("stop");
  private Instant start = Instant.now();

  @Test
  void testDifferenceInMilliseconds() {
    startCol.append(start);
    stopCol = startCol.plusMillis(100_000L);
    LongColumn result = startCol.differenceInMilliseconds(stopCol);
    assertEquals(100_000L, result.getLong(0), "Wrong difference in millis");
  }

  @Test
  void testDifferenceInSeconds() {
    startCol.append(start);
    stopCol = startCol.plusSeconds(100_000L);
    LongColumn result = startCol.differenceInSeconds(stopCol);
    assertEquals(100_000L, result.getLong(0), "Wrong difference in seconds");
  }

  @Test
  void testDifferenceInMinutes() {
    startCol.append(start);
    stopCol = startCol.plusMinutes(100_000L);
    LongColumn result = startCol.differenceInMinutes(stopCol);
    assertEquals(100_000L, result.getLong(0), "Wrong difference in minutes");
  }

  @Test
  void testDifferenceInHours() {
    startCol.append(start);
    stopCol = startCol.plusHours(100_000L);
    LongColumn result = startCol.differenceInHours(stopCol);
    assertEquals(100_000L, result.getLong(0), "Wrong difference in hours");
  }

  @Test
  void testDifferenceInDays() {
    startCol.append(start);
    stopCol = startCol.plusDays(100_000L);
    LongColumn result = startCol.differenceInDays(stopCol);
    assertEquals(100_000L, result.getLong(0), "Wrong difference in days");
  }

  @Test
  void testDifferenceInYears() {
    startCol.append(start);
    stopCol = startCol.plusDays(2L * 365);
    LongColumn result = startCol.differenceInYears(stopCol);
    assertEquals(2L, result.getLong(0), "Wrong difference in years");
  }
  
  @Test
  void testLeadAndLag() {
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
  
  @Test
  void testMissingValues() {
    startCol.append(start);
    startCol.appendMissing();
    BooleanColumn missing = startCol.missingValues();
    assertFalse(missing.get(0));
    assertTrue(missing.get(1));
  }

} 
