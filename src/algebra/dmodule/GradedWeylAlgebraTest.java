package algebra.dmodule;

import java.util.Vector;
import java.util.ArrayList;
import java.util.Iterator;

import algebra.SymbolList;
import algebra.generic.BuchbergerAlgorithm;
import algebra.term.Term;
import algebra.monomial.MonomialOrderClass;
import algebra.field.RationalNumber;
import algebra.field.RationalField;

public class GradedWeylAlgebraTest extends junit.framework.TestCase
{
  public GradedWeylAlgebraTest(String name)
  {
    super(name);
  }

  GradedWeylAlgebra<RationalNumber,RationalField> mOneVariableRingX;
  GradedWeylAlgebra<RationalNumber,RationalField> mOneVariablePolyRingX;
  GradedWeylAlgebra<RationalNumber,RationalField> mTwoVariableRingXYWeight;

  protected void setUp()
  {
    mOneVariableRingX = new GradedWeylAlgebra<RationalNumber,RationalField>(new RationalField(), 
									    new SymbolList(new String [] {"x"}),
									    new boolean [] { true },
									    MonomialOrderClass.lexOrder());
    mOneVariablePolyRingX = new GradedWeylAlgebra<RationalNumber,RationalField>(new RationalField(), 
									    new SymbolList(new String [] {"x"}),
									    new boolean [] { false },
									    MonomialOrderClass.lexOrder());
    try {
      mTwoVariableRingXYWeight = new GradedWeylAlgebra<RationalNumber,RationalField>(new RationalField(), 
										     new int [] {0,0,1,1}, 
										     new SymbolList(new String [] {"x", "y"}), 
										     new boolean [] { true,true },
										     MonomialOrderClass.forName("WeightedRevLexOrder"));
    } catch(ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  protected void tearDown()
  {
    mOneVariableRingX = null;
    mOneVariablePolyRingX = null;
    mTwoVariableRingXYWeight = null;
  }

  public void testEquals()
  {
    GradedDifferentialOperator<RationalNumber,RationalField> do1 = mTwoVariableRingXYWeight.create("(-1) + 1*y*D_y + 1*x*D_x + 1*y^2*D_y^2 + 1*x^2*D_x^2");
    GradedDifferentialOperator<RationalNumber,RationalField> do2 = mTwoVariableRingXYWeight.create("(-1) + 1*x*y*D_x*D_y");
    GradedDifferentialOperator<RationalNumber,RationalField> do3 = mTwoVariableRingXYWeight.create("1*x*D_x + 3*y^2*D_y^2 + 1*y^3*D_y^3");
    GradedDifferentialOperator<RationalNumber,RationalField> do4 = mTwoVariableRingXYWeight.create("(-1) + 1*y*D_y + 1*x*D_x + 1*y^2*D_y^2 + 1*x^2*D_x^2");

    assertEquals(do1, do1);
    assertEquals(do2, do2);
    assertEquals(do3, do3);
    assertEquals(do4, do4);
    assertEquals(do1, do4);
    assertTrue(!do1.equals(do2));
  }

  public void testGradedMultiply()
  {
    GradedDifferentialOperator<RationalNumber,RationalField> do1 = mOneVariableRingX.create("x");
    GradedDifferentialOperator<RationalNumber,RationalField> do2 = mOneVariableRingX.create("D_x");
    GradedDifferentialOperator<RationalNumber,RationalField> do3 = mOneVariableRingX.create("x*D_x + 1");
    GradedDifferentialOperator<RationalNumber,RationalField> do4 = mOneVariableRingX.create("x*D_x");
    GradedDifferentialOperator<RationalNumber,RationalField> do5 = mOneVariableRingX.create("x*D_x^2 + D_x");
    GradedDifferentialOperator<RationalNumber,RationalField> do6 = mOneVariableRingX.create("x*D_x + (-2)");
    GradedDifferentialOperator<RationalNumber,RationalField> do7 = mOneVariableRingX.create("x*D_x + (-4)");
    GradedDifferentialOperator<RationalNumber,RationalField> do8 = mOneVariableRingX.create("x^2*D_x^2 + (-5)*x*D_x + 8");
    assertTrue(do1.rightMultiply(do2).equals(do4));
    assertTrue(do2.rightMultiply(do1).equals(do3));
    assertTrue(do2.rightMultiply(do4).equals(do5));
    assertTrue(do6.rightMultiply(do7).equals(do8));
    assertTrue(do7.rightMultiply(do6).equals(do8));
  }

  public void testGradedNormalForm()
  {
    GradedDifferentialOperator<RationalNumber,RationalField> dop1 = mOneVariableRingX.create("x^2*D_x^2 + (-5)*x*D_x + 8");
    GradedDifferentialOperator<RationalNumber,RationalField> dop2 = mOneVariableRingX.create("x*D_x^4 + (-1)*D_x^3");
    GradedDifferentialOperator<RationalNumber,RationalField> dop3 = mOneVariableRingX.create("D_x^3 + x^2*D_x^2");
    GradedDifferentialOperator<RationalNumber,RationalField> dop4 = mOneVariableRingX.create("x^2*D_x^5");
    java.util.Vector<GradedDifferentialOperator<RationalNumber,RationalField>> list = 
      new java.util.Vector<GradedDifferentialOperator<RationalNumber,RationalField>>();
    list.add(dop1);
    list.add(dop2);

    algebra.generic.GenericNormalForm<RationalNumber,Term<RationalNumber,RationalField>,GradedDifferentialOperator<RationalNumber, RationalField>,Term<RationalNumber,RationalField>,GradedDifferentialOperator<RationalNumber, RationalField>> nf;
    nf = dop3.getNormalForm(list);
    assertTrue(nf.getRemainder().equals(mOneVariableRingX.create("D_x^3 + 5*x*D_x + (-8)")));
    assertTrue(mOneVariableRingX.create("1").equals(nf.getFactor(dop1)));
    assertTrue(mOneVariableRingX.create("0").equals(nf.getFactor(dop2)));
    nf = dop4.getNormalForm(list);
    assertTrue(nf.getRemainder().empty());
    assertTrue(mOneVariableRingX.create("D_x^3").equals(nf.getFactor(dop1)));
    assertTrue(mOneVariableRingX.create("(-1)").equals(nf.getFactor(dop2)));
  }

  public void testSpair()
  {
    GradedDifferentialOperator<RationalNumber,RationalField> dop1 = mOneVariableRingX.create("D_x^2");
    GradedDifferentialOperator<RationalNumber,RationalField> dop2 = mOneVariableRingX.create("x*D_x + (-1)");
    assertTrue(dop1.sPair(dop2).getSyzygyPair().empty());
  }

  public void testGroebner()
  {
    Vector<GradedDifferentialOperator<RationalNumber,RationalField>> list = new Vector<GradedDifferentialOperator<RationalNumber,RationalField>>();
    list.add(mTwoVariableRingXYWeight.create("x^2*D_x^2 + x*D_x + y^2*D_y^2 + y*D_y + (-1)"));
    list.add(mTwoVariableRingXYWeight.create("x*y*D_x*D_y + (-1)"));
    BuchbergerAlgorithm<RationalNumber,Term<RationalNumber,RationalField>,GradedDifferentialOperator<RationalNumber,RationalField>,Term<RationalNumber,RationalField>,GradedDifferentialOperator<RationalNumber,RationalField>> ba = new BuchbergerAlgorithm<RationalNumber,Term<RationalNumber,RationalField>,GradedDifferentialOperator<RationalNumber,RationalField>,Term<RationalNumber,RationalField>,GradedDifferentialOperator<RationalNumber,RationalField>>(list);

    //    System.out.println("Weyl Algebra gb: " + gb.toString());
    assertEquals(3, ba.getStandardBasis().size());
    ArrayList<GradedDifferentialOperator<RationalNumber,RationalField>> expected = new ArrayList<GradedDifferentialOperator<RationalNumber,RationalField>>();
    expected.add(mTwoVariableRingXYWeight.create("(-1) + 1*y*D_y + 1*x*D_x + 1*y^2*D_y^2 + 1*x^2*D_x^2"));
    expected.add(mTwoVariableRingXYWeight.create("(-1) + 1*x*y*D_x*D_y"));
    expected.add(mTwoVariableRingXYWeight.create("1*x*D_x + 3*y^2*D_y^2 + 1*y^3*D_y^3"));
    assertTrue(expected.containsAll(ba.getStandardBasis()) && ba.getStandardBasis().containsAll(expected));
  }

  public void testGradedMultiply2()
  {
    GradedDifferentialOperator<RationalNumber,RationalField> do1 = mOneVariablePolyRingX.create("x");
    GradedDifferentialOperator<RationalNumber,RationalField> do2 = mOneVariablePolyRingX.create("E_x");
    GradedDifferentialOperator<RationalNumber,RationalField> do3 = mOneVariablePolyRingX.create("x*E_x");
    GradedDifferentialOperator<RationalNumber,RationalField> do4 = mOneVariablePolyRingX.create("x*E_x");
    GradedDifferentialOperator<RationalNumber,RationalField> do5 = mOneVariablePolyRingX.create("x*E_x^2");
    GradedDifferentialOperator<RationalNumber,RationalField> do6 = mOneVariablePolyRingX.create("x*E_x + (-2)");
    GradedDifferentialOperator<RationalNumber,RationalField> do7 = mOneVariablePolyRingX.create("x*E_x + (-4)");
    GradedDifferentialOperator<RationalNumber,RationalField> do8 = mOneVariablePolyRingX.create("x^2*E_x^2 + (-6)*x*E_x + 8");
    assertTrue(do1.rightMultiply(do2).equals(do4));
    assertTrue(do2.rightMultiply(do1).equals(do3));
    assertTrue(do2.rightMultiply(do4).equals(do5));
    assertTrue(do6.rightMultiply(do7).equals(do8));
    assertTrue(do7.rightMultiply(do6).equals(do8));
  }

  public static junit.framework.Test suite()
  {
    junit.framework.TestSuite s = new junit.framework.TestSuite();
    s.addTest(new GradedWeylAlgebraTest("testEquals"));
    s.addTest(new GradedWeylAlgebraTest("testGradedMultiply"));
    s.addTest(new GradedWeylAlgebraTest("testSpair"));
    s.addTest(new GradedWeylAlgebraTest("testGradedMultiply2"));
    s.addTest(new GradedWeylAlgebraTest("testGradedNormalForm"));
    s.addTest(new GradedWeylAlgebraTest("testGroebner"));
    return s;    
  }
}
