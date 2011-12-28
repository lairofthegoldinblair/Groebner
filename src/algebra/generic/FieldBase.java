package algebra.generic;

public abstract class FieldBase<R> implements Field<R>, Module<R, R>, LeftModule<R, R>, RightModule<R, R>, BiModule<R, R, R>
{
  abstract public R getIdentity();
  abstract public R getZero();
  abstract public R negate(R x);
  abstract public R add(R x, R y);
  abstract public R subtract(R x, R y);
  public R leftMultiply(R x, R y)
  {
    return multiply(x,y);
  }

  public R rightMultiply(R x, R y)
  {
    return multiply(x,y);
  }

  abstract public R multiply(R x, R y);

  abstract public R invert(R x);

  abstract public R divide(R x, R y);

  abstract public boolean equals(R x, R y);

  abstract public R create(java.math.BigInteger value);

  public R create(int value)
  {
    return create(java.math.BigInteger.valueOf(value));
  }

  public R create(long value)
  {
    return create(java.math.BigInteger.valueOf(value));
  }

  public abstract R create(java.math.BigInteger num, java.math.BigInteger denom);

  public R create(int num, int denom)
  {
    return create(java.math.BigInteger.valueOf(num), java.math.BigInteger.valueOf(denom));
  }

  public R create(long num, int denom)
  {
    return create(java.math.BigInteger.valueOf(num), java.math.BigInteger.valueOf(denom));
  }

  public CommutativeRing<R> getRing()
  {
    return this;
  }
  public Ring<R> getLeftRing()
  {
    return this;
  }
  public Ring<R> getRightRing()
  {
    return this;
  }
}
