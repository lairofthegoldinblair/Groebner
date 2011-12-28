package algebra.monomial;

/**
 * Convert any monomial order into a module order by making the
 * the lexicographic order of module generator position over term.
 */
public class PositionOverTermOrder extends ModuleMonomialOrder
{
  private MonomialOrder mOrder;

  protected PositionOverTermOrder(MonomialOrder order)
  {
    mOrder = order;
  }

  public int compare(ModuleMonomial lhs, ModuleMonomial rhs)
  {
    if (lhs.getPosition() < rhs.getPosition()) return -1;
    if (lhs.getPosition() > rhs.getPosition()) return 1;
    return mOrder.compare(lhs, rhs);
  }

  public int getNumVars()
  {
    return mOrder.getNumVars();
  }

  public MonomialOrderClass getOrderClass()
  {
    return mOrder.getOrderClass();
  }
}
