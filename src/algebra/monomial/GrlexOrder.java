package algebra.monomial;

/**
 * A graded lexicographic ordering of monomials.
 * x > y if and only if degree(x) > degree(y) or (degree(x)==degree(y) and x >_{lex} y).
 */
public class GrlexOrder extends LexOrder
{
  // perm is the list of variables in
  // decreasing order
  public GrlexOrder(MonomialOrderClass clazz, int [] varOrder)
  {
    super(clazz, varOrder);
  }

  public int compare(MonomialBase lhs, MonomialBase rhs)
  {
    // First compare the degree then refine with lex
    if (lhs.getDegree() > rhs.getDegree()) return 1;
    if (lhs.getDegree() < rhs.getDegree()) return -1;
    return super.compare(lhs, rhs);
  } 

}
