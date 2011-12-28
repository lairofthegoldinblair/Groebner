package algebra.generic;

import java.util.Collection;
import java.util.Iterator;

/** 
 * Using templates to get a normal form algorithm (division algorithm) that works
 * for polynomials, differential operators, homogenized differential operators
 * as well as elements of free modules of these puppies.
 * @param R the base ring of the module of which this is an element
 * @param M the module of which this is an element
 * @param MT the class of terms for module elements
 * @param RT the class of terms for ring elements
 */
public class GenericNormalForm<_Field, _Term extends Term<_Term, _Term, _Field>, _Ring extends TermThing<_Field,_Ring, _Term>, _ModuleTerm extends Term<_Term, _ModuleTerm, _Field>, _Module extends ModuleTermThing<_Field,_Term,_Ring,_ModuleTerm,_Module>> implements ModuleNormalForm<_Term,_Ring,_ModuleTerm,_Module>
{
  // This is essentially serving as a typedef.
  // I've become aware that this is considered an antipattern (for somewhat obvious reasons),
  // but there aren't good alternatives that work here.
  class ModuleBuffer extends GenericBuffer<_Field, _Term, _Ring, _ModuleTerm, _Module>
  {
    public ModuleBuffer(_Module p)
    {
      super(p);
    }
  }

  // This is essentially serving as a typedef.
  // I've become aware that this is considered an antipattern (for somewhat obvious reasons).
  class MyRingBuffer extends RingBuffer<_Field, _Term, _Ring>
  {
    public MyRingBuffer(_Ring p)
    {
      super(p);
    }
  }

  private _Module mRemainder;
  private java.util.Hashtable<_Module, MyRingBuffer> mHashFactor;

  // This is DivisionAlgorithm for computing the normal form of a polynomial with respect to
  // a set of other polynomials.  This implementation is abstracted so that it works with both
  // polynomials as well as several important non-commutative rings such as WeylAlgebras, their
  // associated graded rings and their homogenizations.
  //
  // This variant uses a iterator interface to the set of incoming polynomials
  private void initialize(_Module pp, Collection<_Module> list, java.io.PrintStream log)
  {
    ModuleBuffer p = new ModuleBuffer(pp);
    ModuleBuffer r = new ModuleBuffer(pp.getModule().getZero());
    // Create a collection of buffers for divisors
    mHashFactor = new java.util.Hashtable<_Module, MyRingBuffer>();
    Iterator<_Module> init=list.iterator();
    while(init.hasNext())
    {
      mHashFactor.put(init.next(), new MyRingBuffer(pp.getModule().getLeftRing().getZero()));
    }
    
    while(false == p.empty())
      {
	_ModuleTerm lt = p.getInitialTerm();
	if (null != log) 
	  {
	    log.println("Reducing leading term: '" + lt.toString() + "' of element: '" + p.toString() + "'");
	  }
	Iterator<_Module> it = list.iterator();
	boolean putInRemainder = true;
	while(it.hasNext())
	  {
	    _Term d=null;
	    _Module elmt = it.next();
	    if(null != (d=lt.divide(elmt.getInitialTerm())))
	      {
		// A leading term divides the leading term of p.
		// Subtract off the corresponding polynomial from p
		mHashFactor.get(elmt).add(d);
		p.subtract(elmt.leftMultiplyTerm(d));	
		putInRemainder = false;
		break;
	      }
	  }
	// No leading term of a polynomial divides the leading
	// term of p, so put the leading term in the remainder and
	// move on
	if (putInRemainder)
	  {
	    r.add(lt);
	    p.subtract(lt);
	    if (null != log) 
	      {
		log.println("Could not reduce leading term: '" + lt.toString() + "' moving to remainder");
	      }
	  }
      }

    mRemainder = r.toElement();
  }

  /**
   * Calculate the normal form of an element with respect to a collection of elements.
   * pp = getFactor(list[0])*list[0] + ... + getFactor(list[n-1])*list[n-1] + getRemainer()
   */
  public GenericNormalForm(_Module pp, Collection<_Module> list)
  {
    initialize(pp, list, null);
  }

  /**
   * Calculate the normal form of an element with respect to a collection of elements.
   * pp = getFactor(list[0])*list[0] + ... + getFactor(list[n-1])*list[n-1] + getRemainer()
   */
  public GenericNormalForm(_Module pp, Collection<_Module> list, java.io.PrintStream log)
  {
    initialize(pp, list, log);
  }

  /**
   * The remainder of division of element by a list of elements.
   */
  public _Module getRemainder()
  {
    return mRemainder;
  }

  /**
   * The coefficient of dividend in the division of an element by a list of elements.
   */
  public _Ring getFactor(_Module dividend)
  {
    return mHashFactor.get(dividend).toElement();
  }

  public int getNumFactors()
  {
    return mHashFactor.size();
  }

  public String toString()
  {
    StringBuffer buf = new StringBuffer();
    buf.append("remainder: " + getRemainder().toString());
    for(java.util.Enumeration<_Module> it = mHashFactor.keys(); it.hasMoreElements(); )
    {
      _Module r = it.nextElement();
      buf.append("\tquotient of dividend " + r + " : " + mHashFactor.get(r) + "\n");      
    }
    return buf.toString();
  }
}
