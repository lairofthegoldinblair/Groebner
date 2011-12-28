package algebra.term;

/**
 * A SortedSet of terms that the polynomial comprises
 */
public class TermSet<_Coeff extends algebra.generic.FieldElement<_Coeff>, _Field extends algebra.generic.Field<_Coeff>> extends java.util.TreeSet<Term<_Coeff,_Field>>
{
  public TermSet(TermOrder<_Coeff,_Field> order)
  {
    super(order);
  }

  /*
   * Override the call to add to check for zero term
   */

  public boolean add(Term<_Coeff,_Field> t)
  {
    if (!t.isZero()) return super.add(t);
    else return true;
  }
}
