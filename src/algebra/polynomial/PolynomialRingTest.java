package algebra.polynomial;

import algebra.field.RationalField;
import algebra.field.RationalNumber;
import algebra.monomial.MonomialOrderClass;
import algebra.Symbol;
import algebra.SymbolList;
import java.util.Vector;

public class PolynomialRingTest extends junit.framework.TestCase
{
  public PolynomialRingTest(String name)
  {
    super(name);
  }

  public void testSymbolTable()
  {
    SymbolTable sym = new SymbolTable(3);
    assertTrue(1 == sym.getPosition(Symbol.create("x_2")));
  }

  public void testCreate()
  {
    PolynomialRing<RationalNumber,RationalField> ring = new PolynomialRing<RationalNumber,RationalField>(new RationalField(), 3);
//     PolynomialRing<RationalNumber,RationalField> ring1 = new PolynomialRing<RationalNumber,RationalField>(new RationalField(), MonomialOrderClass.grevlexOrder().create(new int [] {0,1}));
  }

  public static junit.framework.Test suite()
  {
    junit.framework.TestSuite s = new junit.framework.TestSuite();
    s.addTest(new PolynomialRingTest("testSymbolTable"));
    s.addTest(new PolynomialRingTest("testCreate"));
    return s;    
  }
}

