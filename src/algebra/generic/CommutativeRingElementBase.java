package algebra.generic;

/** 
 * This implementation of RingElement simply maps the ring operations
 * to those of the corresponding ring class.  Note that a ring element
 * is also a module element.
 */
public abstract class CommutativeRingElementBase<R> implements CommutativeRingElement<R>, LeftModuleElement<R, R>, RightModuleElement<R,R>, BiModuleElement<R, R, R>, ModuleElement<R,R>
{
  private CommutativeRingBase<R> mRing;
  
  public Ring<R> getRing()
  {
    return mRing;
  }

  public CommutativeRing<R> getCommutativeRing()
  {
    return mRing;
  }

  public CommutativeRingBase<R> getModule()
  {
    return mRing;
  }

  abstract protected R getThis();

  abstract public boolean empty();

  public R negate()
  {
    return getRing().negate(getThis());
  }

  public R add(R y)
  {
    return getRing().add(getThis(), y);    
  }

  public R subtract(R y)
  {
    return getRing().subtract(getThis(), y);
  }

  public R leftMultiply(R y)
  {
    return multiply(y);
  }  

  public R rightMultiply(R y)
  {
    return multiply(y);
  }  

  public R multiply(R y)
  {
    return getRing().multiply(getThis(), y);
  }

  protected CommutativeRingElementBase(CommutativeRingBase<R> ring)
  {
    mRing = ring;
  }
}

