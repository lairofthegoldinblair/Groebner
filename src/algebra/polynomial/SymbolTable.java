package algebra.polynomial;

import algebra.Symbol;
import algebra.SymbolList;

public class SymbolTable
{
  // This is a vector/index that allows one
  // to get the symbol at a given position.
  private SymbolList mSymbolList;
  // This is a map/index that allows one to get the
  // position associated with a symbol
  private java.util.HashMap<Symbol,Integer> mSymbolMap;

  public SymbolTable(SymbolList symbolList)
  {
    mSymbolList = symbolList;
    mSymbolMap = new java.util.HashMap<Symbol,Integer>();
    for(int i=0; i<mSymbolList.size(); i++)
    {
      mSymbolMap.put(mSymbolList.get(i), new Integer(i));
    }
  }

  // Create a symbol table with default symbols of the form "x_n"
  public SymbolTable(int nVars)
  {
    mSymbolList = new SymbolList();
    mSymbolMap = new java.util.HashMap<Symbol,Integer>();
    for(int i=0; i<nVars; i++)
    {
      Symbol sym = Symbol.create("x_" + (i + 1));
      mSymbolList.add(sym);
      mSymbolMap.put(sym, new Integer(i));
    }
  }

  public int getPosition(Symbol sym)
  {
    Integer i = mSymbolMap.get(sym);
    return i.intValue();
  }

  public Symbol getSymbol(int pos)
  {
    return mSymbolList.get(pos);
  }

  public SymbolList getSymbolList()
  {
    return mSymbolList;
  }

  public int size()
  {
    return mSymbolList.size();
  }
}

