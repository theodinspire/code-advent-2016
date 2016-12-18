package com.theodinspire.codeadvent.WeekOne;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ecormack on 12/16/2016.
 */
public class AddressSniffer {
    List<String> unbracketed    = new LinkedList<>();
    List<String> bracketed      = new LinkedList<>();

    public AddressSniffer(String input) throws IllegalArgumentException {
        if (!validateInput(input)) throw new IllegalArgumentException("Input malformed");
        String[] segments = input.split("[\\[\\]]");
        int counter = 0;

        for (String segment : segments) {
            if (counter % 2 == 0)
                unbracketed.add(segment);
            else    //  if counter % 2 == 1
                bracketed.add(segment);
            ++counter;
        }

    }

    public boolean supportsTLS() {
        for (String str : bracketed)
            if (checkStringForABBA(str))
                return false;
        for (String str : unbracketed)
            if (checkStringForABBA(str))
                return true;

        return false;
    }

    public boolean supportsSSL() {
        for (char x = 'a'; x <= 'z'; ++x)
            for (char y = 'a'; y <= 'z'; ++y)
                if (x != y) {
                    String aba = "" + x + y + x;
                    String bab = "" + y + x + y;
                    for (String umbra : unbracketed)
                        if (umbra.contains(aba))
                            for (String braza : bracketed)
                                if (braza.contains(bab))
                                    return true;
                }
        return false;
    }

    private static boolean checkStringForABBA(String string) {
        for (char x = 'a'; x <= 'z'; ++x)
            for (char y = 'a'; y <= 'z'; ++y) {
                if (x != y) {
                    String pattern = "" + x + y + y + x;
                    if (string.contains(pattern)) return true;
                }
        }

        return false;
    }

    /// Allows validation that every break in the split in the constructor is valid.
    private boolean validateInput(String input) {
        boolean inBrackets = false;

        for (char c : input.toCharArray()) {
            if (c == '[') {
                if (inBrackets)
                    return false;
                else
                    inBrackets = true;
            } else if (c == ']') {
                if (!inBrackets)
                    return false;
                else
                    inBrackets = false;
            }
        }

        return true;
    }

    public static void main (String[] args) {
        int tlsCount = 0;
        int sslCount = 0;
        try (BufferedReader reader =
                     new BufferedReader(new FileReader("src/resources/WeekOne/AddressSniffer/input.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                AddressSniffer sniffer = new AddressSniffer(line);
                if (sniffer.supportsTLS())
                    ++tlsCount;
                if (sniffer.supportsSSL())
                    ++sslCount;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println(String.format("%d IPs support TLS", tlsCount));
        System.out.println(String.format("%d IPs support SSL", sslCount));
    }
}
