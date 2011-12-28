package algebra.term;

import algebra.field.RationalField;
import algebra.field.RationalNumber;
import algebra.monomial.Monomial;
import algebra.DomainMismatchException;
import java.util.Vector;

public class TermTest extends junit.framework.TestCase
{
  public TermTest(String name)
  {
    super(name);
  }

  private RationalField mField;

  protected void setUp()
  {
    mField = new RationalField();
  }

  protected void tearDown()
  {
    mField = null;
  }

  public void testTermEquals()
  {
    Term<RationalNumber,RationalField> t1 = new Term<RationalNumber,RationalField>(mField.create(4,1), new Monomial(new int [] {3,2,1}));
    Term<RationalNumber,RationalField> t2 = new Term<RationalNumber,RationalField>(mField.create(3,1), new Monomial(new int [] {3,2,1}));
    Term<RationalNumber,RationalField> t3 = new Term<RationalNumber,RationalField>(mField.create(4,1), new Monomial(new int [] {3,2,1}));
    Term<RationalNumber,RationalField> t4 = new Term<RationalNumber,RationalField>(mField.create(4,1), new Monomial(new int [] {3,2,2}));
    Term<RationalNumber,RationalField> t5 = new Term<RationalNumber,RationalField>(mField.create(4,1), new Monomial(new int [] {3,2}));
    assertTrue(!t1.equals(t2));
    assertTrue(t1.equals(t3));
    assertTrue(!t1.equals(t4));
    assertTrue(!t1.equals(t5));
  }

  public void testTermDivide()
  {
    Term<RationalNumber,RationalField> t1 = new Term<RationalNumber,RationalField>(mField.create(4,1), new Monomial(new int [] {3,2,1}));
    Term<RationalNumber,RationalField> t2 = new Term<RationalNumber,RationalField>(mField.create(3,2), new Monomial(new int [] {1,2,1}));
    Term<RationalNumber,RationalField> t3 = new Term<RationalNumber,RationalField>(mField.create(4,1), new Monomial(new int [] {3,2}));
    
    assertTrue(t1.divide(t2).equals(new Term<RationalNumber,RationalField>(mField.create(8,3), new Monomial(new int [] {2,0,0}))));
    try {
      t1.divide(t3);
      fail();
    } catch(DomainMismatchException e) {
    }
    assertTrue(null == t2.divide(t1));
  }

  public void testTermMultiply()
  {
    Term<RationalNumber,RationalField> t1 = new Term<RationalNumber,RationalField>(mField.create(4,1), new Monomial(new int [] {3,2,1}));
    Term<RationalNumber,RationalField> t2 = new Term<RationalNumber,RationalField>(mField.create(3,2), new Monomial(new int [] {1,2,1}));
    Term<RationalNumber,RationalField> t3 = new Term<RationalNumber,RationalField>(mField.create(4,1), new Monomial(new int [] {3,2}));
    
    assertTrue(t1.multiply(t2).equals(new Term<RationalNumber,RationalField>(mField.create(6,1), new Monomial(new int [] {4,4,2}))));
    try {
      t1.multiply(t3);
      fail();
    } catch(DomainMismatchException e) {
    }
    assertTrue(t2.multiply(t1).equals(new Term<RationalNumber,RationalField>(mField.create(6,1), new Monomial(new int [] {4,4,2}))));
  }

  public static junit.framework.Test suite()
  {
    junit.framework.TestSuite s = new junit.framework.TestSuite();
    s.addTest(new TermTest("testTermEquals"));
    s.addTest(new TermTest("testTermDivide"));
    s.addTest(new TermTest("testTermMultiply"));
    return s;
  }
}
