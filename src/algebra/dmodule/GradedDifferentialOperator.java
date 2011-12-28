package algebra.dmodule;

import algebra.generic.ModuleNormalForm;
import algebra.generic.SyzygyPair;

import algebra.monomial.Monomial;

import algebra.term.Term;

/**
 * Represents an element in a graded Weyl Algebra.
 * Elements of a graded Weyl algebra are represented in the normal form in which all differential operators
 * appear on the right in monomials (which obviously can be arranged by applying the product rule).
 */
public class GradedDifferentialOperator<_Coeff extends algebra.generic.FieldElement<_Coeff>, _Field extends algebra.generic.Field<_Coeff>> implements algebra.generic.TermThing<_Coeff,GradedDifferentialOperator<_Coeff, _Field>, Term<_Coeff,_Field>>, algebra.generic.ModuleTermThing<_Coeff,Term<_Coeff,_Field>,GradedDifferentialOperator<_Coeff, _Field>,Term<_Coeff,_Field>,GradedDifferentialOperator<_Coeff, _Field>>
{
  // Represent the differential operator by its symbol (a polynomial)
  private GradedWeylAlgebra<_Coeff, _Field> mRing;
  private algebra.polynomial.Polynomial<_Coeff,_Field> mPolynomial;

  private algebra.polynomial.Polynomial<_Coeff,_Field> getPolynomial()
  {
    return mPolynomial;
  }
  
  // Common TermThing/ModuleTermThing implementation
  public GradedDifferentialOperator<_Coeff,_Field> negate()
  {
    return multiply(getRing().getBaseField().getIdentity().negate());
  }

  /**
   * Calculate the sum of two GradedDifferentialOperators.
   */
  public GradedDifferentialOperator<_Coeff,_Field> add(GradedDifferentialOperator<_Coeff,_Field> p)
  {
    return getRing().create(mPolynomial.add(p.mPolynomial));
  }

  /**
   * Calculate the difference of two GradedDifferentialOperators 
   * return this-p.
   */
  public GradedDifferentialOperator<_Coeff,_Field> subtract(GradedDifferentialOperator<_Coeff,_Field> p)
  {
    return getRing().create(mPolynomial.subtract(p.mPolynomial));
  }

  /**
   * Multiply this on the left by operator p.
   */
  public GradedDifferentialOperator<_Coeff,_Field> leftMultiply(GradedDifferentialOperator<_Coeff,_Field> p)
  {
    return p.rightMultiply(this);
  }

  /**
   * Multiply this on the right by the operator rhs.
   */
  public GradedDifferentialOperator<_Coeff,_Field> rightMultiply(GradedDifferentialOperator<_Coeff,_Field> rhs)
  {
    // Use the Liebnitz formula on the polynomial representation
    int numVars = getPolynomial().getRing().getNumVars()/2;
    if(2*(numVars) != getPolynomial().getRing().getNumVars()) throw new RuntimeException("GradedWeylAlgebra doesn't have even number of variables");

    algebra.polynomial.PolynomialBuffer<_Coeff,_Field> polynomialBuffer = 
      new algebra.polynomial.PolynomialBuffer<_Coeff,_Field>(getRing().getCanonicalRing().getZero());
    int [] lhsDerivative = new int [getPolynomial().getRing().getNumVars()];
    int [] rhsDerivative = new int [getPolynomial().getRing().getNumVars()];
    int [] order = new int [numVars];
    boolean [] isDifferential = new boolean [numVars];
    // lhs is always differentiated by the derivative vars.
    // rhs is always differentiated by the variable vars.
    for(int i = 0; i< numVars; i++)
      {
	rhsDerivative[numVars + i] = 0;
	lhsDerivative[i] = 0;
	isDifferential[i] = getRing().isDifferential(i);
	order[i] = 0;
      }

    multiply(this, rhs, polynomialBuffer, isDifferential, lhsDerivative, rhsDerivative, order, 0);

    return getRing().create(polynomialBuffer.toElement());
  }

  public boolean empty()
  {
    return mPolynomial.empty();
  }

  public Term<_Coeff,_Field> getInitialTerm()
  {
    return mPolynomial.getInitialTerm();
  }

  public GradedDifferentialOperator<_Coeff,_Field> addTerm(Term<_Coeff,_Field> t)
  {
    return add(getRing().create(t));
  }

  public GradedDifferentialOperator<_Coeff,_Field> subtractTerm(Term<_Coeff,_Field> t)
  {
    return subtract(getRing().create(t));
  }

  public GradedDifferentialOperator<_Coeff,_Field> leftMultiplyTerm(Term<_Coeff,_Field> rhs)
  {
    return getRing().create(rhs).rightMultiply(this);
  }



  // TermThing implementation
  public GradedWeylAlgebra<_Coeff,_Field> getRing()
  {
    return mRing;
  }

