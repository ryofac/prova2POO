import exceptions.valueExceptions.InvalidValueException;

public class SavingsAccount extends Account {
    double rate;
    String typeStr = "CP";

    public SavingsAccount(int id, String name, double rate){
        super(id, name);
        this.rate = rate;
    }
    public double getRate(){
        return rate;
    }
    public SavingsAccount(int id, String name, double rate, double balance) throws InvalidValueException {
        super(id, name, balance);
        this.rate = rate;
    }

    void passMonth() throws InvalidValueException{
        deposit(getBalance() * rate);
    }

    @Override
    public String toString() {
        return super.toString() + String.format(";%f", rate);
    }
}
