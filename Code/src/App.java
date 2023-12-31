import java.util.Stack;

import exceptions.accountExceptions.AccountAlreadyExistsException;
import exceptions.accountExceptions.AccountNotFoundException;
import exceptions.accountExceptions.InsufficientFundsException;
import exceptions.valueExceptions.InvalidValueException;
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
    String[] byeMessages = {
      "Até logo, espero que nos encontremos novamente em breve.",
      "A despedida é difícil, mas as memórias permanecem.",
      "Partir é inevitável, mas a saudade é opcional.",
      "Que nossos caminhos se cruzem novamente no futuro.",
      "A vida é feita de despedidas, mas também de reencontros.",
      "Dizer adeus não é o fim, é o início de uma nova jornada.",
      "Levo comigo as lembranças e deixo para trás o que não serve mais.",
      "A cada adeus, um novo horizonte se revela.",
      "Despedir-se é abrir espaço para novos capítulos.",
      "Que a distância fortaleça os laços que nos unem.",
      "Adeus não é um fim, é um até breve em outra página da vida.",
      "A despedida é um marco, não um ponto final.",
      "Partir é uma oportunidade para crescer e aprender.",
      "O adeus é uma ponte entre o que foi e o que será.",
      "As despedidas moldam o caminho para novos começos.",
      "Cada adeus é uma porta que se fecha, mas outras se abrem.",
      "Na despedida, carregamos as experiências que nos transformaram.",
      "Despeço-me com gratidão pelos momentos compartilhados.",
      "Que a vida nos reserve reencontros cheios de alegria.",
      "A despedida é um adeus temporário, pois a vida é um ciclo de encontros e despedidas."
    }
;
    String selected = byeMessages[(int) (Math.random() * 100) % byeMessages.length];
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
      return;
    } catch (InsufficientFundsException e){
      IOUtils.show("Não se pode transferir para conta destino: " + destin.toString());
      IOUtils.show(String.format("Saldo (R$ %.2f) menor que o valor desejado (R$ %.2f)", loggedAccount.getBalance(), amount));
      return;
    }

    IOUtils.show("Transferência para " + destin.getName() + " realizada com sucesso!");
  }


  // Implementando as funções do banco:
  private void initMenu(){
    String accountsToShow = patRoBank.getAccountAmount() > 0? "Contas Disponíveis: " + patRoBank.getAccountAmount() + '\n' : "";
    IOUtils.clearScreen();
    // TODO: Fazer um método que quebre a linhas do texto em uma "largura máxima"
    IOUtils.show("-> P A T R O  B A N K <- \n" + accountsToShow + "_".repeat(40));
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
    String accountType = loggedAccount instanceof SavingsAccount? 
      "Conta Poupança" : "Conta Corrente";
    String finalMessage = loggedAccount instanceof SavingsAccount ? 
      String.format(" > Rendendo %.2f %s por mês", ((SavingsAccount) loggedAccount).getRate(), "%") : "";

    IOUtils.show(String.format("+++ MENU CONTA (%s) +++\n>Número da conta: %d\n>Titular: %s\n>>TOTAL: R$%.2f<<\n%s", 
      accountType, loggedAccount.getId(), loggedAccount.getName(), loggedAccount.getBalance(), finalMessage));
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
  
  // Classe App
  public void run(){
    viewStack.push(this::initMenu);

    do {
      // Questao 14: Adcionado um try dentro do loop principal do app
      try {
        Runnable stackTop = viewStack.peek();
        stackTop.run();
        // Questao 15: Capturando Exceções que cancelem o input de dados
      } catch (NumberFormatException e){
        IOUtils.show("Digite um valor válido...");
      } catch (Exception e){
        IOUtils.show("Um erro ocorreu...");
        IOUtils.show("ERRO! " + e.getMessage());
      } finally {
        IOUtils.enterToContinue();
      }
      
    } while (!viewStack.empty());
    bye();

    IOUtils.closeScanner();
  }
  
}

