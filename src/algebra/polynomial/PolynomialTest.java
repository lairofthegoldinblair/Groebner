package algebra.polynomial;

import algebra.field.RationalField;
import algebra.field.RationalNumber;
import algebra.monomial.MonomialOrderClass;
import algebra.Symbol;
import algebra.SymbolList;
import java.util.Vector;

import algebra.generic.BuchbergerAlgorithm;
import algebra.generic.Syzygy;

import algebra.term.Term;

public class PolynomialTest extends junit.framework.TestCase
{
  public PolynomialTest(String name)
  {
    super(name);
  }

  PolynomialRing<RationalNumber,RationalField> mTwoVariableGrlexRing;
  PolynomialRing<RationalNumber,RationalField> mTwoVariableGrevlexRing;
  PolynomialRing<RationalNumber,RationalField> mTwoVariableRing;
  PolynomialRing<RationalNumber,RationalField> mTwoVariableRingXY;

  Polynomial<RationalNumber,RationalField> p1;
  Polynomial<RationalNumber,RationalField> p2;
  Polynomial<RationalNumber,RationalField> p3;
  Polynomial<RationalNumber,RationalField> p4;
  Polynomial<RationalNumber,RationalField> p5;
  Polynomial<RationalNumber,RationalField> p6;
  Polynomial<RationalNumber,RationalField> p7;
  Polynomial<RationalNumber,RationalField> p8;

