import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import exceptions.accountExceptions.AccountAlreadyExistsException;
import exceptions.accountExceptions.AccountException;
import exceptions.accountExceptions.AccountNotFoundException;
import exceptions.accountExceptions.SavingsAccountInvalidException;
import exceptions.valueExceptions.InvalidValueException;

class Bank {
  List<Account> accounts = new ArrayList<>();
  int nextId = accounts.size();

  enum ACCTYPES {
    REGULAR,
    SAVINGS
  }

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

  SavingsAccount createSavingsAccount(String name, double rate) {
    int accAmount = nextId;
    nextId++;
    double initBalance = 0;
    SavingsAccount newAccount;
    try {
      newAccount = new SavingsAccount(accAmount, name, rate, initBalance);
      return newAccount;

    } catch (InvalidValueException e) {
      e.printStackTrace();
    }
    return null;

  }
  
  

  // Classe App
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
  // * Questão 8
  // Somente o método Consultar foi implementado por estar utilizando uma ArrayList que implementa uma lista dinâmica
  // Caso fosse um Array, a operação consultar por índice seria necessária e funcionaria da seguinte forma:
  //  > Buscaria no array um índice em específico por meio de um for
  //  > Ao encontrar o elemento, faria com que ele recebesse o endereço do próximo e assim sucessivamente
  //  > Ao final, o elemento não teria referência do objeto em memória e seria apagado pelo garbage collector
  // Se o índice nunca for encontrado, seria jogado uma AccountNotFoundException
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
    
    public int getAccountAmount(){
      return accounts.size();

  }


  // Questão 5
  public static void main(String[] args) throws InvalidValueException, AccountException{
    Bank banco = new Bank();
    banco.addAccount(new Account(1, "Robson", 10));
    banco.addAccount(new Account(2, "Robervaldo", 10));

    banco.transfer(1, 2, 100);
    // Output: Exception in thread "main" exceptions.InsufficientFundsException: ERRO: Saldo Insuficiente
    // A exceção foi propagada mostrando que esse método é confiável

    
  }

}