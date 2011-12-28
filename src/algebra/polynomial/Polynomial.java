package algebra.polynomial;

import algebra.Symbol;
import algebra.SymbolList;
import algebra.WeightVector;

import algebra.generic.SyzygyPair;
import algebra.generic.GenericNormalForm;

import algebra.monomial.Monomial;

import algebra.term.Term;
import algebra.term.TermSet;
import algebra.term.TermOrder;

/**
 * A polynomial in a ring with a monomial order.  The polynomial may be thought of as a member of a commutative or non-commutative ring
 * as well as be thought of as a member of the free module in one generator over its ring.
 */
public class Polynomial<_Coeff extends algebra.generic.FieldElement<_Coeff>, _Field extends algebra.generic.Field<_Coeff>> implements 
algebra.generic.TermThing<_Coeff,Polynomial<_Coeff, _Field>, Term<_Coeff,_Field>>, 
algebra.generic.ModuleTermThing<_Coeff,Term<_Coeff,_Field>,Polynomial<_Coeff, _Field>,Term<_Coeff,_Field>,Polynomial<_Coeff, _Field>>, 
algebra.generic.CommutativeRingElement<Polynomial<_Coeff, _Field>>, 
algebra.generic.ModuleElement<Polynomial<_Coeff, _Field>,Polynomial<_Coeff, _Field>>, 
algebra.generic.LeftModuleElement<Polynomial<_Coeff, _Field>,Polynomial<_Coeff, _Field>>, 
algebra.generic.RightModuleElement<Polynomial<_Coeff, _Field>,Polynomial<_Coeff, _Field>>, 
algebra.generic.BiModuleElement<Polynomial<_Coeff, _Field>,Polynomial<_Coeff, _Field>,Polynomial<_Coeff, _Field>>
{
  private PolynomialRing<_Coeff, _Field> mRing;
  
  public PolynomialRing<_Coeff,_Field> getRing()
  {
    return mRing;
  }

  public PolynomialRing<_Coeff,_Field> getModule()
  {
    return mRing;
  }

  private PolynomialRing<_Coeff,_Field> getPolynomialRing()
  {
    return mRing;
  }

  private TermSet<_Coeff,_Field> mTerms;

  private Polynomial(PolynomialRing<_Coeff,_Field> ring, TermSet<_Coeff,_Field> terms)
  {
    mRing = ring;
    mTerms = terms;
  }

  private Polynomial(PolynomialRing<_Coeff,_Field> ring, Term<_Coeff,_Field> term)
  {
    mRing = ring;
    mTerms = new TermSet<_Coeff,_Field>(new TermOrder<_Coeff,_Field>(ring.getOrder()));
    mTerms.add(term);
  }
  
  public Polynomial(PolynomialRing<_Coeff,_Field> ring, java.util.Vector<Term<_Coeff,_Field>> terms)
  {
    mRing = ring;
    mTerms = new TermSet<_Coeff,_Field>(new TermOrder<_Coeff,_Field>(ring.getOrder()));
    for(int i=0; i<terms.size(); i++)
      {
	mTerms.add(terms.elementAt(i));
      }
  }

  public Polynomial(PolynomialRing<_Coeff,_Field> ring)
  {
    mRing = ring;
    mTerms = new TermSet<_Coeff,_Field>(new TermOrder<_Coeff,_Field>(ring.getOrder()));
  }

  // Change the polynomial order
//    public Polynomial(Ring ring, Polynomial p)
//    {
//      mRing = ring;
//      mTerms = new TermSet<_Coeff,_Field>(new TermOrder<_Coeff,_Field>(ring.getOrder()));
//      mTerms.addAll(p.mTerms());
//    }

  /**
   * Returns true if the polynomial p is a non-zero scalar multiple of this
   * polynomial.
   *
   */
  public boolean isScalarMultiple(Polynomial<_Coeff,_Field> p)
  {
    if(!getPolynomialRing().equals(p.getPolynomialRing())) throw new RuntimeException("Polynomial.isScalarMultiple has term arg in different ring than base polynomial");    

    if(p.mTerms.size() != mTerms.size()) return false;

    // The initial monomials must be the same.  If they are then
    // save the coefficients so that we can do further comparisons.
    if(!p.getInitialTerm().getMonomial().equals(getInitialTerm().getMonomial())) return false;
    _Coeff c1 = getInitialTerm().getCoefficient();
    _Coeff c2 = p.getInitialTerm().getCoefficient();
    java.util.Iterator<Term<_Coeff,_Field>> it1 = mTerms.iterator();
    java.util.Iterator<Term<_Coeff,_Field>> it2 = p.mTerms.iterator();
    while(it1.hasNext() && it2.hasNext())
      {
	Term<_Coeff,_Field> t1 = it1.next();
	Term<_Coeff,_Field> t2 = it2.next();
	if(!t1.getMonomial().equals(t2.getMonomial())) return false;
	if(!c1.multiply(t2.getCoefficient()).equals(c2.multiply(t1.getCoefficient()))) return false;
      }
    return !it1.hasNext() && !it2.hasNext(); 
  }

  public Polynomial<_Coeff,_Field> rightMultiply(Polynomial<_Coeff,_Field> p)
  {
    return multiply(p);
  }

  public Polynomial<_Coeff,_Field> rightMultiplyTerm(Term<_Coeff,_Field> rhs)
  {
    return multiply(rhs);
  }

  public Polynomial<_Coeff,_Field> leftMultiply(Polynomial<_Coeff,_Field> p)
  {
    return multiply(p);
  }

  public Polynomial<_Coeff,_Field> leftMultiplyTerm(Term<_Coeff,_Field> rhs)
  {
    return multiply(rhs);
  }

  public Polynomial<_Coeff,_Field> multiply(Term<_Coeff,_Field> rhs)
  {
    TermSet<_Coeff,_Field> treeSet = new TermSet<_Coeff,_Field>(new TermOrder<_Coeff,_Field>(getPolynomialRing().getOrder()));
    java.util.Iterator<Term<_Coeff,_Field>> terms = mTerms.iterator();
    while(terms.hasNext())
    {
      treeSet.add(terms.next().multiply(rhs));
      // Compiler BUG: the extra set of parens here causes a verification
      // failure when the class file is loaded!!!!!
//       treeSet.add((terms.next()).multiply(rhs));
    }
    return new Polynomial<_Coeff,_Field>(getPolynomialRing(), treeSet);
  }

  public Polynomial<_Coeff,_Field> multiply(Polynomial<_Coeff,_Field> rhs)
  {
    algebra.generic.GenericBuffer<_Coeff,Term<_Coeff,_Field>,Polynomial<_Coeff,_Field>,Term<_Coeff,_Field>,Polynomial<_Coeff,_Field>> buff = new algebra.generic.GenericBuffer<_Coeff,Term<_Coeff,_Field>,Polynomial<_Coeff,_Field>,Term<_Coeff,_Field>,Polynomial<_Coeff,_Field>>(getPolynomialRing().getZero());
    java.util.Iterator<Term<_Coeff,_Field>> terms = rhs.mTerms.iterator();
    while (terms.hasNext())
      {
	buff.add(multiply(terms.next()));
      }
    return buff.toElement();
  }
 
  public Polynomial<_Coeff,_Field> multiply(_Coeff coeff)
  {
    if(!getPolynomialRing().getBaseField().equals(coeff.getField())) throw new RuntimeException("Polynomial.multiply has coeff arg in different field than base polynomial");
    TermSet<_Coeff,_Field> treeSet = new TermSet<_Coeff,_Field>(new TermOrder<_Coeff,_Field>(getPolynomialRing().getOrder()));
    java.util.Iterator<Term<_Coeff,_Field>> terms = mTerms.iterator();
    while(terms.hasNext())
      {
	treeSet.add(terms.next().scalarMultiply(coeff));
      }
    return new Polynomial<_Coeff,_Field>(getPolynomialRing(), treeSet);    
  }

  public Polynomial<_Coeff,_Field> exp(int n)
  {
    if (n < 0) throw new RuntimeException("Cannot invert polynomials");
    if (n == 0) return getPolynomialRing().getIdentity();
    Polynomial<_Coeff,_Field> result = this;
    for(int i=1; i<n; i++) result = result.multiply(this);
    return result;
  }

  public Polynomial<_Coeff,_Field> divide(_Coeff coeff)
  {
    if(!getPolynomialRing().getBaseField().equals(coeff.getField())) throw new RuntimeException("Polynomial.divide has coeff arg in different field than base polynomial");
    TermSet<_Coeff,_Field> treeSet = new TermSet<_Coeff,_Field>(new TermOrder<_Coeff,_Field>(getPolynomialRing().getOrder()));
    java.util.Iterator<Term<_Coeff,_Field>> terms = mTerms.iterator();
    while(terms.hasNext())
    {
      treeSet.add(terms.next().scalarDivide(coeff));
    }
    Polynomial<_Coeff,_Field> ret = new Polynomial<_Coeff,_Field>(getPolynomialRing(), treeSet);    
    return ret;
  }

  public Polynomial<_Coeff,_Field> getMonic()
  {
    return divide(getInitialTerm().getCoefficient());
  }

  public Polynomial<_Coeff,_Field> negate()
  {
    return multiply(getPolynomialRing().getBaseField().getIdentity().negate());
  }

  public GenericNormalForm<_Coeff,Term<_Coeff,_Field>, Polynomial<_Coeff, _Field>, Term<_Coeff,_Field>, Polynomial<_Coeff, _Field>>
    getNormalForm(java.util.Collection<Polynomial<_Coeff,_Field>> list)
  {
    return new GenericNormalForm<_Coeff, Term<_Coeff,_Field>, Polynomial<_Coeff, _Field>, Term<_Coeff,_Field>, Polynomial<_Coeff, _Field>>(this, list);
  }

  /**
   * PolynomialBuffer methods
   */
  public Term<_Coeff,_Field> getInitialTerm()
  {
    return mTerms.last();
  }

  public Monomial getInitialMonomial()
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
    Term<_Coeff,_Field> t = mTerms.first();
    if(t.getMonomial().getDegree()>0)
    {
      return getPolynomialRing().getBaseField().getZero();
    }
    else
    {
      return t.getCoefficient();
    }
  }

  public Polynomial<_Coeff,_Field> add(Polynomial<_Coeff,_Field> p)
  {
    // Perform a merge of the two lists of terms.
    // When you get a match of two monomials in the terms,
    // add the coefficients.
    TermOrder<_Coeff,_Field> order = new TermOrder<_Coeff,_Field>(getPolynomialRing().getOrder());
    TermSet<_Coeff,_Field> treeSet = new TermSet<_Coeff,_Field>(order);
    java.util.Iterator<Term<_Coeff,_Field>> left = mTerms.iterator();
    java.util.Iterator<Term<_Coeff,_Field>> right = p.mTerms.iterator();
    Term<_Coeff,_Field> leftTerm=left.hasNext() ? left.next() : null;
    Term<_Coeff,_Field> rightTerm=right.hasNext() ? right.next() : null;

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
	    treeSet.add(getPolynomialRing().createTerm(coeff, leftTerm.getMonomial()));
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

    return new Polynomial<_Coeff,_Field>(getPolynomialRing(), treeSet);
  }

  public Polynomial<_Coeff,_Field> addTerm(Term<_Coeff,_Field> t)
  {
    return add(getPolynomialRing().createPolynomial(t));
  }

  public Polynomial<_Coeff,_Field> subtractTerm(Term<_Coeff,_Field> t)
  {
    return subtract(getPolynomialRing().createPolynomial(t));
  }

  public Polynomial<_Coeff,_Field> subtract(Polynomial<_Coeff,_Field> p)
  {
    // Perform a merge of the two lists of terms.
    // When you get a match of two monomials in the terms,
    // add the coefficients.
    TermOrder<_Coeff,_Field> order = new TermOrder<_Coeff,_Field>(getPolynomialRing().getOrder());
    TermSet<_Coeff,_Field> treeSet = new TermSet<_Coeff,_Field>(order);
    java.util.Iterator<Term<_Coeff,_Field>> left = mTerms.iterator();
    java.util.Iterator<Term<_Coeff,_Field>> right = p.mTerms.iterator();
    Term<_Coeff,_Field> leftTerm=left.hasNext() ? left.next() : null;
    Term<_Coeff,_Field> rightTerm=right.hasNext() ? right.next() : null;

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
	    treeSet.add(getPolynomialRing().createTerm(rightTerm.getCoefficient().negate(), rightTerm.getMonomial()));
	    rightTerm = right.hasNext() ? right.next() : null;
	  }
	else
	  {
	    // These terms have equal monomials.  Move the difference of terms and advance both.
	    // Catch the case where the terms cancel.
	    if (false == leftTerm.getCoefficient().equals(rightTerm.getCoefficient()))
	      {
		treeSet.add(getPolynomialRing().createTerm(leftTerm.getCoefficient().subtract(rightTerm.getCoefficient()), leftTerm.getMonomial()));
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
	treeSet.add(getPolynomialRing().createTerm(rightTerm.getCoefficient().negate(), rightTerm.getMonomial()));
	rightTerm = right.hasNext() ? right.next() : null;
      }

    return new Polynomial<_Coeff,_Field>(getPolynomialRing(), treeSet);
  }

  public boolean empty()
  {
    return mTerms.size() == 0;
  }

  /**
   * Compute the S-Pair of the polynomial with another
   */
  public SyzygyPair<Term<_Coeff,_Field>,Polynomial<_Coeff,_Field>> sPair(Polynomial<_Coeff,_Field> rhs)
  {
    Term<_Coeff,_Field> lcmTerm = getPolynomialRing().createTerm(getPolynomialRing().getBaseField().getIdentity(), getInitialTerm().getMonomial().lcm(rhs.getInitialTerm().getMonomial()));
    Term<_Coeff,_Field> t1 = lcmTerm.divide(getInitialTerm());
    Term<_Coeff,_Field> t2 = lcmTerm.divide(rhs.getInitialTerm());
    Polynomial<_Coeff,_Field> p1 = multiply(t1);
    Polynomial<_Coeff,_Field> p2 = rhs.multiply(t2);
    return new SyzygyPair<Term<_Coeff,_Field>,Polynomial<_Coeff,_Field>>(this, t1, rhs, t2, p1.subtract(p2));
  }

  /*
   * Take the derivative of the polynomial of multi-order order
   */
  public Polynomial<_Coeff,_Field> differentiate(int [] order)
  {
    TermSet<_Coeff,_Field> treeSet = new TermSet<_Coeff,_Field>(new TermOrder<_Coeff,_Field>(getPolynomialRing().getOrder()));
    java.util.Iterator<Term<_Coeff,_Field>> it = mTerms.iterator();
    while(it.hasNext())
      {
	Term<_Coeff,_Field> t = it.next();
	t = t.differentiate(order);
	if (!t.isZero()) treeSet.add(t);
      }
    return new Polynomial<_Coeff,_Field>(getPolynomialRing(), treeSet);
  }

  public String toString()
  {
    java.util.Iterator<Term<_Coeff,_Field>> it = mTerms.iterator();
    boolean init=false;
    StringBuffer buf=new StringBuffer();
    while(it.hasNext())
    {
      if(init) buf.append(" + ");
      buf.append(it.next().toString());
      init = true;
    }
    return init ? buf.toString() : "0";
  }

  public boolean equals(Object obj)
  {
    if(!(obj instanceof Polynomial)) return false;
    Polynomial p = (Polynomial)obj;
    if(!getPolynomialRing().equals(p.getPolynomialRing())) return false;
    java.util.Iterator<Term<_Coeff,_Field>> lhs = mTerms.iterator();
    java.util.Iterator rhs = p.mTerms.iterator();

    while(lhs.hasNext() && rhs.hasNext())
    {
      if (!lhs.next().equals(rhs.next())) return false;
    }
    return !lhs.hasNext() && !rhs.hasNext();    
  }

  public int hashCode()
  {
    java.util.Iterator<Term<_Coeff,_Field>> it = mTerms.iterator();
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
    java.util.Iterator<Term<_Coeff,_Field>> it = mTerms.iterator();
    while (it.hasNext())
      {
	Term<_Coeff,_Field> t = it.next();
	int tmp;
	totalDegree = (tmp=t.getMonomial().getDegree())>totalDegree ? tmp : totalDegree;
      }
    return totalDegree;
  }

  public int getDegree(WeightVector w)
  {
    int totalDegree = 0;
    java.util.Iterator<Term<_Coeff,_Field>> it = mTerms.iterator();
    while (it.hasNext())
      {
	Term<_Coeff,_Field> t = it.next();
	int tmp;
	totalDegree = (tmp=t.getMonomial().getDegree(w))>totalDegree ? tmp : totalDegree;
      }
    return totalDegree;
  }

  public Polynomial<_Coeff,_Field> getHomogenization(Symbol homogenizingParameter)
  {
    int totalDegree = getDegree();
    PolynomialRing<_Coeff,_Field> homogenizedRing = getPolynomialRing().getHomogenization(homogenizingParameter);
    TermOrder<_Coeff,_Field> order = new TermOrder<_Coeff,_Field>(homogenizedRing.getOrder());
    TermSet<_Coeff,_Field> treeSet = new TermSet<_Coeff,_Field>(order);
    java.util.Iterator<Term<_Coeff,_Field>> it = mTerms.iterator();
    while (it.hasNext())
      {
	Term<_Coeff,_Field> t = it.next();
	int [] exp = new int [t.getMonomial().getNumExponents()+1];
	exp[0] = totalDegree - t.getMonomial().getDegree();
	for(int i=0; i<t.getMonomial().getNumExponents(); i++)
	  {
	    exp[i+1] = t.getMonomial().getExponent(i);
	  }
	treeSet.add(homogenizedRing.createTerm(t.getCoefficient(), exp));
      }
    return new Polynomial<_Coeff,_Field>(homogenizedRing, treeSet);
  }

  public Polynomial<_Coeff,_Field> getDehomogenization()
  {
    PolynomialRing<_Coeff, _Field> dehomogenizedRing = getPolynomialRing().getDehomogenization();
    TermOrder<_Coeff,_Field> order = new TermOrder<_Coeff,_Field>(dehomogenizedRing.getOrder());
    TermSet<_Coeff,_Field> treeSet = new TermSet<_Coeff,_Field>(order);
    java.util.Iterator<Term<_Coeff,_Field>> it = mTerms.iterator();
    while (it.hasNext())
      {
	Term<_Coeff,_Field> t = it.next();
	int [] exp = new int [t.getMonomial().getNumExponents()-1];
	for(int i=1; i<t.getMonomial().getNumExponents(); i++)
	  {
	    exp[i-1] = t.getMonomial().getExponent(i);
	  }
	treeSet.add(dehomogenizedRing.createTerm(t.getCoefficient(), exp));
      }
    return new Polynomial<_Coeff,_Field>(dehomogenizedRing, treeSet);
  }

  public java.util.Iterator<Term<_Coeff,_Field>> iterator()
  {
    return mTerms.iterator();
  }

  public int size()
  {
    return mTerms.size();
  }

}
