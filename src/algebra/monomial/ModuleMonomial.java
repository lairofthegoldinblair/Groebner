package algebra.monomial;

import algebra.WeightVector;
import algebra.DomainMismatchException;
import algebra.Symbol;

/**
 * A monomial is an element of a free module over a ring.  The ring is assumed to be an 
 * finitely generated algebra over a field (e.g. a polynomial ring or a Weyl algebra).
 * The ModuleMonomial comprises a monomial in the underlying ring (a list of exponents of the
 * algebra generators in the ring) as well as the index of the free module component in which
 * the element lives.
 */
public class ModuleMonomial implements algebra.generic.Monomial<Monomial, ModuleMonomial>, MonomialBase
{
  private int [] mExponents;

  private int mPosition;

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
   * The index of the free module generator this monomial contains.
   */
  public int getPosition()
  {
    return mPosition;
  }

  /**
   * This predicate tests whether the rhs is compatible 
   * for an algebraic operation
   */
  public void checkCompatible(ModuleMonomial rhs)
  {
    if (getNumExponents() != rhs.getNumExponents()) throw new DomainMismatchException();
    if (getPosition() != rhs.getPosition()) throw new DomainMismatchException();
  }

  public void checkCompatible(Monomial rhs)
  {
    if (getNumExponents() != rhs.getNumExponents()) throw new DomainMismatchException();
  }

  // Don't create Monomials directly, but only as
  // a part of a ring
  public ModuleMonomial(int [] exponents, int position)
  {
    mExponents = exponents;
    mPosition = position;
  }

  public ModuleMonomial(Monomial m, int position)
  {
    mExponents = new int [m.getNumExponents()];
    for(int i=0; i<m.getNumExponents(); i++)
    {
      mExponents[i] = m.getExponent(i);
    }
    mPosition = position;
  }

  /**
   * Dehomogenization of a monomial.  Assume that the variable to dehomogenize is
   * the first variable.
   */
  public ModuleMonomial getDehomogenization()
  {
    int [] exp = new int [getNumExponents() -1];
    for(int i=1; i<getNumExponents(); i++)
    {
      exp[i-1] = getExponent(i);
    }
    return new ModuleMonomial(exp, getPosition());
  }

  /**
   * Homogenize a monomial with respect to the ordinary degree weight.  Assume that 
   * the homogenization variable is the first variable.
   */
  public ModuleMonomial getHomogenization(int degree)
  {
    if (getDegree() > degree) throw new RuntimeException("Can homogenize to degree < monomial degree");
    int [] exp = new int [getNumExponents()+1];
    for(int i=0; i<getNumExponents(); i++)
    {
      exp[i+1] = getExponent(i);
    }
    exp[0] = degree - getDegree();
    return new ModuleMonomial(exp, getPosition());
  }

  /**
   * Returns true is o is a Monomial in the same Ring and whose 
   * exponents are all the same.
   */
  public boolean equals(Object obj)
  {
    if (!(obj instanceof ModuleMonomial)) return false;
    ModuleMonomial m = (ModuleMonomial)obj;
    if (m.getPosition() != getPosition()) return false;
    if (m.getNumExponents() != getNumExponents()) return false;
    for (int i=0; i<getNumExponents(); i++)
    {
      if (getExponent(i) != m.getExponent(i)) return false;
    }
    return true;
  }

  public int hashCode()
  {
    return getDegree() + getPosition();
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

  public ModuleMonomial multiply(Monomial rhs)
  {
    checkCompatible(rhs);
    int [] exp = new int [getNumExponents()];
    for(int i=0; i<exp.length; i++)
    {
      exp[i] = getExponent(i) + rhs.getExponent(i);
    }
    return new ModuleMonomial(exp, getPosition());
  }

  /**
   * Divide this polynomial by rhs.  Return null if this monomial is
   * not evenly divisible.
   */
  public Monomial divide(ModuleMonomial rhs)
  {
    if (getPosition() != rhs.getPosition()) return null;
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
  public ModuleMonomial lcm(ModuleMonomial rhs)
  {
    if (getNumExponents() != rhs.getNumExponents()) throw new DomainMismatchException();
    if (getPosition() != rhs.getPosition()) return null;

    int [] exp = new int [getNumExponents()];
    for(int i=0; i<exp.length; i++)
    {
      exp[i] = getExponent(i) > rhs.getExponent(i) ? getExponent(i) : rhs.getExponent(i);
    }
    return new ModuleMonomial(exp, getPosition());    
  }

  /**
   * Create the Term which is the derivative of this monomial. 
   */
  public DifferentiatedMonomial<ModuleMonomial> differentiate(int [] order)
  {
    if (order.length != getNumExponents()) throw new RuntimeException("Mismatched differentiation order");
    int coeff = 1;
    int [] exponents = new int [getNumExponents()];
    for(int i=0; i<order.length; i++)
    {
      if ((exponents[i]=getExponent(i)-order[i]) < 0) 
	return new DifferentiatedMonomial<ModuleMonomial>(new ModuleMonomial(exponents, getPosition()), 0);
      for(int j=getExponent(i); j>exponents[i]; j--) coeff *= j;
    }

    return new DifferentiatedMonomial<ModuleMonomial> (new ModuleMonomial(exponents, getPosition()), coeff);
  }

  /**
   * Move this monomial to a new base ring.  
   * If the new ring has more variables than the current one, the
   * exponents for those variables will be assumed to be zero.  
   * If the new ring has fewer variables than the current one, the
   * extraneous exponents will be dropped.
   */
  public ModuleMonomial baseChange(int numVars)
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

    return new ModuleMonomial(exponents, getPosition());
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
