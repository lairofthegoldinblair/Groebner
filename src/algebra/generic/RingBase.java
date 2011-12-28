package algebra.generic;

/**
 * This class really doesn't do much other than
 * implement the fact that when a ring is thought
 * of as a bi-module over itself, the base ring of the
 * module is itself.
 */
public abstract class RingBase<R> implements Ring<R>, Module<R, R>, LeftModule<R, R>, RightModule<R, R>, BiModule<R, R, R>
{
  abstract public R getIdentity();
  abstract public R getZero();
  abstract public R negate(R x);
  abstract public R add(R x, R y);
  abstract public R subtract(R x, R y);
  abstract public R multiply(R x, R y);

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

  public Ring<R> getLeftRing()
  {
    return this;
  }
  public Ring<R> getRightRing()
  {
    return this;
  }
}
