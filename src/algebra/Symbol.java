package algebra;

/**
 * Represents a canonicalized string that can be compared quickly for equality by object identity comparison rather than
 * a more expensive string compare.
 */
public class Symbol
{
  private String mName;
  private Symbol(String name) { mName = name; }
  /**
   * The string underlying the symbol.
   * 
   * @return the string that the symbol represents.
   */
  public String toString() { return mName; }

  private static java.util.Hashtable mHash = new java.util.Hashtable();
  /**
   * Create the symbol associated with a string.  May be called concurrently from multiple threads.
   * Will create a symbol if none is already associated with the string value, otherwise returns the already
   * create symbol.
   *
   * @param name the string whose representing Symbol we retrieve
   * @return the symbol associated with the string.
   */
  public static Symbol create(String name)
  {
    String internStr = name.intern();
    synchronized (mHash)
    {
      Symbol sym = (Symbol) mHash.get(internStr);
      if (null == sym) 
      {
	sym = new Symbol(internStr);
	mHash.put(internStr, sym);
      }
      return sym;
    }
  }
}
