import java.util.Stack;

import utils.IOUtils;

public class App {
  Stack<Runnable> viewStack = new Stack<>();
  Account contaLogada;
  String actualMenu;
  int opcao = -1;
  int menuAtual = 0;

  // Implementando as funções do banco:

  private void menu(){
    opcao = IOUtils.getInt("1 - Menu Conta, 2 - Se juntar ao PatRoBank, 0 - Sair do APP \n > ");
    switch (opcao) {
      case 1:
        viewStack.push(this::menuConta);
        break;

      case 2:
        viewStack.push(this:: menuCriar);
        break;

      case 0:
        viewStack.pop();
        break;

      default:
        IOUtils.show("Opção inválida!");
        break;
    }
  }
  private void menuConta(){
    opcao = IOUtils.getInt( "1 - Depositar, 2 - Sacar, 3 - Transferir, 0 - Deslogar \n > ");
    switch (opcao) {
      case 1:
      // deposit();
        break;
      case 2:
      // withdraw();
      break;
      case 3:
      // transfer();
      break;
      case 0:
        viewStack.pop();
      break;
    
      default:
        IOUtils.show("Opção Inválida!");
        break;
    }
  }
  private void menuCriar(){
    opcao = IOUtils.getInt( "1 - Criar conta poupança, 2 - Criar conta corrente \n > ");
    switch (opcao) {
      case 1:
        break;
      
      case 2:
        break;
    
      default:
        IOUtils.show("Opção Inválida!");
        break;
    }
  }
  
  public void run(){
    viewStack.push(this::menu);

    do {
      
    } while (false);



    IOUtils.closeScanner();
  }
  
}