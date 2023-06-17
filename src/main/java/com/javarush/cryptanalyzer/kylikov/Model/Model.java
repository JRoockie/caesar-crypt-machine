package com.javarush.cryptanalyzer.kylikov.Model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class Model {
    public static String bigCap = "АБВГДЕЁЖЗИКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    public static String smallCap = "абвгдеёжзиклмнопрстуфхцчшщъыьэя";
    public static String numbers = "123456789";
    public static String symbolsExample = ".,\":-!? ";
    public static String myAlphabet = bigCap + smallCap + numbers + symbolsExample;
    public Map<Character, Double> standartAlphabetMap = new HashMap<>();

    {
        standartAlphabetMap.put(' ', 15.74);
        standartAlphabetMap.put('о', 7.65);
        standartAlphabetMap.put('е', 7.20);
        standartAlphabetMap.put('и', 6.60);
        standartAlphabetMap.put('а', 6.15);
        standartAlphabetMap.put('н', 5.85);
        standartAlphabetMap.put('т', 5.55);
        standartAlphabetMap.put('с', 4.80);
        standartAlphabetMap.put('м', 4.05);
        standartAlphabetMap.put('в', 4.05);
        standartAlphabetMap.put('л', 3.60);
        standartAlphabetMap.put('ы', 3.00);
        standartAlphabetMap.put('к', 2.55);
        standartAlphabetMap.put('р', 2.40);
        standartAlphabetMap.put('х', 2.25);
        standartAlphabetMap.put('ь', 2.25);
        standartAlphabetMap.put('п', 2.10);
        standartAlphabetMap.put('у', 1.80);
        standartAlphabetMap.put(',', 1.65);
        standartAlphabetMap.put('д', 1.50);
        standartAlphabetMap.put('б', 1.50);
        standartAlphabetMap.put('з', 1.35);
        standartAlphabetMap.put('г', 1.05);
        standartAlphabetMap.put('ч', 0.90);
        standartAlphabetMap.put('ж', 0.75);
        standartAlphabetMap.put('.', 0.75);
        standartAlphabetMap.put('я', 0.75);
        standartAlphabetMap.put('ф', 0.60);
        standartAlphabetMap.put('ш', 0.60);
        standartAlphabetMap.put('ц', 0.45);
        standartAlphabetMap.put('э', 0.30);
        standartAlphabetMap.put('щ', 0.30);
    }

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
                    decode(inputUser, outputUser);
                    completeMessage();
                    showFile(outputUser);
                } else if (currentFunction == 3) {
                    System.out.println("ТЕКУЩАЯ ОПЕРАЦИЯ: " + BRUTEFORCE);
                    takeInputPath();
                    takeOutputPath();
                    showFile(inputUser);
//                    bruteforce(inputUser, outputUser);
                    ultraBruteforce(inputUser, outputUser);
                    completeMessage();
                    showFile(outputUser);
                } else if (currentFunction == 4) {
                    System.out.println("ТЕКУЩАЯ ОПЕРАЦИЯ: " + DEFAULT);
                    getDefaultExample();
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
        String inputStr = "src\\main\\resources\\input.txt";
        String outputStr = "src\\main\\resources\\output.txt";
        String encodedStr = "src\\main\\resources\\encoded.txt";
        File input = new File(inputStr);
        File output = new File(outputStr);
        File encoded = new File(encodedStr);


//        String inputStr = "C:\\testSrc\\source.txt";
//        String outputStr = "C:\\testSrc\\destination.txt";
//        String encodedStr = "C:\\testSrc\\decode.txt";
//        String bruteforceTest = "C:\\testSrc\\bruteforceRes.txt";
        key = 1;
        System.out.println("\n");
        System.out.println("----------------------------------");
        System.out.println("Текущий файл:");
        System.out.println("----------------------------------");
        showFile(inputStr);
        encode(inputStr, encodedStr);
        showFile(encodedStr);
        decode(encodedStr, outputStr);
        showFile(outputStr);
        ultraBruteforce(encodedStr, outputStr);
//      bruteforce(encodedStr, outputStr);
//      showFile(outputStr);
    }

    private void ultraBruteforce(String encodedStr, String outputStr) throws IOException {

//        Map<Integer, Integer> res = new HashMap<>();
        char[] memory = fileToMemory(encodedStr);

        char[] result = new char[memory.length];
        int temp = 0;
        for (int i = 0; i < myAlphabet.length() ; i++) {
            var map = solveFrequency(memory);
            var count = calculateCount(map, 0.01);
//            res.put(i, count);
            if (count>= temp){
                result = Arrays.copyOf(memory,memory.length);
                temp = count;
            }
            recalculateMemory(memory);
        }
        writeToFile(outputStr, result);
    }
    public void writeToFile(String outputStr, char[] result) throws IOException {
        try (
             FileWriter writer = new FileWriter(outputStr)
        ) {
            for (char i : result){
                writer.write(i);
            }
        }
    }


    public void recalculateMemory(char[] memory) {
        for (int i = 0; i < memory.length; i++) {
            char temp = memory[i];
            // найти индекс символа в алфавите
            var index = myAlphabet.indexOf(temp);
            memory[i] = myAlphabet.toCharArray()[index == myAlphabet.length() - 1 ? 0 : index + 1];
        }


    }

    private char[] fileToMemory(String encodedStr) throws IOException {
        try (FileReader reader = new FileReader(encodedStr)) {

            StringBuilder sb = new StringBuilder();
            int character;
            while ((character = reader.read()) != -1) {
                Character c = (char) character;
                String string = c.toString();
                sb.append(string);
            }
            return sb.toString().toCharArray();
        }
    }


    public void encode(String source, String destination) throws IOException {
        encode(source, destination, key);
    }

    public void encode(String source, String destination, int shiftInt) throws IOException {
        System.out.println("----------------------------------");
        System.out.println("Шифровка");
        System.out.println("----------------------------------");
        try (FileReader reader = new FileReader(source);
             FileWriter writer = new FileWriter(destination)
        ) {
            int symbol;
            while ((symbol = reader.read()) != -1) {
                char currentSymbol = symbolShift((char) symbol, shiftInt);
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


//    public void bruteforce(String source, String destination) throws IOException {
//        for (int i = 0; i < ; i++) {
//            solveFrequency(getCurrentTextStats(source, destination));
//
//            encode();
//
//        }
//
//
//    }

    public char[] getCurrentTextStats(String source, String destination) {
        try (FileReader reader = new FileReader(source)) {
            StringBuilder sb = new StringBuilder();

            int character;
            while ((character = reader.read()) != -1) {
                Character c = (char) character;
                String string = c.toString().toLowerCase();
                sb.append(string);
            }
            char[] charArray = sb.toString().toCharArray();
            return charArray;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<Character, Double> solveFrequency(char[] charArray) {

        Map<Character, Integer> rawTextFrequencyMap = new HashMap<>();
        Map<Character, Double> result = new HashMap<>();

        for (char symbol : charArray) {
            if (rawTextFrequencyMap.containsKey(symbol)) {
                // Если символ уже существует в Map, обновляем его значение
                int currentCount = rawTextFrequencyMap.get(symbol);
                rawTextFrequencyMap.put(symbol, currentCount + 1);
            } else {
                // Если символ не существует в Map, добавляем его со значением 1
                rawTextFrequencyMap.put(symbol, 1);
            }
        }
        for (Character i : rawTextFrequencyMap.keySet()) {
            result.put(i, Double.valueOf(rawTextFrequencyMap.get(i)) / charArray.length * 100);
        }
        return result;
//        realTextFrequencyMap.forEach(x -> check(x){);
//        System.out.println(realTextFrequencyMap);

    }

    public int calculateCount(Map<Character, Double> map, Double range) {
        int counter = 0;
        for (var i : map.entrySet()) {
            if (check(i, range)) {
                counter++;
            }
        }
        return counter;
    }

    public boolean check(Map.Entry<Character, Double> couple, Double range) {
        var key = couple.getKey();
        var value = couple.getValue();
        if (!standartAlphabetMap.containsKey(key)) {
            return false;
        }
        double d = standartAlphabetMap.get(key);
// хранение эталонного значения
        double maxRange = d + range;
        double minRange = d - range;
        return value > minRange && value < maxRange;
    }


}
