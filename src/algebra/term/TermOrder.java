package algebra.term;

import algebra.monomial.MonomialOrder;

/**
 * A TermOrder is a ordering of terms derived from an underying ordering on monomials.
 */
public class TermOrder<_Coeff extends algebra.generic.FieldElement<_Coeff>, _Field extends algebra.generic.Field<_Coeff>> implements java.util.Comparator<Term<_Coeff,_Field>>
{
  private MonomialOrder mOrder;

  public TermOrder(MonomialOrder order)
  {
    mOrder = order;
  }

  public int compare(Term<_Coeff,_Field> lhs, Term<_Coeff,_Field> rhs)
  {
    return mOrder.compare(lhs.getMonomial(), rhs.getMonomial());
  }
}
