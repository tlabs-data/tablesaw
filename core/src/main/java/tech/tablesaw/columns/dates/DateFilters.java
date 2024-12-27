package tech.tablesaw.columns.dates;

import static tech.tablesaw.columns.DateAndTimePredicates.*;

import it.unimi.dsi.fastutil.ints.IntIterator;
import java.time.LocalDate;
import java.util.function.BiPredicate;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import tech.tablesaw.api.DateColumn;
import tech.tablesaw.columns.Column;
import tech.tablesaw.filtering.DateAndDateTimeFilterSpec;
import tech.tablesaw.filtering.predicates.IntBiPredicate;
import tech.tablesaw.selection.BitmapBackedSelection;
import tech.tablesaw.selection.Selection;

public interface DateFilters extends Column<LocalDate>, DateAndDateTimeFilterSpec<Selection> {

  @Override
  DateColumn where(Selection selection);

  /**
   * This version operates on predicates that treat the given IntPredicate as operating on a packed
   * local time This is much more efficient that using a LocalTimePredicate, but requires that the
   * developer understand the semantics of packedLocalTimes
   */
  default Selection eval(IntPredicate predicate) {
    Selection selection = new BitmapBackedSelection();
    IntIterator iterator = intIterator();
    int idx = 0;
    while (iterator.hasNext()) {
      int next = iterator.nextInt();
      if (predicate.test(next)) {
        selection.add(idx);
      }
      idx++;
    }
    return selection;
  }

  default Selection eval(IntBiPredicate predicate, int value) {
    Selection selection = new BitmapBackedSelection();
    IntIterator iterator = intIterator();
    int idx = 0;
    while (iterator.hasNext()) {
      int next = iterator.nextInt();
      if (predicate.test(next, value)) {
        selection.add(idx);
      }
      idx++;
    }
    return selection;
  }

  default Selection eval(IntBiPredicate predicate, DateColumn otherColumn) {
    Selection selection = new BitmapBackedSelection();
    for (int idx = 0; idx < size(); idx++) {
      if (predicate.test(getIntInternal(idx), otherColumn.getIntInternal(idx))) {
        selection.add(idx);
      }
    }
    return selection;
  }

  int getIntInternal(int idx);

  default Selection eval(BiPredicate<LocalDate, LocalDate> predicate, LocalDate valueToCompare) {
    Selection selection = new BitmapBackedSelection();
    for (int idx = 0; idx < size(); idx++) {
      if (predicate.test(get(idx), valueToCompare)) {
        selection.add(idx);
      }
    }
    return selection;
  }

  /**
   * Returns a selection formed by applying the given predicate
   *
   * <p>Prefer using an IntPredicate where the int is a PackedDate, as this version creates a date
   * object for each value in the column
   */
  default Selection eval(Predicate<LocalDate> predicate) {
    Selection selection = new BitmapBackedSelection();
    for (int idx = 0; idx < size(); idx++) {
      if (predicate.test(get(idx))) {
        selection.add(idx);
      }
    }
    return selection;
  }

  @Override
  default Selection isMonday() {
    return eval(PackedLocalDate::isMonday);
  }

  @Override
  default Selection isTuesday() {
    return eval(PackedLocalDate::isTuesday);
  }

  @Override
  default Selection isWednesday() {
    return eval(PackedLocalDate::isWednesday);
  }

  @Override
  default Selection isThursday() {
    return eval(PackedLocalDate::isThursday);
  }

  @Override
  default Selection isFriday() {
    return eval(PackedLocalDate::isFriday);
  }

  @Override
  default Selection isSaturday() {
    return eval(PackedLocalDate::isSaturday);
  }

  @Override
  default Selection isSunday() {
    return eval(PackedLocalDate::isSunday);
  }

  @Override
  default Selection isInJanuary() {
    return eval(PackedLocalDate::isInJanuary);
  }

  @Override
  default Selection isInFebruary() {
    return eval(PackedLocalDate::isInFebruary);
  }

  @Override
  default Selection isInMarch() {
    return eval(PackedLocalDate::isInMarch);
  }

  @Override
  default Selection isInApril() {
    return eval(PackedLocalDate::isInApril);
  }

  @Override
  default Selection isInMay() {
    return eval(PackedLocalDate::isInMay);
  }

  @Override
  default Selection isInJune() {
    return eval(PackedLocalDate::isInJune);
  }

  @Override
  default Selection isInJuly() {
    return eval(PackedLocalDate::isInJuly);
  }

  @Override
  default Selection isInAugust() {
    return eval(PackedLocalDate::isInAugust);
  }

  @Override
  default Selection isInSeptember() {
    return eval(PackedLocalDate::isInSeptember);
  }

  @Override
  default Selection isInOctober() {
    return eval(PackedLocalDate::isInOctober);
  }

