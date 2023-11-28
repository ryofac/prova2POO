package ImplementacaoBanco;

public class Runner {
    public static void main(String[] args) {

        Double savingsAccountTax = 10.0 / 100;
        Double taxAccountTax = 20.0 / 100;

        Bank banco = new Bank(savingsAccountTax, taxAccountTax);

        String accountsDataPath = "/home/ryofac/Works/Prova-poo/ImplementacaoBanco/data/Accounts.txt";
        String clientsDataPath = "/home/ryofac/Works/Prova-poo/ImplementacaoBanco/data/Clients.txt";

        App app = new App(banco, accountsDataPath, clientsDataPath);
        app.run();

    }

}
