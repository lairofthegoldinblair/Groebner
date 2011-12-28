package algebra.polynomial;

import algebra.Symbol;
import algebra.monomial.Monomial;
import algebra.term.Term;

/**
 * Builder implementation for creating polynomials from a specified polynomial ring.
 */
public class RingPolynomialBuilder<_Coeff extends algebra.generic.FieldElement<_Coeff>, _Field extends algebra.generic.Field<_Coeff>> implements algebra.PolynomialBuilder
{
  private java.util.HashMap<Symbol,Integer>  mMonomialBuilder = new java.util.HashMap<Symbol,Integer>();
  private java.util.Vector<Term<_Coeff,_Field>> mPolynomialBuilder = new java.util.Vector<Term<_Coeff,_Field>>();
  private Monomial mMonomial=null;
  private _Coeff mCoefficient=null;
  private PolynomialRing<_Coeff,_Field> mRing;
  private Polynomial<_Coeff,_Field> mPolynomial=null;

  public RingPolynomialBuilder(PolynomialRing<_Coeff,_Field> ring)
  {
    mRing = ring;
  }

  public Polynomial<_Coeff,_Field> getPolynomial()
  {
    return mPolynomial;
  }

  public void buildModule()
  {
    throw new RuntimeException("Not Implemented");
  }

  /**
   * Take all of the terms built since the last call buildPolynomial and
   * create a polynomial out of them
   */
  public void buildPolynomial()
  {
    mPolynomial = mRing.createPolynomial(mPolynomialBuilder);
  }

  /**
   * Take the most recently created coefficient and monomial and
   * make a term out of them.
   */
  public void buildTerm()
  {
    mPolynomialBuilder.add(mRing.createTerm(mCoefficient, mMonomial));
  }

  /**
   * takes all single variable monomials that have been 
   * built since the last call and creates a monomial from
   * them.
   */
  public void buildMonomial()
  {
    mMonomial = mRing.createMonomial(mMonomialBuilder);
    mMonomialBuilder.clear();
  }

  public void buildSingleVariableMonomial(Symbol var, int exp)
  {
    mMonomialBuilder.put(var, new Integer(exp));
  }

  public void buildCoefficient(int i)
  {
    mCoefficient = mRing.getBaseField().create(i);
  }

  public void buildCoefficient(int i, int j)
  {
    mCoefficient = mRing.getBaseField().create(i, j);
  }
}
