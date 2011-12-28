package algebra.monomial;

public class DifferentiatedMonomial<_Monomial>
{
  private _Monomial mMonomial;
  public _Monomial getMonomial()
  {
    return mMonomial;
  }

  private int mCoefficient;
  public int getCoefficient()
  {
    return mCoefficient;
  }

  public boolean isZero()
  {
    return mCoefficient == 0;
  }

  public boolean equals(DifferentiatedMonomial m)
  {
    return mCoefficient == m.getCoefficient() && mMonomial.equals(m.getMonomial());
  }

  public DifferentiatedMonomial(_Monomial m, int c)
  {
    mMonomial = m;
    mCoefficient = c;
  }
}
