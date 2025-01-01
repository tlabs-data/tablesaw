package tech.tablesaw.filtering;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import tech.tablesaw.api.Table;
import tech.tablesaw.selection.Selection;

@Beta
public class Or implements Function<Table, Selection> {

  private final List<Function<Table, Selection>> arguments;

  @SafeVarargs
  @SuppressWarnings("varargs")
  public Or(Function<Table, Selection>... arguments) {
    Preconditions.checkNotNull(arguments, "The arguments to Or must be non-null");
    Preconditions.checkArgument(
        arguments.length > 0, "The arguments to Or must be an array of length 1 or greater");
    this.arguments = Arrays.stream(arguments).collect(Collectors.toList());
  }

  @Override
  public Selection apply(Table table) {
    Selection result = arguments.get(0).apply(table);
    for (int i = 1; i < arguments.size(); i++) {
      result.or(arguments.get(i).apply(table));
    }
    return result;
  }
}