  protected void setUp()
  {
    try {
      MonomialOrderClass grevlexClass = MonomialOrderClass.forName("GrevlexOrder");
      MonomialOrderClass grlexClass = MonomialOrderClass.forName("GrlexOrder");
      
      mTwoVariableGrevlexRing = new PolynomialRing<RationalNumber,RationalField>(new RationalField(), grevlexClass.create(new int [] {0,1}));
      mTwoVariableGrlexRing = new PolynomialRing<RationalNumber,RationalField>(new RationalField(), grlexClass.create(new int [] {0,1}));
      mTwoVariableRing = new PolynomialRing<RationalNumber,RationalField>(new RationalField(), 2);
      
      mTwoVariableRingXY = new PolynomialRing<RationalNumber,RationalField>(new RationalField(), new SymbolList(new String [] {"x", "y"}), MonomialOrderClass.forName("LexOrder"));
    } catch (ClassNotFoundException e) {
    }

    Vector<Term<RationalNumber,RationalField>> t1 = new Vector<Term<RationalNumber,RationalField>>();
    Vector<Term<RationalNumber,RationalField>> t2 = new Vector<Term<RationalNumber,RationalField>>();
    Vector<Term<RationalNumber,RationalField>> t3 = new Vector<Term<RationalNumber,RationalField>>();
    t1.add(mTwoVariableRing.createTerm(mTwoVariableRing.getBaseField().create(4,1), (new int [] {3,2})));
    t1.add(mTwoVariableRing.createTerm(mTwoVariableRing.getBaseField().create(3,1), (new int [] {1,3})));
    t2.add(mTwoVariableRing.createTerm(mTwoVariableRing.getBaseField().create(4,1), (new int [] {3,2})));
    t2.add(mTwoVariableRing.createTerm(mTwoVariableRing.getBaseField().create(4,1), (new int [] {3,6})));
    t2.add(mTwoVariableRing.createTerm(mTwoVariableRing.getBaseField().create(4,3), (new int [] {1,2})));
    t3.add(mTwoVariableRing.createTerm(mTwoVariableRing.getBaseField().create(4,1), (new int [] {3,2})));
    t3.add(mTwoVariableRing.createTerm(mTwoVariableRing.getBaseField().create(4,1), (new int [] {3,6})));
    t3.add(mTwoVariableRing.createTerm(mTwoVariableRing.getBaseField().create(4,3), (new int [] {1,2})));

    p1 = mTwoVariableRing.createPolynomial(t1);
    // 4*x^3*y^2 + 4*x^3*y^6 + (4/3)*x^1*y^2
    p2 = mTwoVariableRing.createPolynomial(t2);
    p3 = mTwoVariableRing.createPolynomial(t3);

    t1 = new Vector<Term<RationalNumber,RationalField>>();
    t2 = new Vector<Term<RationalNumber,RationalField>>();
    t3 = new Vector<Term<RationalNumber,RationalField>>();
    t1.add(mTwoVariableGrevlexRing.createTerm(mTwoVariableGrevlexRing.getBaseField().create(4,1), (new int [] {3,2})));
    t1.add(mTwoVariableGrevlexRing.createTerm(mTwoVariableGrevlexRing.getBaseField().create(3,1), (new int [] {1,3})));
    t2.add(mTwoVariableGrevlexRing.createTerm(mTwoVariableGrevlexRing.getBaseField().create(4,1), (new int [] {6,2})));
    t2.add(mTwoVariableGrevlexRing.createTerm(mTwoVariableGrevlexRing.getBaseField().create(4,1), (new int [] {3,5})));
    t2.add(mTwoVariableGrevlexRing.createTerm(mTwoVariableGrevlexRing.getBaseField().create(4,3), (new int [] {1,2})));
    t3.add(mTwoVariableGrevlexRing.createTerm(mTwoVariableGrevlexRing.getBaseField().create(4,1), (new int [] {3,2})));
    t3.add(mTwoVariableGrevlexRing.createTerm(mTwoVariableGrevlexRing.getBaseField().create(4,1), (new int [] {3,6})));
    t3.add(mTwoVariableGrevlexRing.createTerm(mTwoVariableGrevlexRing.getBaseField().create(4,3), (new int [] {1,2})));

    p4 = mTwoVariableGrevlexRing.createPolynomial(t1);
    p5 = mTwoVariableGrevlexRing.createPolynomial(t2);
    p6 = mTwoVariableGrevlexRing.createPolynomial(t3);

    t1 = new Vector<Term<RationalNumber,RationalField>>();
    t2 = new Vector<Term<RationalNumber,RationalField>>();
    t1.add(mTwoVariableRingXY.createTerm(mTwoVariableRingXY.getBaseField().create(4,1), (new int [] {3,2})));
    t1.add(mTwoVariableRingXY.createTerm(mTwoVariableRingXY.getBaseField().create(3,1), (new int [] {1,3})));
    t2.add(mTwoVariableRingXY.createTerm(mTwoVariableRingXY.getBaseField().create(8,1), (new int [] {3,2})));
    t2.add(mTwoVariableRingXY.createTerm(mTwoVariableRingXY.getBaseField().create(6,1), (new int [] {1,3})));
    p7 = mTwoVariableRingXY.createPolynomial(t1);
    p8 = mTwoVariableRingXY.createPolynomial(t1);
  }

  protected void tearDown()
  {
    mTwoVariableGrevlexRing = null;
    mTwoVariableGrlexRing = null;
    mTwoVariableRing = null;
    mTwoVariableRingXY = null;
    p1 = null;
    p2 = null;
    p3 = null;
    p4 = null;
    p5 = null;
    p6 = null;
    p7 = null;
    p8 = null;
  }

  public void testEquals()
  {
    assertTrue(p1.equals(p1));
    assertTrue(!p1.equals(p2));
    assertTrue(!p1.equals(p3));
    assertTrue(!p2.equals(p1));
    assertTrue(p2.equals(p3));
    assertTrue(p2.equals(p2));
    assertTrue(!p3.equals(p1));
    assertTrue(p3.equals(p3));
    assertTrue(p3.equals(p2));

    assertTrue(p3.isScalarMultiple(p2));
    assertTrue(p7.isScalarMultiple(p8));
    assertTrue(!p1.isScalarMultiple(p2));
  }

