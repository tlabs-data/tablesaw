package tech.tablesaw.table;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tech.tablesaw.aggregate.AggregateFunctions.sum;

import com.google.common.collect.Streams;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.tablesaw.api.IntColumn;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;
import tech.tablesaw.selection.Selection;
import tech.tablesaw.sorting.Sort;
import tech.tablesaw.sorting.Sort.Order;

class TableSliceTest {

  private Table source;

  @BeforeEach
  void setUp() throws Exception {
    source = Table.read().csv("../data/bush.csv");
  }

  @Test
  void column() {
    TableSlice slice = new TableSlice(source, Selection.withRange(0, 4));
    assertEquals(source.column(1).name(), slice.column(1).name());
    assertTrue(source.rowCount() > slice.column(1).size());
    assertEquals(source.column("date").name(), slice.column("date").name());
    assertTrue(source.rowCount() > slice.column("date").size());
    assertEquals(slice.column(1).size(), slice.column("date").size());
    assertEquals(4, slice.column("date").size());
  }

  @Test
  void columnCount() {
    TableSlice slice = new TableSlice(source, Selection.withRange(0, source.rowCount()));
    assertEquals(source.columnCount(), slice.columnCount());
  }

  @Test
  void rowCount() {
    TableSlice slice = new TableSlice(source, Selection.withRange(0, source.rowCount()));
    assertEquals(source.rowCount(), slice.rowCount());

    TableSlice slice1 = new TableSlice(source, Selection.withRange(0, 100));
    assertEquals(100, slice1.rowCount());
  }

  @Test
  void columns() {
    TableSlice slice = new TableSlice(source, Selection.withRange(0, source.rowCount()));
    assertEquals(source.columns().get(0).size(), slice.columns().get(0).size());
  }

  @Test
  void columnIndex() {
    TableSlice slice = new TableSlice(source, Selection.withRange(0, source.rowCount()));
    assertEquals(source.columnIndex("who"), slice.columnIndex("who"));

    Column<?> who = source.column("who");
    assertEquals(source.columnIndex(who), slice.columnIndex(who));
  }

  @Test
  void get() {
    TableSlice slice = new TableSlice(source, Selection.withRange(10, source.rowCount()));
    assertNotNull(slice.get(0, 1));
    assertEquals(source.get(10, 1), slice.get(0, 1));
  }

  @Test
  void name() {
    TableSlice slice = new TableSlice(source, Selection.withRange(0, source.rowCount()));
    assertEquals(source.name(), slice.name());
  }

  @Test
  void clear() {
    TableSlice slice = new TableSlice(source, Selection.withRange(0, source.rowCount()));
    slice.clear();
    assertTrue(slice.isEmpty());
    assertFalse(source.isEmpty());
  }

  @Test
  void columnNames() {
    TableSlice slice = new TableSlice(source, Selection.withRange(0, source.rowCount()));
    assertEquals(source.columnNames(), slice.columnNames());
  }

  @Test
  void addColumn() {
    UnsupportedOperationException thrown =
        assertThrows(
            UnsupportedOperationException.class,
            () -> {
              TableSlice slice = new TableSlice(source, Selection.withRange(0, source.rowCount()));
              slice.addColumns(StringColumn.create("test"));
            });
    assertTrue(
        thrown.getMessage().contains("Class TableSlice does not support the addColumns operation"));
  }

  @Test
  void removeColumns() {
    UnsupportedOperationException thrown =
        assertThrows(
            UnsupportedOperationException.class,
            () -> {
              TableSlice slice = new TableSlice(source, Selection.withRange(0, source.rowCount()));
              slice.removeColumns("who");
            });
    assertTrue(
        thrown
            .getMessage()
            .contains("Class TableSlice does not support the removeColumns operation"));
  }

  @Test
  void first() {
    TableSlice slice = new TableSlice(source, Selection.withRange(2, 12));
    Table first = slice.first(5);
    assertEquals(first.get(0, 1), slice.get(0, 1));
    assertEquals(first.get(0, 1), source.get(2, 1));
  }

  @Test
  void setName() {
    TableSlice slice = new TableSlice(source, Selection.withRange(0, source.rowCount()));
    slice.setName("foo");
    assertEquals("foo", slice.name());
    assertNotEquals("foo", source.name());
  }

  @Test
  void print() {
    TableSlice slice = new TableSlice(source, Selection.withRange(0, source.rowCount()));
    assertEquals(source.print(), slice.print());
  }

  @Test
  void asTable() {
    TableSlice slice = new TableSlice(source, Selection.withRange(1, 11));
    Table t = slice.asTable();
    assertEquals(10, t.rowCount());
    assertEquals(source.get(1, 1), t.get(0, 1));
  }

  @Test
  void reduce() throws Exception {
    source = Table.read().csv("../data/bush.csv");
    TableSlice slice = new TableSlice(source, Selection.with(2));
    assertEquals(58.0, slice.reduce("approval", sum), 0.0001);
  }

  @Test
  void reduceNoSelection() throws Exception {
    source = Table.read().csv("../data/bush.csv");
    TableSlice slice = new TableSlice(source);
    assertEquals(20957.0, slice.reduce("approval", sum), 0.0001);
  }

