package algebra.generic;

/**
 * Buchberger's algorithm for the computation of Groebner bases.  This is a very simple realization of the
 * algorithm at this point, the most interesting characteristic being the typing of the algorithm via
 * generics in such a way that the algorithm may be used to compute standard bases in both commutative and
 * non-commutative rings as well as in rings and modules on top of such rings.
 * 
 * This implementation current calculates reduced Groebner basis: those which satisfy the constraints that
 * 1) every element of the basis has a leading coefficient that is unity 
 * 2) no term in any element of the basis is the leading term of different element.
 */
public class BuchbergerAlgorithm<_Field, _Term extends Term<_Term, _Term, _Field>, _Ring extends TermThing<_Field,_Ring, _Term>, _ModuleTerm extends Term<_Term, _ModuleTerm, _Field>, _Module extends ModuleTermThing<_Field,_Term,_Ring,_ModuleTerm,_Module>>
{
  java.io.PrintStream mLog = null;
  java.util.HashMap<_Module,java.util.HashMap<_Module,_Ring>> mBasisChange;
  
  public java.util.HashMap<_Module,java.util.HashMap<_Module,_Ring>> getBasisChange()
  {
    return mBasisChange;
  }

  private void checkBasisChange(java.util.Collection<_Module> v)
  {
    java.util.Iterator<_Module> it = v.iterator();
    while(it.hasNext())
    {
      java.util.HashMap<_Module,_Ring> map = getBasisChange().get(it.next());
      if (map == null) throw new RuntimeException("Internal Error");
      if (map.size() != mInput.size()) throw new RuntimeException("Internal Error");
      java.util.Iterator<_Module> inner = mInput.iterator();
      while(inner.hasNext())
      {
	_Ring r = map.get(inner.next());
	if (r == null) throw new RuntimeException("Internal Error");
      }
    }
  }

  /**
   * This method is a coroutine of sorts.  This method is really the core
   * of Buchberger's algorithm.  Which takes a list of module elements
   * and then calculates all spairs of the elements, takes the normal form
   * with respect to the list of module elements and adds the remainder if non-zero (thus
   * achieving the spair criterion that guarantees we have a Groebner basis).
   * The coroutine-ness is that this method yields/returns after each spair is added.  The state
   * of the method that needs to be saved at that point is a pair of integers (representing the
   * two loop counters necessary to iterate over all the pairs in the list) and the list itself.
   * The use of the coroutine is a just a tricky way of dealing with fact that the list of elements
   * is being modified in the loop body itself so the endpoint of the iterations need to be reevaluated
   * everytime a new element is added.  The yield and loop has the effect of getting those terminiating
   * conditions updated.  Could have done this with a local variable as well but that probably would have
   * been hard to understand too.
   */
  private boolean addSyzygy(java.util.Vector<_Module> v, int [] start)
  {
    // Debug check: 
    checkBasisChange(v);

    // Check to see if any syzygy doesn't reduce to zero
    for(int i=start[0]; i<v.size(); i++)
    {
      // We will only process pairs v[i] and v[j] with i<j (because of
      // symmetry of spairs).
      // If i=start[0], then pick up where we had left off, otherwise
      // we are off checking an i that hasn't be touched yet.  In that case
      // we process from i+1.
      for(int j=(i==start[0]?start[1]:i+1); j<v.size(); j++)
      {
	SyzygyPair<_Term,_Module> syz = v.get(i).sPair(v.get(j));
	if (mLog != null) {
	  mLog.println("Testing sPair(" + v.get(i) + ", " + v.get(j) + ") = " + syz.getSyzygyPair());
	}
	if (syz == null) continue;

	ModuleNormalForm<_Term,_Ring,_ModuleTerm,_Module> nf = syz.getSyzygyPair().getNormalForm(v);
	if(!nf.getRemainder().empty())
	{
	  // Found a syzygy which doesn't reduce.  
	  if (mLog != null) {
	    mLog.println("Syzygy doesn't reduce, adding remainder (" + nf.getRemainder() + ") to basis");
	  }
	  // Add the expression of the remainder in terms of the original
	  // elements.
	  java.util.HashMap<_Module,_Ring> change = new java.util.HashMap<_Module,_Ring>();
	  java.util.Iterator<_Module> outer = mInput.iterator();
	  while(outer.hasNext()) 
	  {
	    _Module m = outer.next();
	    _Ring elt = mRing.getZero();
	    elt = elt.add(mBasisChange.get(v.get(i)).get(m).leftMultiplyTerm(syz.getSyzygyCoefficient(v.get(i))));
	    elt = elt.subtract(mBasisChange.get(v.get(j)).get(m).leftMultiplyTerm(syz.getSyzygyCoefficient(v.get(j))));
	    java.util.Iterator<_Module> inner = v.iterator();
	    while(inner.hasNext())
	    {
	      _Module n = inner.next();
	      elt = elt.subtract(mBasisChange.get(n).get(m).leftMultiply(nf.getFactor(n)));
	    }
	    change.put(m, elt);
	  }
	  mBasisChange.put(nf.getRemainder(), change);
	  // Add its remainder to the list.
	  v.add(nf.getRemainder());
	  // Record where we left off and return.
	  start[0]=i;
	  // TODO: Shouldn't this be j+1 ????  We just got through with j...
	  start[1]=j+1;
	  return true;
	}
      }
    }
    // We made it through the loop.  All syzygies reduce
    // to zero.  So spair criterion guarantees a Groebner basis
    return false;
  }

