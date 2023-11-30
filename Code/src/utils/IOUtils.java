package utils;

import java.util.Scanner;

import exceptions.InvalidValueException;

public class IOUtils {
    private static Scanner sc = new Scanner(System.in, "UTF-8").useDelimiter("\n");

    static public void show(String text){
        System.out.println(text);
    }

    static public String getText(String message) {
        System.out.print(message);
        return sc.next().trim();
    }
    
    static public int getInt(String message) {
        int value = Integer.parseInt(getText(message));
        return value;
    }
     static public double getDouble(String message) {
        double value = Double.parseDouble(getText(message).replace(',', '.'));
        return value;
    }
    static public double getPercent(String message) throws InvalidValueException{
        double value = getDouble(message);
        if(value < 0 || value > 100){
            throw new InvalidValueException("ERRO: O valor deve ser uma porcentagem");
        }
        return value;

    }

    static public void clearScreen(){
        IOUtils.show("\n".repeat(100));
    }

    static public void enterToContinue(){
        System.out.print("<Enter>");
        sc.next();
    }

    static public void closeScanner(){
        sc.close();
    }
    
}
