package algebra.polynomial.module;

import algebra.field.RationalField;
import algebra.field.RationalNumber;
import algebra.monomial.MonomialOrderClass;
import algebra.Symbol;
import algebra.SymbolList;
import java.util.Vector;
import java.util.List;
import java.util.ArrayList;
import algebra.generic.BuchbergerAlgorithm;
import algebra.generic.Syzygy;
import algebra.term.Term;
import algebra.term.ModuleTerm;

import algebra.polynomial.Polynomial;
import algebra.polynomial.PolynomialRing;

public class FreePolynomialModuleTest extends junit.framework.TestCase
{
  public FreePolynomialModuleTest(String name)
  {
    super(name);
  }

  PolynomialRing<RationalNumber,RationalField> mTwoVariableGrlexRing;
  PolynomialRing<RationalNumber,RationalField> mTwoVariableGrevlexRing;
  PolynomialRing<RationalNumber,RationalField> mTwoVariableRing;
  PolynomialRing<RationalNumber,RationalField> mTwoVariableRingXY;

  FreePolynomialModule<RationalNumber,RationalField> mTwoVariableGrlexModule;
  FreePolynomialModule<RationalNumber,RationalField> mTwoVariableGrevlexModule;
  FreePolynomialModule<RationalNumber,RationalField> mTwoVariableModule;
  FreePolynomialModule<RationalNumber,RationalField> mTwoVariableModuleXY;
  FreePolynomialModule<RationalNumber,RationalField> mTwoVariableRank2ModuleXY;

  FreePolynomialModuleElement<RationalNumber,RationalField> p1;
  FreePolynomialModuleElement<RationalNumber,RationalField> p2;
  FreePolynomialModuleElement<RationalNumber,RationalField> p3;
  FreePolynomialModuleElement<RationalNumber,RationalField> p4;
  FreePolynomialModuleElement<RationalNumber,RationalField> p5;
  FreePolynomialModuleElement<RationalNumber,RationalField> p6;
  FreePolynomialModuleElement<RationalNumber,RationalField> p7;
  FreePolynomialModuleElement<RationalNumber,RationalField> p8;

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

    mTwoVariableGrevlexModule = new FreePolynomialModule<RationalNumber,RationalField>(mTwoVariableGrevlexRing, 1);
    mTwoVariableGrlexModule = new FreePolynomialModule<RationalNumber,RationalField>(mTwoVariableGrlexRing, 1);
    mTwoVariableModule = new FreePolynomialModule<RationalNumber,RationalField>(mTwoVariableRing, 1);
    mTwoVariableModuleXY = new FreePolynomialModule<RationalNumber,RationalField>(mTwoVariableRingXY, 1);
    mTwoVariableRank2ModuleXY = new FreePolynomialModule<RationalNumber,RationalField>(mTwoVariableRingXY, 2);

    Vector<ModuleTerm<RationalNumber,RationalField>> t1 = new Vector<ModuleTerm<RationalNumber,RationalField>>();
    Vector<ModuleTerm<RationalNumber,RationalField>> t2 = new Vector<ModuleTerm<RationalNumber,RationalField>>();
    Vector<ModuleTerm<RationalNumber,RationalField>> t3 = new Vector<ModuleTerm<RationalNumber,RationalField>>();
    t1.add(mTwoVariableModule.createTerm(mTwoVariableModule.getBaseField().create(4,1), (new int [] {3,2}), 0));
    t1.add(mTwoVariableModule.createTerm(mTwoVariableModule.getBaseField().create(3,1), (new int [] {1,3}), 0));
    t2.add(mTwoVariableModule.createTerm(mTwoVariableModule.getBaseField().create(4,1), (new int [] {3,2}), 0));
    t2.add(mTwoVariableModule.createTerm(mTwoVariableModule.getBaseField().create(4,1), (new int [] {3,6}), 0));
    t2.add(mTwoVariableModule.createTerm(mTwoVariableModule.getBaseField().create(4,3), (new int [] {1,2}), 0));
    t3.add(mTwoVariableModule.createTerm(mTwoVariableModule.getBaseField().create(4,1), (new int [] {3,2}), 0));
    t3.add(mTwoVariableModule.createTerm(mTwoVariableModule.getBaseField().create(4,1), (new int [] {3,6}), 0));
    t3.add(mTwoVariableModule.createTerm(mTwoVariableModule.getBaseField().create(4,3), (new int [] {1,2}), 0));

