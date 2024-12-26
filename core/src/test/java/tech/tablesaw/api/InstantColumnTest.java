package tech.tablesaw.api;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.tablesaw.columns.AbstractColumnParser;

class InstantColumnTest {

  private final InstantColumn instanceColumn = InstantColumn.create("Test");

  private Instant now = Instant.now();

  private long baseline = now.getEpochSecond();
  private long before = baseline - 100L;
  private long after = baseline + 100L;

  private Instant baselineInst = Instant.ofEpochSecond(baseline);
  private Instant beforeInst = Instant.ofEpochSecond(before);
  private Instant afterInst = Instant.ofEpochSecond(after);

  @BeforeEach
  void setUp() {

    instanceColumn.append(beforeInst);
    instanceColumn.append(baselineInst);
    instanceColumn.append(afterInst);
    instanceColumn.appendMissing();
  }

  @Test
  void isAfter() {
    assertEquals(2, instanceColumn.isAfter(baselineInst).get(0));
  }

  @Test
  void isBefore() {
    assertEquals(0, instanceColumn.isBefore(baselineInst).get(0));
    assertEquals(1, instanceColumn.isBefore(afterInst).get(1));
  }

  @Test
  void isEqualTo() {
    assertEquals(2, instanceColumn.isEqualTo(afterInst).get(0));
  }

  @Test
  void isMissing() {
    assertEquals(3, instanceColumn.isMissing().get(0));
  }

  @Test
  void isNotMissing() {
    assertEquals(0, instanceColumn.isNotMissing().get(0));
    assertEquals(1, instanceColumn.isNotMissing().get(1));
    assertEquals(2, instanceColumn.isNotMissing().get(2));
  }

  @Test
  void testCountUnique() {
    InstantColumn column1 = InstantColumn.create("instants");
    column1.append(baselineInst);
    column1.append(baselineInst);
    column1.append(afterInst);
    column1.appendMissing();

    assertEquals(3, column1.countUnique());
  }

  @Test
  void testCustomParser() {
    class CustomParser extends AbstractColumnParser<Instant> {

      private final List<String> VALID_VALUES = Arrays.asList("now");

      public CustomParser() {
        super(ColumnType.INSTANT);
      }

      @Override
      public boolean canParse(String s) {
        return true;
      }

      @Override
      public Instant parse(String s) {
        return VALID_VALUES.contains(s) ? Instant.now() : null;
      }
    }

    InstantColumn column1 = InstantColumn.create("instants");
    column1.setParser(new CustomParser());

    // Just do enough to ensure the parser is wired up correctly
    column1.appendCell("not now");
    assertTrue(column1.isMissing(0));
    column1.appendCell("now");
    assertFalse(column1.isMissing(1));
  }

  @Test
  void testSetMissing() {
    BooleanColumn missingBefore = instanceColumn.missingValues();
    assertFalse(missingBefore.get(0));
    assertFalse(missingBefore.get(1));
    assertTrue(missingBefore.get(3));
    instanceColumn.setMissing(0);
    BooleanColumn missingAfter = instanceColumn.missingValues();
    assertTrue(missingAfter.get(0));
    assertFalse(missingAfter.get(1));
    assertTrue(missingBefore.get(3));
  }
  
  @Test
  void removeMissing() {
    assertEquals(3, instanceColumn.removeMissing().size());
  }
  
  @Test
  void contains() {
    assertTrue(instanceColumn.contains(baselineInst));
    assertFalse(instanceColumn.contains(baselineInst.plusSeconds(1_000)));
  }

  @Test
  void testSubset() {
    final InstantColumn subset = instanceColumn.subset(new int[] {0, 1});
    assertEquals(2, subset.size());
    assertEquals(beforeInst, subset.get(0));
    assertEquals(baselineInst, subset.get(1));
  }
  
  @Test
  void testWhere() {
    final InstantColumn subset = instanceColumn.where(instanceColumn.isNotMissing().and(instanceColumn.isBefore(afterInst)));
    assertEquals(2, subset.size());
    assertEquals(beforeInst, subset.get(0));
    assertEquals(baselineInst, subset.get(1));
  }

  @Test
  void testSetSelection() {
    instanceColumn.set(instanceColumn.isMissing(), afterInst);
    final InstantColumn subset = instanceColumn.where(instanceColumn.isBefore(afterInst));
    assertEquals(2, subset.size());
    assertEquals(beforeInst, subset.get(0));
    assertEquals(baselineInst, subset.get(1));
  }
  
  @Test
  void testCountMissing() {
    assertEquals(1, instanceColumn.countMissing());
  }
  
  @Test
  void testAppendColumn() {
    assertEquals(8, instanceColumn.append(instanceColumn.copy()).size());
  }

  @Test
  void testAppendRow() {
    final InstantColumn appended = instanceColumn.append(instanceColumn.copy(), 3);
    assertEquals(5, appended.size());
    assertEquals(2, appended.countMissing());
  }
  
  @Test
  void testSetRow() {
    final InstantColumn appended = instanceColumn.set(0, instanceColumn.copy(), 3);
    assertEquals(4, appended.size());
    assertEquals(2, appended.countMissing());
  }
  
  @Test
  void testTopN() {
    final List<Instant> top = instanceColumn.top(2);
    assertEquals(2, top.size());
    assertEquals(afterInst, top.get(0));
    assertEquals(baselineInst, top.get(1));
  }

  @Test
  void testTopNAboveSize() {
    final List<Instant> top = instanceColumn.top(10);
    assertEquals(instanceColumn.size(), top.size());
  }

  @Test
  void testBottomN() {
    final List<Instant> bottom = instanceColumn.bottom(2);
    assertEquals(2, bottom.size());
    assertNull(bottom.get(0));
    assertEquals(beforeInst, bottom.get(1));
  }

  @Test
  void testBottomNAboveSize() {
    final List<Instant> bottom = instanceColumn.bottom(instanceColumn.size() + 2);
    assertEquals(instanceColumn.size(), bottom.size());
  }

}
