package algebra.generic;

/**
 * A commutative ring is just a ring where the operations satisfy equations.
 * Doesn't seem to be any meaningful way of representing those constraints
 * in the Java type system.  Hmmmm.....
 */
public interface CommutativeRing<R> extends Ring<R>
{
}