  public void testBuilder()
  {
    RingPolynomialBuilder<RationalNumber,RationalField> builder = new RingPolynomialBuilder<RationalNumber,RationalField>(mTwoVariableRing);
    builder.buildSingleVariableMonomial(Symbol.create("x_1"), 3);
    builder.buildSingleVariableMonomial(Symbol.create("x_2"), 2);
    builder.buildMonomial();
    builder.buildCoefficient(4);
    builder.buildTerm();
    builder.buildSingleVariableMonomial(Symbol.create("x_1"), 1);
    builder.buildSingleVariableMonomial(Symbol.create("x_2"), 3);
    builder.buildMonomial();
    builder.buildCoefficient(3);
    builder.buildTerm();
    builder.buildPolynomial();

    assertTrue(p1.equals(builder.getPolynomial()));
  }

  public void testParser()
  {
    Polynomial<RationalNumber,RationalField> b = mTwoVariableRingXY.createPolynomial("2*x*y");
    Polynomial<RationalNumber,RationalField> a = mTwoVariableRingXY.createPolynomial("x*y"); 
    boolean result = a.equals(b);
    assertTrue(!result);

    Polynomial<RationalNumber,RationalField> p = mTwoVariableRing.createPolynomial("4*x_1^3*x_2^2 + 3*x_1*x_2^3");
    assertTrue(p.equals(p1));
    p = mTwoVariableRingXY.createPolynomial("4*x^3*y^2 + (6/2)*x*y^3");
    assertTrue(p.equals(p7));
  }

  public void testAdd()
  {
    Vector<Term<RationalNumber,RationalField>> t = new Vector<Term<RationalNumber,RationalField>>();
    t.add(mTwoVariableRing.createTerm(mTwoVariableRing.getBaseField().create(8,1), (new int [] {3,2})));
    t.add(mTwoVariableRing.createTerm(mTwoVariableRing.getBaseField().create(4,1), (new int [] {3,6})));
    t.add(mTwoVariableRing.createTerm(mTwoVariableRing.getBaseField().create(4,3), (new int [] {1,2})));
    t.add(mTwoVariableRing.createTerm(mTwoVariableRing.getBaseField().create(3,1), (new int [] {1,3})));

    assertTrue(mTwoVariableRing.createPolynomial(t).equals(p1.add(p2)));
    assertTrue(mTwoVariableRing.createPolynomial(t).equals(p1.add(p3)));
  }

  public void testSubtract()
  {
    Vector<Term<RationalNumber,RationalField>> t = new Vector<Term<RationalNumber,RationalField>>();
    t.add(mTwoVariableRing.createTerm(mTwoVariableRing.getBaseField().create(3,1), (new int [] {1,3})));
    t.add(mTwoVariableRing.createTerm(mTwoVariableRing.getBaseField().create(-4,1), (new int [] {3,6})));
    t.add(mTwoVariableRing.createTerm(mTwoVariableRing.getBaseField().create(-4,3), (new int [] {1,2})));

    assertTrue(mTwoVariableRing.createPolynomial(t).equals(p1.subtract(p2)));
    assertTrue(mTwoVariableRing.createPolynomial(t).equals(p1.subtract(p3)));
  }

