package tech.tablesaw.api;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class FixIsInCollectionTest {

    @Test
    void testIsInIntColumn() {
        IntColumn column = IntColumn.create("c", 1, 2);
        assertEquals(1, column.isIn(1).size()); // passes
        assertEquals(1, column.isIn(Arrays.asList(1d)).size()); // passes
        assertEquals(1, column.isIn(Arrays.asList(1)).size()); // fails
    }
}
