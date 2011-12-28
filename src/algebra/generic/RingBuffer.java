package algebra.generic;

/**
 * Simple PolynomialBuffer implementation which simply delegates to
 * methods based on the sorted list implementation of Polynomial.  This
 * should over time grow into something more sophisticated such as a 
 * geobucket implementation (see Thomas Yan, "The Geobucket Data Structure
 * for Polynomials")
 */

public class RingBuffer<_Field, _Term extends Term<_Term, _Term, _Field>, _Ring extends TermThing<_Field, _Ring, _Term>>
{
  _Ring mPoly;

  public RingBuffer(_Ring p)
  {
    mPoly = p;
  }

  public _Term getInitialTerm()
  {
    return mPoly.getInitialTerm();
  }

  public void add(_Ring p)
  {
    mPoly = mPoly.add(p);
  }

  public void add(_Term t)
  {
    mPoly = mPoly.addTerm(t);
  }

  public void subtract(_Ring p)
  {
    mPoly = mPoly.subtract(p);
  }

  public void subtract(_Term t)
  {
    mPoly = mPoly.subtractTerm(t);
  }

  public boolean empty()
  {
    return mPoly.empty();
  }

  public _Ring toElement()
  {
    return mPoly;
  }

  public String toString()
  {
    return mPoly.toString();
  }
}
