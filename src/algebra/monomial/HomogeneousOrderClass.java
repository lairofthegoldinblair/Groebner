package algebra.monomial;

/**
 * The notion of MonomialOrderClass is a bit confused.  What is clear is that we want
 * some notion of specifying that an order is a "lex" order or a "grevlex" order without
 * specifying the number of variables or the specific order of the variables.  The confusion
 * begins to arise when thinking about weight vectors that are refined by an order; in particular,
 * what is the class of these orders?  Are they of type equal to the refinement and that we now
 * extend weights to be defined at the instance?  Are they of type e.g. "Weighted Lex" and the weights
 * are specified at the instance?  How do we model the homogenized order that is lifted from a Weyl
 * Algebra into the associated homogenized Weyl Algebra (Heisenberg Algebra)?
 */
public class HomogeneousOrderClass extends MonomialOrderClass
{
  private MonomialOrderClass mClazz;

  protected HomogeneousOrderClass(MonomialOrderClass clazz) 
  {
    super("Homogeneous");
    mClazz = clazz;
  }

  /**
   * If this order refines another, then return it.
   */
  MonomialOrderClass getRefinedOrder()
  {
    return mClazz;
  }

  // Ignore the first element when constructing the base.  If the first
  // element is not the smallest, then complain.
  int [] dehomogenizeVarOrder(int [] varOrder)
  {
    if (varOrder[0] != 0) throw new RuntimeException("Homogenizing parameter must be minimal");
    int [] ret = new int [varOrder.length - 1];
    for (int i=1; i<varOrder.length; i++) ret[i-1] = varOrder[i] -1;
    return ret;
  }

  int [] dehomogenizeWeights(int [] weights)
  {
    int [] ret = new int [weights.length - 1];
    for (int i=1; i<weights.length; i++) ret[i-1] = weights[i];
    return ret;
  }

  public MonomialOrder create(int [] varOrder) 
  {
    MonomialOrder base = mClazz.create(dehomogenizeVarOrder(varOrder));
    return new HomogenizedOrder(this, base);
  }

  public MonomialOrder create(int [] weights, int [] varOrder) 
  {
    MonomialOrder base = mClazz.create(dehomogenizeWeights(weights), dehomogenizeVarOrder(varOrder));
    return new HomogenizedOrder(this, base);
  }

  public MonomialOrder create(MonomialOrder baseOrder)
  {
    return new HomogenizedOrder(this, baseOrder);
  }

  public boolean equals(HomogeneousOrderClass c)
  {
    return mClazz.equals(c.mClazz);
  }

}
