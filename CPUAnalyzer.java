/**
 * This class is designed to read user-inputted data from the CPUReader class
 * and analyze it based on certain characteristics exhibited by different
 * Intel CPUs. For each characteristic, there is a respective instance
 * variable and getter/setter/"find" method. "Find" methods, in this class,
 * are methods that analyze the CPU name for a certain characteristic, e.g.
 * findGeneration tries to find the CPU's generation.
 * 
 * Assumptions:
 * - A valid and workable CPU is inputted by the user and processed
 * successfully by the CPUReader class.
 * - The instance variables of the class are for later use for the
 * rest of the program, so no console output is done.
 */

public class CPUAnalyzer {
    /**
     * Several instance variables grouped based on data type. Each instance
     * variable is later used in the class to store the inputted CPU's
     * characteristics.
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

    private String CPUName;
    private String CPUNumber;
    
    CPUReader analyzeObj = new CPUReader();

    /**
     * This method imports the CPU name and CPU number from the CPUReader
     * class and stores it into instance variables for later use.
     */
    public void importCPUReader() {
        analyzeObj.readCPUName();
        analyzeObj.readCPUNumber();

        CPUName = analyzeObj.getCPUName();
        CPUNumber = analyzeObj.getCPUNumber();
    }

    /**
     * This method removes the last three digits in the CPU number (the CPU's
     * name but with its family name and nonnumeric characters removed).
     * Sets the instance variable generation to the number left behind.
     */
    public void findGeneration() {
        String tempString = "";
        tempString = CPUNumber.substring(0, (CPUNumber.length() - 3));
        generation = Integer.parseInt(tempString);
    }

    /**
     * This method uses nested if-else statements to determine the lithography
     * of the CPU depending on its generation. For example, all 6th generation
     * CPU's or newer have a lithography of 14nm. Sets the lithography
     * instance variable to the CPU's respective lithography.
     */
    public void findLithography() {
        if (generation == 2) {
            lithography = 32;
        } else if ((generation == 3) || (generation == 4)) {
            lithography = 22;
        } else {
            lithography = 14;
        }
    }

    /**
     * This method uses nested if-else statements to determine the core count of
     * the CPU. The if-else logic uses the CPU's family and generation to be able
     * to determine the core count of the CPU. Sets the instance variable coreCount
     * to the CPU's respective number.
     */
    public void findCoreCount() {
        family = family.toLowerCase();
        if (family.equals("i3")) {
            if (generation <= 7) {
                coreCount = 2;
            } else {
                coreCount = 4;
            }
        }

        if (family.equals("i5")) {
            if (generation <= 7) {
                coreCount = 4;
            } else {
                coreCount = 6;
            }
        }

        if (family.equals("i7")) {
            if (generation <= 7) {
                coreCount = 4;
            } else if (generation == 8) {
                coreCount = 6;
            } else {
                coreCount = 8;
            }
        }

        if (family.equals("i9")) {
            if ((generation == 9) || (generation == 11)) {
                coreCount = 8;
            } else {
                coreCount = 10;
            }
        }
    }

    /**
     * This method finds a singular or multi-character suffix of the CPU
     * and tests if it is equal to the strings "k", "kf", or "ks", as these
     * suffixes denote an unlocked CPU. Sets the unlocked instance variable
     * to the resulting boolean.
     */
    public void findUnlocked() {
        String suffix = CPUName.substring(3);
        suffix = suffix.replaceAll("[^a-zA-Z]","");
        suffix = suffix.toLowerCase();

        if ((suffix.equals("k")) || (suffix.equals("kf")) || (suffix.equals("ks"))) {
            unlocked = true;
        } else {
            unlocked = false;
        }
    }

    /**
     * This method uses the CPU's family and generation in nested if-else
     * statements to determine if the CPU has turbo boost. Sets the hasTurbo
     * instance variable to the resulting boolean.
     */
    public void findHasTurbo() {

        if (family.equals("i3")) {
            if (generation >= 9) {
                hasTurbo = true;
            }
        }

        if ((family.equals("i5")) || (family.equals("i7")) ||(family.equals("i9"))) {
            hasTurbo = true;
        }
    }

    /**
     * This method uses a singular character suffix of the CPU to determine
     * if the CPU does or does not have integrated graphics. Any CPU with a
     * suffix of "f" will not have an iGPU. Sets the hasIGPU instance
     * variable to the resulting boolean.
     */
    public void findHasIGPU() {
        if (CPUName.endsWith("f")) {
            hasIGPU = false;
        } else {
            hasIGPU = true;
        }
    }