    p1 = mTwoVariableModule.create(t1);
    // 4*x^3*y^2 + 4*x^3*y^6 + (4/3)*x^1*y^2
    p2 = mTwoVariableModule.create(t2);
    p3 = mTwoVariableModule.create(t3);

    t1 = new Vector<ModuleTerm<RationalNumber,RationalField>>();
    t2 = new Vector<ModuleTerm<RationalNumber,RationalField>>();
    t3 = new Vector<ModuleTerm<RationalNumber,RationalField>>();
    t1.add(mTwoVariableGrevlexModule.createTerm(mTwoVariableGrevlexModule.getBaseField().create(4,1), (new int [] {3,2}), 0));
    t1.add(mTwoVariableGrevlexModule.createTerm(mTwoVariableGrevlexModule.getBaseField().create(3,1), (new int [] {1,3}), 0));
    t2.add(mTwoVariableGrevlexModule.createTerm(mTwoVariableGrevlexModule.getBaseField().create(4,1), (new int [] {6,2}), 0));
    t2.add(mTwoVariableGrevlexModule.createTerm(mTwoVariableGrevlexModule.getBaseField().create(4,1), (new int [] {3,5}), 0));
    t2.add(mTwoVariableGrevlexModule.createTerm(mTwoVariableGrevlexModule.getBaseField().create(4,3), (new int [] {1,2}), 0));
    t3.add(mTwoVariableGrevlexModule.createTerm(mTwoVariableGrevlexModule.getBaseField().create(4,1), (new int [] {3,2}), 0));
    t3.add(mTwoVariableGrevlexModule.createTerm(mTwoVariableGrevlexModule.getBaseField().create(4,1), (new int [] {3,6}), 0));
    t3.add(mTwoVariableGrevlexModule.createTerm(mTwoVariableGrevlexModule.getBaseField().create(4,3), (new int [] {1,2}), 0));

    p4 = mTwoVariableGrevlexModule.create(t1);
    p5 = mTwoVariableGrevlexModule.create(t2);
    p6 = mTwoVariableGrevlexModule.create(t3);

