package exceptions.accountExceptions;

public class InsufficientFundsException extends AccountException {
  public InsufficientFundsException(String message) {
    super(message);
  }

}
