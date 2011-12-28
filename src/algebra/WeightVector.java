package algebra;

public class WeightVector
{
  private int [] mWeights;

  private WeightVector(int [] weights)
  {
    mWeights = weights;
  }

  public int get(int i)
  {
    return mWeights[i];
  }

  public int size()
  {
    return mWeights.length;
  }

  static private java.util.Vector mWeightVectors = new java.util.Vector();

  static public WeightVector create(int [] weights)
  {
    for (int i=0; i<mWeightVectors.size(); i++)
      {
	WeightVector wv = (WeightVector)mWeightVectors.get(i);
	if (wv.mWeights.length == weights.length)
	  {
	    int j;
	    for (j=0; j<weights.length; j++)
	      {
		if (weights[j] != wv.mWeights[j]) break;
	      }
	    if (j==weights.length)
	      {
		return wv;
	      }
	  }
      }
    WeightVector alloc = new WeightVector(weights);
    mWeightVectors.add(alloc);
    return alloc;
  }
}
