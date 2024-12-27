package tech.tablesaw.aggregate;

import tech.tablesaw.api.BooleanColumn;
import tech.tablesaw.api.ColumnType;

/** A partial implementation of aggregate functions to summarize over a boolean column */
public abstract class BooleanAggregateFunction extends AggregateFunction<BooleanColumn, Boolean> {

  /**
   * Constructs a BooleanAggregateFunction with the given name. The name is used to name a column in
   * the output when this function is used by {@link Summarizer}
   */
  protected BooleanAggregateFunction(String name) {
    super(name);
  }

  /** Returns a Boolean value as a result of applying this function to the given column */
  @Override
  public abstract Boolean summarize(BooleanColumn column);

  /** {@inheritDoc} */
  @Override
  public boolean isCompatibleColumn(ColumnType type) {
    return type == ColumnType.BOOLEAN;
  }

  /** {@inheritDoc} */
  @Override
  public ColumnType returnType() {
    return ColumnType.BOOLEAN;
  }
}
