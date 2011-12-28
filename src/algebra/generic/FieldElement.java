package algebra.generic;

public interface FieldElement<R> extends CommutativeRingElement<R>
{
  public Field<R> getField();
  public R invert();
  public R divide(R y);
}

