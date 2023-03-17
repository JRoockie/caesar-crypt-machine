package com.javarush.cryptanalyzer.kylikov.Model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;


public class Model {
    public static String bigCap = "АБВГДЕЁЖЗИКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    public static String smallCap = "абвгдеёжзиклмнопрстуфхцчшщъыьэя";
    public static String numbers = "123456789";
    public static String symbolsExample = ".,\"\":-!? ";

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
                } else if (currentFunction == 3) {
                    System.out.println("ТЕКУЩАЯ ОПЕРАЦИЯ: " + BRUTEFORCE);
                    takeInputPath();
                    takeOutputPath();
                    showFile(inputUser);
                    decode(inputUser, outputUser);
                    completeMessage();
                    showFile(outputUser);
                } else if (currentFunction == 4) {
                    System.out.println("ТЕКУЩАЯ ОПЕРАЦИЯ: " + DEFAULT);
                    getDefaultExample();
                } else if (currentFunction == 5) {
                    System.out.println("ТЕКУЩАЯ ОПЕРАЦИЯ: " + ANALYSIS);
                    takeKey();
                    takeInputPath();
                    takeOutputPath();
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
        key = 1;
        System.out.println("\n");
        System.out.println("Текущий файл:\n");
        showFile(inputStr);
        encode(inputStr, outputStr);
        showFile(outputStr);
        decode(outputStr, decodeTest);
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

        try (FileReader reader = new FileReader(path);
        ) {
            int symbol;
            while ((symbol = reader.read()) != -1) {
                System.out.print((char) symbol);
            }
        }
        System.out.println("\n");
    }

    private char symbolShift(char symbol, int shift) {
        if (bigCap.indexOf(symbol) != -1) {
            return bigCap.charAt((bigCap.indexOf(symbol) + shift) % bigCap.length());
        } else if (smallCap.indexOf(symbol) != -1) {
            return smallCap.charAt((smallCap.indexOf(symbol) + shift) % smallCap.length());
        } else if (numbers.indexOf(symbol) != -1) {
            return numbers.charAt((numbers.indexOf(symbol) + shift) % numbers.length());
        } else if (symbolsExample.indexOf(symbol) != -1) {
            return symbolsExample.charAt((symbolsExample.indexOf(symbol) + shift) % symbolsExample.length());
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
            int shiftBig = bigCap.length() - key;
            int shiftNum = numbers.length() - key;
            int shiftSymbols = symbolsExample.length() - key;
            int shiftSmall = smallCap.length() - key;

//            int shift = -key;
            while ((symbol = reader.read()) != -1) {
                char currentSymbol = (char) symbol;
                if (Character.isUpperCase(currentSymbol)) {
                    currentSymbol = symbolShift(currentSymbol, shiftBig);
                } else if (Character.isLowerCase(currentSymbol)) {
                    currentSymbol = symbolShift(currentSymbol, shiftSmall);
                } else if (Character.isDigit(currentSymbol)) {
                    currentSymbol = symbolShift(currentSymbol, shiftNum);
                }else if (isSpecialSymbol(currentSymbol,symbolsExample)){
                    currentSymbol = symbolShift(currentSymbol, shiftSymbols);
                }
                writer.write(currentSymbol);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean isSpecialSymbol(char c, String symbolsExample) {
        for (int i = 0; i < symbolsExample.length(); i++) {
            if (c == symbolsExample.charAt(i)) {
                return true;
            }
        }
        return false;
    }
    public void hackThis(String source) {


    }

    public void splitter() {
        System.out.println("===========================================");

    }
}