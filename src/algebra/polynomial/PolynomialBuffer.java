package algebra.polynomial;

/**
 * This is essentially a typedef
 */

import algebra.term.Term;

public class PolynomialBuffer<_Coeff extends algebra.generic.FieldElement<_Coeff>, _Field extends algebra.generic.Field<_Coeff>> extends algebra.generic.GenericBuffer<_Coeff,Term<_Coeff,_Field>,Polynomial<_Coeff,_Field>,Term<_Coeff,_Field>,Polynomial<_Coeff,_Field>>
{
  public PolynomialBuffer(Polynomial<_Coeff,_Field> p)
  {
    super(p);
  }
}
