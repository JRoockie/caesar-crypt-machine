package com.javarush.cryptanalyzer.kylikov.Model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Model {
    public static String bigCap = "АБВГДЕЁЖЗИКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    public static String smallCap = "абвгдеёжзиклмнопрстуфхцчшщъыьэя";
    public static String numbers = "123456789";
    public static String symbolsExample = ".,\"\":-!? ";
    public static String myAlphabet = bigCap + smallCap + numbers + symbolsExample;
    int currentFunction;
    int key;
    String inputUser;
    String outputUser;
    Path inputPath;
    Path outputPath;
    String ANALYSIS = "Взлом методом анализа";
    String BRUTEFORCE = "Взлом шифра файла";
    String DECODE = "Расшифровка файла";
    String ENCODE = "Шифровка файла";
    String DEFAULT = "Пример работы программы";
    Scanner s = new Scanner(System.in);

    public void getOperation() {
        boolean isItTrue = true;

        while (isItTrue) {
            try {
                System.out.printf("Выберете операцию:\n\n0 - %s\n1 - %s\n2 - %s\n3 - %s\n4 - %s\n5 - %s\n", "EXIT",
                        ENCODE, DECODE, BRUTEFORCE, DEFAULT, ANALYSIS);

                String choice = s.nextLine();
                currentFunction = Integer.parseInt(choice);
                isItTrue = false;
                if (currentFunction == 0) {
                    System.out.println("ВЫХОД");
                    System.exit(0);
                } else if (currentFunction == 1) {
                    System.out.println("ТЕКУЩАЯ ОПЕРАЦИЯ: " + ENCODE);
                    takeKey();
                    takeInputPath();
                    takeOutputPath();
                    showFile(inputUser);
                    encode(inputUser, outputUser);
                    completeMessage();
                    showFile(outputUser);
                } else if (currentFunction == 2) {
                    System.out.println("ТЕКУЩАЯ ОПЕРАЦИЯ: " + DECODE);
                    takeKey();
                    takeInputPath();
                    takeOutputPath();
                    decode(inputUser,outputUser);
                    completeMessage();
                    showFile(outputUser);
                } else if (currentFunction == 3) {
                    System.out.println("ТЕКУЩАЯ ОПЕРАЦИЯ: " + BRUTEFORCE);
                    takeInputPath();
                    takeOutputPath();
                    showFile(inputUser);
                    bruteforce(inputUser,outputUser);
                    completeMessage();
                    showFile(outputUser);
                } else if (currentFunction == 4) {
                    System.out.println("ТЕКУЩАЯ ОПЕРАЦИЯ: " + DEFAULT);
                    getDefaultExample();
                } else if (currentFunction == 5) {
                    System.out.println("ТЕКУЩАЯ ОПЕРАЦИЯ: " + ANALYSIS);
                    System.out.println("Не готово");
                } else {
                    System.err.println("Такой команды не существует!");
                }
            } catch (Exception e) {
                System.err.println("Вы ввели НЕ ЧИСЛО\n");
            }
        }
    }

    public void takeKey() {
        boolean isItTrue = true;

        while (isItTrue) {
            try {
                System.out.println("\nВведите ключ: в соответствии с формой (ед. сдвига. Пример:5");

                String choice = s.nextLine();
                key = Integer.parseInt(choice);
                isItTrue = false;
                if (key < 26) {
                    System.out.println("Ключ " + key + " принят!");
                }

            } catch (RuntimeException e) {
                System.out.println("Неверный шаблон ключа");
            }
        }
    }

    public void takeInputPath() {

        boolean isItTrue = true;
        while (isItTrue) {
            try {
                System.out.println("\nВведите АБСОЛЮТНЫЙ путь ИСХОДНИКА: в соответствии с формой, без пробелов( Пример: \"C:\\testDest\\file.txt\" )");

                String choice = s.nextLine();
                outputPath = Path.of(choice);

                if (fileCheck(choice)) {
                    if (outputPath.isAbsolute()) {
                        System.out.println("Путь " + outputPath + " принят!");
                        outputUser = choice;
                        isItTrue = false;
                    } else {
                        System.out.println("ПУТЬ НЕ АБСОЛЮТНЫЙ");
                    }
                }
            } catch (RuntimeException e) {
                System.err.println("Неверный путь, или файла не существует");
            }
        }
    }

    public void takeOutputPath() {
        boolean isItTrue = true;
        while (isItTrue) {
            try {
                System.out.println("\nВведите АБСОЛЮТНЫЙ путь ЗАПИСИ: в соответствии с формой, без пробелов( Пример:\"C:\\testDest\" )");

                String choice = s.nextLine();
                inputPath = Path.of(choice);
                if (fileCheck(choice)) {
                    if (inputPath.isAbsolute()) {
                        System.out.println("Путь " + inputPath + " принят!\n");
                        inputUser = choice;
                        isItTrue = false;
                    } else {
                        System.out.println("ПУТЬ НЕ АБСОЛЮТНЫЙ");
                    }
                }
            } catch (RuntimeException e) {
                System.err.println("Неверный путь");
            }
        }
    }


    public boolean fileCheck(String pathCheck) {

        Path path = Paths.get(pathCheck);

        boolean exists = Files.exists(path);
        boolean notExists = Files.notExists(path);
        boolean isDir = Files.isDirectory(path);

        if (isDir) {
            System.out.println("Это Директория!\n");
            return false;
        } else if (exists) {
            System.out.println("Файл существует\n");
            return true;
        } else if (notExists) {
            System.err.println("ТАКОГО ФАЙЛА НЕТ\n");
            return false;
        } else {
            System.err.println("НЕТ ДОСТУПА К ФАЙЛУ\n");
            return false;
        }
    }

    public void getDefaultExample() throws IOException {
        String inputStr = "C:\\testSrc\\source.txt";
        String outputStr = "C:\\testSrc\\destination.txt";
        String decodeTest = "C:\\testSrc\\decode.txt";
        String bruteforceTest = "C:\\testSrc\\bruteforceRes.txt";
        key = 1;
        System.out.println("\n");
        System.out.println("----------------------------------");
        System.out.println("Текущий файл:");
        System.out.println("----------------------------------");
        showFile(inputStr);
        encode(inputStr, outputStr);
        showFile(outputStr);
        decode(outputStr, decodeTest);
        showFile(decodeTest);
        bruteforce(decodeTest, bruteforceTest);
        showFile(decodeTest);
    }

    public void encode(String source, String destination) throws IOException {
        System.out.println("----------------------------------");
        System.out.println("Шифровка");
        System.out.println("----------------------------------");
        try (FileReader reader = new FileReader(source);
             FileWriter writer = new FileWriter(destination)
        ) {
            int symbol;
            while ((symbol = reader.read()) != -1) {
                char currentSymbol = symbolShift((char) symbol, key);
                writer.write(currentSymbol);

            }

        }
    }


    private void showFile(String path) throws IOException {

        try (FileReader reader = new FileReader(path)
        ) {
            int symbol;
            while ((symbol = reader.read()) != -1) {
                System.out.print((char) symbol);
            }
        }
        System.out.println("\n");
    }

    private char symbolShift(char symbol, int shift) {
        if (myAlphabet.indexOf(symbol) != -1) {
            return myAlphabet.charAt((myAlphabet.indexOf(symbol) + shift) % myAlphabet.length());
        } else {
            return symbol;
        }
    }

    public void completeMessage() {
        System.out.println("\n");
        System.out.println("-------------------------------------------");
        System.out.println("------------ОПЕРАЦИЯ ВЫПОЛНЯЕТСЯ-----------");
        System.out.println("-------------------------------------------");
        System.out.println("------------------SUCCESS!-----------------");
        System.out.println("-------------------------------------------");

        System.out.println("Результат:");

    }

    public void decode(String source, String destination) {
        System.out.println("----------------------------------");
        System.out.println("Расшифровка");
        System.out.println("----------------------------------");
        try (FileReader reader = new FileReader(source);
             FileWriter writer = new FileWriter(destination)
        ) {
            int symbol;
            int shiftAlphabet = myAlphabet.length() - key;
//            int shift = -key;
            while ((symbol = reader.read()) != -1) {
                char currentSymbol = (char) symbol;

                currentSymbol = symbolShift(currentSymbol, shiftAlphabet);

                writer.write(currentSymbol);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void bruteforce(String source, String destination) throws IOException {
        System.out.println("----------------------------------");
        System.out.println("Bruteforce");
        System.out.println("----------------------------------");
        Map<Character, Integer> frequency = getFrequency(source);
        int maxFrequency = 0;
        char mostFrequentLetter = ' ';
        for (Map.Entry<Character, Integer> entry : frequency.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                maxFrequency = entry.getValue();
                mostFrequentLetter = entry.getKey();
            }
        }
        int key = myAlphabet.indexOf(mostFrequentLetter) - myAlphabet.indexOf('О');
        if (key < 0) {
            key += myAlphabet.length();
        }
        try (FileWriter writer = new FileWriter(destination)) {
            try (FileReader reader = new FileReader(source)) {
                int symbol;
                while ((symbol = reader.read()) != -1) {
                    char currentSymbol = (char) symbol;
                    if (myAlphabet.indexOf(currentSymbol) != -1) {
                        char decryptedSymbol = decryptSymbol(currentSymbol, key);
                        writer.write(decryptedSymbol);
                    } else {
                        writer.write(currentSymbol);
                    }
                }
            }
        }
    }

    private static Map<Character, Integer> getFrequency(String inputFile) throws IOException {
        Map<Character, Integer> frequency = new HashMap<>();
        try (FileReader reader = new FileReader(inputFile)) {
            int symbol;
            while ((symbol = reader.read()) != -1) {
                char currentSymbol = (char) symbol;
                if (myAlphabet.indexOf(currentSymbol) != -1) {
                    frequency.put(currentSymbol, frequency.getOrDefault(currentSymbol, 0) + 1);
                }
            }
        }
        return frequency;
    }

    private static char decryptSymbol(char symbol, int key) {
        int index = myAlphabet.indexOf(symbol) - key;
        if (index < 0) {
            index += myAlphabet.length();
        }
        return myAlphabet.charAt(index);
    }


}