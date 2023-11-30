import exceptions.AccountException;
import exceptions.InsufficientFundsException;
import exceptions.InvalidValueException;

public class Account {
    public int id;
    private String name;
    private double balance;

    public Account(Integer id, String name, double balance) throws InvalidValueException {
        this.id = id;
        this.name = name;
        if (balance < 0)
            throw new InvalidValueException("Saldo deve ser positivo!");
        this.balance = balance;
    }

    public Account(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.balance = 0;
    }

    /** Corresponde a Questão 6: 
     * Valida o valor passado como parâmetro, analisando se ele é menor que 0
     * @param value um valor numérico
     * @throws InvalidValueException caso o valor seja menor que 0
     */
    private void _validateValue(double value) throws InvalidValueException {
        if (value < 0) {
            throw new InvalidValueException("ERRO: Valor inválido");
        }
    }

    /**
     * Analisa se a operação de sacar pode acontecer
     * @param amount a quantidade a ser sacada
     * @return o valor booleano da expressão: (total - a quantidade a ser sacada) > 0
     */
    private boolean _canWithdraw(double amount) {
        double result = this.balance - amount;
        return result >= 0;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return this.balance;
    }

    public void withdraw(double amount) throws InsufficientFundsException, InvalidValueException {
        _validateValue(amount);
        if (!_canWithdraw(amount)) {
            throw new InsufficientFundsException("ERRO: Saldo Insuficiente");
        }
        this.balance -= amount;
    }

    public void deposit(double amount) throws InvalidValueException{
        _validateValue(amount);
        this.balance += amount;
    }

    public void transfer(Account destinAcc, double amount) throws InsufficientFundsException, InvalidValueException {
        this.withdraw(amount);
        destinAcc.deposit(amount);
    }

    @Override
    public String toString() {
        return "Número: " + getId() + " | " + "Titular: " + getName(); 
    }

    // Questão 4
    public static void main(String[] args) throws InvalidValueException, InsufficientFundsException{
        Account acc1 = new Account(1, "Ryan", 1);
        Account acc2 = new Account(2, "Livia", 10);
        acc1.transfer(acc2, 100);

        // Ouput: Exception in thread "main" exceptions.InsufficientFundsException: ERRO: Saldo Insuficiente
        // O que ocorreu é que a exceção foi lançada pelo método transferir da conta e não foi tratada.
    }

}
