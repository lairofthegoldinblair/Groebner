package algebra.generic;

/**
 * The generic interface for a term covers the case in which
 * we have a module term or a ring/algebra term.
 * @param _Term An algebra term in the ring underlying this
 * @param _ModuleTerm A term in the module we are modeling (might be the same as _Term)
 * @param _Coeff The field of coefficients
 * @param _Monomial The class of monomial (either a module monomial or a ring monomial)
 */
public interface Term<_Term, _FreeTerm, _Coeff>
{
  public _Coeff getCoefficient();

  //public _Monomial getMonomial();

  public _FreeTerm multiply(_Term t);

  public _FreeTerm scalarMultiply(_Coeff c);

  public _FreeTerm scalarDivide(_Coeff c);

  public _Term divide(_FreeTerm t);

  public boolean isZero();
}

