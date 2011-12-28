package algebra.dmodule;

import algebra.Symbol;
import algebra.SymbolList;
import algebra.WeightVector;

import algebra.monomial.Monomial;
import algebra.monomial.MonomialOrder;
import algebra.monomial.MonomialOrderClass;

import algebra.term.Term;

/**
 * Represents the associated graded ring of a Weyl algebra.  Recall that the Weyl algebra is isomorphic to the set of all
 * differential operators in N variables with polynomial coefficients.
 *
 * There is a family of algebras that are derived from the Weyl algebra: the associated graded algebras. If we think of the Weyl
 * Algebra as having generators x_n an D_n for n=1..N (where D_n represents differentiation with respect to x_n) subject to the 
 * product rule D_n*x_n - x_n*D_n = 1, D_i*D_j = D_j*D_i, x_i*D_j=D_j*x_i and x_i*x_j=x_j*x_i for i != j, then we get the family of associted
 * graded by replacing some product rule relations with commutativity relations: D_n*x_n=x_n*D_n.
 * One of extreme of this family is where replace none of the product rule relations in which case we have the ordinary Weyl Algebra.
 * The other extreme of this family is where we replace all of the product rule relations in which case we get the polynomial algebra on
 * 2*N variables.
 * 
 * Standard notation is that we replace the symbol D_n by E_n when replacing the product rule relation for D_n.
 * Groebner basis theory for the Weyl Algebra takes places in the full family of associated graded rings as developed in
 * Saito, Sturmfels and Takayama "Groebner Deformations of Hypergeometric Differential Equations".
 */
