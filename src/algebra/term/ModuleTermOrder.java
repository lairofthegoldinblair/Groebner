package algebra.term;

import algebra.monomial.ModuleMonomialOrder;

public class ModuleTermOrder<_Coeff extends algebra.generic.FieldElement<_Coeff>, _Field extends algebra.generic.Field<_Coeff>> implements java.util.Comparator<ModuleTerm<_Coeff,_Field>>
{
  private ModuleMonomialOrder mOrder;

  public ModuleTermOrder(ModuleMonomialOrder order)
  {
    mOrder = order;
  }

  public int compare(ModuleTerm<_Coeff,_Field> lhs, ModuleTerm<_Coeff,_Field> rhs)
  {
    return mOrder.compare(lhs.getMonomial(), rhs.getMonomial());
  }
}
