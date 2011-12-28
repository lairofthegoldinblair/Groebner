package algebra.term;

/**
 * A SortedSet of terms that an element of a free module comprises
 */
public class ModuleTermSet<_Coeff extends algebra.generic.FieldElement<_Coeff>, _Field extends algebra.generic.Field<_Coeff>> extends java.util.TreeSet<ModuleTerm<_Coeff,_Field>>
{
  public ModuleTermSet(ModuleTermOrder<_Coeff,_Field> order)
  {
    super(order);
  }

  /*
   * Override the call to add to check for zero term
   */

  public boolean add(ModuleTerm<_Coeff,_Field> t)
  {
    if (!t.isZero()) return super.add(t);
    else return true;
  }
}
