package algebra.term;

import algebra.monomial.Monomial;

/**
 * A Term is a monomial with a coefficient from a base field.
 */
public class Term<_Coeff extends algebra.generic.FieldElement<_Coeff>, _Field extends algebra.generic.Field<_Coeff>> 
  implements algebra.generic.Term<Term<_Coeff,_Field>, Term<_Coeff,_Field>, _Coeff>
{
  public Term(_Coeff coeff, Monomial m)
  {
    mCoefficient = coeff;
    mMonomial = m;
  }

  public boolean equals(Object o)
  {
    if (!(o instanceof Term)) return false;
    Term t = (Term)o;
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

  private Monomial mMonomial;
  public Monomial getMonomial()
  {
    return mMonomial;
  }

  public Term<_Coeff,_Field> multiply(Term<_Coeff,_Field> t)
  {
    Monomial m = mMonomial.multiply(t.getMonomial());
    _Coeff x = mCoefficient;
    _Coeff y = t.getCoefficient();
    _Coeff z = x.multiply(y);
    return m == null ? null : new Term<_Coeff,_Field>(mCoefficient.multiply(t.getCoefficient()), m);
  }

  public Term<_Coeff,_Field> scalarMultiply(_Coeff c)
  {
    return new Term<_Coeff,_Field>(getCoefficient().multiply(c), getMonomial());
  }

  public Term<_Coeff,_Field> scalarDivide(_Coeff c)
  {
    return new Term<_Coeff,_Field>(getCoefficient().divide(c), getMonomial());
  }

  public Term<_Coeff,_Field> divide(Term<_Coeff,_Field> t)
  {
    Monomial m = mMonomial.divide(t.getMonomial());
    return m == null ? null : new Term<_Coeff,_Field>(mCoefficient.divide(t.getCoefficient()), m);
  }

  public Term<_Coeff,_Field> differentiate(int [] order)
  {
    algebra.monomial.DifferentiatedMonomial<Monomial> d = getMonomial().differentiate(order);
    return new Term<_Coeff,_Field>(getCoefficient().multiply(getCoefficient().getRing().create(d.getCoefficient())), 
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


