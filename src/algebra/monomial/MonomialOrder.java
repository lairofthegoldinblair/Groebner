package algebra.monomial;

/**
 * TODO: A monomial order is only meaningful attached to a ring.
 * On the other hand, the basic notion of having a lexicographic
 * or graded reverse lexicographic order needs to be captured.
 * Perhaps the right way to do this is through some kind of 
 * strategy pattern.  For the moment, I am using the MonomialOrderClass 
 * to capture the concept.  This might be sufficient; however note that
 * the class is also being represented in some part by the Java 
 * class of the MonomialOrder.
 */
public abstract class MonomialOrder implements java.util.Comparator<MonomialBase>
{
  abstract public int compare(MonomialBase lhs, MonomialBase rhs);

  abstract public int getNumVars();

  abstract public MonomialOrderClass getOrderClass();
}
