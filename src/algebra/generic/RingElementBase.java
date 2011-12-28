package algebra.generic;

/** 
 * This abstract implementation of RingElement simply maps the ring operations
 * to those of the corresponding ring class.  Note that a ring element
 * is also a module element (the ring being the free module on one generator over itself).
 */
public abstract class RingElementBase<R> implements RingElement<R>, LeftModuleElement<R, R>, RightModuleElement<R,R>, BiModuleElement<R, R, R>
{
  private RingBase<R> mRing;
  
  public Ring<R> getRing()
  {
    return mRing;
  }

  public RingBase<R> getModule()
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
    return getRing().multiply(y, getThis());
  }  

  public R rightMultiply(R y)
  {
    return getRing().multiply(getThis(), y);
  }  

  protected RingElementBase(RingBase<R> ring)
  {
    mRing = ring;
  }
}

