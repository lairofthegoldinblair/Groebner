package algebra;

/**
 * Interface encapsuolating the builder pattern for terms, polynomials and modules.
 * Note this makes no assumptions about the commutativity of the base ring so may be used
 * to build elements of Weyl algebras and other noncommutative structure we are interested in.
 */
public interface PolynomialBuilder
{
  /**
   * Take all of the polynomials build since last call to buildModule and
   * create a module element out of them.
   */
  public void buildModule();

  /**
   * Take all of the terms built since the last call buildPolynomial and
   * create a polynomial out of them
   */
  public void buildPolynomial();

  /**
   * Take the most recently created coefficient and monomial and
   * make a term out of them.
   */
  public void buildTerm();

  /**
   * Take all single variable monomials that have been 
   * built since the last call and creates a monomial from
   * them.
   */
  public void buildMonomial();

  /**
   * Construct a single variable monomial 
   *
   * @param var the Symbol representing the variable 
   * @param exp the power/exponent of the variable
   */
  public void buildSingleVariableMonomial(Symbol var, int exp);

  /** 
   * Build a coefficient from an integer literal.  Assumes some mapping
   * of the ring of integers into the base ring of the polynomials.
   *
   * @param i literal value of the coefficient
   */
  public void buildCoefficient(int i);

  /** 
   * Build a coefficient from a pair of integer literals interpreted as a quotient.  Assumes some mapping
   * of the field of rational numbers into the base ring of the polynomials.
   * @param i literal value of the numerator of the coefficient
   * @param j literal value of the denominator of the coefficient
   */
  public void buildCoefficient(int i, int j);
}
