package exceptions;

public class AccountAlreadyExistsException extends AccountException{

    public AccountAlreadyExistsException(String msg) {
        super(msg);
    }

}
