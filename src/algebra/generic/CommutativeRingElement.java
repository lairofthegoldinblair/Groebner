package algebra.generic;

public interface CommutativeRingElement<R> extends RingElement<R>
{
  public R multiply(R x);
}

