package exceptions.valueExceptions;

import exceptions.AppException;

public class InvalidValueException extends AppException {

  public InvalidValueException(String string) {
    super(string);
  }

}