  // ModuleTermThing implementation
  public GradedWeylAlgebra<_Coeff,_Field> getModule()
  {
    return mRing;
  }

  /**
   * Calculate the s-pair of two graded differential operators.
   */
  public SyzygyPair<Term<_Coeff,_Field>,GradedDifferentialOperator<_Coeff,_Field>> sPair(GradedDifferentialOperator<_Coeff,_Field> rhs)
  {
    Term<_Coeff,_Field> lcmTerm = getRing().createTerm(getRing().getBaseField().getIdentity(), getInitialTerm().getMonomial().lcm(rhs.getInitialTerm().getMonomial()));
    Term<_Coeff,_Field> t1 = lcmTerm.divide(getInitialTerm());
    Term<_Coeff,_Field> t2 = lcmTerm.divide(rhs.getInitialTerm());
    GradedDifferentialOperator<_Coeff,_Field> p1 = leftMultiplyTerm(t1);
    GradedDifferentialOperator<_Coeff,_Field> p2 = rhs.leftMultiplyTerm(t2);
    return new SyzygyPair<Term<_Coeff,_Field>,GradedDifferentialOperator<_Coeff,_Field>>(this, t1, rhs, t2, p1.subtract(p2));
  }

  /**
   * Return a scaled version of this operator such that the coefficient of its leading term is 1.
   */
  public GradedDifferentialOperator<_Coeff,_Field> getMonic()
  {
    return divide(getInitialTerm().getCoefficient());
  }

  /**
   * Calculate the normal form of this operator with respect to a set of GradedDifferentialOperators.
   * That is to say, find a representation
   * this = a_0*list_0 + ... a_N*list_N + r where r is a linear combination of monomials none of which 
   * is divisible by a leading term of any element of list.
   */
  public algebra.generic.GenericNormalForm<_Coeff,Term<_Coeff,_Field>, GradedDifferentialOperator<_Coeff, _Field>, Term<_Coeff,_Field>, GradedDifferentialOperator<_Coeff, _Field>>
    getNormalForm(java.util.Collection<GradedDifferentialOperator<_Coeff,_Field>> list)
  {
    return new algebra.generic.GenericNormalForm<_Coeff, Term<_Coeff,_Field>, GradedDifferentialOperator<_Coeff, _Field>, Term<_Coeff,_Field>, GradedDifferentialOperator<_Coeff, _Field>>(this, list);
  }



  protected GradedDifferentialOperator(GradedWeylAlgebra<_Coeff,_Field> ring, 
				       algebra.polynomial.Polynomial<_Coeff,_Field> p)
  {
    mRing = ring;
    mPolynomial = p;
    if(!p.getRing().equals(getRing().getCanonicalRing())) throw new algebra.DomainMismatchException();
  }

  /**
   * Returns true if the polynomial p is a non-zero scalar multiple of this
   * polynomial.
   *
   */
  public boolean isScalarMultiple(GradedDifferentialOperator<_Coeff,_Field> p)
  {
    if(!getRing().equals(p.getRing())) throw new RuntimeException("GradedDifferentialOperator.isScalarMultiple has term arg in different ring than base operator");    
    return mPolynomial.isScalarMultiple(p.mPolynomial);
  }

  /**
   * Right multiple this by the term rhs.
   */
  public GradedDifferentialOperator<_Coeff,_Field> rightMultiplyTerm(Term<_Coeff,_Field> rhs)
  {
    return rightMultiply(getRing().create(rhs));
  }

  /**
   * Multiply this by the field constant coeff.
   */
  public GradedDifferentialOperator<_Coeff,_Field> multiply(_Coeff coeff)
  {
    if(!getRing().getBaseField().equals(coeff.getField())) throw new RuntimeException("GradedDifferentialOperator.multiply has coeff arg in different field than base operator");
    return getRing().create(mPolynomial.multiply(coeff));
  }

  /**
   * Calculate this^n where we assume n>=0.
   */
  public GradedDifferentialOperator<_Coeff,_Field> exp(int n)
  {
    if (n < 0) throw new RuntimeException("Cannot invert polynomials");
    if (n == 0) return getRing().getIdentity();
    GradedDifferentialOperator<_Coeff,_Field> result = this;
    for(int i=1; i<n; i++) result = result.rightMultiply(this);
    return result;
  }

  public GradedDifferentialOperator<_Coeff,_Field> divide(_Coeff coeff)
  {
    if(!getRing().getBaseField().equals(coeff.getField())) throw new RuntimeException("GradedDifferentialOperator.divide has coeff arg in different field than base polynomial");
    return getRing().create(mPolynomial.divide(coeff));
  }

  /**
   * PolynomialBuffer methods
   */
  public Monomial getInitialMonomial()
  {
    return getInitialTerm().getMonomial();
  }