    /**
     * This method uses the CPU's generation and family to determine if it has
     * hyperthreading (SMT). Sets the hasSMT instance variable to the resulting
     * boolean.
     */
    public void findHasSMT() {

        if (family.equals("i7") || (family.equals("i9"))) {
            hasSMT = true;
        }

        if (family.equals("i5")) {
            if (generation >= 8) {
                hasSMT = true;
            }
        }

        if (family.equals("i3")) {
            if ((generation != 8) || (generation != 9)) {
                hasSMT = true;
            }
        }
    }

    /**
     * This method uses the generation of the CPU to determine its socket
     * using if and OR statements. Sets the socket instance variable
     * to the string of the CPU's respective socket.
     */
    public void findSocket() {
        if ((generation == 2) || (generation == 3)) {
            socket = "LGA1155";
        }

        if ((generation == 4) || (generation == 5)) {
            socket = "LGA1150";
        }

        if ((generation >= 6) && (generation <= 9)) {
            socket = "LGA1151";
        }

        if (generation >= 10) {
            socket = "LGA1200";
        }
    }

    /**
     * This method takes a substring of the first two characters in the CPU's
     * name. Sets the family instance variable to the resulting string.
     */
    public void findFamily() {
        String tempString = CPUName.substring(0, 2);
        family = tempString.toLowerCase();
    }

    /**
     * This method uses the family the CPU is in to determine its performance
     * tier using a switch statement. Sets the tier instance variable to
     * the resulting string, or "N/A" if an error occurs.
     */
    public void findTier() {

        switch(family) {
            case "i3":
                tier = "Entry level";
                break;
            case "i5":
                tier = "Mainstream";
                break;
            case "i7":
                tier = "Performance";
                break;
            case "i9":
                tier = "Enthusiast";
                break;
            default:
                tier = "N/A";
                break;
        }
    }

    /**
     * This method finds the microarchitecture of the CPU using its generation
     * and a switch statement. Sets the architecture instance variable to
     * the resulting string, or "N/A" if an error occurs.
     */
    public void findArchitecture() {

        switch(generation) {
            case 2:
                architecture = "Sandy Bridge";
                break;
            case 3:
                architecture = "Ivy Bridge";
                break;
            case 4:
                architecture = "Haswell";
                break;
            case 5:
                architecture = "Broadwell";
                break;
            case 6:
                architecture = "Skylake";
                break;
            case 7:
                architecture = "Kaby Lake";
                break;
            case 8:
                architecture = "Coffee Lake";
                break;
            case 9:
                architecture = "Coffee Lake Refresh";
                break;
            case 10:
                architecture = "Comet Lake";
                break;
            case 11:
                architecture = "Rocket Lake";
                break;
            default:
                architecture = "N/A";
                break;
        }
    }

    /**
     * Getter method to return the CPU's generation. 
     * @return
     */
    public int getGeneration() {
        return generation;
    }

    /**
     * Getter method to return the CPU's lithography.
     * @return
     */
    public int getLithography() {
        return lithography;
    }

    /**
     * Getter method to return the CPU's core count.
     * @return
     */
    public int getCoreCount() {
        return coreCount;
    }

    /**
     * Getter method to return whether or not the CPU is unlocked.
     * @return
     */
    public boolean getUnlocked() {
        return unlocked;
    }

    /**
     * Getter method to return whether or not the CPU has turbo boost.
     * @return
     */
    public boolean getHasTurbo() {
        return hasTurbo;
    }

    /**
     * Getter method to return whether or not the CPU has integrated graphics.
     * @return
     */
    public boolean getHasIGPU() {
        return hasIGPU;
    }

    /**
     * Getter method to return whether or not the CPU has hyperthreading.
     * @return
     */
    public boolean getHasSMT() {
        return hasSMT;
    }

    /**
     * Getter method to return the CPU's socket.
     * @return
     */
    public String getSocket() {
        return socket;
    }

    /**
     * Getter method to return the CPU's family.
     * @return
     */
    public String getFamily() {
        return family;
    }

    /**
     * Getter method to return the CPU's performance tier.
     * @return
     */
    public String getTier() {
        return tier;
    }

    /**
     * Getter method to return the CPU's microarchitecture.
     * @return
     */
    public String getArchitecture() {
        return architecture;
    }

    /**
     * Getter method to return the CPU's number.
     * @return
     */
    public String getCPUNumber() {
        return CPUNumber;
    }

    /**
     * Getter method to return the CPU's full name.
     * @return
     */
    public String getCPUName() {
        return CPUName;
    }
}