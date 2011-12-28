package algebra.generic;

/**
 * Methods required of an element of a ring.  The class representing the element data is
 * captured in the template parameter R. This interface is intended to be used as a "mixin" interface. 
 */
public interface RingElement<R>
{
  public R negate();
  public R add(R y);
  public R subtract(R y);
  public R leftMultiply(R y);
  public R rightMultiply(R y);

  public boolean empty();

  public Ring<R> getRing();
}

