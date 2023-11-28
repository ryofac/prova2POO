package ImplementacaoBanco;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utils {
    static void show(String text) {
        System.out.println(text);
    }

    static void createMenu() {

    }

    static void createFile(String filePath) {
        try {
            File logs = new File(filePath);
            if (!logs.exists()) {
                logs.createNewFile();
                System.out.println("Arquivo " + filePath + " criado!");
            }
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao escrever no arquivo " + filePath);
            e.printStackTrace();

        }

    }

    static File writeOnFile(String filePath, String content) {
        Charset utf8 = Charset.forName("UTF-8");
        try (FileWriter escritor = new FileWriter(filePath, utf8, false)) {
            escritor.write(content);
            return new File(filePath);

        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao escrever no arquivo " + filePath);
        }
        return null;

    }

    static List<String> readLinesOnFile(String filePath) {
        try {

            File toRead = new File(filePath);
            if (!toRead.exists()) {
                createFile(filePath);
            }
            Scanner reader = new Scanner(toRead);
            List<String> lines = new ArrayList<String>();
            while (reader.hasNextLine()) {
                lines.add(reader.nextLine());

            }
            reader.close();
            return lines;

        } catch (Exception e) {
            Utils.show("Ocorreu um erro ao ler o arquivo " + filePath);
            e.printStackTrace();
            return null;
        }

    }

    static Client findClientByCpf(List<Client> clientsList, String target) {
        for (Client client : clientsList) {
            if (client.getCpf().equals(target)) {
                return client;
            }
        }
        return null;
    }

}
