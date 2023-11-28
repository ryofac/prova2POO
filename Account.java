package ImplementacaoBanco;

public class Account {
    public String accountNumber;
    private Client client;
    private double total;

    Account(String _accountNumber, Client _client, double _total) {
        accountNumber = _accountNumber;
        client = _client;
        total = _total;
    }

    public Client getClient() {
        return this.client;
    }

    public Double getTotal() {
        return this.total;
    }

    public boolean deposit(double amount) {
        this.total += amount;
        return true;
    }

    public void transfer(double amount, Account account) throws Exception {
        if (account.accountNumber != this.accountNumber && this.total > amount) {
            try{
                this.withdraw(amount);
                account.deposit(amount);
            } catch (Exception e){
                throw e;
            }
            
            
        }
    }


    // Essa função é encarregada de realizar o saque, recebendo o valor
    // Joga Exception caso o saldo seja menor que valor
    public void withdraw(double amount) throws Exception {
        if (this.total < amount) {
            System.err.println("Deu erro...");
            throw new Exception("ERRO: Você não tem saldo suficiente para realizar essa operação");
        }
         this.total -= amount;
         System.err.println(" Reduzi");
        
    }

    @Override
    public String toString() {
        String accountType = "CC";
        if (this instanceof TaxAccount) {
            accountType = "CI";
        } else if (this instanceof SavingsAccount) {
            accountType = "CP";
        }
        return this.accountNumber + ";" + accountType + ";" + this.client.getCpf() + ";" + this.total;
    }

}
