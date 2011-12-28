package algebra.polynomial.module;

import algebra.Symbol;
import algebra.SymbolList;

import algebra.monomial.Monomial;
import algebra.monomial.ModuleMonomial;
import algebra.monomial.ModuleMonomialOrder;
import algebra.monomial.MonomialOrderClass;

import algebra.term.Term;
import algebra.term.ModuleTerm;

import algebra.polynomial.PolynomialRing;
import algebra.polynomial.Polynomial;

/**
 * A free module over a polynomial ring.
 */
public class FreePolynomialModule<_Coeff extends algebra.generic.FieldElement<_Coeff>, _Field extends algebra.generic.Field<_Coeff>> implements 
algebra.generic.Module<Polynomial<_Coeff, _Field>,FreePolynomialModuleElement<_Coeff, _Field>>, 
algebra.generic.BiModule<Polynomial<_Coeff, _Field>,Polynomial<_Coeff, _Field>,FreePolynomialModuleElement<_Coeff, _Field>>, 
algebra.generic.LeftModule<Polynomial<_Coeff, _Field>,FreePolynomialModuleElement<_Coeff, _Field>>, 
algebra.generic.RightModule<Polynomial<_Coeff, _Field>,FreePolynomialModuleElement<_Coeff, _Field>>
{
  public _Field getBaseField()
  {
    return getRing().getBaseField();
  }

  private ModuleMonomialOrder mOrder;
  private int mRank;

  public int getRank()
  {
    return mRank;
  }

  public ModuleMonomialOrder getOrder()
  {
    return mOrder;
  }

  ////////////////////////////////////////////////////////////////////////
  // Ring and Module interface
  ////////////////////////////////////////////////////////////////////////

  private PolynomialRing<_Coeff,_Field> mRing;

  public PolynomialRing<_Coeff,_Field> getRing()
  {
    return mRing;
  }

  public PolynomialRing<_Coeff,_Field> getLeftRing()
  {
    return getRing();
  }
  public PolynomialRing<_Coeff,_Field> getRightRing()
  {
    return getRing();
  }

  public FreePolynomialModuleElement<_Coeff,_Field> add(FreePolynomialModuleElement<_Coeff,_Field> x, FreePolynomialModuleElement<_Coeff,_Field> y)
  {
    return x.add(y);
  }

  public FreePolynomialModuleElement<_Coeff,_Field> subtract(FreePolynomialModuleElement<_Coeff,_Field> x, FreePolynomialModuleElement<_Coeff,_Field> y)
  {
    return x.subtract(y);
  }

  public FreePolynomialModuleElement<_Coeff,_Field> negate(FreePolynomialModuleElement<_Coeff,_Field> x)
  {
    return x.negate();
  }

  public FreePolynomialModuleElement<_Coeff,_Field> getZero()
  {
    return create(new java.util.Vector<ModuleTerm<_Coeff,_Field>> ());
  }

  public FreePolynomialModuleElement<_Coeff,_Field> multiply(Polynomial<_Coeff,_Field> x, FreePolynomialModuleElement<_Coeff,_Field> y)
  {
    return y.multiply(x);
  }
  
  public FreePolynomialModuleElement<_Coeff,_Field> leftMultiply(Polynomial<_Coeff,_Field> x, FreePolynomialModuleElement<_Coeff,_Field> y)
  {
    return multiply(x,y);
  }
  
  public FreePolynomialModuleElement<_Coeff,_Field> rightMultiply(Polynomial<_Coeff,_Field> x, FreePolynomialModuleElement<_Coeff,_Field> y)
  {
    return multiply(x,y);
  }
  
  public boolean equals(FreePolynomialModuleElement<_Coeff,_Field> x, FreePolynomialModuleElement<_Coeff,_Field> y)
  {
    return x.equals(y);
  }

  ////////////////////////////////////////////////////////////////////////
  // Custom factory methods 
  ////////////////////////////////////////////////////////////////////////
  public FreePolynomialModule(PolynomialRing<_Coeff,_Field> ring, int rank)
  {
    mRing = ring;
    mOrder = ring.getOrder().getOrderClass().getPositionOverTermOrder(ring.getOrder());
    mRank = rank;
  }

  /**
   * Get a ring with the same symbols, monomial order but different
   * base field.
   */
  public FreePolynomialModule<_Coeff, _Field> baseChange(_Field baseField)
  {
    return new FreePolynomialModule<_Coeff, _Field>(getRing().baseChange(baseField), getRank());
  }

  /**
   * Get a ring with the same symbols, base field, but new order
   */
  public FreePolynomialModule<_Coeff, _Field> orderChange(MonomialOrderClass clazz)
  {
    return new FreePolynomialModule<_Coeff, _Field>(getRing().orderChange(clazz), getRank());
  }

  /**
   * The homogeneous ring has an additional symbol.  The order on the
   * homogeneous ring is degree refined by the order on R.
   */
  public FreePolynomialModule<_Coeff, _Field> getHomogenization(Symbol homogenizationVariable)
  {
    return new FreePolynomialModule<_Coeff, _Field>(getRing().getHomogenization(homogenizationVariable), getRank());
  }

  public FreePolynomialModule<_Coeff, _Field> getDehomogenization()
  {
    return new FreePolynomialModule<_Coeff, _Field>(getRing().getDehomogenization(), getRank());
  }

  /**
   * Two rings are considered to be equal if they have the
   * same base field, the same symbols and the same monomial
   * ordering.
   *
   */
  public boolean equals(Object obj)
  {
    if (!(obj instanceof FreePolynomialModule)) return false;
    FreePolynomialModule m = (FreePolynomialModule)obj;
    return getRank() == m.getRank() && getRing().equals(m.getRing());
  }

  public String toString()
  {
    return getRing().toString() + "^" + getRank();
  }

  // Builder interfaces
  public ModuleMonomial createMonomial(int [] exponents, int position)
  {
    if (exponents.length != getRing().getNumVars()) 
    {
      throw new RuntimeException("Tried to create monomial with incorrect number of exponents");
    }
    return new ModuleMonomial(exponents, position);
  }

  public ModuleMonomial createMonomial(java.util.HashMap<Symbol,Integer> symToExp, int position)
  {
    return new ModuleMonomial(getRing().createMonomial(symToExp), position);
  }

  public ModuleTerm<_Coeff,_Field> createTerm(_Coeff coeff, int [] exponents, int position)
  {
    if(!coeff.getField().equals(getBaseField())) throw new RuntimeException("Coefficient of term not in base field of ring");

    return new ModuleTerm<_Coeff,_Field>(coeff, new ModuleMonomial(exponents, position));
  }
  
  public ModuleTerm<_Coeff,_Field> createTerm(_Coeff coeff, ModuleMonomial monomial)
  {
    if (!coeff.getField().equals(getBaseField())) throw new RuntimeException("Coefficient of term not in base field of ring");
    if (getRing().getNumVars() != monomial.getNumExponents()) throw new algebra.DomainMismatchException();
    if (monomial.getPosition() >= getRank()) throw new algebra.DomainMismatchException();
    return new ModuleTerm<_Coeff,_Field>(coeff, monomial);
  }

  public FreePolynomialModuleElement<_Coeff,_Field> create(java.util.Collection<ModuleTerm<_Coeff,_Field>> terms)
  {
    // With all of this compile time checking, do I need to do this anymore?
    // Probably?
//     for(int i=0; i<terms.size(); i++)
//       {
// 	if(!terms.elementAt(i).getRing().equals(this)) throw new RuntimeException("Coefficient of term not in base field of ring");
//       }
    return new FreePolynomialModuleElement<_Coeff,_Field>(this, terms);
  }

  public FreePolynomialModuleElement<_Coeff,_Field> create(ModuleTerm<_Coeff,_Field> term)
  {
    java.util.Vector<ModuleTerm<_Coeff,_Field>> terms = new java.util.Vector<ModuleTerm<_Coeff,_Field>> ();
    terms.addElement(term);
    return create(terms);
  }

  public FreePolynomialModuleElement<_Coeff,_Field> create(java.util.List<Polynomial<_Coeff,_Field>> polys)
  {
    java.util.Vector<ModuleTerm<_Coeff,_Field>> terms = new java.util.Vector<ModuleTerm<_Coeff,_Field>>();
    // Convert the terms of the polynomials into module terms
    if (getRank() != polys.size()) throw new algebra.DomainMismatchException();
    for(int i=0; i<polys.size(); i++)
    {
      java.util.Iterator<Term<_Coeff,_Field>> it = polys.get(i).iterator();
      while(it.hasNext())
      {
	terms.add(new ModuleTerm<_Coeff,_Field>(it.next(), i));
      }
    }
    return new FreePolynomialModuleElement<_Coeff,_Field>(this, terms);
  }

  public FreePolynomialModuleElement<_Coeff,_Field> create(String str)
  {
    FreePolynomialModuleElement<_Coeff,_Field> poly = null;
    try {
      algebra.parser.AlgebraLexer lexer = new algebra.parser.AlgebraLexer(new java.io.StringReader(str));
      algebra.parser.AlgebraParser parser = new algebra.parser.AlgebraParser(lexer);
      parser.element();
      algebra.parser.AlgebraBuilder builder = new algebra.parser.AlgebraBuilder();
      ModuleElementBuilder<_Coeff,_Field> pbuild = new ModuleElementBuilder<_Coeff,_Field>(this);
      builder.setPolynomialBuilder(pbuild);
      builder.element(parser.getAST());
      poly = pbuild.getModuleElement();
    } catch(Exception e) {
      e.printStackTrace();
    }
    return poly;
  }
}
