package algebra.polynomial;

import algebra.Symbol;
import algebra.SymbolList;

import algebra.monomial.Monomial;
import algebra.monomial.MonomialOrder;
import algebra.monomial.MonomialOrderClass;

import algebra.term.Term;

/**
 * A ring of polynomials over a field together with a term ordering for elements in the ring.
 * A ring of polynomials is a commutative ring as well as a module over itself.
 */
public class PolynomialRing<_Coeff extends algebra.generic.FieldElement<_Coeff>, _Field extends algebra.generic.Field<_Coeff>> implements 
algebra.generic.CommutativeRing<Polynomial<_Coeff, _Field>>, 
algebra.generic.Module<Polynomial<_Coeff, _Field>,Polynomial<_Coeff, _Field>>, 
algebra.generic.BiModule<Polynomial<_Coeff, _Field>,Polynomial<_Coeff, _Field>,Polynomial<_Coeff, _Field>>, 
algebra.generic.LeftModule<Polynomial<_Coeff, _Field>,Polynomial<_Coeff, _Field>>, 
algebra.generic.RightModule<Polynomial<_Coeff, _Field>,Polynomial<_Coeff, _Field>>
{
  private _Field mField;

  public _Field getBaseField()
  {
    return mField;
  }

  private MonomialOrder mOrder;

  private SymbolTable mSymbolTable;
  protected SymbolTable getSymbolTable()
  {
    return mSymbolTable;
  }

  public SymbolList getSymbolList()
  {
    return mSymbolTable.getSymbolList();
  }

  public MonomialOrder getOrder()
  {
    return mOrder;
  }

  ////////////////////////////////////////////////////////////////////////
  // Ring and Module interface
  ////////////////////////////////////////////////////////////////////////

  public PolynomialRing<_Coeff,_Field> getRing()
  {
    return this;
  }

  public PolynomialRing<_Coeff,_Field> getLeftRing()
  {
    return this;
  }
  public PolynomialRing<_Coeff,_Field> getRightRing()
  {
    return this;
  }

  public Polynomial<_Coeff,_Field> add(Polynomial<_Coeff,_Field> x, Polynomial<_Coeff,_Field> y)
  {
    return x.add(y);
  }

  public Polynomial<_Coeff,_Field> subtract(Polynomial<_Coeff,_Field> x, Polynomial<_Coeff,_Field> y)
  {
    return x.subtract(y);
  }

  public Polynomial<_Coeff,_Field> negate(Polynomial<_Coeff,_Field> x)
  {
    return x.negate();
  }

  public Polynomial<_Coeff,_Field> getZero()
  {
    return createPolynomial(new java.util.Vector<Term<_Coeff,_Field>> ());
  }

  public Polynomial<_Coeff,_Field> getIdentity()
  {
    return getScalarElement(getBaseField().getIdentity());
  }

  public Polynomial<_Coeff,_Field> multiply(Polynomial<_Coeff,_Field> x, Polynomial<_Coeff,_Field> y)
  {
    return x.multiply(y);
  }
  
  public Polynomial<_Coeff,_Field> leftMultiply(Polynomial<_Coeff,_Field> x, Polynomial<_Coeff,_Field> y)
  {
    return x.multiply(y);
  }
  
  public Polynomial<_Coeff,_Field> rightMultiply(Polynomial<_Coeff,_Field> x, Polynomial<_Coeff,_Field> y)
  {
    return x.multiply(y);
  }
  
  public boolean equals(Polynomial<_Coeff,_Field> x, Polynomial<_Coeff,_Field> y)
  {
    return x.equals(y);
  }

  /**
   * Every ring has a natural homomorphism of the
   * integers into it.  These factory methods give that
   * homomorphism.
   */
  public Polynomial<_Coeff,_Field> create(java.math.BigInteger value)
  {
    return getScalarElement(getBaseField().create(value));
  }

  public Polynomial<_Coeff,_Field> create(int value)
  {
    return create(java.math.BigInteger.valueOf(value));
  }

  public Polynomial<_Coeff,_Field> create(long value)
  {
    return create(java.math.BigInteger.valueOf(value));
  }

  ////////////////////////////////////////////////////////////////////////
  // Custom factory methods 
  ////////////////////////////////////////////////////////////////////////

  /**
   * Create a polynomial in variables "x_i" with a graded reverse lexicographic monomial order.
   * @param baseField the base field of the polynomial ring
   * @param nVars the number of variables in the polynomial ring
   */
  public PolynomialRing(_Field baseField, int nVars)
  {
    mField = baseField;
    int [] defaultOrder = new int [nVars];
    for(int i=0; i<nVars; i++) defaultOrder[i] = i;
    try {
      MonomialOrderClass clazz = MonomialOrderClass.forName("GrevlexOrder");
      mOrder = clazz.create(defaultOrder);
    } catch (ClassNotFoundException ex) {
      ex.printStackTrace();
    }
    mSymbolTable = new SymbolTable(nVars);
  }

  /**
   * Create a polynomial in variables "x_i" with number of variables equal to the number of variables in the monomial order.
   */
  public PolynomialRing(_Field baseField, MonomialOrder order)
  {
    mField = baseField;
    mOrder = order;
    mSymbolTable = new SymbolTable(mOrder.getNumVars());
  }

  public PolynomialRing(_Field baseField, SymbolList vars)
  {
    // The variables in the variable list are treated as being in
    // decreasing order.  The ring will make sure that the symbols
    // are mapped to the correct integers in the terms of all polynomials
    // created in the ring.
    mField = baseField;
    mSymbolTable = new SymbolTable(vars);
    int [] varOrder = new int [vars.size()];
    for(int i =0; i<varOrder.length; i++) varOrder[i] = i;
    try {
      MonomialOrderClass clazz = MonomialOrderClass.forName("GrevlexOrder");
      mOrder = clazz.create(varOrder);
    } catch (ClassNotFoundException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Create a ring on the base field with a weighted monomial order.  The variables/generators of the 
   * ring are assumed to be in descending order with respect to the monomial order.
   * @param baseField the base field of the polynomial ring
   * @param vars the variables of the polynomial ring in descending monomial order
   * @param orderClass the type of monomial order to use on the ring
   */
  public PolynomialRing(_Field baseField, SymbolList vars, MonomialOrderClass orderClass)
  {
    // The variables in the variable list are treated as being in
    // decreasing order.  The ring will make sure that the symbols
    // are mapped to the correct integers in the terms of all polynomials
    // created in the ring.
    mField = baseField;
    mSymbolTable = new SymbolTable(vars);
    int [] varOrder = new int [vars.size()];
    for(int i =0; i<varOrder.length; i++) varOrder[i] = i;
    mOrder = orderClass.create(varOrder);
  }

  /**
   * Create a ring on the base field with a weighted monomial order.  The variables/generators of the 
   * ring are assumed to be in descending order with respect to the monomial order.
   * @param baseField the base field of the polynomial ring
   * @param weights monomial order weights for the variable generators of the ring
   * @param vars the variables of the polynomial ring in descending monomial order
   * @param orderClass the type of monomial order to use on the ring
   */
  public PolynomialRing(_Field baseField, int [] weights, SymbolList vars, MonomialOrderClass orderClass)
  {
    // The variables in the variable list are treated as being in
    // decreasing order.  The ring will make sure that the symbols
    // are mapped to the correct integers in the terms of all polynomials
    // created in the ring.
    mField = baseField;
    mSymbolTable = new SymbolTable(vars);
    int [] varOrder = new int [vars.size()];
    for(int i =0; i<varOrder.length; i++) varOrder[i] = i;
    mOrder = orderClass.create(weights, varOrder);
  }

  /**
   * For derived classes that have the responsibility for constructing with an order that
   * is consistent with the list of symbols.
   */
  protected PolynomialRing(_Field baseField, SymbolList vars, MonomialOrder order)
  {
    mField = baseField;
    mSymbolTable = new SymbolTable(vars);
    mOrder = order;
  }

  /**
   * Get a ring with the same symbols, monomial order but different
   * base field.
   */
  public PolynomialRing<_Coeff, _Field> baseChange(_Field baseField)
  {
    return new PolynomialRing<_Coeff, _Field>(baseField, getSymbolList(), getOrder());
  }

  /**
   * Get a ring with the same symbols, base field, but new order
   */
  public PolynomialRing<_Coeff, _Field> orderChange(MonomialOrderClass clazz)
  {
    return new PolynomialRing<_Coeff, _Field>(getBaseField(), getSymbolList(), clazz);
  }

  /**
   * The homogeneous ring has an additional symbol.  The order on the
   * homogeneous ring is degree refined by the order on R.
   */
  public PolynomialRing<_Coeff, _Field> getHomogenization(Symbol homogenizationVariable)
  {
    SymbolList sym = new SymbolList();
    sym.add(homogenizationVariable);
    for(int i=0; i<getSymbolTable().size(); i++) sym.add(getSymbolTable().getSymbol(i));
    return new PolynomialRing<_Coeff, _Field>(getBaseField(), sym, mOrder.getOrderClass());    
  }
  /**
   * Compute dehomogenization of the polynomial ring with respect to the first variable.
   */
  public PolynomialRing<_Coeff, _Field> getDehomogenization()
  {
    SymbolList sym = new SymbolList();
    for(int i=1; i<getSymbolTable().size(); i++) sym.add(getSymbolTable().getSymbol(i));
    return new PolynomialRing<_Coeff, _Field>(getBaseField(), sym, mOrder.getOrderClass());
  }

  /**
   * The number of variables in the polynomial ring.
   */
  public int getNumVars()
  {
    return getSymbolTable().size();
  }

  /**
   * Return symbol corresponding to a variable position in the ring.
   */
  public Symbol getSymbol(int i)
  {
    return getSymbolTable().getSymbol(i);
  }

  /**
   * Two rings are considered to be equal if they have the
   * same base field, the same symbols and the same monomial
   * ordering.
   *
   */
  public boolean equals(PolynomialRing<_Coeff, _Field> r)
  {
    return getBaseField().equals(r.getBaseField()) && getOrder().equals(r.getOrder());
  }

  public String toString()
  {
    StringBuffer buf = new StringBuffer();
    buf.append(getBaseField().toString());
    buf.append("[");
    for(int i=0; i<getNumVars(); i++)
      {
	if (i>0) buf.append(", ");
	buf.append(getSymbol(i).toString());
      }
    buf.append("; ");
    buf.append(getOrder().getOrderClass());
    buf.append("]");
    return buf.toString();
  }

  // Builder interfaces
  public Monomial createMonomial(int [] exponents)
  {
    if (exponents.length != getNumVars()) 
      {
	throw new RuntimeException("Tried to create monomial with incorrect number of exponents");
      }
    return new Monomial(exponents);
  }

  public Monomial createMonomial(java.util.HashMap<Symbol,Integer> symToExp)
  {
    // Rearrange the exponents
    int [] exponents = new int [getNumVars()];
    for (int i =0; i<getNumVars(); i++)
    {
      exponents[i] = 0;
    }
    // Iterate over the table, map the symbol to the position in the
    // array and set the exponent.
//     for(java.util.Enumeration<Symbol> e = symToExp.keys(); e.hasMoreElements(); )
//     {
//       Symbol s = e.nextElement();
//       int exp = (symToExp.get(s)).intValue();
//       exponents[getSymbolTable().getPosition(s)] = exp;
//     }

    java.util.Iterator<java.util.Map.Entry<Symbol,Integer>> it = symToExp.entrySet().iterator();
    while(it.hasNext())
    {
      java.util.Map.Entry<Symbol,Integer> e = it.next();
      exponents[getSymbolTable().getPosition(e.getKey())] = e.getValue().intValue();
    }

    return new Monomial(exponents);
  }

  public Term<_Coeff,_Field> createTerm(_Coeff coeff, int [] exponents)
  {
    if(!coeff.getField().equals(mField)) throw new RuntimeException("Coefficient of term not in base field of ring");

    return new Term<_Coeff,_Field>(coeff, new Monomial(exponents));
  }
  
  public Term<_Coeff,_Field> createTerm(_Coeff coeff, Monomial monomial)
  {
    if (!coeff.getField().equals(mField)) throw new RuntimeException("Coefficient of term not in base field of ring");
    if (getNumVars() != monomial.getNumExponents()) throw new algebra.DomainMismatchException();
    return new Term<_Coeff,_Field>(coeff, monomial);
  }

  /**
   * TODO:
   * HACK!!!!  Right now we have a WeylAlgebra inheriting from Ring which seems kind of
   * broken.  In particular, the getZero() and getIdentity() methods are returning polynomials.
   * They probably should be returning AlgebraElements and the WeylAlgebra and its relatives should
   * override to return DifferentialOperator or whatever is appropriate.  Before I figure this out,
   * I am putting in the new method which serves the purpose that getZero() probably should.
   */
  public Polynomial<_Coeff,_Field> getScalarElement(_Coeff n)
  {
    int [] exponents = new int [getNumVars()];
    for(int i=0; i<exponents.length; i++) exponents[i] = 0;
    java.util.Vector<Term<_Coeff,_Field>> terms = new java.util.Vector<Term<_Coeff,_Field>> ();
    terms.addElement(createTerm(n, exponents));
    return createPolynomial(terms);
  }

  public Polynomial<_Coeff,_Field> createPolynomial(java.util.Vector<Term<_Coeff,_Field>> terms)
  {
    // With all of this compile time checking, do I need to do this anymore?
    // Probably?
//     for(int i=0; i<terms.size(); i++)
//       {
// 	if(!terms.elementAt(i).getRing().equals(this)) throw new RuntimeException("Coefficient of term not in base field of ring");
//       }
    return new Polynomial<_Coeff,_Field>(this, terms);
  }

  public Polynomial<_Coeff,_Field> createPolynomial(Term<_Coeff,_Field> term)
  {
    java.util.Vector<Term<_Coeff,_Field>> terms = new java.util.Vector<Term<_Coeff,_Field>> ();
    terms.addElement(term);
    return createPolynomial(terms);
  }

  public Polynomial<_Coeff,_Field> createPolynomial(String str)
  {
    Polynomial<_Coeff,_Field> poly = null;
    try {
      algebra.parser.AlgebraLexer lexer = new algebra.parser.AlgebraLexer(new java.io.StringReader(str));
      algebra.parser.AlgebraParser parser = new algebra.parser.AlgebraParser(lexer);
      parser.element();
      algebra.parser.AlgebraBuilder builder = new algebra.parser.AlgebraBuilder();
      RingPolynomialBuilder<_Coeff,_Field> pbuild = new RingPolynomialBuilder<_Coeff,_Field>(this);
      builder.setPolynomialBuilder(pbuild);
      builder.element(parser.getAST());
      poly = pbuild.getPolynomial();
    } catch(Exception e) {
      e.printStackTrace();
    }
    return poly;
  }
}