  @Test
  void iterateOverRowsWithSelection() {
    IntColumn rowNumbers =
        IntColumn.create("originalRowNumber", IntStream.range(0, source.rowCount()).toArray());
    source.addColumns(rowNumbers);

    TableSlice tableSlice = new TableSlice(source, Selection.with(3, 4));

    int count = 0;
    for (Row row : tableSlice) {
      assertEquals(count + 3, row.getInt(3));
      count++;
    }
    assertEquals(2, count);
  }

  @Test
  void iterateOverRowsWithSort() {
    Selection selection = Selection.withRange(0, 5);
    TableSlice tableSlice = new TableSlice(source, selection);
    tableSlice.sortOn(Sort.on("approval", Order.ASCEND));

    Integer[] expected = new Integer[] {52, 52, 53, 53, 58};
    Integer[] actual =
        Streams.stream(tableSlice).map(r -> r.getInt("approval")).toArray(Integer[]::new);

    assertArrayEquals(expected, actual);
  }

  @Test
  void firstWithSort() {
    Selection selection = Selection.withRange(0, 5);
    TableSlice tableSlice = new TableSlice(source, selection);
    tableSlice.sortOn(Sort.on("approval", Order.ASCEND));

    double[] expected = new double[] {52.0, 52.0, 53.0, 53.0, 58.0};
    double[] actual = tableSlice.first(5).intColumn("approval").asDoubleArray();

    assertArrayEquals(expected, actual);
  }

  @Test
  void firstWithMultipleSortCriteria() {
    TableSlice tableSlice = new TableSlice(source);
    tableSlice.sortOn(Sort.on("who", Order.DESCEND).next("approval", Order.DESCEND));

    double[] expected = new double[] {82.0, 82.0, 81.0};
    double[] actual = tableSlice.first(3).intColumn("approval").asDoubleArray();

    assertArrayEquals(expected, actual);
  }

  @Test
  void columnWithSort() {
    Selection selection = Selection.withRange(0, 5);
    TableSlice tableSlice = new TableSlice(source, selection);
    tableSlice.sortOn(Sort.on("approval", Order.ASCEND));

    double[] expected = new double[] {52.0, 52.0, 53.0, 53.0, 58.0};
    double[] actual = ((IntColumn) tableSlice.column("approval")).asDoubleArray();

    assertArrayEquals(expected, actual);
  }

  @Test
  void columnNoSortNoSelection() {
    TableSlice tableSlice = new TableSlice(source);
    assertEquals(tableSlice.column("approval").asList(), source.column("approval").asList());
  }

  @Test
  void rowCountWithSort() {
    Selection selection = Selection.with(0, 1);
    TableSlice tableSlice = new TableSlice(source, selection);
    assertEquals(2, tableSlice.rowCount());
    tableSlice.removeSelection();
    assertEquals(source.rowCount(), tableSlice.rowCount());
  }

  @Test
  void removeSort() {
    Selection selection = Selection.withRange(0, 5);
    TableSlice tableSlice = new TableSlice(source, selection);
    tableSlice.sortOn(Sort.on("approval", Order.ASCEND));
    tableSlice.removeSort();

    double[] expected = new double[] {53.0, 53.0, 58.0, 52.0, 52.0};
    double[] actual = ((IntColumn) tableSlice.column("approval")).asDoubleArray();

    assertArrayEquals(expected, actual);
  }

  @Test
  void rowNumberIteratorWithSort() {
    Selection selection = Selection.withRange(0, 5);
    TableSlice tableSlice = new TableSlice(source, selection);
    tableSlice.sortOn(Sort.on("approval", Order.ASCEND));

    Integer[] expected = new Integer[] {52, 52, 53, 53, 58};
    Integer[] actual =
        Streams.stream(tableSlice.sourceRowNumberIterator())
            .map(i -> source.column("approval").get(i))
            .toArray(Integer[]::new);

    assertArrayEquals(expected, actual);
  }

  @Test
  void rowNumberIteratorWithSelection() {
    Selection selection = Selection.withRange(0, 5);
    TableSlice tableSlice = new TableSlice(source, selection);

    Integer[] expected = new Integer[] {53, 53, 58, 52, 52};
    Integer[] actual =
        Streams.stream(tableSlice.sourceRowNumberIterator())
            .map(i -> source.column("approval").get(i))
            .toArray(Integer[]::new);

    assertArrayEquals(expected, actual);
  }

  @Test
  void rowNumberIteratorWithNoSelection() {
    TableSlice tableSlice = new TableSlice(source);

    Integer[] expected = new Integer[] {53, 53, 58, 52, 52};
    Integer[] actual =
        Streams.stream(tableSlice.sourceRowNumberIterator())
            .map(i -> source.column("approval").get(i))
            .limit(5)
            .toArray(Integer[]::new);

    assertArrayEquals(expected, actual);
  }

  @Test
  void structure() {

    Table table = Table.create(StringColumn.create("MyCol", "A", "A", "B"));
    TableSliceGroup sliceGroup = table.splitOn("MyCol");

    for (TableSlice slice : sliceGroup.getSlices()) {
      assertNotNull(slice.structure());
    }
  }
}
