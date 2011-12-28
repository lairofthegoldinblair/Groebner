package algebra.generic;

/**
 * This is my stab at a parameterized interface that represents
 * elements of rings that are algebras over fields as well as
 * elements in free modules over such rings.  The goal is to put
 * enough information in this interface so that we can perform
 * the division algorithm and Buchberger's algorithm.
 * R is the class representing the ring elements that are made from terms
 * F represents the field over which the ring is an F-algebra
 * T is the class representing the term elements.
 */
public interface TermThing<F, R, T> extends RingElement<R>
{
  // Ring element interface.
  public R negate();
  public R add(R y);
  public R subtract(R y);
  public R leftMultiply(R y);
  public R rightMultiply(R y);

  public boolean empty();

//   public boolean equals(R y);

  public Ring<R> getRing();

  /**
   * Get the initial term of the element
   */
  public T getInitialTerm();
  /**
   * Add a term to this element 
   * @return the sum of this and the element t.
   */
  public R addTerm(T t);
  /**
   * Subtract a term from this element 
   * @return the difference of this-t.
   */
  public R subtractTerm(T t);
  /**
   * Multiply this on the left by a ring element that is a term.
   * @return the product of t*this.
   */  
  public R leftMultiplyTerm(T t);
  public R divide(F coeff);
}
