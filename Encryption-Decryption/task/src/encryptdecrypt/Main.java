package encryptdecrypt;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        boolean encrypt = true;
        boolean dataExists = false;
        boolean outExists = false;
        boolean shiftIsAlphabet = true;
        String text = "";
        String fileName = "";
        int key = 0;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-mode":
                    encrypt = !"dec".equals(args[i + 1]);
                    break;
                case "-data":
                    dataExists = true;
                    text = args[i + 1];
                    break;
                case "-in":
                    if (!dataExists) {
                        try (Scanner reader = new Scanner(new File(args[i + 1]))) {
                            text = reader.nextLine();
                        } catch (FileNotFoundException e) {
                            System.out.println("Error");
                        }
                    }
                    break;
                case "-out":
                    outExists = true;
                    fileName = args[i + 1];
                    break;
                case "-alg":
                    shiftIsAlphabet = !"unicode".equals(args[i + 1]);
                    break;
                case "-key":
                    key = Integer.parseInt(args[i + 1]);
                    break;
            }
        }

        Factory factory = new Factory(shiftIsAlphabet ? new AlphabetAlgorithm() : new UnicodeAlgorithm());
        String cipher = factory.shift(text, key, encrypt);
        factory.write(fileName, outExists, cipher);
    }
}

interface Algorithm {
    String shift(String string, int key, boolean encrypt);
}

class UnicodeAlgorithm implements Algorithm {
    @Override
    public String shift(String string, int key, boolean encrypt) {
        StringBuilder cipher = new StringBuilder();
        for (char ch : string.toCharArray()) {
            cipher.append(encrypt ? (char) (ch + key) : (char) (ch - key));
        }
        return new String(cipher);
    }
}

class AlphabetAlgorithm implements Algorithm {
    @Override
    public String shift(String string, int key, boolean encrypt) {
        StringBuilder cipher = new StringBuilder();
        for (char ch : string.toCharArray()) {
            if (ch >= 'a' && ch <= 'z') {
                char shiftedCh;
                if (encrypt) {  
                    shiftedCh = (char) ((ch - 'a' + key) % 26 + 'a');
                } else {
                    shiftedCh = (char) ((26 + ch - 'a' - key) % 26 + 'a');
                }
                cipher.append(shiftedCh);
            } else {
                cipher.append(ch);
            }
        }
        return new String (cipher);
    }
}

class Factory {
    Algorithm algorithm;

    Factory(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public String shift(String string, int key, boolean encrypt) {
        return this.algorithm.shift(string, key, encrypt);
    }

    public void write(String fileName, boolean outExists, String cipher) {
        if (outExists) {
            try (Writer writer = new PrintWriter(new File(fileName))) {
                writer.write(cipher);
            } catch (IOException e) {
                System.out.println("Error: " + e);
            }
        } else {
            System.out.println(cipher);
        }
    }
}