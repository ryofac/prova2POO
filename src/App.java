import exceptions.InsufficientFundsException;
import exceptions.InvalidValueException;

public class App {
  public void run() {
    Account mei = new Account(1, "Mei", -10);
  }
}

class Runner {
  public static void main(String[] args){
    App app = new App();
    app.run();
  }
}