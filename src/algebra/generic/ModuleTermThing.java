package algebra.generic;

/**
 * This is my stab at a parameterized interface that represents
 * elements of rings that are
 * 1) algebras over fields 
 * 2) elements in free modules over such rings.  
 * The fact that these are elements of an algebra over a field leads to
 * idea of terms themselves and the the notion that element of this ring are sums of terms.
 * The goal is to put enough information in this interface so that we can perform
 * the division algorithm and Buchberger's algorithm.
 * Note that I don't want to make any assumption about commutativity because we are 
 * handling at least cases involving polynomial rings over fields and Weyl algebras.
 * So right here I am assuming that I have a left module.  On the
 * other hand, shouldn't I be able to do everything for right modules
 * as well?
 * @param F the base field for the base ring (assuming the ring is an algebra over this field).
 * @param R the base ring of the module of which this is an element
 * @param M the module of which this is an element
 * @param MT the class of terms for module elements
 * @param RT the class of terms for ring elements
 */
public interface ModuleTermThing<F, RT, R, MT, M> extends LeftModuleElement<R, M>
{
  /**
   * The module to which this element belongs.
   */
  public LeftModule<R, M> getModule();
  public M add(M y);
  public M subtract(M y);
  public M negate();
  public M leftMultiply(R r);

  public boolean empty();

//   public boolean equals(M y);

  /**
   *  Extract the initial term with respect to a term ordering.
   */
  public MT getInitialTerm();
  /**
   * Add the module term to this module element.
   */
  public M addTerm(MT t);
  /**
   * Subtract a module term from this module element.
   */
  public M subtractTerm(MT t);
  /**
   * Left multiply this module element by the term from the ring.
   */
  public M leftMultiplyTerm(RT t);
  /**
   * Calculate the spair of two module elements.
   * @return the value (lcm(lt(this),lt(y))/lt(y))*this - (lcm(lt(this),lt(y))/lt(this))*y
   */
  public SyzygyPair<RT,M> sPair(M y);
  /**
   * Calculate the normal form of the module element with respect to the collection of module elements:
   * that is to say, write 
   * <code>this=a[0]*c[0] + ... + a[n]*c[n] + r</code> 
   * This is generally performed using the division algorithm for the appropriate context (e.g. polynomial
   * rings or Weyl algebras).
   */
  public ModuleNormalForm<RT, R, MT, M> getNormalForm(java.util.Collection<M> c);
  /**
   * Returns the monic element derived from this element.  Calculated by dividing by the coefficient of the initial term.
   */
  public M getMonic();
  /**
   * Divide this element by an element of the field.
   */
  public M divide(F coeff);
}
