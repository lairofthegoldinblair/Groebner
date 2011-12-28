package algebra.generic;

public interface BiModuleElement<R, S, M>
{
  public BiModule<R, S, M> getModule();
  public M add(M y);
  public M subtract(M y);
  public M negate();
  public M leftMultiply(R r);
  public M rightMultiply(S r);

  public boolean empty();

}

