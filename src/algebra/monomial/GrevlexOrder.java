package algebra.monomial;

/**
 *
 * x >_{grevlex} y if and only if degree(x) > degree(y) or there is some N such that x.getExponent(i)==y.getExponent(i) for i=getNumVars()-1..N+1
 * and x.getExponent(N) < y.getExponent(N).
 */
public class GrevlexOrder extends MonomialOrder
{
  private int [] mVarOrder;
  private MonomialOrderClass mClazz;

  // perm is the list of variables in
  // decreasing order
  public GrevlexOrder(MonomialOrderClass clazz, int [] varOrder)
  {
    mVarOrder = varOrder;
    mClazz = clazz;
  }

  public MonomialOrderClass getOrderClass()
  {
    return mClazz;
  }

  public int compare(MonomialBase lhs, MonomialBase rhs)
  {
    // Walk through the variables of the monomials
    // in increasing order until the exponent of one
    // is larger than the other.
    if (mVarOrder.length != lhs.getNumExponents() || mVarOrder.length != rhs.getNumExponents()) 
    {
      throw new RuntimeException("GrevlexOrder is from different ring than monomials");
    }
    if (lhs.getDegree() > rhs.getDegree()) return 1;
    if (lhs.getDegree() < rhs.getDegree()) return -1;
    for(int i=mVarOrder.length-1; i>=0; i--)
    {
      if (lhs.getExponent(mVarOrder[i]) == rhs.getExponent(mVarOrder[i])) continue;
      if (lhs.getExponent(mVarOrder[i]) > rhs.getExponent(mVarOrder[i])) return -1;
      else return 1;
    }
    return 0;
  }
  
  public int getNumVars()
  {
    return mVarOrder.length;
  }

  protected int getVarOrder(int i)
  {
    return mVarOrder[i];
  }

  public boolean equals(GrevlexOrder l)
  {
    if (!l.getOrderClass().equals(getOrderClass()) || l.getNumVars() != getNumVars()) return false;

    for(int i=0; i<getNumVars(); i++)
    {
      if(l.getVarOrder(i) != getVarOrder(i)) return false;
    }
    return true;
  }
}