public class GradedWeylAlgebra<_Coeff extends algebra.generic.FieldElement<_Coeff>, _Field extends algebra.generic.Field<_Coeff>> implements 
algebra.generic.Ring<GradedDifferentialOperator<_Coeff, _Field>>, 
algebra.generic.BiModule<GradedDifferentialOperator<_Coeff, _Field>,GradedDifferentialOperator<_Coeff, _Field>,GradedDifferentialOperator<_Coeff, _Field>>, 
algebra.generic.LeftModule<GradedDifferentialOperator<_Coeff, _Field>,GradedDifferentialOperator<_Coeff, _Field>>, 
algebra.generic.RightModule<GradedDifferentialOperator<_Coeff, _Field>,GradedDifferentialOperator<_Coeff, _Field>>
{
  private boolean [] mIsDifferential;

  private algebra.polynomial.PolynomialRing<_Coeff,_Field>  mCanonicalRing;

  protected algebra.polynomial.PolynomialRing<_Coeff,_Field> getCanonicalRing()
  {
    return mCanonicalRing;
  }

  /**
   * Add derivative operators for the list of symbols with respect to a set of weights.
   */
  static SymbolList addDerivativeOperators(SymbolList vars, boolean [] isDifferential)
  {
    SymbolList varsWithDops = new SymbolList();
    varsWithDops.addAll(vars);
    for(int i=0; i<vars.size(); i++)
    {
      varsWithDops.add(Symbol.create((isDifferential[i] ? "D_" : "E_") + vars.get(i).toString()));
    }
    return varsWithDops;
  }

//   static SymbolList convertDerivativeOperators(WeylAlgebra alg, WeightVector weights)
//   {
//     SymbolList varsWithDops = new SymbolList();
//     for(int i=0; i<alg.getNumVars()/2; i++) 
//     {
//       varsWithDops.add(alg.getSymbol(i));
//     }
//     for(int i=0; i<alg.getNumVars()/2; i++) 
//     {
//       varsWithDops.add(Symbol.create((weights.get(i)+weights.get(i+alg.getNumVars()/2)>0 ? "E_" : "D_") + alg.getSymbol(i).toString()));
//     }
//     return varsWithDops;
//   }

  public boolean equals(GradedWeylAlgebra<_Coeff,_Field> obj)
  {
    if(!getCanonicalRing().equals(obj.getCanonicalRing())) return false;
    for(int i=0; i<mIsDifferential.length; i++)
    {
      if(mIsDifferential[i] != obj.mIsDifferential[i]) return false;
    }
    return true;
  }

  /**
   * Create an associated graded Weyl Algebra of dimension vars.size() on the variables vars and using the term order orderClass.
   * Collapse product rule relations for variables vars[i] for which isDifferential[i] is false.
   */
  public GradedWeylAlgebra(_Field baseField, 
			   SymbolList vars, 
			   boolean [] isDifferential, 
			   MonomialOrderClass orderClass)
  {
    mCanonicalRing = new algebra.polynomial.PolynomialRing<_Coeff,_Field>(baseField, addDerivativeOperators(vars, isDifferential), orderClass);
    mIsDifferential = isDifferential;
  }

  /**
   * Create an associated graded Weyl Algebra of dimension vars.size() on the variables vars and using the weighted term order 
   * with weights and base order of type orderClass.
   * Collapse product rule relations for variables vars[i] for which isDifferential[i] is false.
   */
  public GradedWeylAlgebra(_Field baseField, int [] weights, SymbolList vars, boolean [] isDifferential, MonomialOrderClass orderClass)
  {
    if(weights.length != 2*vars.size()) throw new RuntimeException("Number of weights must be twice number of symbols");
    mCanonicalRing = new algebra.polynomial.PolynomialRing<_Coeff,_Field>(baseField, weights, addDerivativeOperators(vars, isDifferential), orderClass);
    mIsDifferential = isDifferential;
  }

//   public GradedWeylAlgebra(Field baseField, WeightVector weights, WeylAlgebra alg)
//   {
//     mCanonicalRing = new algebra.polynomial.PolynomialRing<_Coeff,_Field>(baseField, convertDerivativeOperators(alg, weights), alg.getOrder());
//     mIsDifferential = new boolean [weights.size()/2];
//     for (int i=0; i<weights.size()/2; i++)
//     {
//       mIsDifferential[i] = (weights.get(i) + weights.get(i+weights.size()/2)) == 0;
//     }
//   }

  protected GradedDifferentialOperator<_Coeff,_Field> create(algebra.polynomial.Polynomial<_Coeff,_Field> p)
  {
    return new GradedDifferentialOperator<_Coeff,_Field>(this, p);
  }

  /**
   * Create a scalar differential operator.
   */
  public GradedDifferentialOperator<_Coeff,_Field> getScalarElement(_Coeff n)
  {
    return new GradedDifferentialOperator<_Coeff,_Field>(this, getCanonicalRing().getScalarElement(n));
  }

  public boolean isDifferential(int i)
  {
    return mIsDifferential[i];
  }

  public _Field getBaseField()
  {
    return mCanonicalRing.getBaseField();
  }

  ////////////////////////////////////////////////////////////////////////
  // Ring and Module interface
  ////////////////////////////////////////////////////////////////////////

  public GradedWeylAlgebra<_Coeff,_Field> getRing()
  {
    return this;
  }

  public GradedWeylAlgebra<_Coeff,_Field> getLeftRing()
  {
    return this;
  }
  public GradedWeylAlgebra<_Coeff,_Field> getRightRing()
  {
    return this;
  }

  public GradedDifferentialOperator<_Coeff,_Field> add(GradedDifferentialOperator<_Coeff,_Field> x, GradedDifferentialOperator<_Coeff,_Field> y)
  {
    return x.add(y);
  }

  public GradedDifferentialOperator<_Coeff,_Field> subtract(GradedDifferentialOperator<_Coeff,_Field> x, GradedDifferentialOperator<_Coeff,_Field> y)
  {
    return x.subtract(y);
  }

  public GradedDifferentialOperator<_Coeff,_Field> negate(GradedDifferentialOperator<_Coeff,_Field> x)
  {
    return x.negate();
  }

  public GradedDifferentialOperator<_Coeff,_Field> getZero()
  {
    return create(new java.util.Vector<Term<_Coeff,_Field>> ());
  }

  public GradedDifferentialOperator<_Coeff,_Field> getIdentity()
  {
    return getScalarElement(getBaseField().getIdentity());
  }

  public GradedDifferentialOperator<_Coeff,_Field> multiply(GradedDifferentialOperator<_Coeff,_Field> x, GradedDifferentialOperator<_Coeff,_Field> y)
  {
    return x.leftMultiply(y);
  }
  
  public GradedDifferentialOperator<_Coeff,_Field> leftMultiply(GradedDifferentialOperator<_Coeff,_Field> x, GradedDifferentialOperator<_Coeff,_Field> y)
  {
    return x.leftMultiply(y);
  }
  
  public GradedDifferentialOperator<_Coeff,_Field> rightMultiply(GradedDifferentialOperator<_Coeff,_Field> x, GradedDifferentialOperator<_Coeff,_Field> y)
  {
    return x.rightMultiply(y);
  }
  
  public boolean equals(GradedDifferentialOperator<_Coeff,_Field> x, GradedDifferentialOperator<_Coeff,_Field> y)
  {
    return x.equals(y);
  }

  /**
   * Every ring has a natural homomorphism of the
   * integers into it.  These factory methods give that
   * homomorphism.
   */
  public GradedDifferentialOperator<_Coeff,_Field> create(java.math.BigInteger value)
  {
    return getScalarElement(getBaseField().create(value));
  }

  public GradedDifferentialOperator<_Coeff,_Field> create(int value)
  {
    return create(java.math.BigInteger.valueOf(value));
  }

  public GradedDifferentialOperator<_Coeff,_Field> create(long value)
  {
    return create(java.math.BigInteger.valueOf(value));
  }

  /**
   * Term constructors
   */
  public Term<_Coeff,_Field> createTerm(_Coeff coeff, Monomial monomial)
  {
    return getCanonicalRing().createTerm(coeff, monomial);
  }

  public GradedDifferentialOperator<_Coeff,_Field> create(java.util.Vector<Term<_Coeff,_Field>> terms)
  {
    return create(getCanonicalRing().createPolynomial(terms));
  }

  public GradedDifferentialOperator<_Coeff,_Field> create(Term<_Coeff,_Field> term)
  {
    return create(getCanonicalRing().createPolynomial(term));
  }

  public GradedDifferentialOperator<_Coeff,_Field> create(String str)
  {
    return create(getCanonicalRing().createPolynomial(str));
  }
}
