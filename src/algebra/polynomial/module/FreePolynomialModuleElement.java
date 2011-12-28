package algebra.polynomial.module;

import algebra.Symbol;
import algebra.SymbolList;
import algebra.WeightVector;

import algebra.generic.SyzygyPair;

import algebra.monomial.Monomial;
import algebra.monomial.ModuleMonomial;

import algebra.term.Term;
import algebra.term.ModuleTerm;
import algebra.term.ModuleTermSet;
import algebra.term.ModuleTermOrder;

import algebra.polynomial.Polynomial;

public class FreePolynomialModuleElement<_Coeff extends algebra.generic.FieldElement<_Coeff>, _Field extends algebra.generic.Field<_Coeff>> implements 
algebra.generic.ModuleTermThing<_Coeff,Term<_Coeff,_Field>,Polynomial<_Coeff, _Field>,ModuleTerm<_Coeff,_Field>,FreePolynomialModuleElement<_Coeff, _Field>>, 
algebra.generic.ModuleElement<Polynomial<_Coeff, _Field>,FreePolynomialModuleElement<_Coeff, _Field>>, 
algebra.generic.LeftModuleElement<Polynomial<_Coeff, _Field>,FreePolynomialModuleElement<_Coeff, _Field>>, 
algebra.generic.RightModuleElement<Polynomial<_Coeff, _Field>,FreePolynomialModuleElement<_Coeff, _Field>>, 
algebra.generic.BiModuleElement<Polynomial<_Coeff, _Field>,Polynomial<_Coeff, _Field>,FreePolynomialModuleElement<_Coeff, _Field>>
{
  private FreePolynomialModule<_Coeff, _Field> mModule;
  
  public FreePolynomialModule<_Coeff,_Field> getModule()
  {
    return mModule;
  }

  private ModuleTermSet<_Coeff,_Field> mTerms;

  private FreePolynomialModuleElement(FreePolynomialModule<_Coeff,_Field> module, ModuleTermSet<_Coeff,_Field> terms)
  {
    mModule = module;
    mTerms = terms;
  }

  private FreePolynomialModuleElement(FreePolynomialModule<_Coeff,_Field> module, ModuleTerm<_Coeff,_Field> term)
  {
    mModule = module;
    mTerms = new ModuleTermSet<_Coeff,_Field>(new ModuleTermOrder<_Coeff,_Field>(module.getOrder()));
    mTerms.add(term);
  }
  
  public FreePolynomialModuleElement(FreePolynomialModule<_Coeff,_Field> module, java.util.Collection<ModuleTerm<_Coeff,_Field>> terms)
  {
    mModule = module;
    mTerms = new ModuleTermSet<_Coeff,_Field>(new ModuleTermOrder<_Coeff,_Field>(module.getOrder()));
    java.util.Iterator<ModuleTerm<_Coeff,_Field>> it = terms.iterator();
    while(it.hasNext())
    {
      mTerms.add(it.next());
    }
  }

  public FreePolynomialModuleElement(FreePolynomialModule<_Coeff,_Field> module)
  {
    mModule = module;
    mTerms = new ModuleTermSet<_Coeff,_Field>(new ModuleTermOrder<_Coeff,_Field>(module.getOrder()));
  }

  /**
   * Returns true if the polynomial p is a non-zero scalar multiple of this
   * polynomial.
   *
   */
  public boolean isScalarMultiple(FreePolynomialModuleElement<_Coeff,_Field> p)
  {
    if(!getModule().equals(p.getModule())) throw new RuntimeException("Polynomial.isScalarMultiple has term arg in different ring than base polynomial");    

    if(p.mTerms.size() != mTerms.size()) return false;

    // The initial monomials must be the same.  If they are then
    // save the coefficients so that we can do further comparisons.
    if(!p.getInitialTerm().getMonomial().equals(getInitialTerm().getMonomial())) return false;
    _Coeff c1 = getInitialTerm().getCoefficient();
    _Coeff c2 = p.getInitialTerm().getCoefficient();
    java.util.Iterator<ModuleTerm<_Coeff,_Field>> it1 = mTerms.iterator();
    java.util.Iterator<ModuleTerm<_Coeff,_Field>> it2 = p.mTerms.iterator();
    while(it1.hasNext() && it2.hasNext())
    {
      ModuleTerm<_Coeff,_Field> t1 = it1.next();
      ModuleTerm<_Coeff,_Field> t2 = it2.next();
      if(!t1.getMonomial().equals(t2.getMonomial())) return false;
      if(!c1.multiply(t2.getCoefficient()).equals(c2.multiply(t1.getCoefficient()))) return false;
    }
    return !it1.hasNext() && !it2.hasNext(); 
  }

  public FreePolynomialModuleElement<_Coeff,_Field> rightMultiply(Polynomial<_Coeff,_Field> p)
  {
    return multiply(p);
  }

  public FreePolynomialModuleElement<_Coeff,_Field> rightMultiplyTerm(Term<_Coeff,_Field> rhs)
  {
    return multiply(rhs);
  }

  public FreePolynomialModuleElement<_Coeff,_Field> leftMultiply(Polynomial<_Coeff,_Field> p)
  {
    return multiply(p);
  }

  public FreePolynomialModuleElement<_Coeff,_Field> leftMultiplyTerm(Term<_Coeff,_Field> rhs)
  {
    return multiply(rhs);
  }

  public FreePolynomialModuleElement<_Coeff,_Field> multiply(Term<_Coeff,_Field> rhs)
  {
    ModuleTermSet<_Coeff,_Field> treeSet = new ModuleTermSet<_Coeff,_Field>(new ModuleTermOrder<_Coeff,_Field>(getModule().getOrder()));
    java.util.Iterator<ModuleTerm<_Coeff,_Field>> terms = mTerms.iterator();
    while(terms.hasNext())
    {
      treeSet.add(terms.next().multiply(rhs));
    }
    return new FreePolynomialModuleElement<_Coeff,_Field>(getModule(), treeSet);
  }

  public FreePolynomialModuleElement<_Coeff,_Field> multiply(Polynomial<_Coeff,_Field> rhs)
  {
    algebra.generic.GenericBuffer<_Coeff,Term<_Coeff,_Field>,Polynomial<_Coeff,_Field>,ModuleTerm<_Coeff,_Field>,FreePolynomialModuleElement<_Coeff,_Field>> buff = new algebra.generic.GenericBuffer<_Coeff,Term<_Coeff,_Field>,Polynomial<_Coeff,_Field>,ModuleTerm<_Coeff,_Field>,FreePolynomialModuleElement<_Coeff,_Field>>(getModule().getZero());
    java.util.Iterator<Term<_Coeff,_Field>> terms = rhs.iterator();
    while (terms.hasNext())
    {
      buff.add(multiply(terms.next()));
    }
    return buff.toElement();
  }
 
  public FreePolynomialModuleElement<_Coeff,_Field> multiply(_Coeff coeff)
  {
    if(!getModule().getBaseField().equals(coeff.getField())) throw new RuntimeException("Polynomial.multiply has coeff arg in different field than base polynomial");
    ModuleTermSet<_Coeff,_Field> treeSet = new ModuleTermSet<_Coeff,_Field>(new ModuleTermOrder<_Coeff,_Field>(getModule().getOrder()));
    java.util.Iterator<ModuleTerm<_Coeff,_Field>> terms = mTerms.iterator();
    while(terms.hasNext())
    {
      treeSet.add(terms.next().scalarMultiply(coeff));
    }
    return new FreePolynomialModuleElement<_Coeff,_Field>(getModule(), treeSet);    
  }

  public FreePolynomialModuleElement<_Coeff,_Field> divide(_Coeff coeff)
  {
    if(!getModule().getBaseField().equals(coeff.getField())) throw new RuntimeException("Polynomial.divide has coeff arg in different field than base polynomial");
    ModuleTermSet<_Coeff,_Field> treeSet = new ModuleTermSet<_Coeff,_Field>(new ModuleTermOrder<_Coeff,_Field>(getModule().getOrder()));
    java.util.Iterator<ModuleTerm<_Coeff,_Field>> terms = mTerms.iterator();
    while(terms.hasNext())
    {
      treeSet.add(terms.next().scalarDivide(coeff));
    }
    FreePolynomialModuleElement<_Coeff,_Field> ret = new FreePolynomialModuleElement<_Coeff,_Field>(getModule(), treeSet);    
    return ret;
  }

  public FreePolynomialModuleElement<_Coeff,_Field> getMonic()
  {
    return divide(getInitialTerm().getCoefficient());
  }

  public FreePolynomialModuleElement<_Coeff,_Field> negate()
  {
    return multiply(getModule().getBaseField().getIdentity().negate());
  }

  public algebra.generic.GenericNormalForm<_Coeff,Term<_Coeff,_Field>, Polynomial<_Coeff, _Field>, ModuleTerm<_Coeff,_Field>, FreePolynomialModuleElement<_Coeff, _Field>>
    getNormalForm(java.util.Collection<FreePolynomialModuleElement<_Coeff,_Field>> list)
  {
    return new algebra.generic.GenericNormalForm<_Coeff, Term<_Coeff,_Field>, Polynomial<_Coeff, _Field>, ModuleTerm<_Coeff,_Field>, FreePolynomialModuleElement<_Coeff, _Field>>(this, list);
  }

  /**
   * PolynomialBuffer methods
   */
  public ModuleTerm<_Coeff,_Field> getInitialTerm()
  {
    return mTerms.last();
  }

  public ModuleMonomial getInitialMonomial()
  {
    return getInitialTerm().getMonomial();
  }

  public _Coeff getInitialCoefficient()
  {
    return getInitialTerm().getCoefficient();
  }

  public _Coeff getConstantTerm()
  {
    // Since we assume a term order was used to create
    // the sorted set, we may look at the first element
    ModuleTerm<_Coeff,_Field> t = mTerms.first();
    if(t.getMonomial().getDegree()>0)
    {
      return getModule().getBaseField().getZero();
    }
    else
    {
      return t.getCoefficient();
    }
  }

  public FreePolynomialModuleElement<_Coeff,_Field> add(FreePolynomialModuleElement<_Coeff,_Field> p)
  {
    // Perform a merge of the two lists of terms.
    // When you get a match of two monomials in the terms,
    // add the coefficients.
    ModuleTermOrder<_Coeff,_Field> order = new ModuleTermOrder<_Coeff,_Field>(getModule().getOrder());
    ModuleTermSet<_Coeff,_Field> treeSet = new ModuleTermSet<_Coeff,_Field>(order);
    java.util.Iterator<ModuleTerm<_Coeff,_Field>> left = mTerms.iterator();
    java.util.Iterator<ModuleTerm<_Coeff,_Field>> right = p.mTerms.iterator();
    ModuleTerm<_Coeff,_Field> leftTerm=left.hasNext() ? left.next() : null;
    ModuleTerm<_Coeff,_Field> rightTerm=right.hasNext() ? right.next() : null;

    while(leftTerm != null && rightTerm != null)
    {
      // First advance the iterator if necessary
      int cmp = order.compare(leftTerm, rightTerm);
      if (cmp < 0) 
      {
	// Left side is smaller.  Move the term into the new set and advance
	treeSet.add(leftTerm);
	leftTerm = left.hasNext() ? left.next() : null;
      }
      else if (cmp > 0)
      {
	// Right side is smaller.  Move the term into the new set and advance
	treeSet.add(rightTerm);
	rightTerm = right.hasNext() ? right.next() : null;
      }
      else
      {
	// These terms have equal monomials.  Move the sum and advance both.
	_Coeff coeff = leftTerm.getCoefficient().add(rightTerm.getCoefficient());
	treeSet.add(getModule().createTerm(coeff, leftTerm.getMonomial()));
	leftTerm = left.hasNext() ? left.next() : null;
	rightTerm = right.hasNext() ? right.next() : null;	    
      }
    }

    // Move what remains
    while (leftTerm != null)
    {
      treeSet.add(leftTerm);
      leftTerm = left.hasNext() ? left.next() : null;
    }

    while (rightTerm != null)
    {
      treeSet.add(rightTerm);
      rightTerm = right.hasNext() ? right.next() : null;
    }

    return new FreePolynomialModuleElement<_Coeff,_Field>(getModule(), treeSet);
  }

  public FreePolynomialModuleElement<_Coeff,_Field> addTerm(ModuleTerm<_Coeff,_Field> t)
  {
    return add(getModule().create(t));
  }

  public FreePolynomialModuleElement<_Coeff,_Field> subtractTerm(ModuleTerm<_Coeff,_Field> t)
  {
    return subtract(getModule().create(t));
  }

  public FreePolynomialModuleElement<_Coeff,_Field> subtract(FreePolynomialModuleElement<_Coeff,_Field> p)
  {
    // Perform a merge of the two lists of terms.
    // When you get a match of two monomials in the terms,
    // add the coefficients.
    ModuleTermOrder<_Coeff,_Field> order = new ModuleTermOrder<_Coeff,_Field>(getModule().getOrder());
    ModuleTermSet<_Coeff,_Field> treeSet = new ModuleTermSet<_Coeff,_Field>(order);
    java.util.Iterator<ModuleTerm<_Coeff,_Field>> left = mTerms.iterator();
    java.util.Iterator<ModuleTerm<_Coeff,_Field>> right = p.mTerms.iterator();
    ModuleTerm<_Coeff,_Field> leftTerm=left.hasNext() ? left.next() : null;
    ModuleTerm<_Coeff,_Field> rightTerm=right.hasNext() ? right.next() : null;

    while(leftTerm != null && rightTerm != null)
    {
      // First advance the iterator if necessary
      int cmp = order.compare(leftTerm, rightTerm);
      if (cmp < 0) 
      {
	// Left side is smaller.  Move the term into the new set and advance
	treeSet.add(leftTerm);
	leftTerm = left.hasNext() ? left.next() : null;
      }
      else if (cmp > 0)
      {
	// Right side is smaller.  Move the inverse of the term into the new set and advance
	treeSet.add(getModule().createTerm(rightTerm.getCoefficient().negate(), rightTerm.getMonomial()));
	rightTerm = right.hasNext() ? right.next() : null;
      }
      else
      {
	// These terms have equal monomials.  Move the difference of terms and advance both.
	// Catch the case where the terms cancel.
	if (false == leftTerm.getCoefficient().equals(rightTerm.getCoefficient()))
	{
	  treeSet.add(getModule().createTerm(leftTerm.getCoefficient().subtract(rightTerm.getCoefficient()), leftTerm.getMonomial()));
	}
	leftTerm = left.hasNext() ? left.next() : null;
	rightTerm = right.hasNext() ? right.next() : null;	    
      }
    }

    // Move what remains
    while (leftTerm != null)
    {
      treeSet.add(leftTerm);
      leftTerm = left.hasNext() ? left.next() : null;
    }

    while (rightTerm != null)
    {
      treeSet.add(getModule().createTerm(rightTerm.getCoefficient().negate(), rightTerm.getMonomial()));
      rightTerm = right.hasNext() ? right.next() : null;
    }

    return new FreePolynomialModuleElement<_Coeff,_Field>(getModule(), treeSet);
  }

  public boolean empty()
  {
    return mTerms.size() == 0;
  }

  /**
   * Compute the S-Pair of the polynomial with another
   */
  public SyzygyPair<Term<_Coeff,_Field>,FreePolynomialModuleElement<_Coeff,_Field>> sPair(FreePolynomialModuleElement<_Coeff,_Field> rhs)
  {
    ModuleMonomial lcmMonomial = getInitialTerm().getMonomial().lcm(rhs.getInitialTerm().getMonomial());
    if (lcmMonomial == null) return null;
    ModuleTerm<_Coeff,_Field> lcmTerm = getModule().createTerm(getModule().getBaseField().getIdentity(), lcmMonomial);
    Term<_Coeff,_Field> t1 = lcmTerm.divide(getInitialTerm());
    Term<_Coeff,_Field> t2 = lcmTerm.divide(rhs.getInitialTerm());
    FreePolynomialModuleElement<_Coeff,_Field> p1 = multiply(t1);
    FreePolynomialModuleElement<_Coeff,_Field> p2 = rhs.multiply(t2);
    return new 
      SyzygyPair<Term<_Coeff,_Field>,FreePolynomialModuleElement<_Coeff,_Field>>(this, t1, rhs, t2, 
										       p1.subtract(p2));
  }

  /*
   * Take the derivative of the polynomial of multi-order order
   */
  public FreePolynomialModuleElement<_Coeff,_Field> differentiate(int [] order)
  {
    ModuleTermSet<_Coeff,_Field> treeSet = new ModuleTermSet<_Coeff,_Field>(new ModuleTermOrder<_Coeff,_Field>(getModule().getOrder()));
    java.util.Iterator<ModuleTerm<_Coeff,_Field>> it = mTerms.iterator();
    while(it.hasNext())
    {
      ModuleTerm<_Coeff,_Field> t = it.next();
      t = t.differentiate(order);
      if (!t.isZero()) treeSet.add(t);
    }
    return new FreePolynomialModuleElement<_Coeff,_Field>(getModule(), treeSet);
  }

  public String toString()
  {
    java.util.Iterator<ModuleTerm<_Coeff,_Field>> it = mTerms.iterator();
    boolean init=false;
    StringBuffer buf=new StringBuffer();
    while(it.hasNext())
    {
      if(init) buf.append(" + ");
      buf.append(it.next().toString());
      init = true;
    }
    return buf.toString();
  }

  public boolean equals(Object obj)
  {
    if(!(obj instanceof FreePolynomialModuleElement)) return false;
    FreePolynomialModuleElement p = (FreePolynomialModuleElement)obj;
    if(!getModule().equals(p.getModule())) return false;
    java.util.Iterator<ModuleTerm<_Coeff,_Field>> lhs = mTerms.iterator();
    java.util.Iterator rhs = p.mTerms.iterator();

    while(lhs.hasNext() && rhs.hasNext())
    {
      if (!lhs.next().equals(rhs.next())) return false;
    }
    return !lhs.hasNext() && !rhs.hasNext();    
  }

  public boolean equals(FreePolynomialModuleElement<_Coeff,_Field> p)
  {
    if(!getModule().equals(p.getModule())) return false;
    java.util.Iterator<ModuleTerm<_Coeff,_Field>> lhs = mTerms.iterator();
    java.util.Iterator<ModuleTerm<_Coeff,_Field>> rhs = p.mTerms.iterator();

    while(lhs.hasNext() && rhs.hasNext())
    {
      if (!lhs.next().equals(rhs.next())) return false;
    }
    return !lhs.hasNext() && !rhs.hasNext();
  }

  public int hashCode()
  {
    java.util.Iterator<ModuleTerm<_Coeff,_Field>> it = mTerms.iterator();
    int hash=0;
    while(it.hasNext())
    {
      hash += it.next().hashCode();
    }
    return hash;
  }

  public int getDegree()
  {
    int totalDegree = -1;
    java.util.Iterator<ModuleTerm<_Coeff,_Field>> it = mTerms.iterator();
    while (it.hasNext())
    {
      ModuleTerm<_Coeff,_Field> t = it.next();
      int tmp;
      totalDegree = (tmp=t.getMonomial().getDegree())>totalDegree ? tmp : totalDegree;
    }
    return totalDegree;
  }

  public int getDegree(WeightVector w)
  {
    int totalDegree = 0;
    java.util.Iterator<ModuleTerm<_Coeff,_Field>> it = mTerms.iterator();
    while (it.hasNext())
    {
      ModuleTerm<_Coeff,_Field> t = it.next();
      int tmp;
      totalDegree = (tmp=t.getMonomial().getDegree(w))>totalDegree ? tmp : totalDegree;
    }
    return totalDegree;
  }

  public FreePolynomialModuleElement<_Coeff,_Field> getHomogenization(Symbol homogenizingParameter)
  {
    int totalDegree = getDegree();
    FreePolynomialModule<_Coeff,_Field> homogenizedRing = getModule().getHomogenization(homogenizingParameter);
    ModuleTermOrder<_Coeff,_Field> order = new ModuleTermOrder<_Coeff,_Field>(homogenizedRing.getOrder());
    ModuleTermSet<_Coeff,_Field> treeSet = new ModuleTermSet<_Coeff,_Field>(order);
    java.util.Iterator<ModuleTerm<_Coeff,_Field>> it = mTerms.iterator();
    while (it.hasNext())
    {
      ModuleTerm<_Coeff,_Field> t = it.next();
      int [] exp = new int [t.getMonomial().getNumExponents()+1];
      exp[0] = totalDegree - t.getMonomial().getDegree();
      for(int i=0; i<t.getMonomial().getNumExponents(); i++)
      {
	exp[i+1] = t.getMonomial().getExponent(i);
      }
      treeSet.add(homogenizedRing.createTerm(t.getCoefficient(), exp, t.getMonomial().getPosition()));
    }
    return new FreePolynomialModuleElement<_Coeff,_Field>(homogenizedRing, treeSet);
  }

  public FreePolynomialModuleElement<_Coeff,_Field> getDehomogenization()
  {
    FreePolynomialModule<_Coeff, _Field> dehomogenizedRing = getModule().getDehomogenization();
    ModuleTermOrder<_Coeff,_Field> order = new ModuleTermOrder<_Coeff,_Field>(dehomogenizedRing.getOrder());
    ModuleTermSet<_Coeff,_Field> treeSet = new ModuleTermSet<_Coeff,_Field>(order);
    java.util.Iterator<ModuleTerm<_Coeff,_Field>> it = mTerms.iterator();
    while (it.hasNext())
    {
      ModuleTerm<_Coeff,_Field> t = it.next();
      int [] exp = new int [t.getMonomial().getNumExponents()-1];
      for(int i=1; i<t.getMonomial().getNumExponents(); i++)
      {
	exp[i-1] = t.getMonomial().getExponent(i);
      }
      treeSet.add(dehomogenizedRing.createTerm(t.getCoefficient(), exp, t.getMonomial().getPosition()));
    }
    return new FreePolynomialModuleElement<_Coeff,_Field>(dehomogenizedRing, treeSet);
  }

  public java.util.Iterator<ModuleTerm<_Coeff,_Field>> iterator()
  {
    return mTerms.iterator();
  }

  public int size()
  {
    return mTerms.size();
  }

}
