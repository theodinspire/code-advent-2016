package com.theodinspire.codeadvent.WeekOne;

import java.security.*;

/**
 * Created by ecormack on 12/16/2016.
 */
public class DoorHasher {
    public static void main(String[] args) {
        String doorID = "ugkcyxxp";

        //System.out.println("First door: " + firstDoor(doorID));

        System.out.println(secondDoor(doorID));
    }

    private static String firstDoor(String input) {
        MessageDigest md;
        String algorithm = "MD5";
        StringBuilder builder = new StringBuilder();
        int count = 0;
        int index = 0;

        try {
            md = MessageDigest.getInstance(algorithm);

            while (count < 8) {
                byte[] hash = md.digest((input + index++).getBytes());
                if (hash[0] == 0 && hash[1] == 0 && (hash[2] & 0xf0) == 0) {
                    builder.append(String.format("%x", hash[2]));
                    ++count;
                }
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Algorithm " + algorithm + " does not exist");
            e.printStackTrace();
            System.exit(1);
        }

        return builder.toString();
    }

    private static String secondDoor(String input) {
        PassBuilder passBuilder = new PassBuilder();

        MessageDigest md;
        String algorithm = "MD5";
        int index = 0;

        try {
            md = MessageDigest.getInstance(algorithm);

            while (!passBuilder.isComplete()) {
                byte[] hash = md.digest((input + index++).getBytes());
                if (hash[0] == 0 && hash[1] == 0 && (hash[2] & 0xf0) == 0) {
                    passBuilder.addCharacter(hash[2], hash[3]);
                }
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Algorithm " + algorithm + " does not exist");
            e.printStackTrace();
            System.exit(1);
        }

        return passBuilder.getPassword();
    }

    private static class PassBuilder {
        private boolean complete = false;

        private String password = "ABCDEFGH";

        public boolean isComplete() {
            return complete;
        }

        public String getPassword() {
            return password;
        }

        public void addCharacter(byte index, byte replacement) {
            char nova = String.format("%02x", replacement).charAt(0);
            char olda = (char) (index + 65);

            password = password.replace(olda, nova);

            System.out.println(password.replaceAll("[A-H]", "-"));

            if (!password.matches("\\w*[A-H]\\w*")) {
                complete = true;
            }
        }
    }
}
