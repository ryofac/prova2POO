package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import exceptions.fileExceptions.FileEmptyException;
import exceptions.fileExceptions.FileException;
import exceptions.valueExceptions.InvalidValueException;


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

    static public void createFile(String filePath) throws FileException{
        try{
            File toBeCreated = new File(filePath);
            toBeCreated.createNewFile();
        }catch (IOException e) {
            throw new FileException("Um erro inesperado ocorreu ao criar o arquivo: " + filePath);
        }
        
    }

    static public List<String> readFromFile(String filePath) throws FileException, FileEmptyException {
        File toBeRead = new File(filePath);
        List<String> lines = new ArrayList<>();

        try(Scanner reader = new Scanner(toBeRead)){
            while(reader.hasNextLine()){
                lines.add(reader.nextLine());
            }
        } catch(FileNotFoundException e){
            System.out.println("Arquivo não encontrado: " + toBeRead);
            createFile(filePath);
        }
        if(lines.size() <= 0) throw new FileEmptyException("ERRO: Arquivo" + filePath + " não contém dados");
        return lines;
       
    }

    static public void writeOnFile(String filePath, String content) throws FileException{
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(content);
        } catch (IOException e) {
            throw new FileException("ERRO: ocorreu um erro ao escrever no arquivo" + filePath);
        }
    }

    static public void closeScanner(){
        sc.close();
    }
    
}
