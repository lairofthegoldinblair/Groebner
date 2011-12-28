package algebra.generic;

/** 
 * A module over a commutative ring.  Elements of the base ring live in the class R and
 * elements of the module live in the class M.
 */
public interface Module<R, M>
{
  /**
   * Base ring of the module.
   * @return base ring of the module.
   */
  public CommutativeRing<R> getRing();
  public M add(M x, M y);
  public M subtract(M x, M y);
  public M negate(M x);
  public M getZero();
  /**
   * Multiply the element of the module by a commutative ring element.
   */
  public M multiply(R r, M m);
}
