/**
 * This class is designed to read in an Intel CPU name from the user with a 
 * format similar to this: i7-6700K (CPU family, -, number, suffix). Once the
 * CPU name is inputted, the class attempts to verify if the inputted CPU name
 * is valid for analysis by the program or not.
 * 
 * Assumptions:
 * - The user inputs a valid and real CPU name. Sample CPU names are given 
 * in the file "ExampleCPUs.txt". Nonreal CPU names might not be caught
 * by the program.
 * - The user formats the CPU name according to the example given.
 * - Invalid CPUs are laptop/mobile CPUs, non-Intel CPUs, non-
 * Intel Core CPUs, CPUs older than Intel Core i3/i5/i7-2xxx CPUs, and Intel
 * Core-X/Extreme Edition CPUs.
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CPUReader {
    /**
     * Instance variables for the class. 
     */
    private String CPUName;
    private String CPUNumber;
    private boolean tryAgain;
    
    Scanner keyboard = new Scanner(System.in);

    public CPUReader() {
        CPUName = "";
        CPUNumber = "";
        tryAgain = false;
    }

    public CPUReader(String CPUName) {
        this.CPUName = CPUName;
        CPUNumber = "";
        tryAgain = false;
    }

    /**
     * This method reads in a CPU name from the user and validates the input
     * by comparing it against known characteristics not exhibited by modern
     * desktop Intel Core CPUs, such as whether or not "i#" is in the name
     * or if the name has an incompatible suffix.
     */
    public void readCPUName() {

        do {
            verifyInput();
        } while(tryAgain == true);

    }

    /**
     * This method gets the CPU's number out of the CPU's inputted name.
     * The method takes a substring starting at index 3 and removes all non-
     * numeric characters from the name to get the number. Sets the 
     * instance variable CPUNumber to the result.
     */
    public void readCPUNumber() {
        String tempCPUNum = "";
        tempCPUNum = CPUName.substring(3);
        tempCPUNum = tempCPUNum.replaceAll("[^0-9]", "");
        CPUNumber = tempCPUNum;
    }

    /**
     * Getter method to return the CPU's name.
     * @return
     */
    public String getCPUName() {
        return CPUName;
    }

    /**
     * Setter method to set the CPU's name to a new name.
     * @param newCPUName
     */
    public void setCPUName(String newCPUName) {
        CPUName = newCPUName;
    }

    /**
     * Getter method to return the CPU's number.
     * @return
     */
    public String getCPUNumber() {
        return CPUNumber;
    }

    /**
     * Setter method to set the CPU's number to a new number.
     * @param newCPUNum
     */
    public void setCPUNumber(String newCPUNum) {
        CPUNumber = newCPUNum;
    }

    /**
     * Helper method that takes in a file name string and, depending on the
     * file name, will attempt to open the file, compare the CPU's name to
     * the CPU names listed in the file, and if there is a match, the
     * instance variable tryAgain is set to true for use in the method
     * readCPUName. An output string is also set according to the
     * file name argument.
     * @param fileName
     */
    private void readList(String fileName) {
        String currBadCPU;
        String outputString = "";
        Scanner readList = null;

        try {
            readList = new Scanner(new FileInputStream(fileName));
        } catch (FileNotFoundException e) {
            System.out.println("File \"" + fileName + "\" not found.");
            System.out.println("Terminating program...");
            System.exit(0);
        }
        
        if (fileName.equals("extremeEditionCPUList.txt")) {
            outputString = "Extreme edition CPUs are not supported. Please enter a new CPU.";
        }

        if (fileName.equals("oldCPUList.txt")) {
            outputString = "CPU too old! Please enter a newer CPU.";
        }

        while (readList.hasNextLine()) {
            currBadCPU = readList.nextLine();
            if (CPUName.equals(currBadCPU)) {
                System.out.println(outputString);
                tryAgain = true;
                break;
            }
        }

        readList.close();
    }

    /**
     * Helper method to consolidate all of the CPU verification logic into
     * one method. The method makes two calls to readList using two different
     * text files, filters laptop/mobile CPUs using the last two characters of
     * CPUName, and catches unrecognized CPUs.
     */
    private void verifyInput() {
        String tempName = "";
        tryAgain = false;
        tempName = keyboard.nextLine();
        tempName = tempName.toLowerCase();
        CPUName = tempName;

        readList("extremeEditionCPUList.txt");
        readList("oldCPUList.txt");

        if (CPUName.startsWith("i3") || CPUName.startsWith("i5") 
        || CPUName.startsWith("i7") || CPUName.startsWith("i9")) {

            if (CPUName.endsWith("h") || CPUName.endsWith("u") || CPUName.endsWith("y") 
            || CPUName.endsWith("hk") || CPUName.endsWith("hq") || CPUName.endsWith("m")
            || CPUName.endsWith("g1") || CPUName.endsWith("g2") || CPUName.endsWith("g3")
            || CPUName.endsWith("g4") || CPUName.endsWith("g5") || CPUName.endsWith("g6")
            || CPUName.endsWith("g7")) {
                System.out.println("Laptop CPUs are not supported. Please enter a desktop CPU.");
                tryAgain = true;
            }

            if ((CPUName.length() < 7) || (CPUName.length() > 9)) {
                System.out.println("Unrecognized CPU. Please reenter your CPU.");
            }
            
        } else {
            System.out.println("Unrecognized CPU. Please reenter your CPU.");
            tryAgain = true;
        }
    }
}