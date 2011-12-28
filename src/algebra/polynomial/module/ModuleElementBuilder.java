package algebra.polynomial.module;

import algebra.Symbol;
import algebra.monomial.ModuleMonomial;
import algebra.term.ModuleTerm;
import algebra.polynomial.Polynomial;

public class ModuleElementBuilder<_Coeff extends algebra.generic.FieldElement<_Coeff>, _Field extends algebra.generic.Field<_Coeff>> implements algebra.PolynomialBuilder
{
  private java.util.HashMap<Symbol,Integer>  mMonomialBuilder = new java.util.HashMap<Symbol,Integer>();
  private java.util.Vector<ModuleTerm<_Coeff,_Field>> mPolynomialBuilder = new java.util.Vector<ModuleTerm<_Coeff,_Field>>();
  private ModuleMonomial mMonomial=null;
  private _Coeff mCoefficient=null;
  private FreePolynomialModule<_Coeff,_Field> mModule;
  private FreePolynomialModuleElement<_Coeff,_Field> mModuleElement;
  private int mCurrentPosition=0;

  public ModuleElementBuilder(FreePolynomialModule<_Coeff,_Field> module)
  {
    mModule = module;
  }

  public FreePolynomialModuleElement<_Coeff,_Field> getModuleElement()
  {
    return mModuleElement;
  }

  public void buildModule()
  {
    mModuleElement = mModule.create(mPolynomialBuilder);
  }

  /**
   * Take all of the terms built since the last call buildPolynomial and
   * create a polynomial out of them
   */
  public void buildPolynomial()
  {
    mCurrentPosition++;
  }

  /**
   * Take the most recently created coefficient and monomial and
   * make a term out of them.
   */
  public void buildTerm()
  {
    mPolynomialBuilder.add(mModule.createTerm(mCoefficient, mMonomial));
  }

  /**
   * takes all single variable monomials that have been 
   * built since the last call and creates a monomial from
   * them.
   */
  public void buildMonomial()
  {
    mMonomial = mModule.createMonomial(mMonomialBuilder, mCurrentPosition);
    mMonomialBuilder.clear();
  }

  public void buildSingleVariableMonomial(Symbol var, int exp)
  {
    mMonomialBuilder.put(var, new Integer(exp));
  }

  public void buildCoefficient(int i)
  {
    mCoefficient = mModule.getBaseField().create(i);
  }

  public void buildCoefficient(int i, int j)
  {
    mCoefficient = mModule.getBaseField().create(i, j);
  }
}
