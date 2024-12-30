package tech.tablesaw.analytic;

import static org.junit.jupiter.api.Assertions.*;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;
import tech.tablesaw.analytic.AggregateFunctions.Max;
import tech.tablesaw.analytic.AggregateFunctions.Sum;

class AnalyticAggregateFunctionsTest {

  @Test
  void testSumFixed() {
    AggregateFunction<Integer, Double> function = new Sum<Integer>().functionForAppendWindows();
    function.addAllRightMost(ImmutableList.of(10, 10, 10));
    assertEquals(30, function.getValue());
  }

  @Test
  void testSumSliding() {
    AggregateFunction<Integer, Double> function = new Sum<Integer>().functionForSlidingWindows();
    function.addAllRightMost(ImmutableList.of(10, 10, 10));
    function.removeLeftMost();
    function.addRightMost(100);
    assertEquals(120, function.getValue());
  }

  @Test
  void testMaxFixed() {
    AggregateFunction<Integer, Double> function = new Max<Integer>().functionForAppendWindows();
    function.addAllRightMost(ImmutableList.of(11, 10, 9));
    assertEquals(11, function.getValue());
  }

  @Test
  void testMaxSliding() {
    AggregateFunction<Integer, Double> function = new Max<Integer>().functionForSlidingWindows();
    function.addAllRightMost(ImmutableList.of(100, 10, 9));
    function.removeLeftMost();
    function.addRightMost(3);
    assertEquals(10, function.getValue());
  }
}
