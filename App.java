package ImplementacaoBanco;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    private Bank bank;
    private Scanner in = new Scanner(System.in).useDelimiter("\n");

    // Paths for saving data
    private String accountsDataPath;
    private String clientsDataPath;

    public App(Bank bank, String accountsPath, String clientsPath) {
        this.bank = bank;
        this.accountsDataPath = accountsPath;
        this.clientsDataPath = clientsPath;
        readData();

    }

    private void searchAccount() {
        String accountId = getText("Digite o número da conta: ");
        Account finded = this.bank.findAccount(accountId);

        if (finded == null) {
            Utils.show("Nenhuma conta foi encontrada! ");
            return;
        }
        Utils.show("NOME DA CONTA: " + finded.getClient().getName());
        Utils.show("CPF DO TITTULAR: " + finded.getClient().getCpf());
        Utils.show("VALOR TOTAL: R$" + finded.getTotal());

    }

    private void deleteAccount() {
        String accountId = getText("Digite o número da conta: ");
        Account finded = this.bank.findAccount(accountId);

        if (finded == null) {
            Utils.show("Nenhuma conta foi encontrada! ");
            return;
        }
        bank.remove(accountId);
        Utils.show("Conta removida com sucesso!");
    }

    private String menu() {
        return ("Selecione uma opção:" +
                "\n========================================\n" +
                "1 - Cadastrar\t 2 - Consultar\t 3 - Sacar" +
                "\n4 - Depositar\t 5 - Excluir\t 6 - Transferir" +
                "\n7 - Totalizações 8 - Passar mês" +
                "\n0 - Sair" +
                "\n======================================\n");

    }

    private int getInt(String inputText) {
        System.out.println(inputText);
        int opcao = in.nextInt();
        return opcao;

    }

    private double getDouble(String inputText) {
        System.out.println(inputText);
        double opcao = in.nextDouble();
        return opcao;

    }

    private String getText(String inputString) {
        Utils.show(inputString);
        String text = in.next();
        return text;
    }

    private void passMonth() {
        Utils.show("Passando o mês...");
        for (Account acc : bank.getAccounts()) {
            if (acc instanceof SavingsAccount) {
                ((SavingsAccount) acc).applyTax();

            }

        }
    }

    private void insertAccount() {
        Utils.show("Digite os dados da criação de conta: ");
        String nome = getText("Nome do titular: ");
        String cpf = getText("Cpf do Titular: ");
        String numeroConta = generateAccountNum();
        Client newClient = new Client(nome, cpf);
        String tipoConta;
        Account newAccount = null;

        do {
            tipoConta = getText("Digite o tipo da conta a ser criada: (CC/CP/CI)").toUpperCase();
            switch (tipoConta) {
                case "CC":
                    newAccount = new Account(numeroConta, newClient, 0);
                    bank.insert(newAccount);
                    break;
                case "CI":
                    newAccount = new TaxAccount(numeroConta, newClient, 0, bank.getTaxAccountTax());
                    bank.insert(newAccount);
                    break;
                case "CP":
                    newAccount = new SavingsAccount(numeroConta, newClient, 0, bank.getTaxAccountTax());
                    bank.insert(newAccount);
                    break;
            }

        } while (tipoConta == null);

        if (newAccount == null) {
            System.out.println("Erro: Conta vazia!");
            return;
        }
        Utils.show("");
        Utils.show("Conta de número [ " + newAccount.accountNumber + " ] Criada com Sucesso!");
        Utils.show("Cliente " + newAccount.getClient().getName() + " seja bem vindo!");
    }

    private void withdrawAccount() {
        Utils.show("==== SAQUE ==== \n");
        String accountNum = getText("Digite o número da conta que quer sacar: ");
        Account finded = bank.findAccount(accountNum);
        if (bank.findAccount(accountNum) == null) {
            Utils.show("Conta não encontrada! ");
            return;
        }
        String cpf = getText("Digite o cpf correspondente a conta de " + finded.getClient().getName());
        if (finded.getClient().getCpf().compareTo(cpf) != 0) {
            Utils.show("Autenticação não funcionou com sucesso...");
            return;
        }
        double amount = getInt("Digite a quantidade que você quer sacar: ");

        // Classe APP
        try {
            bank.withdraw(accountNum, amount);
            Utils.show("Saque efetuado com sucesso!");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        
    }

    private void transferAccount() {
        Utils.show("==== TRASFERÊNCIA ==== \n");
        String accountNum = getText("Digite o número da conta: ");
        Account finded = bank.findAccount(accountNum);
        if (bank.findAccount(accountNum) == null) {
            Utils.show("Conta não encontrada! ");
            return;
        }
        String cpf = getText("Digite o getCpf() correspondente a conta de " + finded.getClient().getName());
        if (finded.getClient().getCpf().compareTo(cpf) != 0) {
            Utils.show("Autenticação não funcionou com sucesso...");
            return;
        }
        String recieveAccountNum = getText("Digite o número da conta que você quer transferir: ");
        Account recieveAccount = bank.findAccount(recieveAccountNum);
        if (recieveAccount == null) {
            Utils.show("Conta não encontrada! ");
            return;
        }
        double amount = getInt("Digite a quantidade que você quer transferir: ");

        try{
            bank.transfer(accountNum, recieveAccountNum, amount);
            Utils.show("Transferência realizada com sucesso");
        } catch (Exception e){
            Utils.show(e.getMessage());
        }

        Utils.show("Transferência realizada com sucesso!");
        Utils.show(finded.getClient().getName() + ">>>" + recieveAccount.getClient().getName());
        Utils.show("VALOR: " + amount);

    }

    private void depositAccount() {
        Utils.show("==== DEPÓSITO ==== \n");
        String accountNum = getText("Digite o número da conta que quer depositar: ");
        Account finded = bank.findAccount(accountNum);
        if (finded == null) {
            Utils.show("Conta não encontrada! ");
            return;
        }
        String cpf = getText("Digite o cpf correspondente a conta de " + finded.getClient().getName());
        if (finded.getClient().getCpf().compareTo(cpf) != 0) {
            Utils.show("Autenticação não funcionou com sucesso...");
            return;
        }
        double amount = getDouble("Digite a quantidade que você quer depositar: ");
        if (!bank.deposit(accountNum, amount)) {
            Utils.show("Saque negado!");
            return;
        }
        Utils.show("Depósito efetuado com sucesso!");

    }

    private void getData() {
        Utils.show("\n=================== STATUS BANCO =====================");
        Utils.show("TOTAL de contas ativas:\t\t" + bank.totalAccounts());
        Utils.show("MÉDIA dos valores: \t\t" + "R$" + bank.getValueAvr());
        Utils.show("TOTAL de dinheiro guardado:\t" + "R$" + bank.getTotalMoney());
        Utils.show("======================================================\n");
    }

    private String generateAccountNum() {
        int numAccount = (bank.totalAccounts() % 9) + 1;
        int charCode = 65 + bank.totalAccounts() / 10;
        char charAccount = (char) charCode;

        String out = String.format("%c-%d", charAccount, numAccount);
        return out;

    }

    private boolean saveData() {
        String accountsContent = "";
        String clientsContent = "";

        for (Account acc : bank.getAccounts()) {
            accountsContent += acc.toString() + "\n"; // For accounts
            clientsContent += acc.getClient().toString() + "\n"; // For clients:
        }
        File accountsSaved = Utils.writeOnFile(accountsDataPath, accountsContent);
        File clientsSaved = Utils.writeOnFile(clientsDataPath, clientsContent);

        if (clientsSaved.exists() && clientsSaved.isFile() &&
                accountsSaved.exists() && accountsSaved.isFile()) {
            return true;
        }
        return false;

    }

    private void readData() {
        List<String> accountsData = Utils.readLinesOnFile(this.accountsDataPath);
        List<String> clientsData = Utils.readLinesOnFile(this.clientsDataPath);

        List<Client> clients = new ArrayList<Client>();

        for (String clientStr : clientsData) {
            String[] clientLine = clientStr.split(";");
            Client newClient = new Client(clientLine[1], clientLine[0]);
            clients.add(newClient);

        }

        for (String accountStr : accountsData) {
            String[] accountLine = accountStr.split(";");
            // Determinando o tipo da conta:
            switch (accountLine[1]) {
                case "CC":
                    Account newAccount = new Account(accountLine[0], Utils.findClientByCpf(clients, accountLine[2]),
                            Double.parseDouble(accountLine[3]));
                    bank.insert(newAccount);
                    break;

                case "CI":
                    TaxAccount newTAccount = new TaxAccount(accountLine[0],
                            Utils.findClientByCpf(clients, accountLine[2]),
                            Double.parseDouble(accountLine[3]), Double.parseDouble(accountLine[4]));
                    bank.insert(newTAccount);
                    break;
                case "CP":
                    SavingsAccount newSAccount = new SavingsAccount(accountLine[0],
                            Utils.findClientByCpf(clients, accountLine[2]),
                            Double.parseDouble(accountLine[3]), Double.parseDouble(accountLine[4]));
                    bank.insert(newSAccount);
                    break;
            }

        }
        Utils.show("Contas adcionadas!");
    }

    public void run() {
        int opcao = -1;
        do {
            opcao = getInt(menu());
            switch (opcao) {
                case 1:
                    insertAccount();
                    break;
                case 2:
                    searchAccount();
                    break;
                case 3:
                    withdrawAccount();
                    break;
                case 4:
                    depositAccount();
                    break;
                case 5:
                    deleteAccount();
                    break;
                case 6:
                    transferAccount();
                    break;
                case 7:
                    getData();
                    break;
                case 8:
                    passMonth();
                    break;
                default:
                    continue;

            }
        } while (opcao != 0);
        Utils.show("Volte Sempre!");
        if (saveData()) {
            System.out.println("Dados Salvos!");
        } else {
            System.out.println("Erro ao salvar dados!");
        }

        in.close();

    }

}