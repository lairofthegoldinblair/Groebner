package algebra.generic;

public interface ModuleElement<R, M>
{
  public Module<R, M> getModule();
  public M add(M y);
  public M subtract(M y);
  public M negate();
  public M multiply(R r);

  public boolean empty();

}

