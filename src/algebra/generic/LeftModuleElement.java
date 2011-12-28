package algebra.generic;

public interface LeftModuleElement<R, M>
{
  public LeftModule<R, M> getModule();
  public M add(M y);
  public M subtract(M y);
  public M negate();
  public M leftMultiply(R r);

  public boolean empty();

}
