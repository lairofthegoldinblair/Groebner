package algebra.generic;

/**
 * The ring of integers.
 */
public class IntegerRing extends CommutativeRingBase<IntegerRingElement>
{
  private IntegerRingElement ZERO;
  private IntegerRingElement ONE;

  public IntegerRing()
  {
    ZERO = new IntegerRingElement(this, java.math.BigInteger.ZERO);
    ONE = new IntegerRingElement(this, java.math.BigInteger.ONE);
  }

  public IntegerRingElement getIdentity()
  {
    return ONE;
  }

  public IntegerRingElement getZero()
  {
    return ZERO;
  }

  public boolean equals(IntegerRingElement x, IntegerRingElement y)
  {
    return x.getInteger().equals(y.getInteger());
  }

  public IntegerRingElement negate(IntegerRingElement x)
  {
    return new IntegerRingElement(this, x.getInteger().negate());
  }

  public IntegerRingElement add(IntegerRingElement x, IntegerRingElement y)
  {
    return new IntegerRingElement(this, x.getInteger().add(y.getInteger()));
  }

  public IntegerRingElement subtract(IntegerRingElement x, IntegerRingElement y)
  {
    return new IntegerRingElement(this, x.getInteger().subtract(y.getInteger()));
  }

  public IntegerRingElement multiply(IntegerRingElement x, IntegerRingElement y)
  {
    return new IntegerRingElement(this, x.getInteger().multiply(y.getInteger()));
  } 

  public IntegerRingElement create(java.math.BigInteger value)
  {
    return new IntegerRingElement(this, value);
  }
}
