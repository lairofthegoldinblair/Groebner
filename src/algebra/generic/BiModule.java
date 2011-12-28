package algebra.generic;

/**
 * A left module over a possiblly non-commutative ring R  and right module over the possible non-commutative ring S.  Elements of the left base ring
 * are in the class R, elements of the right base ring are in S and elements of the module are in the class M.
 */
public interface BiModule<R, S, M>
{
  /**
   * The left base ring of the module.
   */
  public Ring<R> getLeftRing();
  /**
   * The right base ring of the module.
   */
  public Ring<S> getRightRing();
  public M add(M x, M y);
  public M subtract(M x, M y);
  public M negate(M x);
  public M getZero();
  /**
   * Multiply on the left by an element of the left ring.
   */
  public M leftMultiply(R r, M m);
  /**
   * Multiply on the right by an element of the right ring.
   */
  public M rightMultiply(S r, M m);
}

