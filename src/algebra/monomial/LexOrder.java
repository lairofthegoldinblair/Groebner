package algebra.monomial;

/**
 * Lexicographic ordering of monomials based on ordering of exponents of the variables
 * in the monomial. Definition simply requires specifying
 * the order of the variables that the monomials are built out of.
 * x >_{lex} y if and only if there is some N such that x.getExponent(i)==y.getExponent(i) for i=0..N-1
 * and x.getExponent(N) > y.getExponent(N).
 */
public class LexOrder extends MonomialOrder
{
  private int [] mVarOrder;
  private MonomialOrderClass mClazz;

  // perm is the list of variables in
  // decreasing order
  public LexOrder(MonomialOrderClass clazz, int [] varOrder)
  {
    mVarOrder = varOrder;
    mClazz = clazz;
  }

  public MonomialOrderClass getOrderClass()
  {
    return mClazz;
  }

  /**
   * Compare two monomials with respect to this ordering.
   */
  public int compare(MonomialBase lhs, MonomialBase rhs)
  {
    // Runtime type check that this monomial order is relative to the same number of
    // variables as the monomials we are checking.  TODO: Can this be handled at compile
    // time by an integer template parameter for example?
    if (mVarOrder.length != lhs.getNumExponents() || mVarOrder.length != rhs.getNumExponents()) 
    {
      throw new RuntimeException("LexOrder is from different ring than monomials");
    }
    // Walk through the variables of the monomials
    // in increasing order until the exponent of one
    // is larger than the other.
    for(int i=0; i<mVarOrder.length; i++)
    {
      if (lhs.getExponent(mVarOrder[i]) == rhs.getExponent(mVarOrder[i])) continue;
      if (lhs.getExponent(mVarOrder[i]) > rhs.getExponent(mVarOrder[i])) return 1;
      else return -1;
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

  public boolean equals(LexOrder l)
  {
    if (!l.getOrderClass().equals(getOrderClass()) || l.getNumVars() != getNumVars()) return false;

    for(int i=0; i<getNumVars(); i++)
    {
      if(l.getVarOrder(i) != getVarOrder(i)) return false;
    }
    return true;
  }
}
