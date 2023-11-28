package ImplementacaoBanco;

public class TaxAccount extends Account {
    private Double tax;

    TaxAccount(String _accountNumber, Client _client, double _total, Double tax) {
        super(_accountNumber, _client, _total);
        this.tax = tax;

    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    @Override
    public void transfer(double amount, Account account) {
        super.transfer(amount, account);
        try{
            withdraw(amount * tax);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + ";" + tax;
    }

}
