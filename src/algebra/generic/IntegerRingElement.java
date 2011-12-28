package algebra.generic;

/**
 * An element of the integer ring.  Can't really call this Integer since that class name is
 * already taken.
 */
public class IntegerRingElement extends CommutativeRingElementBase<IntegerRingElement>
{
  private java.math.BigInteger mInteger;

  protected java.math.BigInteger getInteger()
  {
    return mInteger;
  }

  protected IntegerRingElement getThis()
  {
    return this;
  }

  public boolean empty()
  {
    return getRing().getZero().equals(this);
  }

  public boolean equals(IntegerRingElement elt)
  {
    return getRing().equals(this, elt);
  }

  protected IntegerRingElement(IntegerRing ring, java.math.BigInteger value)
  {
    super(ring);
    mInteger = value;
  }

  public IntegerRingElement(IntegerRing ring, IntegerRingElement value)
  {
    super(ring);
    mInteger = value.getInteger();
  }
}
