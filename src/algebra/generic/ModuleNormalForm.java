package algebra.generic;

public interface ModuleNormalForm<RT, R, MT, M> 
{
  public M getRemainder();
  public R getFactor(M dividend);
}