  /**
   * Transform a Groebner basis into one that is minimal.  That is to say satisfies the constraints
   * that
   * 1) All leading coefficients are unity.
   * 2) No leading term of an element is the leading term of another element.
   *
   * The algorithm proceeds by a nested loop that checks for each element whether the leading term
   * of another entry and marks the element as removed if it is.
   */
  private java.util.Vector<_Module> minimizeGroebnerBasis(java.util.Vector<_Module> gb)
  {
    // Debug check: 
    checkBasisChange(gb);

    // We simply remove any element whose leading term is already a leading
    // term of an element.
    if (mLog != null) {
      mLog.println("Original groebner basis size = " + gb.size());
    }
    
    boolean [] removed = new boolean [gb.size()];
    for(int i=0; i<removed.length; i++)
    {
      removed[i] = false;
    }

    java.util.Vector<_Module> mgb = new java.util.Vector<_Module>();
    for(int i=0; i<gb.size(); i++)
    {
      int j;
      for(j=0; j<gb.size(); j++)
      {
	// Only check for divisibility by elements that aren't already removed.
	if(removed[j]) continue;

	if (mLog != null) {
	  mLog.println(gb.get(i).getInitialTerm().toString() + "/" + gb.get(j).getInitialTerm().toString());
	}

	if(i!=j && null != gb.get(i).getInitialTerm().divide(gb.get(j).getInitialTerm())) 
	{
	  removed[i] = true;
	  // Remove the element from the basis change matrix
	  mBasisChange.remove(gb.get(i));
	  if(mLog != null) {
	    mLog.println("Divisibility detected; omitting basis element");
	  }
	  break;
	}
      }
      if(j==gb.size()) mgb.add(gb.get(i));
    }
    if (mLog != null) {
      mLog.println("Minimal groebner basis size = " + mgb.size());
    }

    return mgb;
  }

  private java.util.List<_Module> setDifference(java.util.Collection<_Module> list, _Module p)
  {
    java.util.List<_Module> diff = new java.util.ArrayList<_Module>();
    java.util.Iterator<_Module> it = list.iterator();
    while(it.hasNext())
    {
      _Module m = it.next();
      if (!m.equals(p)) diff.add(m);
    }
    return diff;
  }

