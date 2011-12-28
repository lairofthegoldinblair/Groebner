package algebra.generic;

/**
 * A left module over a non-commutative ring.  Elements of the base ring
 * are in the class R and elements of the module are in the class M.
 */
public interface LeftModule<R, M>
{
  /**
   * Base ring of the module.
   * 
   * @return the base ring of the module.
   */
  public Ring<R> getLeftRing();
  public M add(M x, M y);
  public M subtract(M x, M y);
  public M negate(M x);
  public M getZero();
  /**
   * Left multiplication of the module element by and element of the ring.
   */
  public M leftMultiply(R r, M m);
}
