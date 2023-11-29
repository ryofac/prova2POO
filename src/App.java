import java.util.Stack;
import exceptions.AccountAlreadyExistsException;
import exceptions.AccountNotFoundException;
import exceptions.InsufficientFundsException;
import exceptions.InvalidValueException;
import utils.IOUtils;

public class App {
  Stack<Runnable> viewStack = new Stack<>();
  Bank patRoBank = new Bank();
  Account loggedAccount;
  String actualMenu;
  int opcao = 0;

  

  private Account login() throws AccountNotFoundException{
    int idAccount = IOUtils.getInt("Qual o seu número da conta? \n> ");
    Account foundAcc = patRoBank.findAccountById(idAccount);
    return foundAcc;

  }

  private void bye(){
    String[] byeMessages = {"Até logo!", "Nunca é um adeus", "Tchau tchau!"};
    String selected = byeMessages[(int) Math.random() % byeMessages.length];
    IOUtils.show(selected);

  }

  private void createAccount(int type){
    String name = IOUtils.getText("Digite o seu belo nome: ");

    Account createdAcc;
    switch (type) {
      case 1: // Regular Account
        createdAcc = patRoBank.createAccount(name);
        break;
      case 2: // Savings Account
        try{
          double rate = IOUtils.getPercent("Digite a taxa (0 - 100)% : ");
          createdAcc = patRoBank.createSavingsAccount(name, rate);
        } catch (InvalidValueException e){
          IOUtils.show("O valor da taxa deve estar entre 0 e 100 !");
          return;
        }
        break;

      default:
        return;
    } 
    try {
      patRoBank.addAccount(createdAcc);
      IOUtils.show("Conta " + createdAcc.getId() + " criada com sucesso");
    } catch(AccountAlreadyExistsException e){
      IOUtils.show(e.getMessage());
    }
   
  }


  private void deposit(){
    double amount = IOUtils.getDouble("Digite a quantidade a ser depositada (R$): ");
    try {
      loggedAccount.deposit(amount);
    } catch (InvalidValueException e) {
      e.printStackTrace();
    }

    IOUtils.show("Deposito realizado com sucesso!");
  }

  private void withdraw(){
    double amount = IOUtils.getDouble("Digite a quantidade a ser depositada (R$): ");
    try {
      loggedAccount.withdraw(amount);
    } catch (InvalidValueException e) {
      e.printStackTrace();
    } catch (InsufficientFundsException e){
      IOUtils.show("Não se pode sacar! ");
      IOUtils.show(e.getMessage());
      return;
    }

    IOUtils.show("Saque realizado com sucesso!");
  }

  private void transfer(){
    int idDestin = IOUtils.getInt("Digite o número da conta para quem você quer transferir: ");
    Account destin;
    try{
      destin = patRoBank.findAccountById(idDestin);
    } catch( AccountNotFoundException e){
      IOUtils.show(e.getMessage());
      return;
    }
    double amount = IOUtils.getDouble("Digite o quanto você quer transferir (R$): ");
    try {
      loggedAccount.transfer(destin, amount);
    } catch (InvalidValueException e) {
      e.printStackTrace();
    } catch (InsufficientFundsException e){
      IOUtils.show("Não se pode transferir! ");
      IOUtils.show(e.getMessage());
    }

    IOUtils.show("Transferência realizada com sucesso!");
  }


  // Implementando as funções do banco:
  private void initMenu(){
    IOUtils.clearScreen();
    // TODO: Fazer um método que quebre a linhas do texto em uma "largura máxima"
    IOUtils.show("-> P A T R O  B A N K <- \n" + "_".repeat(40));
    opcao = IOUtils.getInt("1 - Menu Conta\n2 - Se juntar ao PatRoBank\n0 - Sair do APP \n> ");
    switch (opcao) {
      case 1:
        try{
          loggedAccount = login();
          viewStack.add(this::mainMenu);
        } catch(AccountNotFoundException e){
          IOUtils.show("Opa amigo não conseguimos te logar...");
          IOUtils.show(e.getMessage());
        }
        break;

      case 2:
        viewStack.push(this:: createAccMenu);
        break;

      case 0:
        viewStack.pop();
        break;

      default:
        IOUtils.show("Opção inválida!");
        break;
    }
  }
  private void mainMenu(){
    IOUtils.clearScreen();
    IOUtils.show(String.format("+++ MENU CONTA +++\n>Número da conta: %d\n>Titular: %s\n>>TOTAL: R$%.2f<<", 
      loggedAccount.getId(), loggedAccount.getName(), loggedAccount.getBalance()));
    opcao = IOUtils.getInt( "1 - Depositar\n2 - Sacar\n3 - Transferir\n0 - Deslogar \n> ");
    switch (opcao) {
      case 1:
        deposit();
        break;
      case 2:
        withdraw();
        break;
      case 3:
        transfer();
        break;
      case 0:
        viewStack.pop();
        break;
    
      default:
        IOUtils.show("Opção Inválida!");
        break;
    }
  }
  private void createAccMenu(){
    IOUtils.clearScreen();
    opcao = IOUtils.getInt( "1 - Criar conta corrente\n2 - Criar conta poupança\n0 - Cancelar \n> ");
    switch (opcao) {
      case 1:
        createAccount(1); // Criando uma conta normal
        viewStack.pop();
        break;

      case 2:
        createAccount(2); // Criando uma conta poupança
        viewStack.pop();
        break;

      case 0:
        viewStack.pop();
        break;
    
      default:
        IOUtils.show("Opção Inválida!");
        break;
    }
  }
  
  public void run(){
    viewStack.push(this::initMenu);

    do {
      try {
      Runnable stackTop = viewStack.peek();
      stackTop.run();
      } catch (NumberFormatException e){
        IOUtils.show("Digite um valor válido...");
      } finally {
        IOUtils.enterToContinue();
      }
      
    } while (!viewStack.empty());

    bye();
    IOUtils.closeScanner();
  }
  
}

/**
 * Aux
 */
class Aux {
  public static void main(String[] args) {
    App newApp = new App();
    newApp.run();
  }


  
}