  /** 
   * Reduce a minimal Groebner basis.
   */
  private java.util.List<_Module> reduceGroebnerBasis(java.util.Collection<_Module> gb)
  {
    // Debug check: 
    checkBasisChange(gb);

    // Create new basis change matrix
    java.util.HashMap<_Module,java.util.HashMap<_Module,_Ring>> newBasisChange
      = new java.util.HashMap<_Module,java.util.HashMap<_Module,_Ring>>();

    // Go through each element and replace it with its reduction mod the
    // remaining elements
    java.util.List<_Module> reduced = new java.util.ArrayList<_Module>(gb);
    for(int j=0; j<reduced.size(); j++)
    {
      _Module m = reduced.get(j);
      java.util.Collection<_Module> diff = setDifference(reduced, m);
      ModuleNormalForm<_Term,_Ring,_ModuleTerm,_Module> nf = m.getNormalForm(diff);
      // Adjust the basis change matrix.  Take care to normalize appropriately
      // since we will be normalizing our element to be monic.
      java.util.HashMap<_Module,_Ring> change = new java.util.HashMap<_Module,_Ring>();
      java.util.Iterator<_Module> outer = mInput.iterator();
      _Field lc = nf.getRemainder().getInitialTerm().getCoefficient();
      while(outer.hasNext())
      {
	_Module o = outer.next();
	_Ring elt = mBasisChange.get(m).get(o);
	java.util.Iterator<_Module> inner = diff.iterator();
	while(inner.hasNext())
	{
	  _Module i = inner.next();
	  elt = elt.subtract(mBasisChange.get(i).get(o).leftMultiply(nf.getFactor(i)));
	}
	change.put(o, elt.divide(lc));
      }
      _Module monic = nf.getRemainder().divide(lc);
      mBasisChange.put(monic, change);
      reduced.set(j, monic);
    }
    return reduced;
  }

  private java.util.List<_Module> mList;

  /**
   * The calculated Groebner basis.
   * 
   * @return the list of module elements that the Groebner basis comprises.
   */
  public java.util.List<_Module> getStandardBasis()
  {
    return mList;
  }

  Ring<_Ring> mRing;

  java.util.Collection<_Module> mInput;

  /**
   * Calculate a reduced Groebner basis for the collection of module elements by using Buchberger's algorithm.
   */
  public BuchbergerAlgorithm(java.util.Collection<_Module> list)
  {
    mRing = list.iterator().next().getModule().getLeftRing();
    mInput = list;

    //mLog = System.out;
    // This is a really naive implmentation of Buchberger algorithm.
    // We simply keep comparing spairs until all of them reduce to
    // zero
    java.util.Vector<_Module> v = new java.util.Vector<_Module>(list);
    // This guy tracks the expression of the groebner basis as linear
    // combination of the original elements.  Initialize to the identity.
    mBasisChange = new java.util.HashMap<_Module,java.util.HashMap<_Module,_Ring>>();
    java.util.Iterator<_Module> outer = list.iterator();
    while(outer.hasNext())
    {
      java.util.HashMap<_Module,_Ring> coeff = new java.util.HashMap<_Module,_Ring>();
      _Module outerElt = outer.next();
      mBasisChange.put(outerElt, coeff);
      java.util.Iterator<_Module> inner = list.iterator();
      while(inner.hasNext())
      {
	_Module innerElt = inner.next();
	coeff.put(innerElt, innerElt.equals(outerElt) ? mRing.getIdentity() : mRing.getZero());
      }
    }

    boolean added=false;
    int [] start = new int [] {0,1};
    do
    {
      added = addSyzygy(v, start);
      if (start[1] <= start[0]) throw new RuntimeException("Internal Error in BuchbergerAlgorithm.BuchbergerAlgorithm");
    }
    while(added);

    mList = reduceGroebnerBasis(minimizeGroebnerBasis(v));
  }
}
