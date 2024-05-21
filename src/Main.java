
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
public class Main {
    private static final String red="\033[0;31m";
    private static final String yellow="\033[0;93m";
    private static final String blue="\033[0;34m";
    private static final String purple="\033[0;35m";
    private static final String cyan="\033[0;96m";
    private static final String pink="\033[0;95m";
    private static final String brightRed="\033[0;91m";
    private static final String brightGreen="\033[0;92m";



    private static final String reset="\033[0m";
    public static void main(String[] args) throws FileNotFoundException {
        Scanner userInput = new Scanner(System.in);
        startMenu();
        boolean playAgain = true;
        while (playAgain==true) {
            int wrongCount = 0;
            String[] words = readFileIntoArray("src/HangmanWordsList.txt");
            String word = randomWord(words);
            //hashset: collection of unique items
            Set<Character> guessedLetters = new HashSet<>();
            for (int i = 0; i < word.length(); i++) {
                System.out.print("_ " );
            }
            System.out.println("\n");
            playGame(wrongCount, userInput, word, (HashSet) guessedLetters);
            System.out.println(purple+"Would you like to play again?"+reset+cyan+" \n enter a for yes, b for no"+reset);
            String replay = userInput.nextLine();
            if (!replay.equalsIgnoreCase("a")) {
                playAgain = false;
            }

        }
        userInput.close();
        System.exit(0);
    }

    public static void playGame(int wrongCount, Scanner userInput, String word, HashSet guessedLetters){
        while (wrongCount < 7) {
            System.out.println(pink+"\nPress 'ENTER' to guess a letter, or press 1 to guess the entire word."+reset+purple+"\nRemember, you only have one chance to guess the entire word.\n"+reset);
            if (userInput.nextLine().equals("1")) {
                System.out.println("Enter your word:");
                String userGuess = userInput.nextLine();
                if (userGuess.equalsIgnoreCase(word)) {
                    System.out.println("Congrats! The word is: " + word);
                    break;
                } else {
                    System.out.println(brightRed+"You lose, the word was " +word+"\n"+reset);
                    break;

                }
            }
            if (!guessing(userInput, word, guessedLetters)) {
                wrongCount++;
                hangmanPrint(wrongCount);
                System.out.println(yellow+"\nYou have " + (7 - wrongCount) + " guesses left\n"+reset);
                if (wrongCount==7){
                    System.out.println(brightRed+"You lose, the word was " +word+"\n"+reset);
                    break;
                }
            }
            if (printWord(word, guessedLetters)) {
                System.out.println(brightGreen+"\nCongrats! You win! The word is: " + word+"\n"+reset);
                break;
            }

        }

    }

    public static int countLinesInFile(String nameOfFile) throws FileNotFoundException {
        File file = new File(nameOfFile);
        Scanner scanner = new Scanner(file);
        int lineCount = 0;
        while (scanner.hasNextLine()) {
            lineCount++;
            scanner.nextLine();
        }
        return lineCount;
    }


    public static String[] readFileIntoArray(String nameOfFile) throws FileNotFoundException {
        //reads the contents of a file and stores each line of the file as an element in a string array
        int linesInFile = countLinesInFile(nameOfFile);
        String[] array = new String[linesInFile];
        File file = new File(nameOfFile);
        Scanner scanner = new Scanner(file);
        int index = 0;
        while (scanner.hasNextLine()) {
            array[index++] = scanner.nextLine().strip();
        }
        return array;
    }

    public static String randomWord(String[] array) {
        //randomly selects a word from the array and returns the chosen word
        Random random = new Random();
        String word = array[random.nextInt(array.length)];
        return word;
    }


    public static boolean printWord(String word, Set<Character> guessedLetters) {
        // this method prints out letters for correct guesses and dashes for incorrect guesses
        int correctCount = 0;
        for (int i = 0; i < word.length(); i++) {
            char letter = word.charAt(i);
            if (guessedLetters.contains(Character.toLowerCase(letter))) {
                System.out.print(Character.toUpperCase(letter) + " ");
                correctCount++;
            } else {
                System.out.print("_ ");
            }
        }
        System.out.println();
        return (word.length() == correctCount);
    }


    public static boolean guessing(Scanner userInput, String word, Set<Character> guessedLetters) {
        // this method collects user input and determines whether the letter is present in the word
        String userGuess = userInput.nextLine();
        if (userGuess.length() != 1||!userGuess.matches("[a-zA-Z]")) {
            System.out.println(red+"Invalid guess! Please enter a single letter."+reset );
            return false;
        }
        char letter = Character.toLowerCase(userGuess.charAt(0));
        if (guessedLetters.contains(letter)) {
            System.out.println(red+"You have already guessed that letter. Try again."+reset);
            return false;
        }

        guessedLetters.add(letter);
        return word.toLowerCase().contains(String.valueOf(letter));
    }


    public static void hangmanPrint(int wrongCount) {
    //this method will print the hangman with the body parts corresponding to the amount of incorrect guesses
      if (wrongCount==1){
          System.out.println(cyan+" ( ͡° ͜ʖ ͡°)"+reset);
      }
      if (wrongCount==2){
          System.out.println(cyan+" ( ͡° ͜ʖ ͡°)"+"\n    |"+reset);
      }
      if (wrongCount==3){
          System.out.println(cyan+" ( ͡° ͜ʖ ͡°)"+"\n    |"+"\n    |"+reset);
      }
      if (wrongCount==4){
            System.out.println(cyan+" ( ͡° ͜ʖ ͡°)"+"\n    |"+"\n  --|"+reset);
      }
      if (wrongCount==5){
            System.out.println(cyan+" ( ͡° ͜ʖ ͡°)"+"\n    |"+"\n  --|--"+reset);
      }
        if (wrongCount==6){
            System.out.println(cyan+" ( ͡° ͜ʖ ͡°)"+"\n    |"+"\n  --|--"+"\n _("+reset);
        }
        if (wrongCount==7){
            System.out.println(cyan+" ( ͡° ͜ʖ ͡°)"+"\n    |"+"\n  --|--"+"\n  _(  )_"+reset);
        }
    }
    public static void startMenu() {
        //welcome menu and instructions
        Scanner scanner = new Scanner(System.in);
        System.out.println(pink+"Hello! Welcome to my hangman game! Please enter your name ( ͡° ͜ʖ ͡°)"+reset);
        String name = scanner.next();
        System.out.println(brightGreen+"\nWelcome, " + name + "!"+reset);
        System.out.println(yellow+"\nWhat would you like to do?"+reset+cyan+" \n a. start\n b. see instructions \n c. quit\n"+reset);
        char choice = scanner.next().charAt(0);
        if (choice == 'a') {
            System.out.println(blue+"Yay! Let's start"+reset);
        } else if (choice == 'b') {
            System.out.println("Instructions: \n This is a hangman game where a you will have 7 chances to guess a random word. \n You can either guess a single letter, or an entire word. \n Good luck!");
            System.out.println("Press a to start, or b to quit");
            Scanner instruction = new Scanner(System.in);
            char option = scanner.next().charAt(0);
            if (option == 'a') {
                System.out.println("Let's play!");
            }
            if (option == 'b') {
                System.exit(0);
            }

        } else if (choice == 'c') {
            System.exit(0);
        } else {
            System.out.println("Please try again.");
            startMenu();
        }
    }
}