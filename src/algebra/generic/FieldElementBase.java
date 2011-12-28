package algebra.generic;

public abstract class FieldElementBase<R> implements FieldElement<R>, LeftModuleElement<R, R>, RightModuleElement<R,R>, BiModuleElement<R, R, R>, ModuleElement<R,R>
{
  private FieldBase<R> mField;
  
  public Ring<R> getRing()
  {
    return mField;
  }

  public Field<R> getField()
  {
    return mField;
  }

  public FieldBase<R> getModule()
  {
    return mField;
  }

  abstract protected R getThis();

  abstract public boolean empty();

  public R negate()
  {
    return getField().negate(getThis());
  }

  public R add(R y)
  {
    return getField().add(getThis(), y);    
  }

  public R subtract(R y)
  {
    return getField().subtract(getThis(), y);
  }

  public R leftMultiply(R y)
  {
    return getField().multiply(getThis(), y);
  }  

  public R rightMultiply(R y)
  {
    return getField().multiply(getThis(), y);
  }  

  public R multiply(R y)
  {
    return getField().multiply(getThis(), y);
  }

  public R divide(R y)
  {
    return getField().divide(getThis(), y);
  }

  public R invert()
  {
    return getField().invert(getThis());
  }

  protected FieldElementBase(FieldBase<R> field)
  {
    mField = field;
  }
}
