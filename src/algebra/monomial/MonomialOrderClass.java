package algebra.monomial;

/**
 * TODO: The notion of MonomialOrderClass is a bit confused.  What is clear is that we want
 * some notion of specifying that an order is a "lex" order or a "grevlex" order without
 * specifying the number of variables or the specific order of the variables.  The confusion
 * begins to arise when thinking about weight vectors that are refined by an order; in particular,
 * what is the class of these orders?  Are they of type equal to the refinement and that we now
 * extend weights to be defined at the instance?  Are they of type e.g. "Weighted Lex" and the weights
 * are specified at the instance?  How do we model the homogenized order that is lifted from a Weyl
 * Algebra into the associated homogenized Weyl Algebra (Heisenberg Algebra)?
 */
public class MonomialOrderClass
{
  private String mClass;

  protected MonomialOrderClass(String name)
  {
    mClass = name;
  }

  public String toString()
  {
    return mClass;
  }

  /**
   * If this order refines another, then return it.
   */
  MonomialOrderClass getRefinedOrder()
  {
    if (mClass.equals("WeightedLexOrder")) return new MonomialOrderClass("LexOrder");
    if (mClass.equals("WeightedRevLexOrder")) return new MonomialOrderClass("GrevlexOrder");
    return null;
  }

  /**
   * Create an order of the appropriate class on a number of variables.
   * @param varOrder A permutation of the 0..varOrder.length-1 which list the variables in decreasing order
   */
  public MonomialOrder create(int [] varOrder) 
  {
    if (mClass.equals("GrevlexOrder")) return new GrevlexOrder(this, varOrder);
    if (mClass.equals("GrlexOrder")) return new GrlexOrder(this, varOrder);
    if (mClass.equals("LexOrder")) return new LexOrder(this, varOrder);
    throw new RuntimeException("Must supply weights for Monomial Order class");
  }

  public MonomialOrder create(int [] weights, int [] varOrder) 
  {
    if(mClass.equals("WeightedLexOrder") || mClass.equals("WeightedRevLexOrder"))
      return new WeightOrder(weights, varOrder, this);
    throw new RuntimeException("Cannot supply weights for Monomial Order class");
  }

  public boolean equals(MonomialOrderClass c)
  {
    return mClass.equals(c.mClass);
  }

  HomogeneousOrderClass getHomogenizedOrder()
  {
    return new HomogeneousOrderClass(this);
  }

  public static MonomialOrderClass forName(String name) throws ClassNotFoundException
  {
    return new MonomialOrderClass(name);
  }

  public static MonomialOrderClass lexOrder()
  {
    return new MonomialOrderClass("LexOrder");
  }

  public static MonomialOrderClass grevlexOrder()
  {
    return new MonomialOrderClass("GrevlexOrder");
  }

  public static MonomialOrderClass grlexOrder()
  {
    return new MonomialOrderClass("GrlexOrder");
  }

  public static ModuleMonomialOrder getPositionOverTermOrder(MonomialOrder order)
  {
    return new PositionOverTermOrder(order);
  }
}
