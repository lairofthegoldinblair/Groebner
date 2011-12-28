package algebra.generic;

/**
 * Interface for a generic monomial in a free module over a k-algebra.  
 * @param M The type of a monomial in the k-algebra.
 * @param FM The type of a monomial in the free module.
 */
public interface Monomial<M, FM>
{
  public M divide(FM fm);
  public FM lcm(FM fm);
  public FM multiply(M m);
  public int getDegree();
  public int getDegree(algebra.WeightVector w);
}

