package tech.tablesaw.columns.datetimes;

import static tech.tablesaw.columns.datetimes.DateTimePredicates.isInYear;
import static tech.tablesaw.columns.temporal.TemporalPredicates.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import tech.tablesaw.api.DateTimeColumn;
import tech.tablesaw.columns.temporal.TemporalFilters;
import tech.tablesaw.filtering.DateTimeFilterSpec;
import tech.tablesaw.selection.BitmapBackedSelection;
import tech.tablesaw.selection.Selection;

public interface DateTimeFilters
    extends TemporalFilters<LocalDateTime>, DateTimeFilterSpec<Selection> {

  @Override
  default Selection isAfter(LocalDateTime value) {
    return eval(isGreaterThan, PackedLocalDateTime.pack(value));
  }

  @Override
  default Selection isAfter(LocalDate value) {
    return isOnOrAfter(value.plusDays(1));
  }

  @Override
  default Selection isOnOrAfter(LocalDate value) {
    return isOnOrAfter(value.atStartOfDay());
  }

  @Override
  default Selection isOnOrAfter(LocalDateTime value) {
    return eval(isGreaterThanOrEqualTo, PackedLocalDateTime.pack(value));
  }

  @Override
  default Selection isBefore(LocalDateTime value) {
    return eval(isLessThan, PackedLocalDateTime.pack(value));
  }

  @Override
  default Selection isBefore(LocalDate value) {
    return isBefore(value.atStartOfDay());
  }

  @Override
  default Selection isOnOrBefore(LocalDate value) {
    return isOnOrBefore(value.atStartOfDay());
  }

  @Override
  default Selection isOnOrBefore(LocalDateTime value) {
    return eval(isLessThanOrEqualTo, PackedLocalDateTime.pack(value));
  }

  @Override
  default Selection isAfter(DateTimeColumn column) {
    Selection results = new BitmapBackedSelection();
    for (int i = 0; i < size(); i++) {
      if (getLongInternal(i) > column.getLongInternal(i)) {
        results.add(i);
      }
    }
    return results;
  }

  @Override
  default Selection isBefore(DateTimeColumn column) {
    Selection results = new BitmapBackedSelection();
    for (int i = 0; i < size(); i++) {
      if (getLongInternal(i) < column.getLongInternal(i)) {
        results.add(i);
      }
    }
    return results;
  }

  @Override
  default Selection isEqualTo(LocalDateTime value) {
    long packed = PackedLocalDateTime.pack(value);
    return eval(isEqualTo, packed);
  }

  @Override
  default Selection isNotEqualTo(LocalDateTime value) {
    long packed = PackedLocalDateTime.pack(value);
    return eval(isNotEqualTo, packed);
  }

  @Override
  default Selection isEqualTo(DateTimeColumn column) {
    Selection results = new BitmapBackedSelection();
    for (int i = 0; i < size(); i++) {
      if (getLongInternal(i) == column.getLongInternal(i)) {
        results.add(i);
      }
    }
    return results;
  }

  @Override
  default Selection isNotEqualTo(DateTimeColumn column) {
    Selection results = Selection.withRange(0, size());
    return results.andNot(isEqualTo(column));
  }

  @Override
  default Selection isOnOrAfter(DateTimeColumn column) {
    Selection results = Selection.withRange(0, size());
    return results.andNot(isBefore(column));
  }

  @Override
  default Selection isOnOrBefore(DateTimeColumn column) {
    Selection results = Selection.withRange(0, size());
    return results.andNot(isAfter(column));
  }

  @Override
  default Selection isMonday() {
    return eval(PackedLocalDateTime::isMonday);
  }

  @Override
  default Selection isTuesday() {
    return eval(PackedLocalDateTime::isTuesday);
  }

  @Override
  default Selection isWednesday() {
    return eval(PackedLocalDateTime::isWednesday);
  }

  @Override
  default Selection isThursday() {
    return eval(PackedLocalDateTime::isThursday);
  }

  @Override
  default Selection isFriday() {
    return eval(PackedLocalDateTime::isFriday);
  }

  @Override
  default Selection isSaturday() {
    return eval(PackedLocalDateTime::isSaturday);
  }

  @Override
  default Selection isSunday() {
    return eval(PackedLocalDateTime::isSunday);
  }

  @Override
  default Selection isInJanuary() {
    return eval(PackedLocalDateTime::isInJanuary);
  }

  @Override
  default Selection isInFebruary() {
    return eval(PackedLocalDateTime::isInFebruary);
  }

  @Override
  default Selection isInMarch() {
    return eval(PackedLocalDateTime::isInMarch);
  }

  @Override
  default Selection isInApril() {
    return eval(PackedLocalDateTime::isInApril);
  }

  @Override
  default Selection isInMay() {
    return eval(PackedLocalDateTime::isInMay);
  }

  @Override
  default Selection isInJune() {
    return eval(PackedLocalDateTime::isInJune);
  }

  @Override
  default Selection isInJuly() {
    return eval(PackedLocalDateTime::isInJuly);
  }

  @Override
  default Selection isInAugust() {
    return eval(PackedLocalDateTime::isInAugust);
  }

  @Override
  default Selection isInSeptember() {
    return eval(PackedLocalDateTime::isInSeptember);
  }

  @Override
  default Selection isInOctober() {
    return eval(PackedLocalDateTime::isInOctober);
  }

  @Override
  default Selection isInNovember() {
    return eval(PackedLocalDateTime::isInNovember);
  }

  @Override
  default Selection isInDecember() {
    return eval(PackedLocalDateTime::isInDecember);
  }

  @Override
  default Selection isFirstDayOfMonth() {
    return eval(PackedLocalDateTime::isFirstDayOfMonth);
  }

  @Override
  default Selection isLastDayOfMonth() {
    return eval(PackedLocalDateTime::isLastDayOfMonth);
  }

  @Override
  default Selection isInQ1() {
    return eval(PackedLocalDateTime::isInQ1);
  }

  @Override
  default Selection isInQ2() {
    return eval(PackedLocalDateTime::isInQ2);
  }

  @Override
  default Selection isInQ3() {
    return eval(PackedLocalDateTime::isInQ3);
  }

  @Override
  default Selection isInQ4() {
    return eval(PackedLocalDateTime::isInQ4);
  }

  @Override
  default Selection isNoon() {
    return eval(PackedLocalDateTime::isNoon);
  }

  @Override
  default Selection isMidnight() {
    return eval(PackedLocalDateTime::isMidnight);
  }

  @Override
  default Selection isBeforeNoon() {
    return eval(PackedLocalDateTime::AM);
  }

  @Override
  default Selection isAfterNoon() {
    return eval(PackedLocalDateTime::PM);
  }

  @Override
  default Selection isBetweenExcluding(LocalDateTime lowValue, LocalDateTime highValue) {
    return isBetweenExcluding(
        PackedLocalDateTime.pack(lowValue), PackedLocalDateTime.pack(highValue));
  }

  @Override
  default Selection isBetweenIncluding(LocalDateTime lowValue, LocalDateTime highValue) {
    return isBetweenIncluding(
        PackedLocalDateTime.pack(lowValue), PackedLocalDateTime.pack(highValue));
  }

  @Override
  default Selection isInYear(int year) {
    return eval(isInYear, year);
  }

  @Override
  default Selection isMissing() {
    return eval(isMissing);
  }

  @Override
  default Selection isNotMissing() {
    return eval(isNotMissing);
  }
}
