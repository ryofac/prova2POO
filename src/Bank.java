import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import exceptions.AccountException;
import exceptions.AccountNotFoundException;
import exceptions.InvalidValueException;
import exceptions.SavingsAccountInvalidException;

class Bank {
  List<Account> accounts = new ArrayList<>();
  int nextId = accounts.size();

  Account createAccount(String name) {
    int accAmount = nextId;
    nextId++;
    double initBalance = 0;
    Account newAccount;
    try {
      newAccount = new Account(accAmount, name, initBalance);
      return newAccount;

    } catch (InvalidValueException e) {
      e.printStackTrace();
    }
    return null;

  }

  void addAccount(Account acc) {
    accounts.add(acc);
  }

  Account findAccountById(int id) throws AccountNotFoundException {
    Optional<Account> founded = accounts.stream().filter(elemento -> id == elemento.id).findFirst();

    if (founded.isEmpty()) {
      throw new AccountNotFoundException("ERRO: Conta não foi encontrada");
    }
    return founded.get();

  }

  void deposit(int accountNum, double amount) throws AccountNotFoundException, InvalidValueException {
    Account depositAcc = findAccountById(accountNum);
    depositAcc.deposit(amount);
  }

  void withdraw(int accountNum, double amount) throws AccountException, InvalidValueException {
    Account founded = findAccountById(accountNum);
    founded.withdraw(amount);
  }

  void transfer(int accountDebitNum, int accountCreditNum, int amount) throws AccountException, InvalidValueException {
    Account debitAcc = findAccountById(accountDebitNum);
    Account creditAcc = findAccountById(accountCreditNum);
    debitAcc.transfer(creditAcc, amount);
  }

  void passMonth(int idAccount) throws AccountNotFoundException, SavingsAccountInvalidException{
    Account findedId = findAccountById(idAccount);
    if(!(findedId instanceof SavingsAccount)){
      throw new SavingsAccountInvalidException("ERRO: Conta não é poupança");
    }

  }

}