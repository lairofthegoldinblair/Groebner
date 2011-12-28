package algebra.generic;

/**
 * The SyzygyPair represents the result of an spair computation.  There are two
 * module elements in the pair and each has an associated "coefficient"
 * (lcm(lt(x),lt(y))/lt(y))*x - (lcm(lt(x),lt(y))/lt(x))*y
 *
 * Syzygy pairs are computed relative to a term order on the module.
 * _M is class of module elements whose spair is represented.
 * _T represents the class of terms in the ring underlying the module.
 */
public class SyzygyPair<_T,_M>
{
  java.util.HashMap<_M, _T> mHash = new java.util.HashMap<_M, _T>();
  public _T getSyzygyCoefficient(_M elt)
  {
    return mHash.get(elt);
  }

  private _M mSpair;
  /**
   * The value of the spair.
   * @return the value (lcm(lt(x),lt(y))/lt(y))*x - (lcm(lt(x),lt(y))/lt(x))*y
   */
  public _M getSyzygyPair()
  {
    return mSpair;
  }

  public SyzygyPair(_M lhs, _T lhsCoeff, _M rhs, _T rhsCoeff, _M sPair)
  {
    mHash.put(lhs, lhsCoeff);
    mHash.put(rhs, rhsCoeff);
    mSpair = sPair;
  }
}
