package algebra.monomial;

/**
 * The homogenized order is configured with an underlying base order.
 * The homogenized order is an order on monomials of one variable more
 * than the base order and uses the base order as a refinement of total
 * degree.  Thus the HomogenizedOrder agrees with the base order on
 * homogenized monomials and is extended by degree.
 */
public class HomogenizedOrder extends MonomialOrder
{
  private MonomialOrder mBaseOrder;
  private MonomialOrderClass mClazz;

  public MonomialOrderClass getOrderClass()
  {
    return mClazz; 
  }

  public HomogenizedOrder(MonomialOrderClass clazz, MonomialOrder baseOrder)
  {
    mBaseOrder = baseOrder;
  }

  public int compare(MonomialBase lhs, MonomialBase rhs)
  {
    // First compare the degree.  Then refine by looking at
    // the base order on the non-homogeneous variables. 
    return lhs.getDegree() < rhs.getDegree() ? -1 : 
      (lhs.getDegree() > rhs.getDegree() ? 1 : 
       mBaseOrder.compare(lhs.getDehomogenization(), rhs.getDehomogenization()));
  }

  public int getNumVars()
  {
    return mBaseOrder.getNumVars() + 1;
  }
}