  public _Coeff getInitialCoefficient()
  {
    return getInitialTerm().getCoefficient();
  }

  public String toString()
  {
    return mPolynomial.toString();
  }

  public boolean equals(Object obj)
  {
    if (!(obj instanceof GradedDifferentialOperator)) return false;
    GradedDifferentialOperator dop = (GradedDifferentialOperator)obj;
    if(!getRing().equals(dop.getRing())) return false;
    return mPolynomial.equals(dop.mPolynomial);
  }

  public int hashCode()
  {
    return mPolynomial.hashCode();
  }

  public int getDegree()
  {
    return mPolynomial.getDegree();
  }

//   public GradedDifferentialOperator<_Coeff,_Field> getHomogenization(Symbol homogenizingParameter)
//   {
//     int totalDegree = getDegree();
//     PolynomialRing<_Coeff,_Field> homogenizedRing = getRing().getHomogenization(homogenizingParameter);
//   }

//   public Polynomial<_Coeff,_Field> getDehomogenization()
//   {
//     PolynomialRing<_Coeff, _Field> dehomogenizedRing = getRing().getDehomogenization();
//   }

  public java.util.Iterator<Term<_Coeff,_Field>> iterator()
  {
    return mPolynomial.iterator();
  }

  public int size()
  {
    return mPolynomial.size();
  }

  static private int multiFactorial(int [] o)
  {
    int result=1;
    for (int i=0; i<o.length; i++)
      {
	for(int j=o[i]; j>0; j--)
	  {
	    result *= j;
	  }
      }
    return result;
  }

  private boolean multiply(GradedDifferentialOperator<_Coeff,_Field> lhs, 
			   GradedDifferentialOperator<_Coeff,_Field> rhs, 
			   algebra.polynomial.PolynomialBuffer<_Coeff,_Field> buffer, 
			   boolean [] isDifferential, 
			   int [] lhsOrder, 
			   int [] rhsOrder, 
			   int [] order, 
			   int index)
  {
    if(isDifferential[index])
    {
      // Start at zero and iterate over the order of the derivative on the
      // variable whose index is passed in.  For each order, call the 
      // multiply function for the next index if there is one.  Otherwise,
      // calculate the derivative and multiply. If all derivatives that you found
      // are zero then return true, otherwise return false.
      int i;
      for(i=0; true; i++)
      {
	order[index] = i;
	lhsOrder[order.length + index] = i;
	rhsOrder[index] = i;
	// There are more variables, call for the next variable
	if(index+1 < order.length) 
	{
	  boolean finished = multiply(lhs, rhs, buffer, isDifferential, lhsOrder, rhsOrder, order, index+1);
	  // All derivatives with this order in this variable are zero, no need to go further
	  if (finished) break;
	}
	else
	{
	  // This is the last variable, so the derivative is completely specified.
	  // Calculate and break out once a zero derivative is found.
	  algebra.polynomial.Polynomial<_Coeff,_Field> lhsDiff = lhs.getPolynomial().differentiate(lhsOrder);
	  algebra.polynomial.Polynomial<_Coeff,_Field> rhsDiff = rhs.getPolynomial().differentiate(rhsOrder);
	  if (lhsDiff.empty() || rhsDiff.empty()) break;
	  buffer.add(lhsDiff.multiply(rhsDiff).multiply(lhs.getRing().getBaseField().create(1, multiFactorial(order))));
	}
      }

      // If even zeroth order derivative were zero, then tell our caller
      return i==0;
    }
    else
    {
      // This is not a differential variable.  Only process a zeroth order derivative
      order[index] = 0;
      lhsOrder[order.length + index] = 0;
      rhsOrder[index] = 0;
      // There are more variables, call for the next variable
      if(index+1 < order.length) 
      {
	boolean finished = multiply(lhs, rhs, buffer, isDifferential, lhsOrder, rhsOrder, order, index+1);
	// All derivatives with this order in this variable are zero, no need to go further
	return finished;
      }
      else
      {
	// This is the last variable, so the derivative is completely specified.
	// Calculate and tell caller if result is zero.
	algebra.polynomial.Polynomial<_Coeff,_Field> lhsDiff = lhs.getPolynomial().differentiate(lhsOrder);
	algebra.polynomial.Polynomial<_Coeff,_Field> rhsDiff = rhs.getPolynomial().differentiate(rhsOrder);
	if (lhsDiff.empty() || rhsDiff.empty()) 
	{
	  // This derivative is zero and is the only one we compute, so tell caller we're done.
	  return true;
	}
	else
	{
	  // We have a non zero derivative, signal to parent to keep iterating
	  buffer.add(lhsDiff.multiply(rhsDiff).multiply(lhs.getRing().getBaseField().create(1, multiFactorial(order))));
	  return false;
	}
      }
    }
  }

}