  public void testInitialTerm()
  {
    assertTrue(p1.getInitialTerm().equals(mTwoVariableRing.createTerm(mTwoVariableRing.getBaseField().create(4,1), (new int [] {3,2}))));
    assertTrue(p2.getInitialTerm().equals(mTwoVariableRing.createTerm(mTwoVariableRing.getBaseField().create(4,1), (new int [] {3,6}))));
    assertTrue(p4.getInitialTerm().equals(mTwoVariableGrevlexRing.createTerm(mTwoVariableGrevlexRing.getBaseField().create(4,1), (new int [] {3,2}))));
    assertEquals(mTwoVariableGrevlexRing.createTerm(mTwoVariableGrevlexRing.getBaseField().create(4,1), (new int [] {6,2})), p5.getInitialTerm());
    assertTrue(p1.getInitialMonomial().equals(mTwoVariableRing.createMonomial(new int [] {3,2})));
    assertTrue(p2.getInitialMonomial().equals(mTwoVariableRing.createMonomial(new int [] {3,6})));
    assertTrue(p4.getInitialMonomial().equals(mTwoVariableGrevlexRing.createMonomial(new int [] {3,2})));
    assertTrue(p5.getInitialMonomial().equals(mTwoVariableGrevlexRing.createMonomial(new int [] {6,2})));
    assertTrue(p1.getInitialCoefficient().equals(mTwoVariableRing.getBaseField().create(4,1)));
    assertTrue(p2.getInitialCoefficient().equals(mTwoVariableRing.getBaseField().create(4,1)));
    assertTrue(p4.getInitialCoefficient().equals(mTwoVariableGrevlexRing.getBaseField().create(4,1)));
    assertTrue(p5.getInitialCoefficient().equals(mTwoVariableGrevlexRing.getBaseField().create(4,1)));
  }

  public void testMultiply()
  {
    Vector<Term<RationalNumber,RationalField>> t = new Vector<Term<RationalNumber,RationalField>>();
    t.add(mTwoVariableRing.createTerm(mTwoVariableRing.getBaseField().create(12,7), (new int [] {5,6})));
    t.add(mTwoVariableRing.createTerm(mTwoVariableRing.getBaseField().create(12,7), (new int [] {5,10})));
    t.add(mTwoVariableRing.createTerm(mTwoVariableRing.getBaseField().create(4,7), (new int [] {3,6})));
    Polynomial<RationalNumber,RationalField> result = mTwoVariableRing.createPolynomial(t);

    // ( 4*x^3*y^2 + 4*x^3*y^6 + (4/3)*x^1*y^2 ) * (3/7)*x^2*y^4
    assertTrue(result.equals(p2.multiply(mTwoVariableRing.createTerm(mTwoVariableRing.getBaseField().create(3,7), (new int [] {2,4})))));
  }

  public void testDivide()
  {
    // Here is an example from pg. 80 of Cox, Little, O'Shea
    PolynomialRing<RationalNumber,RationalField> r = mTwoVariableRingXY;
    Vector<Polynomial<RationalNumber,RationalField>> list = new Vector<Polynomial<RationalNumber,RationalField>>();
    list.add(r.createPolynomial("x^2*y + (-1)*y^2"));
    list.add(r.createPolynomial("x^4*y^2 + (-1)*y^2"));
    Polynomial<RationalNumber,RationalField> p = r.createPolynomial("x^5*y");
    algebra.generic.GenericNormalForm<RationalNumber, Term<RationalNumber,RationalField>, Polynomial<RationalNumber, RationalField>, Term<RationalNumber,RationalField>, Polynomial<RationalNumber, RationalField>> nf = p.getNormalForm(list);
    Polynomial<RationalNumber,RationalField> result = r.createPolynomial("x*y^3");
    assertTrue(result.equals(nf.getRemainder()));

    // Pg. 64 of Cox, Little, O'Shea
    list.clear();
    list.add(r.createPolynomial("y^2 + (-1)"));
    list.add(r.createPolynomial("x*y + (-1)"));
    p = r.createPolynomial("x^2*y + x*y^2 + y^2");

    nf = p.getNormalForm(list);
    assertTrue(nf.getRemainder().equals(r.createPolynomial("2*x + 1")));

    // Pg. 61 of Cox, Little, O'Shea
    list.clear();
    list.add(r.createPolynomial("x*y + (-1)"));
    list.add(r.createPolynomial("y^2 + (-1)"));
    p = r.createPolynomial("x^2*y + x*y^2 + y^2");

    nf = p.getNormalForm(list);
    assertTrue(nf.getRemainder().equals(r.createPolynomial("x + y + 1")));
  }

