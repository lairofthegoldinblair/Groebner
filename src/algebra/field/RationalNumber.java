package algebra.field;

public class RationalNumber extends algebra.generic.FieldElementBase<RationalNumber>
{
  private java.math.BigInteger mNum;
  private java.math.BigInteger mDenom;
  private java.math.BigInteger mReducedNum;
  private java.math.BigInteger mReducedDenom;

  public RationalNumber(RationalField field, java.math.BigInteger num, java.math.BigInteger denom)
  {
    super(field);
    mNum = num;
    mDenom = denom;

    if (mNum.compareTo(java.math.BigInteger.ZERO) != 0)
    {
      java.math.BigInteger tmp = num.gcd(denom);
      if (tmp.compareTo(java.math.BigInteger.ONE) < 0) 
	throw new RuntimeException("GCD(" + num + ", " + denom + ") = " + tmp);
      if(denom.compareTo(java.math.BigInteger.ZERO) < 0)
      {
	mReducedNum = num.negate().divide(tmp);
	mReducedDenom = denom.negate().divide(tmp);
      } 
      else
      {
	mReducedNum = num.divide(tmp);
	mReducedDenom = denom.divide(tmp);
      }
    }
    else
    {
      mReducedNum = java.math.BigInteger.ZERO;
      mReducedDenom = java.math.BigInteger.ONE;
    }

    if (mDenom.compareTo(java.math.BigInteger.ZERO) == 0 ||
	mReducedDenom.compareTo(java.math.BigInteger.ZERO) == 0) 
    {
      throw new RuntimeException("Cannot create rational number with zero denominator");
    }
  }

  public final long getNumerator() 
  {
    return mNum.longValue();
  }

  public final long getDenominator()
  {
    return mDenom.longValue();
  }

  public final long getReducedNumerator() 
  {
    return mReducedNum.longValue();
  }

  public final long getReducedDenominator()
  {
    return mReducedDenom.longValue();
  }

  public final java.math.BigInteger getBigReducedNumerator() 
  {
    return mReducedNum;
  }

  public final java.math.BigInteger getBigReducedDenominator()
  {
    return mReducedDenom;
  }

  // Note that the hash code cannot be different for two instances where the
  // equals() is true.  Thus, we compute these based on the reduced representation.
  public int hashCode()
  {
    return getBigReducedNumerator().hashCode() & getBigReducedDenominator().hashCode();
  }

  public boolean isIntegral()
  {
    return getBigReducedDenominator().compareTo(java.math.BigInteger.ONE) == 0;
  }

  public boolean equals(Object o)
  {
    if (o instanceof RationalNumber)
    {
      return getField().equals(this, (RationalNumber)o);
    }
    return false;
  }

  public int compareTo(Object o)
  {
    return compareTo((RationalNumber) o);
  }

  public int compareTo(RationalNumber r)
  {
    java.math.BigInteger cross =getBigReducedNumerator().multiply(r.getBigReducedDenominator()).subtract(getBigReducedDenominator().multiply(r.getBigReducedNumerator()));
    return  cross.compareTo(java.math.BigInteger.ZERO) == 0 ? 0 : (cross.compareTo(java.math.BigInteger.ZERO) < 0 ? -1 : 1);
  }

  public String toString()
  {
    if (getBigReducedDenominator().compareTo(java.math.BigInteger.ONE) != 0) return "(" + getBigReducedNumerator() + "/" + getBigReducedDenominator() + ")";
    else return getBigReducedNumerator().toString();
  }

  protected RationalNumber getThis()
  {
    return this;
  }

  public boolean empty()
  {
    return getBigReducedNumerator().equals(java.math.BigInteger.ZERO);
  }
}
