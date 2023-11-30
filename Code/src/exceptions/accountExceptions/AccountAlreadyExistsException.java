package exceptions.accountExceptions;

public class AccountAlreadyExistsException extends AccountException{

    public AccountAlreadyExistsException(String msg) {
        super(msg);
    }

}