  @Override
  default Selection isInNovember() {
    return eval(PackedLocalDate::isInNovember);
  }

  @Override
  default Selection isInDecember() {
    return eval(PackedLocalDate::isInDecember);
  }

  @Override
  default Selection isFirstDayOfMonth() {
    return eval(PackedLocalDate::isFirstDayOfMonth);
  }

  @Override
  default Selection isLastDayOfMonth() {
    return eval(PackedLocalDate::isLastDayOfMonth);
  }

  @Override
  default Selection isInQ1() {
    return eval(PackedLocalDate::isInQ1);
  }

  @Override
  default Selection isInQ2() {
    return eval(PackedLocalDate::isInQ2);
  }

  @Override
  default Selection isInQ3() {
    return eval(PackedLocalDate::isInQ3);
  }

  @Override
  default Selection isInQ4() {
    return eval(PackedLocalDate::isInQ4);
  }

  @Override
  default Selection isInYear(int year) {
    return eval(PackedLocalDate::isInYear, year);
  }

  default Selection isAfter(int value) {
    return eval(PackedLocalDate::isAfter, value);
  }

  @Override
  default Selection isAfter(LocalDate value) {
    int packed = PackedLocalDate.pack(value);
    return eval(PackedLocalDate::isAfter, packed);
  }

  default Selection isBefore(int value) {
    return eval(PackedLocalDate::isBefore, value);
  }

  default Selection isBetweenExcluding(int lowValue, int highValue) {
    return eval(PackedLocalDate::isAfter, lowValue).and(eval(PackedLocalDate::isBefore, highValue));
  }

  default Selection isBetweenExcluding(LocalDate lowValue, LocalDate highValue) {
    return isBetweenExcluding(PackedLocalDate.pack(lowValue), PackedLocalDate.pack(highValue));
  }

  default Selection isBetweenIncluding(LocalDate lowValue, LocalDate highValue) {
    return isBetweenIncluding(PackedLocalDate.pack(lowValue), PackedLocalDate.pack(highValue));
  }

  default Selection isBetweenIncluding(int lowValue, int highValue) {
    return eval(PackedLocalDate::isOnOrAfter, lowValue)
        .and(eval(PackedLocalDate::isOnOrBefore, highValue));
  }

  @Override
  default Selection isBefore(LocalDate value) {
    return isBefore(PackedLocalDate.pack(value));
  }

  @Override
  default Selection isOnOrBefore(LocalDate value) {
    int packed = PackedLocalDate.pack(value);
    return eval(PackedLocalDate::isOnOrBefore, packed);
  }

  @Override
  default Selection isOnOrAfter(LocalDate value) {
    int packed = PackedLocalDate.pack(value);
    return eval(isGreaterThanOrEqualTo, packed);
  }

  default Selection isEqualTo(LocalDate value) {
    return eval(isEqualTo, PackedLocalDate.pack(value));
  }

  default Selection isEqualTo(int packedDate) {
    return eval(isEqualTo, packedDate);
  }

  default Selection isNotEqualTo(int packedDate) {
    return eval(isNotEqualTo, packedDate);
  }

  default Selection isNotEqualTo(LocalDate value) {
    return isNotEqualTo(PackedLocalDate.pack(value));
  }

  /**
   * Returns a bitmap flagging the records for which the value in this column is equal to the value
   * in the given column Column-wise isEqualTo.
   */
  default Selection isEqualTo(DateColumn column) {
    Selection results = new BitmapBackedSelection();
    for (int i = 0; i < size(); i++) {
      if (getIntInternal(i) == column.getIntInternal(i)) {
        results.add(i);
      }
    }
    return results;
  }

  default Selection isNotEqualTo(DateColumn column) {
    Selection results = Selection.withRange(0, size());
    return results.andNot(isEqualTo(column));
  }

  default Selection isOnOrBefore(DateColumn column) {
    Selection results = Selection.withRange(0, size());
    return results.andNot(isAfter(column));
  }

  default Selection isOnOrAfter(DateColumn column) {
    Selection results = Selection.withRange(0, size());
    return results.andNot(isBefore(column));
  }

  default Selection isAfter(DateColumn column) {
    Selection results = new BitmapBackedSelection();
    for (int i = 0; i < size(); i++) {
      if (getIntInternal(i) > column.getIntInternal(i)) {
        results.add(i);
      }
    }
    return results;
  }

  default Selection isBefore(DateColumn column) {
    Selection results = new BitmapBackedSelection();
    for (int i = 0; i < size(); i++) {
      if (getIntInternal(i) < column.getIntInternal(i)) {
        results.add(i);
      }
    }
    return results;
  }

  @Override
  default Selection isMissing() {
    return eval(isMissing);
  }

  @Override
  default Selection isNotMissing() {
    return eval(isNotMissing);
  }

  IntIterator intIterator();
}
