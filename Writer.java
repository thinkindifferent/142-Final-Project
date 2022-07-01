/**
 * This class is designed to write processed data from the CPUAnalyzer class
 * onto either the console, a text file defined by the user, or both. The
 * class is heavily built around the two methods writeToConsole and 
 * writeToText, which write to the console and text file, respectively.
 * A copy of the data from the CPUAnalyzer class is made in the Writer class
 * for later use in the Driver class method testVariables.
 * 
 * Assumptions:
 * - A successful processing done by the CPUAnalyzer class.
 * - The method writeToConsole would always be invoked before the method
 * writeToText.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class Writer {

    CPUAnalyzer processedObj = new CPUAnalyzer();
    PrintWriter textStream = null;

    /**
     * Instance variables holding the data transferred over from the
     * CPUAnalyzer class.
     */
    private int generation;
    private int lithography;
    private int coreCount;
    
    private boolean unlocked;
    private boolean hasTurbo;
    private boolean hasIGPU;
    private boolean hasSMT;

    private String socket;
    private String family;
    private String tier;
    private String architecture;

    /**
     * Arrays to store the values of the aforementioned transferred data
     * to later be used in the Driver class method testVariables.
     */
    private int[] debugIntArray = new int[3];
    private boolean[] debugBoolArray = new boolean[4];
    private String[] debugStringArray = new String[4];

    /**
     * Method to write the processed data from the CPUAnalyzer class to
     * console.
     */
    public void writeToConsole() {
        runAnalysis();
        consoleOutput();
        fillDebugArrays();
    }

    /**
     * Method to write the processed data from the CPUAnalyzer class to
     * a user-defined file. Uses a try-catch block to handle exceptions
     * while in the Driver class, a while loop handles file names that
     * already exist.
     * @param output
     */
    public void writeToText(File output) {
        
        try {
            textStream = new PrintWriter(new FileOutputStream(output, true));
        } catch(FileNotFoundException e) {
            System.out.println("Error opening file. Shutting down...");
            System.exit(0);
        } catch(Exception e) {
            System.out.println("An unexpected error occurred. Shutting down...");
            System.exit(0);
        }

        textOutput();

    }

    /**
     * Getter method to return the array debugIntArray for debugging purposes.
     * @return
     */
    public int[] getDebugIntArray() {
        return debugIntArray;
    }

    /**
     * Getter method to return the array debugBoolArray for debugging purposes.
     * @return
     */
    public boolean[] getDebugBoolArray() {
        return debugBoolArray;
    }

    /**
     * Getter method to return the array debugStringArray for debugging purposes.
     * @return
     */
    public String[] getDebugStringArray() {
        return debugStringArray;
    }

    /**
     * Helper method to invoke all of the find-x methods in the CPUAnalyzer
     * class and set the instance variables of the Writer class to their
     * respective values.
     */
    private void runAnalysis() {
        processedObj.importCPUReader();
        processedObj.findGeneration();
        processedObj.findFamily();
        processedObj.findLithography();
        processedObj.findCoreCount();
        processedObj.findUnlocked();
        processedObj.findHasTurbo();
        processedObj.findHasIGPU();
        processedObj.findHasSMT();
        processedObj.findSocket();
        processedObj.findTier();
        processedObj.findArchitecture();

        generation = processedObj.getGeneration();
        lithography = processedObj.getLithography();
        coreCount = processedObj.getCoreCount();
        
        unlocked = processedObj.getUnlocked();
        hasTurbo = processedObj.getHasTurbo();
        hasIGPU = processedObj.getHasIGPU();
        hasSMT = processedObj.getHasSMT();

        socket = processedObj.getSocket();
        family = processedObj.getFamily();
        tier = processedObj.getTier();
        architecture = processedObj.getArchitecture();

    }

    /**
     * Helper method to write the processed data onto the console.
     */
    private void consoleOutput() {
        System.out.println("Processing input...");
        System.out.println("Printing results for Intel Core " + processedObj.getCPUName() + ":");
        System.out.println();

        System.out.println("Generation: " + generation);
        System.out.println("Lithography: " + lithography + "nm");
        System.out.println("Core count: " + coreCount);

        System.out.println("Unlocked: " + unlocked);
        System.out.println("Has Turbo Boost: " + hasTurbo);
        System.out.println("Has iGPU: " + hasIGPU);
        System.out.println("Has hyperthreading: " + hasSMT);

        System.out.println("Socket: " + socket);
        System.out.println("Family: Core " + family);
        System.out.println("Tier: " + tier);
        System.out.println("Architecture: " + architecture);
    }

    /**
     * Helper method to write the processed data onto a text file.
     */
    private void textOutput() {
        textStream.println("Processing input...");
        textStream.println("Printing results for Intel Core " + processedObj.getCPUName() + ":");
        textStream.println();

        textStream.println("Generation: " + generation);
        textStream.println("Lithography: " + lithography + "nm");
        textStream.println("Core count: " + coreCount);

        textStream.println("Unlocked: " + unlocked);
        textStream.println("Has Turbo Boost: " + hasTurbo);
        textStream.println("Has iGPU: " + hasIGPU);
        textStream.println("Has hyperthreading: " + hasSMT);

        textStream.println("Socket: " + socket);
        textStream.println("Family: Core " + family);
        textStream.println("Tier: " + tier);
        textStream.println("Architecture: " + architecture);

        textStream.println();
        textStream.close();
    }

    /**
     * Helper method to fill the debug-x arrays with their respective values
     * for later use in the method testVariables located in Driver.
     */
    private void fillDebugArrays() {
        debugIntArray[0] = processedObj.getGeneration();
        debugIntArray[1] = processedObj.getLithography();
        debugIntArray[2] = processedObj.getCoreCount();

        debugBoolArray[0] = processedObj.getUnlocked();
        debugBoolArray[1] = processedObj.getHasTurbo();
        debugBoolArray[2] = processedObj.getHasIGPU();
        debugBoolArray[3] = processedObj.getHasSMT();

        debugStringArray[0] = processedObj.getSocket();
        debugStringArray[1] = processedObj.getFamily();
        debugStringArray[2] = processedObj.getTier();
        debugStringArray[3] = processedObj.getArchitecture();
    }

}