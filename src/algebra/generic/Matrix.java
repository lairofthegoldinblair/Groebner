package algebra.generic;

/**
 * Generally, the matrices of a certain size over a ring are a module.
 * If the the matrices are square, then we have an algebra.
 */
public class Matrix<_Ring extends RingElement<_Ring> >
{
  Object [][] mElements;
  int mRows;
  int mCols;
  Ring<_Ring> mBaseRing;

  public Ring<_Ring> getBaseRing()
  {
    return mBaseRing;
  }

  public int getRows() 
  {
    return mRows;
  }
  
  public int getCols()
  {
    return mCols;
  }

  /**
   * Creates a rows by cols matrix with elt on the diagonal.
   */
  public Matrix(int rows, int cols, _Ring elt)
  {
    mElements = new Object[rows][cols];
    mRows = rows;
    mCols = cols;
    for(int i=0; i<mRows; i++)
    {
      for(int j=0; j<mCols; j++)
      {
	mElements[i][j] = i==j ? elt : elt.getRing().getZero();
      }
    }
    mBaseRing = elt.getRing();
  }

  protected Matrix(Object [] [] elements, int rows, int cols)
  {
    mElements = elements;
    mRows = rows;
    mCols = cols;
    mBaseRing = get(0,0).getRing();
  }

  final public void set(int i, int j, _Ring elt)
  {
    mElements[i][j] = elt;
  }

  final public _Ring get(int i, int j)
  {
    return (_Ring) mElements[i][j];
  }
  
  public Matrix<_Ring> negate()
  {
    Object [][] r = new Object[this.getRows()][this.getCols()];
    for(int i=0; i<this.getRows(); i++)
    {
      for(int j=0; j<this.getCols(); j++)
      {
	r[i][j] = this.get(i,j).negate();
      }
    }
    return new Matrix<_Ring> (r, this.getRows(), this.getCols());
  }

  public Matrix<_Ring> add(Matrix<_Ring> m)
  {
    if(this.getCols() != m.getCols()) throw new RuntimeException("Dimension mismatch");
    if(this.getRows() != m.getRows()) throw new RuntimeException("Dimension mismatch");
    if(this.getBaseRing() != m.getBaseRing()) throw new RuntimeException("Domain mismatch");

    Object [][] r = new Object[this.getRows()][m.getCols()];
    for(int i=0; i<this.getRows(); i++)
    {
      for(int j=0; j<m.getCols(); j++)
      {
	r[i][j] = this.get(i,j).add(m.get(i,j));
      }
    }
    return new Matrix<_Ring> (r, this.getRows(), m.getCols());
  }

  public Matrix<_Ring> subtract(Matrix<_Ring> m)
  {
    if(this.getCols() != m.getCols()) throw new RuntimeException("Dimension mismatch");
    if(this.getRows() != m.getRows()) throw new RuntimeException("Dimension mismatch");
    if(this.getBaseRing() != m.getBaseRing()) throw new RuntimeException("Domain mismatch");

    Object [][] r = new Object[this.getRows()][m.getCols()];
    for(int i=0; i<this.getRows(); i++)
    {
      for(int j=0; j<m.getCols(); j++)
      {
	r[i][j] = this.get(i,j).subtract(m.get(i,j));
      }
    }
    return new Matrix<_Ring> (r, this.getRows(), m.getCols());
  }

  public Matrix<_Ring> rightMultiply(Matrix<_Ring> m)
  {
    if(this.getCols() != m.getRows()) throw new RuntimeException("Dimension mismatch");
    if(this.getBaseRing() != m.getBaseRing()) throw new RuntimeException("Domain mismatch");

    Object [][] r = new Object[this.getRows()][m.getCols()];
    for(int i=0; i<this.getRows(); i++)
    {
      for(int j=0; j<m.getCols(); j++)
      {
	r[i][j] = this.getBaseRing().getZero();
	for(int k=0; k<this.getCols(); k++)
	{
	  r[i][j] = ((_Ring) r[i][j]).add(this.get(i,k).rightMultiply(m.get(k,j)));
	}
      }
    }
    return new Matrix<_Ring> (r, this.getRows(), m.getCols());
  }

  public Matrix<_Ring> leftMultiply(Matrix<_Ring> m)
  {
    return m.rightMultiply(this);
  }
  
  public Matrix<_Ring> leftMultiply(_Ring c)
  {
    Object [][] r = new Object[this.getRows()][this.getCols()];
    for(int i=0; i<this.getRows(); i++)
    {
      for(int j=0; j<this.getCols(); j++)
      {
	r[i][j] = this.get(i,j).leftMultiply(c);
      }
    }
    return new Matrix<_Ring> (r, this.getRows(), this.getCols());
  }

  public Matrix<_Ring> rightMultiply(_Ring c)
  {
    Object [][] r = new Object[this.getRows()][this.getCols()];
    for(int i=0; i<this.getRows(); i++)
    {
      for(int j=0; j<this.getCols(); j++)
      {
	r[i][j] = this.get(i,j).rightMultiply(c);
      }
    }
    return new Matrix<_Ring> (r, this.getRows(), this.getCols());
  }

  public Matrix<_Ring> append(Matrix<_Ring> m)
  {
    if(this.getRows() != m.getRows()) throw new RuntimeException("Row Dimension mismatch");
    Object [][] r = new Object[this.getRows()][this.getCols()+m.getCols()];
    for(int i=0; i<this.getRows(); i++)
    {
      for(int j=0; j<this.getCols(); j++)
      {
	r[i][j] = this.get(i,j);
      }
      for(int j=0; j<m.getCols(); j++)
      {
	r[i][j+this.getCols()] = m.get(i,j);
      }
    }
    return new Matrix<_Ring> (r, this.getRows(), this.getCols()+m.getCols());
  }

  public Matrix<_Ring> stack(Matrix<_Ring> m)
  {
    if(this.getCols() != m.getCols()) throw new RuntimeException("Column Dimension mismatch");
    Object [][] r = new Object[this.getRows()+m.getRows()][this.getCols()];
    for(int i=0; i<this.getRows(); i++)
    {
      for(int j=0; j<this.getCols(); j++)
      {
	r[i][j] = this.get(i,j);
      }
    }
    for(int i=0; i<m.getRows(); i++)
    {
      for(int j=0; j<m.getCols(); j++)
      {
	r[i+this.getRows()][j] = this.get(i,j);
      }
    }
    return new Matrix<_Ring> (r, this.getRows()+m.getRows(), this.getCols());
  }

}
