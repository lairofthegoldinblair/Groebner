package algebra.generic;

public interface RightModuleElement<R, M>
{
  public RightModule<R, M> getModule();
  public M add(M y);
  public M subtract(M y);
  public M negate();
  public M rightMultiply(R r);

  public boolean empty();

}