  /**
   * Test case from pg. 81 of Cox, Little, O'Shea
   */
  public void testSPair()
  {
    Vector<Term<RationalNumber,RationalField>> t1 = new Vector<Term<RationalNumber,RationalField>>();
    Vector<Term<RationalNumber,RationalField>> t2 = new Vector<Term<RationalNumber,RationalField>>();
    Vector<Term<RationalNumber,RationalField>> t3 = new Vector<Term<RationalNumber,RationalField>>();

    RationalNumber one = mTwoVariableGrlexRing.getBaseField().getIdentity();
    RationalNumber negone = one.negate();
//     RationalNumber bug = mTwoVariableGrlexRing.getBaseField().getIdentity().negate();

    t1.add(mTwoVariableGrlexRing.createTerm(mTwoVariableGrlexRing.getBaseField().getIdentity(), (new int [] {3,2})));
    t1.add(mTwoVariableGrlexRing.createTerm(negone, (new int [] {2,3})));
    t1.add(mTwoVariableGrlexRing.createTerm(mTwoVariableGrlexRing.getBaseField().getIdentity(), (new int [] {1,0})));
    t2.add(mTwoVariableGrlexRing.createTerm(mTwoVariableGrlexRing.getBaseField().create(3), (new int [] {4,1})));
    t2.add(mTwoVariableGrlexRing.createTerm(mTwoVariableGrlexRing.getBaseField().getIdentity(), (new int [] {0,2})));
    t3.add(mTwoVariableGrlexRing.createTerm(negone, (new int [] {3,3})));
    t3.add(mTwoVariableGrlexRing.createTerm(mTwoVariableGrlexRing.getBaseField().getIdentity(), (new int [] {2,0})));
    t3.add(mTwoVariableGrlexRing.createTerm(mTwoVariableGrlexRing.getBaseField().create(-1,3), (new int [] {0,3})));

    Polynomial<RationalNumber,RationalField> poly1 = mTwoVariableGrlexRing.createPolynomial(t1);
    Polynomial<RationalNumber,RationalField> poly2 = mTwoVariableGrlexRing.createPolynomial(t2);
    Polynomial<RationalNumber,RationalField> poly3 = mTwoVariableGrlexRing.createPolynomial(t3);

    assertTrue(poly3.equals(poly1.sPair(poly2).getSyzygyPair()));
  }

  public void testGroebner()
  {
    Vector<Polynomial<RationalNumber,RationalField>> list = 
      new Vector<Polynomial<RationalNumber,RationalField>>();
    list.add(mTwoVariableGrlexRing.createPolynomial("x_1^3 + (-2)*x_1*x_2"));
    list.add(mTwoVariableGrlexRing.createPolynomial("x_1^2*x_2 + (-2)*x_2^2 + x_1"));
    BuchbergerAlgorithm<RationalNumber,Term<RationalNumber,RationalField>,Polynomial<RationalNumber,RationalField>,Term<RationalNumber,RationalField>,Polynomial<RationalNumber,RationalField>> ba = 
      new BuchbergerAlgorithm<RationalNumber,Term<RationalNumber,RationalField>,Polynomial<RationalNumber,RationalField>,Term<RationalNumber,RationalField>,Polynomial<RationalNumber,RationalField>>(list);
    
    // Here we are computing a reduced Groebner Basis so we have a uniquely determined
    // basis.
    assertTrue(ba.getStandardBasis().size() == 3);
    assertTrue(ba.getStandardBasis().contains(mTwoVariableGrlexRing.createPolynomial("x_1^2")));
    assertTrue(ba.getStandardBasis().contains(mTwoVariableGrlexRing.createPolynomial("x_1*x_2")));
    assertTrue(ba.getStandardBasis().contains(mTwoVariableGrlexRing.createPolynomial("x_2^2 + (-1/2)*x_1")));

    list = new Vector<Polynomial<RationalNumber,RationalField>>();
    list.add(mTwoVariableRingXY.createPolynomial("(-3) + 2*y + (-1/2)*y^2 + x"));
    list.add(mTwoVariableRingXY.createPolynomial("(-16) + 20*y + (-8)*y^2 + y^3"));
    list.add(mTwoVariableRingXY.createPolynomial("(-205/29791) + (1/961)*y + (27/29791)*x"));

    ba = new BuchbergerAlgorithm<RationalNumber,Term<RationalNumber,RationalField>,Polynomial<RationalNumber,RationalField>,Term<RationalNumber,RationalField>,Polynomial<RationalNumber,RationalField>>(list);
    assertEquals(2, ba.getStandardBasis().size());
    assertTrue(ba.getStandardBasis().contains(mTwoVariableRingXY.createPolynomial("x + (-3)")));
    assertTrue(ba.getStandardBasis().contains(mTwoVariableRingXY.createPolynomial("y + (-4)")));
  }

