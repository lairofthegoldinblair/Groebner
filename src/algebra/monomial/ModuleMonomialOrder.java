package algebra.monomial;

public abstract class ModuleMonomialOrder implements java.util.Comparator<ModuleMonomial>
{
  abstract public int compare(ModuleMonomial lhs, ModuleMonomial rhs);

  abstract public int getNumVars();

  abstract public MonomialOrderClass getOrderClass();
}