    t1 = new Vector<ModuleTerm<RationalNumber,RationalField>>();
    t2 = new Vector<ModuleTerm<RationalNumber,RationalField>>();
    t1.add(mTwoVariableModuleXY.createTerm(mTwoVariableModuleXY.getBaseField().create(4,1), (new int [] {3,2}), 0));
    t1.add(mTwoVariableModuleXY.createTerm(mTwoVariableModuleXY.getBaseField().create(3,1), (new int [] {1,3}), 0));
    t2.add(mTwoVariableModuleXY.createTerm(mTwoVariableModuleXY.getBaseField().create(8,1), (new int [] {3,2}), 0));
    t2.add(mTwoVariableModuleXY.createTerm(mTwoVariableModuleXY.getBaseField().create(6,1), (new int [] {1,3}), 0));
    p7 = mTwoVariableModuleXY.create(t1);
    p8 = mTwoVariableModuleXY.create(t1);
  }

  protected void tearDown()
  {
    mTwoVariableGrevlexModule = null;
    mTwoVariableGrlexModule = null;
    mTwoVariableModule = null;
    mTwoVariableModuleXY = null;
    mTwoVariableRank2ModuleXY = null;
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

    FreePolynomialModuleElement<RationalNumber,RationalField> foo = mTwoVariableModuleXY.create("{x*y}");
    FreePolynomialModuleElement<RationalNumber,RationalField> bar = mTwoVariableModuleXY.create("{2*x*y}");
    boolean result = foo.equals(bar);
    assertTrue(!result);

    assertTrue(p3.isScalarMultiple(p2));
    assertTrue(p7.isScalarMultiple(p8));
    assertTrue(!p1.isScalarMultiple(p2));

    foo = mTwoVariableRank2ModuleXY.create("{x*y, x*y + x^3}");
    bar = mTwoVariableRank2ModuleXY.create("{2*x*y, x*y + x^3}");
    assertTrue(!foo.equals(bar));
  }

  public void testCreate()
  {
    java.util.Vector<Polynomial<RationalNumber,RationalField>> v = 
      new java.util.Vector<Polynomial<RationalNumber,RationalField>>();
    v.add(mTwoVariableRingXY.createPolynomial("x^2*y + 2*y^2"));
    v.add(mTwoVariableRingXY.createPolynomial("x^3 + 2*x*y^2"));
    assertEquals(mTwoVariableRank2ModuleXY.create("{x^2*y + 2*y^2, x^3 + 2*x*y^2}"),
		 mTwoVariableRank2ModuleXY.create(v));
  }

  public void testBuilder()
  {
    ModuleElementBuilder<RationalNumber,RationalField> builder = new ModuleElementBuilder<RationalNumber,RationalField>(mTwoVariableModule);
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
    builder.buildModule();

    assertTrue(p1.equals(builder.getModuleElement()));
  }

  public void testParser()
  {
    FreePolynomialModuleElement<RationalNumber,RationalField> p = mTwoVariableModule.create("{4*x_1^3*x_2^2 + 3*x_1*x_2^3}");
    assertTrue(p.equals(p1));
    p = mTwoVariableModuleXY.create("{4*x^3*y^2 + (6/2)*x*y^3}");
    assertTrue(p.equals(p7));
  }

  public void testAdd()
  {
    Vector<ModuleTerm<RationalNumber,RationalField>> t = new Vector<ModuleTerm<RationalNumber,RationalField>>();
    t.add(mTwoVariableModule.createTerm(mTwoVariableModule.getBaseField().create(8,1), (new int [] {3,2}), 0));
    t.add(mTwoVariableModule.createTerm(mTwoVariableModule.getBaseField().create(4,1), (new int [] {3,6}), 0));
    t.add(mTwoVariableModule.createTerm(mTwoVariableModule.getBaseField().create(4,3), (new int [] {1,2}), 0));
    t.add(mTwoVariableModule.createTerm(mTwoVariableModule.getBaseField().create(3,1), (new int [] {1,3}), 0));

    assertTrue(mTwoVariableModule.create(t).equals(p1.add(p2)));
    assertTrue(mTwoVariableModule.create(t).equals(p1.add(p3)));
 
    FreePolynomialModuleElement<RationalNumber,RationalField> a = mTwoVariableRank2ModuleXY.create("{x*y, x^3 + y^2}");
    FreePolynomialModuleElement<RationalNumber,RationalField> b = mTwoVariableRank2ModuleXY.create("{x*y^2, x + y^2}");
    FreePolynomialModuleElement<RationalNumber,RationalField> c = mTwoVariableRank2ModuleXY.create("{x*y + x*y^2, x + x^3 + 2*y^2}");
    assertEquals(c, a.add(b));
    assertEquals(c, b.add(a));
 }

  public void testSubtract()
  {
    Vector<ModuleTerm<RationalNumber,RationalField>> t = new Vector<ModuleTerm<RationalNumber,RationalField>>();
    t.add(mTwoVariableModule.createTerm(mTwoVariableModule.getBaseField().create(3,1), (new int [] {1,3}), 0));
    t.add(mTwoVariableModule.createTerm(mTwoVariableModule.getBaseField().create(-4,1), (new int [] {3,6}), 0));
    t.add(mTwoVariableModule.createTerm(mTwoVariableModule.getBaseField().create(-4,3), (new int [] {1,2}), 0));

    assertTrue(mTwoVariableModule.create(t).equals(p1.subtract(p2)));
    assertTrue(mTwoVariableModule.create(t).equals(p1.subtract(p3)));

    FreePolynomialModuleElement<RationalNumber,RationalField> a = mTwoVariableRank2ModuleXY.create("{x*y, x^3 + y^2}");
    FreePolynomialModuleElement<RationalNumber,RationalField> b = mTwoVariableRank2ModuleXY.create("{x*y^2, x + y^2}");
    FreePolynomialModuleElement<RationalNumber,RationalField> c = mTwoVariableRank2ModuleXY.create("{x*y + (-1)*x*y^2, (-1)*x + x^3}");
    assertEquals(c, a.subtract(b));
  }

  public void testInitialTerm()
  {
    assertTrue(p1.getInitialTerm().equals(mTwoVariableModule.createTerm(mTwoVariableModule.getBaseField().create(4,1), (new int [] {3,2}), 0)));
    assertTrue(p2.getInitialTerm().equals(mTwoVariableModule.createTerm(mTwoVariableModule.getBaseField().create(4,1), (new int [] {3,6}), 0)));
    assertTrue(p4.getInitialTerm().equals(mTwoVariableGrevlexModule.createTerm(mTwoVariableGrevlexModule.getBaseField().create(4,1), (new int [] {3,2}), 0)));
    assertEquals(mTwoVariableGrevlexModule.createTerm(mTwoVariableGrevlexModule.getBaseField().create(4,1), (new int [] {6,2}), 0), p5.getInitialTerm());
    assertTrue(p1.getInitialMonomial().equals(mTwoVariableModule.createMonomial(new int [] {3,2}, 0)));
    assertTrue(p2.getInitialMonomial().equals(mTwoVariableModule.createMonomial(new int [] {3,6}, 0)));
    assertTrue(p4.getInitialMonomial().equals(mTwoVariableGrevlexModule.createMonomial(new int [] {3,2}, 0)));
    assertTrue(p5.getInitialMonomial().equals(mTwoVariableGrevlexModule.createMonomial(new int [] {6,2}, 0)));
    assertTrue(p1.getInitialCoefficient().equals(mTwoVariableModule.getBaseField().create(4,1)));
    assertTrue(p2.getInitialCoefficient().equals(mTwoVariableModule.getBaseField().create(4,1)));
    assertTrue(p4.getInitialCoefficient().equals(mTwoVariableGrevlexModule.getBaseField().create(4,1)));
    assertTrue(p5.getInitialCoefficient().equals(mTwoVariableGrevlexModule.getBaseField().create(4,1)));

    // Right now we have hard coded the position over term order (with position 0 smallest)
    assertEquals(mTwoVariableRank2ModuleXY.create("{x^2*y^3, x^3*y^2}").getInitialTerm(),
		 mTwoVariableRank2ModuleXY.createTerm(mTwoVariableRank2ModuleXY.getRing().getBaseField().getIdentity(),
						      new int [] {3,2},
						      1));
    // Right now we have hard coded the position over term order
    assertEquals(mTwoVariableRank2ModuleXY.create("{x^2*y^3, 0}").getInitialTerm(),
		 mTwoVariableRank2ModuleXY.createTerm(mTwoVariableRank2ModuleXY.getRing().getBaseField().getIdentity(),
						      new int [] {2,3},
						      0));
  }

  public void testMultiply()
  {
    Vector<ModuleTerm<RationalNumber,RationalField>> t = new Vector<ModuleTerm<RationalNumber,RationalField>>();
    t.add(mTwoVariableModule.createTerm(mTwoVariableModule.getBaseField().create(12,7), (new int [] {5,6}), 0));
    t.add(mTwoVariableModule.createTerm(mTwoVariableModule.getBaseField().create(12,7), (new int [] {5,10}), 0));
    t.add(mTwoVariableModule.createTerm(mTwoVariableModule.getBaseField().create(4,7), (new int [] {3,6}), 0));
    FreePolynomialModuleElement<RationalNumber,RationalField> result = mTwoVariableModule.create(t);

    // ( 4*x^3*y^2 + 4*x^3*y^6 + (4/3)*x^1*y^2 ) * (3/7)*x^2*y^4
    assertTrue(result.equals(p2.multiply(mTwoVariableRing.createTerm(mTwoVariableModule.getBaseField().create(3,7), (new int [] {2,4})))));
  }

  public void testDivide()
  {
    // Here is an example from pg. 80 of Cox, Little, O'Shea
    FreePolynomialModule<RationalNumber,RationalField> r = mTwoVariableModuleXY;
    Vector<FreePolynomialModuleElement<RationalNumber,RationalField>> list = new Vector<FreePolynomialModuleElement<RationalNumber,RationalField>>();
    list.add(r.create("{x^2*y + (-1)*y^2}"));
    list.add(r.create("{x^4*y^2 + (-1)*y^2}"));
    FreePolynomialModuleElement<RationalNumber,RationalField> p = r.create("{x^5*y}");
    algebra.generic.GenericNormalForm<RationalNumber, Term<RationalNumber,RationalField>, Polynomial<RationalNumber, RationalField>, ModuleTerm<RationalNumber,RationalField>, FreePolynomialModuleElement<RationalNumber, RationalField>> nf = p.getNormalForm(list);
    FreePolynomialModuleElement<RationalNumber,RationalField> result = r.create("{x*y^3}");
    assertTrue(result.equals(nf.getRemainder()));

    // Pg. 64 of Cox, Little, O'Shea
    list.clear();
    list.add(r.create("{y^2 + (-1)}"));
    list.add(r.create("{x*y + (-1)}"));
    p = r.create("{x^2*y + x*y^2 + y^2}");

    nf = p.getNormalForm(list);
    assertTrue(nf.getRemainder().equals(r.create("{2*x + 1}")));

    // Pg. 61 of Cox, Little, O'Shea
    list.clear();
    list.add(r.create("{x*y + (-1)}"));
    list.add(r.create("{y^2 + (-1)}"));
    p = r.create("{x^2*y + x*y^2 + y^2}");

    nf = p.getNormalForm(list);
    assertTrue(nf.getRemainder().equals(r.create("{x + y + 1}")));
  }

  /**
   * Test case from pg. 81 of Cox, Little, O'Shea
   */
  public void testSPair()
  {
    Vector<ModuleTerm<RationalNumber,RationalField>> t1 = new Vector<ModuleTerm<RationalNumber,RationalField>>();
    Vector<ModuleTerm<RationalNumber,RationalField>> t2 = new Vector<ModuleTerm<RationalNumber,RationalField>>();
    Vector<ModuleTerm<RationalNumber,RationalField>> t3 = new Vector<ModuleTerm<RationalNumber,RationalField>>();

    RationalNumber one = mTwoVariableGrlexModule.getBaseField().getIdentity();
    RationalNumber negone = one.negate();
//     RationalNumber bug = mTwoVariableGrlexModule.getBaseField().getIdentity().negate();

    t1.add(mTwoVariableGrlexModule.createTerm(mTwoVariableGrlexModule.getBaseField().getIdentity(), (new int [] {3,2}), 0));
    t1.add(mTwoVariableGrlexModule.createTerm(negone, (new int [] {2,3}), 0));
    t1.add(mTwoVariableGrlexModule.createTerm(mTwoVariableGrlexModule.getBaseField().getIdentity(), (new int [] {1,0}), 0));
    t2.add(mTwoVariableGrlexModule.createTerm(mTwoVariableGrlexModule.getBaseField().create(3), (new int [] {4,1}), 0));
    t2.add(mTwoVariableGrlexModule.createTerm(mTwoVariableGrlexModule.getBaseField().getIdentity(), (new int [] {0,2}), 0));
    t3.add(mTwoVariableGrlexModule.createTerm(negone, (new int [] {3,3}), 0));
    t3.add(mTwoVariableGrlexModule.createTerm(mTwoVariableGrlexModule.getBaseField().getIdentity(), (new int [] {2,0}), 0));
    t3.add(mTwoVariableGrlexModule.createTerm(mTwoVariableGrlexModule.getBaseField().create(-1,3), (new int [] {0,3}), 0));

    FreePolynomialModuleElement<RationalNumber,RationalField> poly1 = mTwoVariableGrlexModule.create(t1);
    FreePolynomialModuleElement<RationalNumber,RationalField> poly2 = mTwoVariableGrlexModule.create(t2);
    FreePolynomialModuleElement<RationalNumber,RationalField> poly3 = mTwoVariableGrlexModule.create(t3);

    assertTrue(poly3.equals(poly1.sPair(poly2).getSyzygyPair()));
  }

  public void testGroebner()
  {
    Vector<FreePolynomialModuleElement<RationalNumber,RationalField>> list = new Vector<FreePolynomialModuleElement<RationalNumber,RationalField>>();

    list.add(mTwoVariableGrlexModule.create("{x_1^3 + (-2)*x_1*x_2}"));
    list.add(mTwoVariableGrlexModule.create("{x_1^2*x_2 + (-2)*x_2^2 + x_1}"));

    BuchbergerAlgorithm<RationalNumber,Term<RationalNumber,RationalField>,Polynomial<RationalNumber,RationalField>,ModuleTerm<RationalNumber,RationalField>,FreePolynomialModuleElement<RationalNumber,RationalField>> gb;
    gb = new BuchbergerAlgorithm<RationalNumber,Term<RationalNumber,RationalField>,Polynomial<RationalNumber,RationalField>,ModuleTerm<RationalNumber,RationalField>,FreePolynomialModuleElement<RationalNumber,RationalField>>(list);

    // Here we are computing a reduced Groebner Basis so we have a uniquely determined
    // basis.
    assertEquals(3, gb.getStandardBasis().size());
    List<FreePolynomialModuleElement<RationalNumber,RationalField>> expected = new ArrayList<FreePolynomialModuleElement<RationalNumber,RationalField>>();
    expected.add(mTwoVariableGrlexModule.create("{x_1^2}"));
    expected.add(mTwoVariableGrlexModule.create("{x_1*x_2}"));
    expected.add(mTwoVariableGrlexModule.create("{x_2^2 + (-1/2)*x_1}"));
    assertTrue(expected.containsAll(gb.getStandardBasis()) && gb.getStandardBasis().containsAll(expected));

    list = new Vector<FreePolynomialModuleElement<RationalNumber,RationalField>>();;
    list.add(mTwoVariableModuleXY.create("{(-3) + 2*y + (-1/2)*y^2 + x}"));
    list.add(mTwoVariableModuleXY.create("{(-16) + 20*y + (-8)*y^2 + y^3}"));
    list.add(mTwoVariableModuleXY.create("{(-205/29791) + (1/961)*y + (27/29791)*x}"));
    gb = new BuchbergerAlgorithm<RationalNumber,Term<RationalNumber,RationalField>,Polynomial<RationalNumber,RationalField>,ModuleTerm<RationalNumber,RationalField>,FreePolynomialModuleElement<RationalNumber,RationalField>>(list);
    assertEquals(2, gb.getStandardBasis().size());
    expected = new ArrayList<FreePolynomialModuleElement<RationalNumber,RationalField>>();
    expected.add(mTwoVariableModuleXY.create("{x + (-3)}"));
    expected.add(mTwoVariableModuleXY.create("{y + (-4)}"));
    assertTrue(expected.containsAll(gb.getStandardBasis()) && gb.getStandardBasis().containsAll(expected));
  }



  public void testDifferentiate()
  {
    assertTrue(mTwoVariableGrlexModule.create("{x_1^3 + (-2)*x_1*x_2}").differentiate(new int [] {2, 0}).equals(mTwoVariableGrlexModule.create("{6*x_1}")));
    assertTrue(mTwoVariableGrlexModule.create("{x_1^3 + (-2)*x_1*x_2}").differentiate(new int [] {0, 1}).equals(mTwoVariableGrlexModule.create("{(-2)*x_1}")));
    assertTrue(mTwoVariableModuleXY.create("{x^3 + (-2)*x*y}").differentiate(new int [] {0, 1}).equals(mTwoVariableModuleXY.create("{(-2)*x}")));
    assertTrue(mTwoVariableModuleXY.create("{3*x^3*y^4 + (-1)*x*y^2 + y^3}").differentiate(new int [] {0, 1}).equals(mTwoVariableModuleXY.create("{12*x^3*y^3 + (-2)*x*y + 3*y^2}")));
    assertTrue(mTwoVariableModuleXY.create("{3*x^3*y^4 + (-1)*x*y^2 + y^3}").differentiate(new int [] {2, 1}).equals(mTwoVariableModuleXY.create("{72*x*y^3}")));
  }

  public void testSyzygy()
  {
    // Eisenbud pg 339.
    Vector<FreePolynomialModuleElement<RationalNumber,RationalField>> v = new Vector<FreePolynomialModuleElement<RationalNumber,RationalField>>();
    v.add(mTwoVariableModuleXY.create("{x^2}"));
    v.add(mTwoVariableModuleXY.create("{x*y + y^2}"));
    Syzygy<RationalNumber,Term<RationalNumber,RationalField>,Polynomial<RationalNumber,RationalField>,ModuleTerm<RationalNumber,RationalField>,FreePolynomialModuleElement<RationalNumber,RationalField>> syz = new Syzygy<RationalNumber,Term<RationalNumber,RationalField>,Polynomial<RationalNumber,RationalField>,ModuleTerm<RationalNumber,RationalField>,FreePolynomialModuleElement<RationalNumber,RationalField>>(v);

    System.out.println(syz);
  }

  public static junit.framework.Test suite()
  {
    junit.framework.TestSuite s = new junit.framework.TestSuite();
    s.addTest(new FreePolynomialModuleTest("testEquals"));
    s.addTest(new FreePolynomialModuleTest("testCreate"));
    s.addTest(new FreePolynomialModuleTest("testBuilder"));
    s.addTest(new FreePolynomialModuleTest("testParser"));
    s.addTest(new FreePolynomialModuleTest("testAdd"));
    s.addTest(new FreePolynomialModuleTest("testSubtract"));
    s.addTest(new FreePolynomialModuleTest("testInitialTerm"));
    s.addTest(new FreePolynomialModuleTest("testMultiply"));
    s.addTest(new FreePolynomialModuleTest("testDivide"));
    s.addTest(new FreePolynomialModuleTest("testSPair"));
    s.addTest(new FreePolynomialModuleTest("testGroebner"));
    s.addTest(new FreePolynomialModuleTest("testDifferentiate"));
    s.addTest(new FreePolynomialModuleTest("testSyzygy"));
    return s;    
  }
}
