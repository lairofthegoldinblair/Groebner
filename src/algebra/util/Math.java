package algebra.util;

public class Math
{
  /**
   * @return k! = k*(k-1)* ... * 1
   */
  public static int factorial(int k)
  {
    int result = 1;
    for(int i=0; i<k; i++)
      {
	result *= (i+1);
      }
    return result;
  }

  /**
   * @return o[0]!*o[1]!* ... *o[n]!
   */
  static public int multiFactorial(int [] o)
  {
    int result=1;
    for (int i=0; i<o.length; i++)
      {
	for(int j=o[i]; j>0; j--)
	  {
	    result *= j;
	  }
      }
    return result;
  }

  /**
   * @return n!/k!*(n-k)!
   */
  public static int binomialCoefficient(int n, int k)
  {
    return factorial(n)/(factorial(k)*factorial(n-k));
  }

  final static private java.math.BigInteger MAX_LONG = new java.math.BigInteger(Long.toString(Long.MAX_VALUE));
  final static private java.math.BigInteger MIN_LONG = new java.math.BigInteger(Long.toString(Long.MIN_VALUE));

  // Use the Euclidean algorithm to compute the GCD
  public static long gcd(long f, long g)
  {
    return euclideanAlgorithm(f,g)[0];
  }

  /**
   * Find the number m such that m*n = 1 (mod q)
   */
  public static long invert(long n, long q)
  {
    long [] ea = euclideanAlgorithm(n, q);
    if (ea[0] != 1) throw new RuntimeException("Not invertible");
    // Normalize a to be between 0 and q-1
    long a = ea[1];
    return a<0 ? q - ((-a) % q) : a%q;
  }

  private static long [] euclideanAlgorithm(long f, long g)
  {
    f = java.lang.Math.abs(f);
    g = java.lang.Math.abs(g);

    // This uses the Euclidean algorithm while keeping track of expressions
    // h = a*f + b*g
    // s = c*f + d*g
    long h = f;
    long s = g;
    long a = 1;
    long b = 0;
    long c = 0;
    long d = 1;

    //System.out.println("Step: " + h + " = " + a + "*" + f + "+" + b + "*" + g);
    //System.out.println("Step: " + s + " = " + c + "*" + f + "+" + d + "*" + g);

    while(s != 0)
      {
	long r = h % s;
	long q = h / s;
	//System.out.println("Divide step: " + h + " = " + q + "*" + s + " + " + r);
	long savec = c;
	long saved = d;
	c = a - q*c;
	d = b - q*d;
	a = savec;
	b = saved;
	h = s;
	s = r;
	//System.out.println("Step: " + h + " = " + a + "*" + f + "+" + b + "*" + g);
	//System.out.println("Step: " + s + " = " + c + "*" + f + "+" + d + "*" + g);
      }

    return new long [] {h, a, b};
  }

  public static long lcm(long a, long b)
  {
    return (a*b)/gcd(a,b);
  }
}
