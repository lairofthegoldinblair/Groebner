package algebra.monomial;

/**
 * A WeightOrder compares the weighted degree of two monomials and then
 * if they are equal delegates to a base order to break ties.
 */
public class WeightOrder extends MonomialOrder
{
  private int [] mWeights;
  private MonomialOrder mRefine;
  private MonomialOrderClass mClazz;

  // perm is the list of variables in
  // decreasing order
  public WeightOrder(int [] weights, int [] varOrder, MonomialOrderClass orderClass)
  {
    if (weights.length != varOrder.length) throw new RuntimeException("array size mismatch");
    mWeights = weights;
    mRefine = orderClass.getRefinedOrder().create(varOrder);
    mClazz = orderClass;
  }

  public MonomialOrderClass getOrderClass()
  {
    return mClazz;
  }

  private final static int dotprod(int [] weights, MonomialBase m)
  {
    int accum=0;
    for(int i=0; i<weights.length; i++) accum += weights[i]*m.getExponent(i);
    return accum;
  }

  public int compare(MonomialBase lhs, MonomialBase rhs)
  {
    // Walk through the variables of the monomials
    // in increasing order until the exponent of one
    // is larger than the other.
    if (mWeights.length != lhs.getNumExponents() || mWeights.length != rhs.getNumExponents()) 
    {
      throw new RuntimeException("GrevlexOrder is from different ring than monomials");
    }
    int lhsProd = dotprod(mWeights, lhs);
    int rhsProd = dotprod(mWeights, rhs);
    return lhsProd < rhsProd ? -1 : (lhsProd > rhsProd ? 1 : mRefine.compare(lhs, rhs));
  }
  
  public int getNumVars()
  {
    return mWeights.length;
  }
}
