package algebra;

/**
 * Thrown when trying to perform binary algebraic operations on elements from incompatible domains.
 */
public class DomainMismatchException extends RuntimeException
{
  /**
   * Default constructor specifies a default detail message.
   */
  public DomainMismatchException()
  {
    super("Domain mismatch");
  }

  /**
   * Constructor specifying the detail message associated with the exception.
   *
   * @param msg the detail message
   */
  public DomainMismatchException(String msg)
  {
    super(msg);
  }
}
