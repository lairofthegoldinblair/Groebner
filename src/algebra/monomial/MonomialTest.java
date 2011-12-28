package algebra.monomial;

import algebra.DomainMismatchException;

public class MonomialTest extends junit.framework.TestCase
{
  public MonomialTest(String name)
  {
    super(name);
  }

  protected void setUp()
  {
  }

  protected void tearDown()
  {
  }

  public void testEquals()
  {
    Monomial m1 = new Monomial(new int [] {2,3,4});
    Monomial m2 = new Monomial(new int [] {2,3});
    Monomial m3 = new Monomial(new int [] {2,3,4});
    assertTrue(m1.equals(m1));
    assertTrue(!m1.equals(m2));
    assertTrue(m1.equals(m3));
    assertTrue(!m2.equals(m1));
    assertTrue(m2.equals(m2));
    assertTrue(!m2.equals(m3));
    assertTrue(m3.equals(m1));
    assertTrue(!m3.equals(m2));
    assertTrue(m3.equals(m3));
  }

  public void testDivide()
  {
    Monomial m1 = new Monomial(new int [] {2,3,4});
    Monomial m2 = new Monomial(new int [] {2,3});
    Monomial m3 = new Monomial(new int [] {4,6,4});
    assertTrue(m1.divide(m1).equals(new Monomial(new int [] {0,0,0})));
    try {
      m1.divide(m2);
      fail();
    } catch(DomainMismatchException e) {
    }
    assertTrue(null == m1.divide(m3));
    assertTrue(m3.divide(m1).equals(new Monomial(new int [] {2,3,0})));
  }

  public void testMultiply()
  {
    Monomial m1 = new Monomial(new int [] {2,3,4});
    Monomial m2 = new Monomial(new int [] {2,3});
    Monomial m3 = new Monomial(new int [] {4,6,4});
    assertTrue(m1.multiply(m1).equals(new Monomial(new int [] {4,6,8})));
    try {
      m2.multiply(m1);
      fail();
    } catch(DomainMismatchException e) {
    }
    assertTrue(m1.multiply(m3).equals(new Monomial(new int [] {6,9,8})));
    assertTrue(m3.multiply(m1).equals(new Monomial(new int [] {6,9,8})));
  }

  public void testDegree()
  {
    Monomial m1 = new Monomial(new int [] {2,3,4});
    Monomial m2 = new Monomial(new int [] {2,3});
    Monomial m3 = new Monomial(new int [] {4,6,4});
    assertTrue(9 == m1.getDegree());
    assertTrue(5 == m2.getDegree());
    assertTrue(14 == m3.getDegree());
  }

  public void testLCM()
  {
    Monomial m1 = new Monomial(new int [] {2,3,4});
    Monomial m2 = new Monomial(new int [] {2,3});
    Monomial m3 = new Monomial(new int [] {4,6,4});
    Monomial m4 = new Monomial(new int [] {1,6,3});
    Monomial m5 = new Monomial(new int [] {2,6,4});
    try {
      m1.lcm(m2);
      fail();
    } catch(DomainMismatchException e) {
    }
    assertTrue(m3.equals(m1.lcm(m3)));
    assertTrue(m5.equals(m1.lcm(m4)));
  }

  public void testLexOrderMonomial()
  {
    Monomial m1 = new Monomial(new int [] {2,3,4});
    Monomial m2 = new Monomial(new int [] {1,3,8});
    Monomial m3 = new Monomial(new int [] {4,6,4});
    try {
      MonomialOrderClass clazz = MonomialOrderClass.forName("LexOrder");
      LexOrder lo1 = (LexOrder) clazz.create(new int [] {0,1,2});
      LexOrder lo2 = (LexOrder) clazz.create(new int [] {2,1,0});
      LexOrder lo3 = (LexOrder) clazz.create(new int [] {1,0,2});

      assertTrue(1 == lo1.compare(m1, m2));
      assertTrue(0 == lo1.compare(m1, m1));
      assertTrue(-1 == lo1.compare(m1, m3));
      assertTrue(-1 == lo2.compare(m1, m2));
      assertTrue(0 == lo2.compare(m1, m1));
      assertTrue(-1 == lo2.compare(m1, m3));
      assertTrue(1 == lo3.compare(m1, m2));
      assertTrue(0 == lo3.compare(m1, m1));
      assertTrue(-1 == lo3.compare(m1, m3));
    } catch (ClassNotFoundException e) {
      assertTrue(false);
    }
  }

  public void testGrlexOrderMonomial()
  {
    Monomial m1 = new Monomial(new int [] {2,3,4});
    Monomial m2 = new Monomial(new int [] {1,3,8});
    Monomial m3 = new Monomial(new int [] {4,6,4});
    try {
      MonomialOrderClass clazz = MonomialOrderClass.forName("GrlexOrder");
      LexOrder lo1 = (LexOrder) clazz.create(new int [] {0,1,2});
      LexOrder lo2 = (LexOrder) clazz.create(new int [] {2,1,0});
      LexOrder lo3 = (LexOrder) clazz.create(new int [] {1,0,2});

      assertTrue(-1 == lo1.compare(m1, m2));
      assertTrue(0 == lo1.compare(m1, m1));
      assertTrue(-1 == lo1.compare(m1, m3));
      assertTrue(-1 == lo2.compare(m1, m2));
      assertTrue(0 == lo2.compare(m1, m1));
      assertTrue(-1 == lo2.compare(m1, m3));
      assertTrue(-1 == lo3.compare(m1, m2));
      assertTrue(0 == lo3.compare(m1, m1));
      assertTrue(-1 == lo3.compare(m1, m3));
    } catch (ClassNotFoundException e) {
      assertTrue(false);
    }
  }

