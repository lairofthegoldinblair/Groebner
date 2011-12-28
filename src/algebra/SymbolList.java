package algebra;

/**
 * An order list of Symbols.
 */
public class SymbolList extends java.util.Vector<Symbol>
{
  /**
   * Create an empty list of symbols.
   */
  public SymbolList()
  {
  }

  /**
   * Construct the symbol list out of canonicalization of the String array.
   *
   * @param sym the array of string to canonicalize to yield the list.
   */
  public SymbolList(String [] sym)
  {
    for(int i=0; i<sym.length; i++)
      {
	add(Symbol.create(sym[i]));
      }
  }

  /**
   * Return the sublist of elements that begin at index begin 
   * and end at index end.
   */
  public SymbolList sublist(int begin, int end)
  {
    SymbolList sl = new SymbolList();
    for (int i=begin; i<=end; i++)
      {
	sl.add(get(i));
      }
    return sl;
  }

  /** 
   * Return the sublist of symbols beginning at i
   */
  public SymbolList sublist(int i)
  {
    return sublist(i, size()-1);
  }
}

