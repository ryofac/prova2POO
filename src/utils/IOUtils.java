package utils;

import java.util.Scanner;

public class IOUtils {
    private static Scanner sc = new Scanner(System.in, "UTF-8").useDelimiter("\n");

    static public void show(String text){
        System.out.println(text);
    }

    static public String getText(String message) {
        System.out.println(message);
        return sc.next().trim().toLowerCase();
    }
    
    static public int getInt(String message) {
        int value = Integer.parseInt(getText(message));
        return value;
    }

    static public void closeScanner(){
        sc.close();
    }
    
}