  public void testGrevlexOrderMonomial()
  {
    Monomial m1 = new Monomial(new int [] {2,3,4});
    Monomial m2 = new Monomial(new int [] {1,3,10});
    Monomial m3 = new Monomial(new int [] {4,6,4});
    try {
      MonomialOrderClass grevlexClass = MonomialOrderClass.forName("GrevlexOrder");
      GrevlexOrder lo1 = (GrevlexOrder) grevlexClass.create(new int [] {0,1,2});
      GrevlexOrder lo2 = (GrevlexOrder) grevlexClass.create(new int [] {2,1,0});
      GrevlexOrder lo3 = (GrevlexOrder) grevlexClass.create(new int [] {2,0,1});

      assertTrue(-1 == lo1.compare(m1, m2));
      assertTrue(0 == lo1.compare(m1, m1));
      assertTrue(-1 == lo1.compare(m1, m3));
      assertTrue(-1 == lo1.compare(m2, m3));
      assertTrue(-1 == lo2.compare(m1, m2));
      assertTrue(0 == lo2.compare(m1, m1));
      assertTrue(-1 == lo2.compare(m1, m3));
      assertTrue(1 == lo2.compare(m2, m3));
      assertTrue(-1 == lo3.compare(m1, m2));
      assertTrue(0 == lo3.compare(m1, m1));
      assertTrue(-1 == lo3.compare(m1, m3));
      assertTrue(1 == lo3.compare(m2, m3));

      Monomial m4 = new Monomial(new int [] {6,2});
      Monomial m5 = new Monomial(new int [] {3,5});
      GrevlexOrder lo4 = (GrevlexOrder) grevlexClass.create(new int [] {0,1});
      GrevlexOrder lo5 = (GrevlexOrder) grevlexClass.create(new int [] {1,0});
      assertTrue(1 == lo4.compare(m4, m5));
      assertTrue(-1 == lo5.compare(m4, m5));
    } catch (ClassNotFoundException e) {
      assertTrue(false);
    }
  }

  public void testWeightOrderMonomial()
  {
    Monomial m1 = new Monomial(new int [] {2,3,4});
    Monomial m2 = new Monomial(new int [] {1,3,10});
    Monomial m3 = new Monomial(new int [] {4,6,4});
    try {
      MonomialOrderClass grevlexClass = MonomialOrderClass.forName("WeightedRevLexOrder");
      WeightOrder lo1 = (WeightOrder) grevlexClass.create(new int [] {0, 1, 1}, new int [] {0,1,2});
      WeightOrder lo2 = (WeightOrder) grevlexClass.create(new int [] {-1, 1, 1}, new int [] {2,1,0});
      WeightOrder lo3 = (WeightOrder) grevlexClass.create(new int [] {6, 1, 1}, new int [] {2,0,1});

      assertTrue(-1 == lo1.compare(m1, m2));
      assertTrue(0 == lo1.compare(m1, m1));
      assertTrue(-1 == lo1.compare(m1, m3));
      assertTrue(1 == lo1.compare(m2, m3));
      assertTrue(-1 == lo2.compare(m1, m2));
      assertTrue(0 == lo2.compare(m1, m1));
      assertTrue(-1 == lo2.compare(m1, m3));
      assertTrue(1 == lo2.compare(m2, m3));
      assertTrue(-1 == lo3.compare(m1, m2));
      assertTrue(0 == lo3.compare(m1, m1));
      assertTrue(-1 == lo3.compare(m1, m3));
      assertTrue(-1 == lo3.compare(m2, m3));

      Monomial m4 = new Monomial(new int [] {6,2});
      Monomial m5 = new Monomial(new int [] {3,5});
      WeightOrder lo4 = (WeightOrder) grevlexClass.create(new int [] {1,2}, new int [] {0,1});
      WeightOrder lo5 = (WeightOrder) grevlexClass.create(new int [] {2,1}, new int [] {1,0});
      assertTrue(-1 == lo4.compare(m4, m5));
      assertTrue(1 == lo5.compare(m4, m5));
    } catch (ClassNotFoundException e) {
      assertTrue(false);
    }
  }

  public void testDifferentiate()
  {
    Monomial m1 = new Monomial(new int [] {2,3,4});
    Monomial m2 = new Monomial(new int [] {1,3,10});
    Monomial m3 = new Monomial(new int [] {4,6,4});

    assertTrue(m1.differentiate(new int [] {1, 0, 2}).equals(new DifferentiatedMonomial<Monomial>(new Monomial(new int [] {1,3,2}), 24)));
    assertTrue(m1.differentiate(new int [] {3, 0, 2}).isZero());
    assertTrue(m2.differentiate(new int [] {1, 2, 2}).equals(new DifferentiatedMonomial<Monomial>(new Monomial(new int [] {0,1,8}), 540)));
    assertTrue(m3.differentiate(new int [] {2, 1, 0}).equals(new DifferentiatedMonomial<Monomial>(new Monomial(new int [] {2,5,4}), 72)));
  }

  public static junit.framework.Test suite()
  {
    junit.framework.TestSuite s = new junit.framework.TestSuite();
    s.addTest(new MonomialTest("testEquals"));
    s.addTest(new MonomialTest("testDivide"));
    s.addTest(new MonomialTest("testMultiply"));
    s.addTest(new MonomialTest("testDegree"));
    s.addTest(new MonomialTest("testLCM"));
    s.addTest(new MonomialTest("testLexOrderMonomial"));
    s.addTest(new MonomialTest("testGrlexOrderMonomial"));
    s.addTest(new MonomialTest("testGrevlexOrderMonomial"));
    s.addTest(new MonomialTest("testWeightOrderMonomial"));
    s.addTest(new MonomialTest("testDifferentiate"));
    return s;    
  }
}
