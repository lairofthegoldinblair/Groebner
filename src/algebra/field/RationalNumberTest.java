package algebra.field;

public class RationalNumberTest extends junit.framework.TestCase
{
  RationalField mField = null;

  protected void setUp()
  {
    mField = new RationalField();
  }

  protected void tearDown()
  {
    mField = null;
  }

  public RationalNumberTest(String name)
  {
    super(name);
  }

  public void testSimpleAdd()
  {
    RationalNumber lhs = mField.create(2,3);
    RationalNumber rhs = mField.create(4,5);
    RationalNumber expected = mField.create(22, 15);
    assertTrue(lhs.add(rhs).equals(expected));
  }

  public void testSimpleSubtract()
  {
    RationalNumber lhs = mField.create(2,3);
    RationalNumber rhs = mField.create(4,5);
    RationalNumber expected = mField.create(-2, 15);
    assertTrue(lhs.subtract(rhs).equals(expected));
  }

  public void testSimpleMultiply()
  {
    RationalNumber lhs = mField.create(2,3);
    RationalNumber rhs = mField.create(4,5);
    RationalNumber expected = mField.create(8, 15);
    assertTrue(lhs.multiply(rhs).equals(expected));
  }

  public void testSimpleDivide()
  {
    RationalNumber lhs = mField.create(2,3);
    RationalNumber rhs = mField.create(4,5);
    RationalNumber expected = mField.create(5, 6);
    assertTrue(lhs.divide(rhs).equals(expected));
  }

  public void testEquals()
  {
    RationalNumber reduced = mField.create(2,3);
    RationalNumber nonreduced = mField.create(10, 15);
    RationalNumber other = mField.create(3,4);
    assertTrue(reduced.equals(reduced));
    assertTrue(reduced.equals(nonreduced));
    assertTrue(nonreduced.equals(reduced));
    assertTrue(nonreduced.equals(nonreduced));
    assertTrue(other.equals(other));
    assertTrue(!reduced.equals(other));
    assertTrue(!nonreduced.equals(other));
    assertTrue(!other.equals(reduced));
    assertTrue(!other.equals(nonreduced));
  }

  public void testDistinguished()
  {
    RationalNumber one = mField.create(4,4);
    RationalNumber zero = mField.create(0,4);

    assertTrue(one.equals(mField.getIdentity()));
    assertTrue(!one.equals(mField.getZero()));
    assertTrue(!zero.equals(mField.getIdentity()));
    assertTrue(zero.equals(mField.getZero()));
  }
	   
  public void testSimpleNegate()
  {
    RationalNumber lhs = mField.create(2,3);
    RationalNumber neg = lhs.negate();
    RationalNumber expected = mField.create(-4, 6);
    assertTrue(neg.equals(expected));
  }

  public static junit.framework.Test suite()
  {
    junit.framework.TestSuite s = new junit.framework.TestSuite();
    s.addTest(new RationalNumberTest("testEquals"));
    s.addTest(new RationalNumberTest("testSimpleAdd"));
    s.addTest(new RationalNumberTest("testSimpleSubtract"));
    s.addTest(new RationalNumberTest("testSimpleMultiply"));
    s.addTest(new RationalNumberTest("testSimpleDivide"));
    s.addTest(new RationalNumberTest("testDistinguished"));
    s.addTest(new RationalNumberTest("testSimpleNegate"));
    return s;
  }
}
