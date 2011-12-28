package algebra.generic;

/**
 * An algebra is a ring R with a ring homomorphism S -> R.
 */
public interface Algebra<R, S> extends Ring<R>
{
  /**
   * Get the base ring of the algebra.
   */
  public Ring<S> getBaseRing();
  /**
   * Evaluate the ring homomorphism defining the algebra.
   */
  public R create(S s);
}
