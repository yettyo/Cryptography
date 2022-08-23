/**
 * @author Yener Karaca
 * @since 23.08.2022
 */
package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
        //ask the user if they would like to encrypt or decrypt a message.
        Scanner input = new Scanner(System.in);
        int decision = 0;
        do{
            System.out.print("What would you like to do?\n");
            System.out.print("Press 1 - Encryption (Send a message)\n");
            System.out.print("Press 2 - Decryption (Read a message)\n");
            System.out.print("Press 0 - Exit the program\n");
            decision = input.nextInt();
            if(decision == 1) { //Encryption
                System.out.println("Loading encryption...");
                Thread.sleep(1000);
                encryption();
            }
            else if(decision == 0) {
                System.out.println("Exiting program...");
                Thread.sleep(1000);
                System.exit(1);
            }
            else if(decision == 2) { //Decryption
                System.out.println("Loading decryption...");
                Thread.sleep(1000);
                //decryption();
            }
            else
                System.out.println("Invalid option, please try again.");
                Thread.sleep(1000);
        }while(true);
    }

    public static void encryption() throws InterruptedException, FileNotFoundException {
        String text = "";
        Scanner text2encrypt = new Scanner(System.in);
        System.out.print("Enter the text you would like to encrypt: ");
        text = text2encrypt.next();
        //check if there's an already existing encryption key, if yes then use it, if not then create a new one.
        File file = new File("encryptionKey.txt");
        if(file.exists() && !file.isDirectory()) {
            System.out.println("Encryption key found, using it.");
            Thread.sleep(1000);
            System.out.println("Encrypting...");
            Thread.sleep(2000);
            ArrayList<Integer> key = new ArrayList<>();
            Scanner keyReader = new Scanner(file);
            while(keyReader.hasNextInt()) {
                key.add(keyReader.nextInt());
            }
            keyReader.close();
            encryptMessage(key, text);
        }
        else {
            System.out.println("No encryption key found, creating a new one.");
            ArrayList<Integer> generatedKey = getRandomNonRepeatingIntegers(127, 0, 254);
            Thread.sleep(2000);
            System.out.println("A new encryption key was generated!");
            //write the generated key to a txt file
            try {
                FileWriter writer = new FileWriter("encryptionKey.txt");
                for(int i = 0; i < generatedKey.size(); i++) {
                    writer.write(generatedKey.get(i) + "\n");
                }
                writer.close();
            } catch (Exception e) {
                System.out.println("Error writing to file.");
            }
            //Now encrypt the message
            encryptMessage(generatedKey, text);
        }
    }

    public static void encryptMessage(ArrayList<Integer> generatedKey, String text) {
        //encrypt the message
        ArrayList<Integer> encryptedMessage = new ArrayList<>();
        char c = '0';
        int n = 0;
        for(int i = 0; i < text.length(); i++) {
            c = text.charAt(i);
            n = (int)c; //nth line must be read as an integer and added to encrypted message ArrayList
            encryptedMessage.add(generatedKey.get(n));
        }
        System.out.println("Encrypted message: " + encryptedMessage);
    }

    //Get selected size number without duplicate
    public static ArrayList getRandomNonRepeatingIntegers(int size, int min, int max) {
        ArrayList numbers = new ArrayList();
        Random random = new Random();
        while (numbers.size() < size) {
            //Get Random numbers between range
            int randomNumber = random.nextInt((max - min) + 1) + min;
            //Check for duplicate values
            if (!numbers.contains(randomNumber)) {
                numbers.add(randomNumber);
            }
        }
        return numbers;
    }

    public static void decryption() {

    }
}
