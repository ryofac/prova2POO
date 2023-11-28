package ImplementacaoBanco;

public class SavingsAccount extends Account {
    private Double tax;

    SavingsAccount(String _accountNumber, Client _client, double _total, double tax) {
        super(_accountNumber, _client, _total);
        this.tax = tax;
    }

    public void applyTax() {
        deposit(getTotal() * this.tax);
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    @Override
    public String toString() {
        return super.toString() + ";" + tax;
    }

}
