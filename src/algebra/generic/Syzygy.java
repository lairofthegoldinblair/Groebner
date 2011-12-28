package algebra.generic;

/**
 * Calculate the syzygy module of a module.
 * A syzygy is just a linear relationship between generators of another module: Sum a_i*m_i = 0.
 * Syzygies may be thought of as elements of a free module on number of generators equal to the number of generators
 * of the target module.  It is a simple fact that the set of syzygies is really a bubmodule of the free module (it is the kernel of a 
 * homomorphism between free modules).
 * The syzygy module is represented by calculating a Groebner basis for the syzygy module via 
 * Schreyer's theorem (Eisenbud "Commutative Algebra with a View Toward Algebraic Geometry" Section 15.5).
 */
public class Syzygy<_Field, _Term extends Term<_Term, _Term, _Field>, _Ring extends TermThing<_Field,_Ring, _Term>, _ModuleTerm extends Term<_Term, _ModuleTerm, _Field>, _Module extends ModuleTermThing<_Field,_Term,_Ring,_ModuleTerm,_Module>>
{
  static class Matrix<_R>
  {
    private int mRows;
    private int mCols;
    private Object [][] mMatrix;

    public _R get(int r, int c)
    {
      return (_R) mMatrix[r][c];
    }

    public void set(int r, int c, _R val)
    {
      mMatrix[r][c] = val;
    }
    
    public Matrix(int rows, int cols)
    {
      mRows = rows;
      mCols = cols;
      mMatrix = new Object [mRows][mCols];
    }

    public String toString()
    {
      StringBuffer buff = new StringBuffer();
      for(int i=0; i<mRows; i++)
      {
        buff.append("[");
        for(int j=0; j<mCols; j++)
        {
          if(j>0) buff.append(", ");
          buff.append(get(i,j).toString());
        }
        buff.append("]\n");
      }
      return buff.toString();
    }

  }

  Matrix<_Ring> mSyz;

  public String toString()
  {
    return mSyz.toString();
  }

  /**
   * Calculate the syzygy module of the generates of a module.  The list of generators
   * is not assumed to be a Groebner basis as such a basis will be calculated.
   */
  public Syzygy(java.util.List<_Module> list)
  {
    // Get the underlying ring.
    Ring<_Ring> ring = list.get(0).getModule().getLeftRing();

    // Get the groebner basis corresponding to generators
    BuchbergerAlgorithm <_Field,_Term,_Ring,_ModuleTerm,_Module> ba =
      new BuchbergerAlgorithm <_Field,_Term,_Ring,_ModuleTerm,_Module>(list);

    // Compute the matrix that changes basis from grobner to orignal and vice-versa
    Matrix<_Ring> A = new Matrix<_Ring> (list.size(), ba.getStandardBasis().size());
    for(int i=0; i<ba.getStandardBasis().size(); i++)
    {
      for(int j=0; j<list.size(); j++)
      {
	A.set(j, i, ba.getBasisChange().get(ba.getStandardBasis().get(i)).get(list.get(j)));
	if (A.get(j,i) == null) throw new RuntimeException("Internal Error");
      }
    }

//     for(int i=0; i<list.size(); i++)
//     {
//       for(int j=0; j<ba.getStandardBasis().size(); j++)
//       {
// 	System.out.println("A[" + i + "][" + j + "] = " + A[i][j]);
//       }
//     }

    Matrix<_Ring> B = new Matrix<_Ring>(ba.getStandardBasis().size(), list.size());
    for(int i=0; i<list.size(); i++)
    {
      GenericNormalForm<_Field,_Term,_Ring,_ModuleTerm,_Module> nf =
	new GenericNormalForm<_Field,_Term,_Ring,_ModuleTerm,_Module>(list.get(i), ba.getStandardBasis());
      if(!nf.getRemainder().empty()) throw new RuntimeException("Internal Error");
      for(int j=0; j<ba.getStandardBasis().size(); j++)
      {
	B.set(j,i,nf.getFactor(ba.getStandardBasis().get(j)));
      }
    }

    // Get the syzygies of the groebner basis from the normal form
    // of the spairs of each pair of vertices.
    int cnt=0;
    int numSpairs = (ba.getStandardBasis().size()*(ba.getStandardBasis().size()-1))/2;
    Matrix<_Ring> S = new Matrix<_Ring>(ba.getStandardBasis().size(), numSpairs);
    for(int i=0; i<ba.getStandardBasis().size(); i++)
    {
      for(int j=i+1; j<ba.getStandardBasis().size(); j++)
      {
	SyzygyPair<_Term,_Module> sp = ba.getStandardBasis().get(i).sPair(ba.getStandardBasis().get(j));
	GenericNormalForm<_Field,_Term,_Ring,_ModuleTerm,_Module> nf =
	  new GenericNormalForm<_Field,_Term,_Ring,_ModuleTerm,_Module>(sp.getSyzygyPair(), ba.getStandardBasis());

	// The "spair criterion" for a Groebner basis implies that the remainder is zero
	if(!nf.getRemainder().empty()) throw new RuntimeException("Internal Error");

	// Here we have one column per non-trivial spair sp(b_i, b_j) with b_i, b_j in the Groebner basis.
	// The normal form expression is c_i*b_i - c_j*b_j = Sum NF_k(sp(b_i,b_j)) * b_k
	// Make the syzygy into a column of the matrix.
	for(int k=0; k<ba.getStandardBasis().size(); k++)
	{
	  S.set(k, cnt, nf.getFactor(ba.getStandardBasis().get(k)));
	}

	S.set(i, cnt, S.get(i,cnt).subtractTerm(sp.getSyzygyCoefficient(ba.getStandardBasis().get(i))));
	S.set(j, cnt, S.get(j,cnt).addTerm(sp.getSyzygyCoefficient(ba.getStandardBasis().get(j))));

	cnt++;
      }
    }

//     for(int i=0; i<ba.getStandardBasis().size(); i++)
//     {
//       for(int j=0; j<numSpairs; j++)
//       {
// 	System.out.println("S[" + i + "][" + j + "] = " + S[i][j]);
//       }
//     }

    // Take [I - A*B | A*S]
    mSyz = new Matrix<_Ring> (list.size(), list.size() + numSpairs);
    for(int i=0; i<list.size(); i++)
    {
      for(int j=0; j<list.size(); j++)
      {
	mSyz.set(i,j,(i==j) ? ring.getIdentity() : ring.getZero());
	for(int k=0; k<ba.getStandardBasis().size(); k++)
	{
	  mSyz.set(i,j,mSyz.get(i,j).subtract(A.get(i,k).rightMultiply(B.get(k,j))));
	}
      }
    }
    for(int i=0; i<list.size(); i++)
    {
      for(int j=0; j<numSpairs; j++)
      {
	mSyz.set(i,j+list.size(),ring.getZero());
	for(int k=0; k<ba.getStandardBasis().size(); k++)
	{
	  mSyz.set(i,j+list.size(),mSyz.get(i,j+list.size()).add(A.get(i,k).rightMultiply(S.get(k,j))));
	}
      }
    }
  }
}
