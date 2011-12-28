package algebra.monomial;

public interface MonomialBase
{
  // Monomial Orders need access to the individual
  // exponents.
  public int getExponent(int i);
  public int getNumExponents();
  public int getDegree();
  public MonomialBase getDehomogenization();
}
