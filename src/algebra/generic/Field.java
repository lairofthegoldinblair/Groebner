package algebra.generic;

/**
 * A field is a commutative ring in which one can invert non-zero elements.
 */
public interface Field<R> extends CommutativeRing<R>
{
  /**
   * Invert the element of the field.
   * 
   * @throws RuntimeException if x == getRing().getZero()
   */
  public R invert(R x);
  /**
   * Calculate the quotient x/y in the field.
   * 
   * @throws RuntimeException if y == getRing().getZero()
   */
  public R divide(R x, R y);

  /**
   * Every ring has a natural homomorphism of the
   * integers into it.  These factory methods give that
   * homomorphism a not-necessarily homomorphic extension
   * to the rationals.  In case the field is of characteristic
   * zero, this will in fact be a homomorphism.
   */
  public R create(java.math.BigInteger num, java.math.BigInteger denom);
  public R create(int num, int denom);
  public R create(long value, int denom);
}

