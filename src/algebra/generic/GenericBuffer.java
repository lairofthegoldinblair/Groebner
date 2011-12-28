package algebra.generic;

/**
 * Buffer class for efficiently building polynomials (analogous to StringBuffer).
 * This is a simple PolynomialBuffer implementation which simply delegates to
 * methods based on the sorted list implementation of Polynomial (sorted by term order), so
 * is an abstraction waiting for the implementation that justifies its existence.  This
 * should over time grow into something more sophisticated such as a 
 * geobucket implementation (see Thomas Yan, "The Geobucket Data Structure
 * for Polynomials").  
 */

public class GenericBuffer<_Field, _Term extends Term<_Term, _Term, _Field>, _Ring extends TermThing<_Field,_Ring, _Term>, _ModuleTerm extends Term<_Term, _ModuleTerm, _Field>, _Module extends ModuleTermThing<_Field,_Term,_Ring,_ModuleTerm,_Module>>
{
  _Module mPoly;

  public GenericBuffer(_Module p)
  {
    mPoly = p;
  }

  public _ModuleTerm getInitialTerm()
  {
    return mPoly.getInitialTerm();
  }

  public void add(_Module p)
  {
    mPoly = mPoly.add(p);
  }

  public void add(_ModuleTerm t)
  {
    mPoly = mPoly.addTerm(t);
  }

  public void subtract(_Module p)
  {
    mPoly = mPoly.subtract(p);
  }

  public void subtract(_ModuleTerm t)
  {
    mPoly = mPoly.subtractTerm(t);
  }

  public boolean empty()
  {
    return mPoly.empty();
  }

  public _Module toElement()
  {
    return mPoly;
  }

  public String toString()
  {
    return mPoly.toString();
  }
}
