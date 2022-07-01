/**
 * The purpose of this program is to find, recognize, and print out
 * modern Intel Core CPUs' specifications. This class is the main driving class
 * for the program and contains additional menu-type logic and
 * user-prompts to complete the functionality of the program. Additionally,
 * a debugging method testVariables is placed in the class to compare
 * inputted/processed data with verified data from Intel ARK.
 * 
 * Assumptions:
 * - A successful write to console by the Writer class.
 * - A successful write to text file, if requested by the user.
 * - Instance variable values are stored in their debug arrays
 * if the testVariables method is called.
 * - User inputs a text file with an extension in the name.
 */
import java.io.File;
import java.util.Scanner;

public class Driver {

    static int[] debugIntArray;
    static boolean[] debugBoolArray;
    static String[] debugStringArray;

    /**
     * Main method to invoke the other classes and provide the program
     * menu-type logic and user-input.
     */
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        
        boolean runAgain;
        boolean badAnotherCPU;
        boolean badWriteToFile;
        boolean printToFile = false;
        String runAgainInput = "";
        String writeToFileInput = "";
        String writeToFileName = "";

        /**
         * Main do-while loop that will loop if the user wants to enter more
         * than one CPU. Creates a Writer class object and prompts the user
         * if they would like to enter another CPU or output the analysis
         * onto a file.
         */
        do {
            Writer writeObj = new Writer();
            System.out.println("Enter a modern Intel Core mainstream desktop CPU name, e.g. i7-8700K. Include a hyphen.");
            System.out.println("Please format your CPU name similar to \"i7-8700K\".");
            writeObj.writeToConsole();

            /**
             * Sets the debug arrays to the data within the Writer
             * class for debugging using testVariables.
             * Call of testVariables for debugging purposes, commented out.
             */
            debugIntArray = writeObj.getDebugIntArray();
            debugBoolArray = writeObj.getDebugBoolArray();
            debugStringArray = writeObj.getDebugStringArray();
            //testVariables(debugIntArray, debugBoolArray, debugStringArray);

            System.out.println();
            System.out.println("Would you like to output this CPU to a file? Y/N");

            /**
             * Secondary do-while loop that runs the logic for whether or not
             * the user wants to output the analysis onto a file. The loop
             * checks for whether the user enters a 'Y' to save the analysis
             * onto a file or 'N' if they do not (not case-sensitive) and does 
             * the respective action.
             */
            do {
                writeToFileInput = keyboard.next();
                writeToFileInput = writeToFileInput.toLowerCase();
                keyboard.nextLine();

                if (writeToFileInput.equals("y")) {
                    badWriteToFile = false;
                    printToFile = true;

                } else if (writeToFileInput.equals("n")) {
                    badWriteToFile = false;
                    printToFile = false;
                } else {
                    System.out.println("Try again. Please only use y/n.");
                    badWriteToFile = true;
                    printToFile = false;
                }
                
            } while(badWriteToFile == true);

            /**
             * If statement block to handle file output. It creates a File
             * class object that is named based on user input. A while
             * loop is placed inside to check if the user would like the
             * program to write to a file that already exists, and if 
             * the user does not want to, they can input a new file
             * name.
             */
            if (printToFile == true) {
                System.out.println("Please enter a text file name. Include the file extension.");
                writeToFileName = keyboard.nextLine();

                File outputFile = new File(writeToFileName);

                while(outputFile.exists()) {
                    System.out.println("\"" + writeToFileName + "\""+ " already exists. Are you sure you would like to name your file \"" + writeToFileName + "\"? Y/N");
                    System.out.println("If yes, the data will be appended to the end of the file. If no, you will be prompted to enter a new file name.");

                    writeToFileInput = keyboard.next();
                    writeToFileInput = writeToFileInput.toLowerCase();
                    if (writeToFileInput.equals("y")) {
                        break;
                    } else if (writeToFileInput.equals("n")) {
                        keyboard.nextLine();
                        System.out.println("Enter a new file name. Include the file extension");
                        writeToFileName = keyboard.nextLine();
                        outputFile = new File(writeToFileName);
                    } else {
                        System.out.println("Try again. Please only use y/n.");
                    }
                }

                writeObj.writeToText(outputFile);
                System.out.println("Printed to file \"" + writeToFileName + "\".");
            }

            /**
             * Tertiary do-while loop to check if the user inputs a valid
             * input, 'Y' if they want to enter another CPU, 'N' if they
             * do not.
             */
            System.out.println("Would you like to enter another CPU? Y/N");
            do {
                runAgainInput = keyboard.next();
                runAgainInput = runAgainInput.toLowerCase();

                if (runAgainInput.equals("y")) {
                    runAgain = true;
                    badAnotherCPU = false;
                } else if (runAgainInput.equals("n")) {
                    runAgain = false;
                    badAnotherCPU = false;
                } else {
                    System.out.println("Try again. Please only use y/n.");
                    runAgain = true;
                    badAnotherCPU = true;
                }
                
            } while (badAnotherCPU == true);
        } while (runAgain == true);
        keyboard.close();
    }

    /**
     * Variable tracing method for debugging. Uses verified data from Intel's
     * CPU specifications website, https://ark.intel.com/content/www/us/en/ark.html
     * to check program input and processing against these verified values.
     * The verified values are from the Intel Core i7-6700K CPU.
     * The method receives array parameters that house the processed values.
     * @param debugIntArray
     * @param debugBoolArray
     * @param debugStringArray
     */
    public static void testVariables(int[] debugIntArray, boolean[] debugBoolArray, String[] debugStringArray) {
        System.out.println();
        System.out.println("DEBUGGING MODE");
        System.out.println("Generation should be 6: " + debugIntArray[0]);
        System.out.println("Lithography should be 14nm: " + debugIntArray[1]);
        System.out.println("Core count should be 4: " + debugIntArray[2]);

        System.out.println("Unlocked should be true: " + debugBoolArray[0]);
        System.out.println("hasTurbo should be true: " + debugBoolArray[1]);
        System.out.println("hasIGPU should be true: " + debugBoolArray[2]);
        System.out.println("hasSMT should be true: " + debugBoolArray[3]);

        System.out.println("Socket should be LGA1151: " + debugStringArray[0]);
        System.out.println("Family should be i7: " + debugStringArray[1]);
        System.out.println("Tier should be Performance: " + debugStringArray[2]);
        System.out.println("Architecture should be Skylake: " + debugStringArray[3]);
    }
}