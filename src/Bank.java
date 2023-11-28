import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import exceptions.AccountAlreadyExistsException;
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

  void addAccount(Account acc) throws AccountAlreadyExistsException {

    // Questão 13:
    try{
      Account existent = findAccountById(acc.getId());

      // Se eu não capturei o erro NotFounded, quer dizer que existe, o que eu não quero
      throw new AccountAlreadyExistsException("ERRO: Conta já existente");

    } catch (AccountNotFoundException e) {
      accounts.add(acc);

    } catch (AccountException e){
      throw e;
    }
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

  // Questão 12:
  void passMonth(int idAccount) throws AccountNotFoundException, SavingsAccountInvalidException{
    Account findedId = findAccountById(idAccount);
    if(!(findedId instanceof SavingsAccount)){
      throw new SavingsAccountInvalidException("ERRO: Conta não é poupança");
    }

  }

}