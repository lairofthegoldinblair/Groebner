package algebra.monomial;

import algebra.WeightVector;
import algebra.DomainMismatchException;
import algebra.Symbol;

/**
 * A monomial is an element of a finitely generated algebra over a field.
 * It is represented by the exponents for each of the generators of the algebra.
 * For example, a polynomial ring over a field is a finitely generated free algebra
 * over the field where the generators are the variables of the polynomial ring.
 */
public class Monomial implements algebra.generic.Monomial<Monomial, Monomial>, MonomialBase
{
  private int [] mExponents;

  // Monomial Orders need access to the individual
  // exponents.
  public int getExponent(int i)
  {
    return mExponents[i];
  }
  public int getNumExponents()
  {
    return mExponents.length;
  }

  /**
   * This predicate tests whether the rhs is compatible 
   * for an algebraic operation
   */
  public void checkCompatible(Monomial rhs)
  {
    if (getNumExponents() != rhs.getNumExponents()) throw new DomainMismatchException();
  }

  // Don't create Monomials directly, but only as
  // a part of a ring
  public Monomial(int [] exponents)
  {
    mExponents = exponents;
  }

  /**
   * Dehomogenization of a monomial.  Assume that the variable to dehomogenize is
   * the first variable.
   */
  public Monomial getDehomogenization()
  {
    int [] exp = new int [getNumExponents() -1];
    for(int i=1; i<getNumExponents(); i++)
    {
      exp[i-1] = getExponent(i);
    }
    return new Monomial(exp);
  }

  /**
   * Homogenize a monomial with respect to the ordinary degree weight.  Assume that 
   * the homogenization variable is the first variable.
   */
  public Monomial getHomogenization(int degree)
  {
    if (getDegree() > degree) throw new RuntimeException("Can homogenize to degree < monomial degree");
    int [] exp = new int [getNumExponents()+1];
    for(int i=0; i<getNumExponents(); i++)
    {
      exp[i+1] = getExponent(i);
    }
    exp[0] = degree - getDegree();
    return new Monomial(exp);
  }

  /**
   * Returns true is o is a Monomial in the same Ring and whose 
   * exponents are all the same.
   */
  public boolean equals(Object obj)
  {
    if (!(obj instanceof Monomial)) return false;
    Monomial m = (Monomial)obj;
    if (m.getNumExponents() != getNumExponents()) return false;
    for (int i=0; i<getNumExponents(); i++)
    {
      if (getExponent(i) != m.getExponent(i)) return false;
    }
    return true;
  }

  public int hashCode()
  {
    return getDegree();
  }

  public int getDegree()
  {
    int totalDegree=0;
    for (int i=0; i< getNumExponents(); i++) totalDegree += getExponent(i);
    return totalDegree;
  }

  public int getDegree(WeightVector w)
  {
    int totalDegree=0;
    if (getNumExponents() != w.size()) throw new RuntimeException("Weight vector has wrong size");
    for (int i=0; i< getNumExponents(); i++) totalDegree += getExponent(i)*w.get(i);
    return totalDegree;
  }

  public class SingleVariableMonomialPattern
  {
    private boolean mIsMatched;
    public boolean isMatched()
    {
      return mIsMatched;
    }

    private int mSingleVariable;
    public int getSingleVariable()
    {
      return mSingleVariable;
    }

    public boolean match(Monomial m)
    {
      int num = 0;
      for(int i=0; i<m.getNumExponents(); i++)
      {
	if (m.getExponent(i) > 0) 
	{
	  if(num++ > 0) break;
	  mSingleVariable = i;
	}
      }
      mIsMatched = num==1;

      return mIsMatched;
    }

    public SingleVariableMonomialPattern()
    {
      mIsMatched = false;
      mSingleVariable = -1;
    }
  }

  public Monomial multiply(Monomial rhs)
  {
    checkCompatible(rhs);
    int [] exp = new int [getNumExponents()];
    for(int i=0; i<exp.length; i++)
    {
      exp[i] = getExponent(i) + rhs.getExponent(i);
    }
    return new Monomial(exp);
  }

  /**
   * Divide this polynomial by rhs.  Return null if this monomial is
   * not evenly divisible.
   */
  public Monomial divide(Monomial rhs)
  {
    checkCompatible(rhs);
    int [] exp = new int [getNumExponents()];
    for(int i=0; i<exp.length; i++)
    {
      exp[i] = getExponent(i) - rhs.getExponent(i);
      if (exp[i] < 0) return null;
    }
    return new Monomial(exp);
  }

  /**
   * The least common multiple of this monomial with rhs.  This is
   * simply the max of the exponents.
   */
  public Monomial lcm(Monomial rhs)
  {
    checkCompatible(rhs);

    int [] exp = new int [getNumExponents()];
    for(int i=0; i<exp.length; i++)
    {
      exp[i] = getExponent(i) > rhs.getExponent(i) ? getExponent(i) : rhs.getExponent(i);
    }
    return new Monomial(exp);    
  }

  /**
   * Create the Term which is the derivative of this monomial. 
   */
  public DifferentiatedMonomial<Monomial> differentiate(int [] order)
  {
    if (order.length != getNumExponents()) throw new RuntimeException("Mismatched differentiation order");
    int coeff = 1;
    int [] exponents = new int [getNumExponents()];
    for(int i=0; i<order.length; i++)
    {
      if ((exponents[i]=getExponent(i)-order[i]) < 0) 
	return new DifferentiatedMonomial<Monomial>(new Monomial(exponents), 0);
      for(int j=getExponent(i); j>exponents[i]; j--) coeff *= j;
    }

    return new DifferentiatedMonomial<Monomial>(new Monomial(exponents), coeff);
  }

  /**
   * Move this monomial to a new base ring.  
   * If the new ring has more variables than the current one, the
   * exponents for those variables will be assumed to be zero.  
   * If the new ring has fewer variables than the current one, the
   * extraneous exponents will be dropped.
   */
  public Monomial baseChange(int numVars)
  {
    int [] exponents = new int [numVars];
    int varsToCopy = numVars<getNumExponents() ? numVars : getNumExponents();
    int i;
    for (i=0; i<varsToCopy; i++)
    {
      exponents[i] = getExponent(i);
    }
    for (; i<exponents.length; i++)
    {
      exponents[i] = 0;
    }

    return new Monomial(exponents);
  }

  static public String printVariable(String varName, int exp)
  {
    switch(exp)
    {
    case 0:
      return "";
    case 1:
      return varName;
    default:
      return varName + "^" + exp;
    }
  }

  public String toString()
  {
    switch(getNumExponents())
    {
    case 1:
      return getExponent(0) != 0 ? "x^" + getExponent(0) : "1";
    case 2:
      {
	StringBuffer buf = new StringBuffer();
	buf.append(printVariable("x", getExponent(0)));
	buf.append(printVariable("y", getExponent(1)));
	return buf.toString();
      }
    case 3:
      {
	StringBuffer buf = new StringBuffer();
	buf.append(printVariable("x", getExponent(0)));
	buf.append(printVariable("y", getExponent(1)));
	buf.append(printVariable("z", getExponent(2)));
	return buf.toString();
      }
    default:
      {
	StringBuffer buf = new StringBuffer();
	for(int i=0; i<getNumExponents(); i++)
	{
	  buf.append(printVariable("x_" + i, getExponent(i)));
	}
	return buf.toString();
      }
    }
  }
}
