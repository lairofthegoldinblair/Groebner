package algebra.term;

import algebra.monomial.Monomial;
import algebra.monomial.ModuleMonomial;

/**
 * A term of an element in a free module over a k-algebra.
 */
public class ModuleTerm<_Coeff extends algebra.generic.FieldElement<_Coeff>, _Field extends algebra.generic.Field<_Coeff>> 
  implements algebra.generic.Term<Term<_Coeff,_Field>, ModuleTerm<_Coeff,_Field>, _Coeff>
{
  public ModuleTerm(_Coeff coeff, ModuleMonomial m)
  {
    mCoefficient = coeff;
    mMonomial = m;
  }

  public ModuleTerm(Term<_Coeff,_Field> t, int position)
  {
    mMonomial = new ModuleMonomial(t.getMonomial(), position);
    mCoefficient = t.getCoefficient();
  }

  public boolean equals(Object o)
  {
    if (!(o instanceof ModuleTerm)) return false;
    ModuleTerm t = (ModuleTerm)o;
    return mCoefficient.equals(t.getCoefficient()) && mMonomial.equals(t.getMonomial());
  }

  public int hashCode()
  {
    return getCoefficient().hashCode() + getMonomial().hashCode();
  }

  private _Coeff mCoefficient;
  public _Coeff getCoefficient()
  {
    return mCoefficient;
  }

  private ModuleMonomial mMonomial;
  public ModuleMonomial getMonomial()
  {
    return mMonomial;
  }

  public ModuleTerm<_Coeff,_Field> multiply(Term<_Coeff,_Field> t)
  {
    ModuleMonomial m = mMonomial.multiply(t.getMonomial());
    _Coeff x = mCoefficient;
    _Coeff y = t.getCoefficient();
    _Coeff z = x.multiply(y);
    return m == null ? null : new ModuleTerm<_Coeff,_Field>(mCoefficient.multiply(t.getCoefficient()), m);
  }

  public ModuleTerm<_Coeff,_Field> scalarMultiply(_Coeff c)
  {
    return new ModuleTerm<_Coeff,_Field>(getCoefficient().multiply(c), getMonomial());
  }

  public ModuleTerm<_Coeff,_Field> scalarDivide(_Coeff c)
  {
    return new ModuleTerm<_Coeff,_Field>(getCoefficient().divide(c), getMonomial());
  }

  public Term<_Coeff,_Field> divide(ModuleTerm<_Coeff,_Field> t)
  {
    Monomial m = mMonomial.divide(t.getMonomial());
    return m == null ? null : new Term<_Coeff,_Field>(mCoefficient.divide(t.getCoefficient()), m);
  }

  public ModuleTerm<_Coeff,_Field> differentiate(int [] order)
  {
    algebra.monomial.DifferentiatedMonomial<ModuleMonomial> d = getMonomial().differentiate(order);
    return new ModuleTerm<_Coeff,_Field>(getCoefficient().multiply(getCoefficient().getRing().create(d.getCoefficient())), 
				   d.getMonomial());
  }

  public boolean isZero()
  {
    return getCoefficient().getField().getZero().equals(getCoefficient());
  }

  public String toString()
  {
    String monomialString = getMonomial().toString();
    if(monomialString.length() > 0) return getCoefficient().toString() + "*" + getMonomial().toString();
    else return getCoefficient().toString();
  }
}