  public void testDifferentiate()
  {
    assertTrue(mTwoVariableGrlexRing.createPolynomial("x_1^3 + (-2)*x_1*x_2").differentiate(new int [] {2, 0}).equals(mTwoVariableGrlexRing.createPolynomial("6*x_1")));
    assertTrue(mTwoVariableGrlexRing.createPolynomial("x_1^3 + (-2)*x_1*x_2").differentiate(new int [] {0, 1}).equals(mTwoVariableGrlexRing.createPolynomial("(-2)*x_1")));
    assertTrue(mTwoVariableRingXY.createPolynomial("x^3 + (-2)*x*y").differentiate(new int [] {0, 1}).equals(mTwoVariableRingXY.createPolynomial("(-2)*x")));
    assertTrue(mTwoVariableRingXY.createPolynomial("3*x^3*y^4 + (-1)*x*y^2 + y^3").differentiate(new int [] {0, 1}).equals(mTwoVariableRingXY.createPolynomial("12*x^3*y^3 + (-2)*x*y + 3*y^2")));
    assertTrue(mTwoVariableRingXY.createPolynomial("3*x^3*y^4 + (-1)*x*y^2 + y^3").differentiate(new int [] {2, 1}).equals(mTwoVariableRingXY.createPolynomial("72*x*y^3")));
  }

  public void testSyzygy()
  {
    // Example: Eisenbud "Commutative Algebra with a View Toward Algebraic Geometry" pg 339.
    Vector<Polynomial<RationalNumber,RationalField>> v = new Vector<Polynomial<RationalNumber,RationalField>>();
    v.add(mTwoVariableRingXY.createPolynomial("x^2"));
    v.add(mTwoVariableRingXY.createPolynomial("x*y + y^2"));
    Syzygy<RationalNumber,Term<RationalNumber,RationalField>,Polynomial<RationalNumber,RationalField>,Term<RationalNumber,RationalField>,Polynomial<RationalNumber,RationalField>> syz = new Syzygy<RationalNumber,Term<RationalNumber,RationalField>,Polynomial<RationalNumber,RationalField>,Term<RationalNumber,RationalField>,Polynomial<RationalNumber,RationalField>>(v);

    System.out.println(syz);
  }

  public static junit.framework.Test suite()
  {
    junit.framework.TestSuite s = new junit.framework.TestSuite();
    s.addTest(new PolynomialTest("testEquals"));
    s.addTest(new PolynomialTest("testBuilder"));
    s.addTest(new PolynomialTest("testParser"));
    s.addTest(new PolynomialTest("testAdd"));
    s.addTest(new PolynomialTest("testSubtract"));
    s.addTest(new PolynomialTest("testInitialTerm"));
    s.addTest(new PolynomialTest("testMultiply"));
    s.addTest(new PolynomialTest("testDivide"));
    s.addTest(new PolynomialTest("testSPair"));
    s.addTest(new PolynomialTest("testGroebner"));
    s.addTest(new PolynomialTest("testDifferentiate"));
    s.addTest(new PolynomialTest("testSyzygy"));
    return s;    
  }
}
