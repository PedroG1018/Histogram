/**
 * Title: Histogram.java
 * Purpose: Program displays list of distinct characters and their occurrences from a file as a histogram
 * Author: Pedro Gutierrez Jr.
 * Date: 7 Feb 2020
 * References Used: Bubble Sort - https://www.geeksforgeeks.org/bubble-sort/
 *                  StringBuilder - https://docs.oracle.com/javase/7/docs/api/java/lang/StringBuilder.html
 */

import java.io.*;
import java.util.Scanner;

public class Histogram {
    //declaration of private static class members
    private static char[] letter;
    private static int[] letterCount;
    private static String filename;
    private static int[] count;

    //parameter constructor for Histogram class
    public Histogram(char[] letter, int[] letterCount, String filename, int[] count){
        this.letter = letter;
        this.letterCount = letterCount;
        this.filename = filename;
        this.count = count;
    }

    //method will read the file name from the user using Scanner
    public static String getFileName(){
        Scanner input = new Scanner(System.in);
        System.out.print("Input filename: ");
        filename = input.nextLine();
        return filename;
    }

    //method will read the letters in the file requested by the user using Scanner
    public static void read(char[] letter, int[] letterCount, String filename){
        File f = new File(filename);
        Scanner fileScan = null;
        StringBuilder fileReader = new StringBuilder(200);

        try{
            fileScan = new Scanner(f);
            //use StringBuilder 'fileReader' to store letters from the file
            while (fileScan != null && fileScan.hasNext()){
                fileReader.append(fileScan.nextLine());
            }
            letter = new char[fileReader.length()];
            //store letters from StringBuilder 'fileReader' into char array 'letter'
            for (int i = 0; i < fileReader.length(); i++){
                letter[i] = fileReader.charAt(i);
            }
        }catch(FileNotFoundException e){
            System.out.println("could not find the file " + e);
        }

        //call constructor to update values of class members
        new Histogram(letter, letterCount, filename, count);
    }

    //method will sort the letters and letter occurrences
    public static void sort(char[] letter, int[] letterCount){
        StringBuilder sortedLetters = new StringBuilder(letter.length);
        int occurrence = 0;
        int target = 0;
        boolean check = false;
        int[] tempArr = new int[10];

        //bubble sort to sort elements of 'letter' char array
        int n = letter.length;
        for (int i = 0; i < n-1; i++){
            for (int j = 0; j < n-i-1; j++){
                if (letter[j] > letter[j+1]){
                    char temp = letter[j];
                    letter[j] = letter[j + 1];
                    letter[j+1] = temp;
                }
            }
        }

        //counts number of occurrences of each letter in 'letter' char array, also stores each unique letter in 'sortedLetters' StringBuilder
        for (int i = 1; i < letter.length; i++) {
            if (letter[i] == letter[i-1]) {
                occurrence++;
                tempArr[target] = occurrence;
            }else{
                occurrence++;
                tempArr[target] = occurrence;
                sortedLetters.append(letter[i-1]);
                target++;
                if(i == letter.length-1){
                    tempArr[target] = 1;
                    sortedLetters.append(letter[letter.length-1]);
                    check = true;
                    break;
                }
                occurrence = 0;
            }
        }
        if (!check){
            tempArr[target] += 1;
            sortedLetters.append(letter[letter.length-1]);
        }

        //repopulate 'letter' char array with only each unique letter from 'sortedLetters' StringBuilder
        letter = new char[sortedLetters.length()];
        for (int i = 0; i < letter.length; i++) {
            letter[i] = sortedLetters.charAt(i);
        }

        //populates number of letter occurrences into 'letterCount' int array
        letterCount = new int[target+1];
        for (int i = 0; i < letterCount.length; i++) {
            letterCount[i] = tempArr[i];
        }

        //resorts 'letter' and 'letterCount' arrays by number of letter occurrences, least to greatest
        n = letterCount.length;
        for (int i = 0; i < n-1; i++){
            for (int j = 0; j < n-i-1; j++){
                if (letterCount[j] > letterCount[j+1]){
                    int temp1 = letterCount[j];
                    letterCount[j] = letterCount[j + 1];
                    letterCount[j+1] = temp1;
                    char temp2 = letter[j];
                    letter[j] = letter[j + 1];
                    letter[j+1] = temp2;
                }
            }
        }

        //call constructor to update values of class members
        new Histogram(letter, letterCount, filename, count);
    }

    //method displays the histogram
    public static void display(char[] letter, int[] count){
        StringBuilder fileLetters = new StringBuilder(letter.length);
        StringBuilder alphabet = new StringBuilder("ABCDEFGHIJK");
        StringBuilder histo = new StringBuilder("|    |                     ");
        String[] borders = {"============================", "----------------------------"};
        String nums;

        //stores letters from 'letter' char array in 'fileLetters' StringBuilder with spaces in between
        for (int i = 0; i < letter.length; i++){
            if (i != letter.length-1) {
                fileLetters.append(letter[i] + " ");
            } else {
                fileLetters.append((letter[i]));
            }
        }

        //prints first part of histogram
        System.out.println("Char Occurrences");
        for (int i = 0; i < count.length; i++){
            System.out.println(letter[i] + " " + count[i]);
        }
        System.out.println("\n" + borders[0]);

        //block of code modifies 'histo' StringBuilder to store and display the values of the histogram correctly
        if(letter.length != 1) {
            for (int i = count.length - 1; i > 0; i--) {
                if (count[i] == count[i - 1]) {
                    if (i == 1) {
                        nums = count[i] + "";
                        histo.replace(5-nums.length(), 5, nums);
                        histo.replace(histo.length()-fileLetters.length(), histo.length(), fileLetters.substring(0, fileLetters.length()));
                        System.out.println(histo);
                    }
                } else {
                    nums = count[i] + "";
                    histo.replace(5-nums.length(), 5, nums);
                    histo.replace((histo.length()-fileLetters.length())+i*2, histo.length(), fileLetters.substring(i*2, fileLetters.length()));
                    System.out.println(histo);
                    if (i == 1) {
                        nums = count[i-1] + "";
                        histo.replace(5-nums.length(), 5, nums);
                        histo.replace(histo.length()-fileLetters.length(), histo.length(), fileLetters.substring(0, fileLetters.length()));
                        System.out.println(histo);
                    }
                }
            }
        }else{
            nums = count[0] + "";
            histo.replace(5-nums.length(), 5, nums);
            histo.replace(histo.length()-fileLetters.length(), histo.length(), fileLetters.substring(0, fileLetters.length()));
            System.out.println(histo);
        }

        System.out.println("----------------------------");
        System.out.print("      ");

        //goes through 'alphabet' StringBuilder and removes all letters already included in the histogram
        for (int i = 0; i < fileLetters.length(); i++){
            for (int j = 0; j < alphabet.length(); j++){
                if(alphabet.charAt(j) == fileLetters.charAt(i)) {
                    alphabet.deleteCharAt(j);
                    break;
                }
            }
        }

        //prints out last part of histogram
        for(int i = 0; i < alphabet.length(); i++){
            System.out.print(alphabet.charAt(i) + " ");
        }
        alphabet.append(fileLetters);
        for(int i = alphabet.length()-fileLetters.length(); i < alphabet.length(); i++){
            System.out.print(alphabet.charAt(i));
        }
    }

    //main method will act as driver that calls the class methods
    public static void main(String args[]){
        Histogram h = new Histogram(letter, letterCount, filename, count);

        h.getFileName();
        h.read(letter, letterCount, filename);
        h.sort(letter, letterCount);
        h.display(letter, letterCount);
    }
}
