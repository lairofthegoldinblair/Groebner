package algebra.generic;

/**
 * Interface for a general non-commutative ring.  Representation of the elements of the ring
 * is provided by the template parameter R.
 */
public interface Ring<R>
{
  /**
   * The multiplicative identity element of this ring.
   */
  public R getIdentity();
  /**
   * The additive identity element of this ring.
   */
  public R getZero();
  /**
   * Return the -1*x.
   */
  public R negate(R x);
  /**
   * Compute the sum of two elements of the ring.
   */
  public R add(R x, R y);
  /**
   * Compute the difference x - y in this ring.
   */
  public R subtract(R x, R y);
  /**
   * Compute the product of two elements of this ring.
   */
  public R multiply(R x, R y);

  /**
   * Compute whether two elements of the ring are equal. 
   */
  public boolean equals(R x, R y);

  /**
   * Every ring has a natural homomorphism of the
   * integers into it.  These factory methods give that
   * homomorphism.
   */
  public R create(java.math.BigInteger value);
  public R create(int value);
  public R create(long value);
}

