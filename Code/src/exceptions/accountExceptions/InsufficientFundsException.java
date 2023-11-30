package exceptions;

public class InsufficientFundsException extends AccountException {
  public InsufficientFundsException(String message) {
    super(message);
  }

}
