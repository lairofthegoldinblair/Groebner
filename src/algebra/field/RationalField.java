package algebra.field;

public class RationalField extends algebra.generic.FieldBase<RationalNumber>
{
  private RationalNumber mZero;
  private RationalNumber mIdentity;

  private java.util.Random mRandom;

  // Should this be made into a singleton?
  public RationalField()
  {
    mZero = new RationalNumber(this, java.math.BigInteger.ZERO, java.math.BigInteger.ONE);
    mIdentity = new RationalNumber(this, java.math.BigInteger.ONE, java.math.BigInteger.ONE);
    mRandom = new java.util.Random();
  }

  public RationalNumber getRandom()
  {
    int a = mRandom.nextInt() % 143;
    int b = mRandom.nextInt() % 131;
    if(b == 0) b = 1;

    return create(a,b);
  }

  public boolean equals(Object o)
  {
    return o instanceof RationalField;
  }

  public String toString()
  {
    return "Q";
  }

  public RationalNumber getIdentity()
  {
    return mIdentity;
  }

  public RationalNumber getZero()
  {
    return mZero;
  }

  public RationalNumber negate(RationalNumber x)
  {
    return create(x.getBigReducedNumerator().negate(), x.getBigReducedDenominator());
  }

  public RationalNumber add(RationalNumber x, RationalNumber y)
  {
    return create(x.getBigReducedDenominator().multiply(y.getBigReducedNumerator()).add(x.getBigReducedNumerator().multiply(y.getBigReducedDenominator())), y.getBigReducedDenominator().multiply(x.getBigReducedDenominator()));
  }

  public RationalNumber subtract(RationalNumber x, RationalNumber y)
  {
    return create(y.getBigReducedDenominator().multiply(x.getBigReducedNumerator()).subtract(y.getBigReducedNumerator().multiply(x.getBigReducedDenominator())), 
		  x.getBigReducedDenominator().multiply(y.getBigReducedDenominator()));
  }

  public RationalNumber multiply(RationalNumber x, RationalNumber y)
  {
    return create(x.getBigReducedNumerator().multiply(y.getBigReducedNumerator()), 
		  x.getBigReducedDenominator().multiply(y.getBigReducedDenominator()));
  }

  public RationalNumber invert(RationalNumber x)
  {
    return create(x.getBigReducedDenominator(), x.getBigReducedNumerator());
  }

  public RationalNumber divide(RationalNumber x, RationalNumber y)
  {
    return create(x.getBigReducedNumerator().multiply(y.getBigReducedDenominator()), 
		  x.getBigReducedDenominator().multiply(y.getBigReducedNumerator()));
  }

  public boolean equals(RationalNumber x, RationalNumber y)
  {
    return x.getBigReducedNumerator().multiply(y.getBigReducedDenominator()).equals(x.getBigReducedDenominator().multiply(y.getBigReducedNumerator()));
  }

  public RationalNumber create(java.math.BigInteger value)
  {
    return create(value, java.math.BigInteger.ONE);
  }

  public RationalNumber create(java.math.BigInteger num, java.math.BigInteger denom)
  {
    return new RationalNumber(this, num, denom);
  }
}
