import exceptions.InvalidValueException;

public class SavingsAccount extends Account {
    double tax;
    public SavingsAccount(int id, String name, double tax){
        super(id, name);
        this.tax = tax;
    }
    public SavingsAccount(int id, String name, double tax, double balance) throws InvalidValueException {
        super(id, name, balance);
        this.tax = tax;
    }

    void passMonth() throws InvalidValueException{
        deposit(getBalance() * tax);
    }
}
