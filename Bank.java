package ImplementacaoBanco;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Account> accounts = new ArrayList<Account>();
    private Double savingsAccountTax;
    private Double taxAccountTax;

    public Bank(Double savingsTax, Double taxAccountTax) {
        this.savingsAccountTax = savingsTax;
        this.taxAccountTax = taxAccountTax;
    }

    public Double getSavingsAccountTax() {
        return savingsAccountTax;
    }

    public Double getTaxAccountTax() {
        return taxAccountTax;
    }

    public Integer totalAccounts() {
        return accounts.size();
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public boolean applyTax(String accountNum) {
        Account acc = findAccount(accountNum);
        if (acc == null) {
            System.out.println("Não foi possível render: Conta não encontrada!");
            return false;
        } else if (!(acc instanceof SavingsAccount)) {
            System.out.println("Não foi possível render: Conta não é poupança! ");
            return false;
        }
        SavingsAccount accConverted = (SavingsAccount) acc;

        accConverted.applyTax();
        return true;

    }

    public Account findAccount(String accountNum) {
        for (Account acc : accounts) {
            if (acc.accountNumber.compareTo(accountNum) == 0) {
                return acc;
            }
        }
        return null;
    }

    public boolean insert(Account account) {
        Account equalAccount = findAccount(account.accountNumber);
        if (equalAccount != null) {
            return false;
        }
        accounts.add(account);
        return true;
    }

    public boolean remove(String accountNumber) {
        Account finded = findAccount(accountNumber);
        if (finded == null)
            return false;
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).accountNumber == accountNumber) {
                accounts.remove(i);
                break;
            }

        }
        return true;

    }


    // Esse método pega um número de conta e é encarregado de chamar seu método sacar
    // Propaga Exception vinda de saque da conta
    public void withdraw(String accountNum, double amount) throws Exception {
        Account accountWithdraw = findAccount(accountNum);
        if (accountWithdraw != null) {
            accountWithdraw.withdraw(amount);
        }
    }

    public boolean deposit(String accountNum, double amount) {
        Account accountWithdraw = findAccount(accountNum);
        if (accountWithdraw != null) {
            return accountWithdraw.deposit(amount);
        }
        return false;

    }

    public void transfer(String debitAccountNumber, String creditAccountNumber, double amount) throws Exception{
        Account accountDebit = findAccount(debitAccountNumber);
        Account accountCredit = findAccount(creditAccountNumber);
        if (accountCredit != null && accountDebit != null) {
            accountDebit.transfer(amount, accountCredit);
        }

    }

    int getTotalAccounts() {
        return accounts.size();
    }

    public double getTotalMoney() {
        double total = 0;
        for (Account acc : accounts) {
            total += acc.getTotal();
        }
        return total;
    }

    public double getValueAvr() {
        if (getTotalAccounts() > 0)
            return getTotalMoney() / getTotalAccounts();
        return 0;
    }

}
