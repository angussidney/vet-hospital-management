/*
 * Class Name and Description:
 *     Hospital.java is the interface for the vet hospital management
 *     software. It manages user interaction and data storage, while
 *     Doctor and Pet manage the getting/setting of data.
 *
 *     Any number of doctors and pets are supported.
 *     Data may be imported/exported via HospitalManagement.txt
 *
 *     Compile the program, run it, and follow the prompts.
 *     Press the 'enter' key once you have entered your input.
 */

import java.util.*;
import java.io.*;

public class Hospital {
    private Doctor[] doctors = new Doctor[3]; // A list of all the doctors in the system
                                              // Initialise as null rather than a blank object
                                              // as it is more semantically correct
    private int numDoctors = 0; // No doctors currently in system

    private Pet[] pets = new Pet[3]; // A list of all the pets in the system
                                     // Initialise as null rather than a blank object
                                     // as it is more semantically correct
    private int numPets = 0; // No pets currently in system

    private Scanner console = new Scanner(System.in);

    public static void main(String[] args) {
        Hospital hospital = new Hospital();
        hospital.run();
    }

    /**
     * Main program flow. Each command is delegated into a separate method.
     *
     * Users should follow the prompts on screen.
     */
    private void run() {
        System.out.println("Acme Vetinary Hospital\nManagement Suite v1.0.0\n");
        printHelp();

        String option;
        do {
            System.out.print("What would you like to do? 0 for help, -1 to exit:  ");
            option = console.nextLine();
            switch (option) {
                case "-1":
                    System.out.println("Thankyou for choosing to use Acme Vetinary Hospital Management Suite.");
                    System.exit(0);
                case "0":
                    printHelp();
                    break;
                case "1":
                    addDoctor();
                    break;
                case "2":
                    listDoctors();
                    break;
                case "3":
                    removeDoctor();
                    break;
                case "4":
                    addPet();
                    break;
                case "5":
                    analysePet();
                    break;
                case "6":
                    editPet();
                    break;
                case "7":
                    listPets();
                    break;
                case "8":
                    removePet();
                    break;
                case "9":
                    assignPet();
                    break;
                case "10":
                    listPetsByDoctor();
                    break;
                case "11":
                    readData();
                    break;
                case "12":
                    saveData();
                    break;
                default:
                    System.out.println("That doesn't look like a valid input.\n");
            }
        } while (true); // Program will exit on -1, no need to end loop using a condition
    }


    /* ********  Commands  ******** */


    /**
     * Prints a list of commands
     */
    private void printHelp() {
        System.out.println("Commands List");
        System.out.println("Type the number for the command you want to use, then press enter.");
        System.out.println("  (-1)  Exit");
        System.out.println("   (0)  Command Help\n");

        System.out.println("   (1)  Add doctor");
        System.out.println("   (2)  List doctors");
        System.out.println("   (3)  Delete doctor\n");

        System.out.println("   (4)  Add pet");
        System.out.println("   (5)  Analyse pet");
        System.out.println("   (6)  Edit pet");
        System.out.println("   (7)  List pets");
        System.out.println("   (8)  Delete pet\n");

        System.out.println("   (9)  Assign pet to doctor");
        System.out.println("  (10)  List pets assigned to doctor\n");

        System.out.println("  (11)  Read data from file");
        System.out.println("  (12)  Save data to file\n");
    }

    /**
     * Walks the user through the doctor creation process
     *
     * Will not allow a new doctor to be created another one has the same name
     * Will prompt the user to re-enter their input if invalid
     */
    private void addDoctor() {
        System.out.println("Please input each of the doctor's details and press enter.");

        System.out.print("Name:  ");
        String name = console.nextLine();
        while (isBlank(name)) {
            System.out.print("Invalid input. Please re-enter name (must not be empty):  ");
            name = console.nextLine();
        }
        if (doctorExists(name)) {
            System.out.println("There is already a doctor with that name.\n");
            return;
        }

        System.out.print("Specialisation (dog or cat):  ");
        String specialisation = console.nextLine();
        while (!(specialisation.equalsIgnoreCase("dog") ||
                 specialisation.equalsIgnoreCase("cat"))) {
            System.out.print("Invalid input. Please re-enter specialisation (dog or cat):  ");
            specialisation = console.nextLine();
        }

        Doctor newDoctor = new Doctor(name, specialisation);
        addDoctorToList(newDoctor);

        System.out.println("New doctor successfully added to system.\n");
    }

    /**
     * Prints a list of all the doctors in the system
     */
    private void listDoctors() {
        // Default response if there are no doctors
        if (numDoctors == 0) {
            System.out.println("No doctors currently in the system.\n");
            return;
        }

        System.out.print("Would you like the list to be sorted (y/n)?  ");
        Doctor[] viewingList = getConfirmation() ? sortDoctors(doctors, numDoctors) : doctors;

        System.out.println(numDoctors + pluralise(" doctor", numDoctors) + " currently in the system:");

        for (Doctor doctor : viewingList) {
            if (doctor != null) {
                doctor.printDetails();
            }
        }
    }

    /**
     * Walks the user through the doctor deletion process
     *
     * Will not allow a nonexistent doctor to be deleted
     */
    private void removeDoctor() {
        // More helpful response if there are no doctors
        if (numDoctors == 0) {
            System.out.println("No doctors currently in the system.\n");
            return;
        }

        System.out.print("Enter doctor name:  ");
        String name = console.nextLine();
        if (!doctorExists(name)) {
            System.out.println("There are no doctors named '" + name + "'\n");
        }
        else {
            for (int i = 0; i < doctors.length; i++) {
                if (doctors[i] != null && doctors[i].getName().equalsIgnoreCase(name)) {
                    unassignPetsByDoctor(doctors[i]);
                    doctors[i] = null;
                    numDoctors--;
                    System.out.println("Doctor successfully deleted from system.\n");
                }
            }
        }
    }

    /**
     * Walks the user through the pet creation process
     *
     * Will not allow a new pet to be created if another one has the same name
     * Will prompt the user to re-enter their input if invalid.
     */
    private void addPet() {
        System.out.println("Please input each of the pet's details and press enter.");

        System.out.print("Name:  ");
        String name = console.nextLine();
        while (isBlank(name)) {
            System.out.print("Invalid input. Please re-enter name (must not be empty):  ");
            name = console.nextLine();
        }
        if (petExists(name)) {
            System.out.println("There is already a pet with that name.\n");
            return;
        }

        System.out.print("Size (small, medium, large):  ");
        String size = console.nextLine();
        while (!(size.equalsIgnoreCase("small") ||
                 size.equalsIgnoreCase("medium") ||
                 size.equalsIgnoreCase("large"))) {
            System.out.print("Invalid input. Please re-enter size (small, medium, large):  ");
            size = console.nextLine();
        }

        System.out.print("Type (dog or cat):  ");
        String type = console.nextLine();
        while (!(type.equalsIgnoreCase("dog") ||
                 type.equalsIgnoreCase("cat"))) {
            System.out.print("Invalid input. Please re-enter type (dog or cat):  ");
            type = console.nextLine();
        }

        System.out.print("Age (years):  ");
        int age = console.nextInt();
        while (age < 0) { // A pet can't have a negative age (it can be +ve or 0)
            System.out.print("Invalid input. Please re-enter age (must be a positive no. of years):  ");
            age = console.nextInt();
        }
        console.nextLine(); // Get rid of leftover newline

        System.out.print("Weight (kg):  ");
        double weight = console.nextDouble();
        while (weight <= 0) { // A pet can only be a non-zero positive weight
            System.out.print("Invalid input. Please re-enter weight in kilograms (must not be zero or negative):  ");
            weight = console.nextDouble();
        }
        console.nextLine(); // Get rid of leftover newline

        Pet newPet = new Pet(name, size, type, age, weight, null);
        addPetToList(newPet);

        System.out.println("New pet successfully added to system.\n");
    }

    /**
     * Prints the specified pet's details and whether or not they are overweight
     */
    private void analysePet() {
        // More helpful response if there are no pets
        if (numPets == 0) {
            System.out.println("No pets currently in the system.\n");
            return;
        }

        System.out.print("Enter pet name:  ");
        String name = console.nextLine();
        if (!petExists(name)) {
            System.out.println("There are no pets named '" + name + "'\n");
            return;
        }

        Pet pet = getPetByName(name);
        pet.printDetails();
        System.out.println(pet.getName() + " is " + (pet.isOverweight() ? "overweight." : "not overweight.") + "\n");
    }

    /**
     * Walks the user through the pet editing process
     *
     * Users may leave certain attributes at their original value by leaving the input blank
     */
    private void editPet() {
        // Default response if there are no pets
        if (numPets == 0) {
            System.out.println("No pets currently in the system.\n");
            return;
        }

        System.out.print("Enter pet name:  ");
        String name = console.nextLine();
        if (!petExists(name)) {
            System.out.println("There are no pets named '" + name + "'\n");
            return;
        }
        Pet pet = getPetByName(name);

        System.out.println("For each of the inputs below, type in the new value and press enter.");
        System.out.println("Leave the field blank to use the previous value.");

        System.out.print("Size (previously " + pet.getSize() + "):  ");
        String size = console.nextLine();
        if (!isBlank(size)) {
            while (!(size.equalsIgnoreCase("small") ||
                    size.equalsIgnoreCase("medium") ||
                    size.equalsIgnoreCase("large"))) {
                System.out.print("Invalid input. Please re-enter size (small, medium, large):  ");
                size = console.nextLine();
            }
            pet.setSize(size);
        }

        System.out.print("Type (previously " + pet.getType() + "):  ");
        String type = console.nextLine();
        if (!isBlank(type)) {
            while (!(type.equalsIgnoreCase("dog") ||
                    type.equalsIgnoreCase("cat"))) {
                System.out.print("Invalid input. Please re-enter type (dog or cat):  ");
                type = console.nextLine();
            }
            pet.setType(type);
        }

        System.out.print("Age (previously " + pet.getAge() + "):  ");
        String ageInput = console.nextLine();
        if (!isBlank(ageInput)) {
            int age = Integer.parseInt(ageInput);
            while (age < 0) { // A pet can't have a negative age (it can be +ve or 0)
                System.out.print("Invalid input. Please re-enter age (must be a positive no. of years):  ");
                age = console.nextInt();
                console.nextLine(); // Remove leftover newline
            }
            pet.setAge(age);
        }

        System.out.print("Weight (previously " + pet.getWeight() + "):  ");
        String weightInput = console.nextLine();
        if (!isBlank(weightInput)) {
            double weight = Double.parseDouble(weightInput);
            while (weight <= 0) { // A pet can only be a non-zero positive weight
                System.out.print("Invalid input. Please re-enter weight in kilograms (must not be zero or negative):  ");
                weight = console.nextDouble();
                console.nextLine(); // Remove leftover newline
            }
            pet.setWeight(weight);
        }

        if (numDoctors == 0) {
            System.out.println("Doctor cannot be edited as no doctors exist.\n");
            return;
        }
        System.out.print("Doctor (previously " + (pet.hasDoctor() ? pet.getDoctor().getName() : "none") + "):  ");
        String doctorName = console.nextLine();
        if (!isBlank(doctorName)) {
            Doctor doctor = getDoctorByName(doctorName);
            while (doctor == null) { // Doctor does not exist
                System.out.print("Invalid input. Please re-enter the name of a doctor who exists:  ");
                doctor = getDoctorByName(console.nextLine());
            }

            if (getDoctorReassignmentConfirmation(pet) && getIncorrectSpecialisationConfirmation(pet, doctor)) {
                pet.setDoctor(doctor);
            }
        }

        System.out.println("\nPet properties have been updated to their new values.\n");
    }

    /**
     * Prints a list of all the pets in the system
     */
    private void listPets() {
        // Default response if there are no pets
        if (numPets == 0) {
            System.out.println("No pets currently in the system.\n");
            return;
        }

        System.out.print("Would you like the list to be sorted (y/n)?  ");
        Pet[] viewingList = getConfirmation() ? sortPets(pets, numPets) : pets;

        System.out.println(numPets + pluralise(" pet", numPets) + " currently in the system:");

        for (Pet pet : viewingList) {
            if (pet != null) {
                pet.printDetails();
            }
        }
    }

    /**
     * Walks the user through the pet deletion process
     *
     * Will not allow a nonexistent pet to be deleted
     */
    private void removePet() {
        // More helpful response if there are no pets
        if (numPets == 0) {
            System.out.println("No pets currently in the system.\n");
            return;
        }

        System.out.print("Enter pet name:  ");
        String name = console.nextLine();
        if (!petExists(name)) {
            System.out.println("There are no pets named '" + name + "'\n");
        }
        else {
            for (int i = 0; i < pets.length; i++) {
                if (pets[i] != null && pets[i].getName().equalsIgnoreCase(name)) {
                    pets[i] = null;
                    numPets--;
                    System.out.println("Pet successfully deleted from system.\n");
                }
            }
        }
    }

    /**
     * Walks the user through the process of assigning the pet to a new doctor
     *
     * Will not allow a nonexistent pet/doctor to be assigned
     * Will warn the user if the pet already has an assigned doctor, or the new one
     * has the wrong specialisation
     */
    private void assignPet() {
        // More helpful response if there are no pets
        if (numPets == 0) {
            System.out.println("No pets currently in the system.\n");
            return;
        }
        else if (numDoctors == 0) {
            System.out.println("There are no doctors for you to assign a pet to.\n");
            return;
        }

        System.out.print("Enter pet name:  ");
        String petName = console.nextLine();
        if (!petExists(petName)) {
            System.out.println("There are no pets named '" + petName + "'\n");
            return;
        }
        Pet pet = getPetByName(petName);

        if (!getDoctorReassignmentConfirmation(pet)) {
            return;
        }

        System.out.print("Enter doctor name:  ");
        String doctorName = console.nextLine();
        if (!doctorExists(doctorName)) {
            System.out.println("There are no doctors named '" + doctorName + "'\n");
            return;
        }
        Doctor doctor = getDoctorByName(doctorName);

        if (getIncorrectSpecialisationConfirmation(pet, doctor)) {
            pet.setDoctor(doctor);
            System.out.println("Successfully assigned pet to new doctor.\n");
        }
    }

    /**
     * Lists all pets which are assigned to a specific doctor
     */
    private void listPetsByDoctor() {
        if (numDoctors == 0) {
            System.out.println("No doctors currently in the system.\n");
            return;
        }
        else if (numPets == 0) {
            System.out.println("There are no pets currently in the system.\n");
            return;
        }

        System.out.print("Enter doctor name:  ");
        String name = console.nextLine();
        if (!doctorExists(name)) {
            System.out.println("There are no doctors named '" + name + "'\n");
            return;
        }

        boolean anythingPrinted = false;
        for (Pet pet : pets) {
            if (pet != null && pet.hasDoctor() && pet.getDoctor().getName().equalsIgnoreCase(name)) {
                pet.printDetails();
                anythingPrinted = true;
            }
        }
        // Default response if no pets are otherwise printed
        if (!anythingPrinted) {
            System.out.println(name + " currently has no pets assigned.\n");
        }
    }

    /**
     * Reads doctor/pet data from HospitalManagement.txt
     *
     * All file inputs are assumed to be valid
     * Will alert the user if the file is blank/missing
     * The 'Pets'/'Doctors' headings must be present
     * Will prompt user if a pet/doctor already exists in the system,
     * and will let them either merge or ignore the input.
     */
    private void readData() {
        if (isBlankFile("HospitalManagement.txt")) {
            System.out.println("There isn't anything in HospitalManagement.txt. Please add valid data before retrying.\n");
            return;
        }

        int doctorsRead, petsRead;
        try {
            // We have to read doctors before pets, otherwise when importing pets we may
            // run into a NullPointerException since some doctors may not be in the system yet
            doctorsRead = readDoctorsFromFile("HospitalManagement.txt");
            petsRead = readPetsFromFile("HospitalManagement.txt");
        }
        catch (FileNotFoundException e) {
            System.out.println("'HospitalManagement.txt' was not found. Please create the file and ensure that the input " +
                    "is valid before proceeding.\n");
            return;
        }

        if (doctorsRead == 0 && petsRead == 0) {
            System.out.println("No doctors or pets were imported from HospitalManagement.txt\n");
        }
        else {
            System.out.println("Data successfully imported into system.");
            System.out.println(petsRead + pluralise(" pet", petsRead) + " and " +
                               doctorsRead + pluralise(" doctor", doctorsRead) + " were imported.\n");
        }
    }

    /**
     * Writes the doctor/pet data currently in the system to HospitalManagement.txt
     *
     * Any data already in the file will be overwritten. Will prompt the user
     * before attempting to write any data to a non-empty file.
     */
    private void saveData() {
        if (numDoctors == 0 && numPets == 0) {
            System.out.println("There isn't any data which can be written to file.\n");
            return;
        }
        else if (!isBlankFile("HospitalManagement.txt")) {
            System.out.print("Any data already in HospitalManagement.txt will be overwritten. " +
                             "Would you like to proceed (y/n)?  ");
            if (!getConfirmation()) {
                System.out.println();
                return;
            }
        }

        try {
            writeDataToFile("HospitalManagement.txt");
        }
        catch (Exception e) {
            System.out.println("An error occurred while writing data to the file.\n");
            return;
        }

        System.out.println("Data successfully written to file.");
        System.out.println(numPets + pluralise(" pet", numPets) + " and " +
                           numDoctors + pluralise(" doctor", numDoctors) + " were exported.\n");
    }


    /* ******** Doctor/Pet management methods ******** */


    /**
     * Adds a new doctor to the list of all doctors
     * Will resize the doctors array if needed
     *
     * @param doctor  the doctor to be added
     * Returns nothing; the specified doctor will be added
     */
    private void addDoctorToList(Doctor doctor) {
        if (numDoctors < doctors.length) {
            for (int i = 0; i < doctors.length; i++) {
                if (doctors[i] == null) {
                    doctors[i] = doctor;
                    break;
                }
            }
        }
        else {
            doctors = resizeList(doctors, 1);
            doctors[doctors.length - 1] = doctor;
        }
        numDoctors++;
    }

    /**
     * Adds a new pet to the list of all pets
     * Will resize the pets array if needed
     *
     * @param pet  the pet to be added
     * Returns nothing; the specified pet will be added
     */
    private void addPetToList(Pet pet) {
        if (numPets < pets.length) {
            for (int i = 0; i < pets.length; i++) {
                if (pets[i] == null) {
                    pets[i] = pet;
                    break;
                }
            }
        }
        else {
            pets = resizeList(pets, 1);
            pets[pets.length - 1] = pet;
        }
        numPets++;
    }

    /**
     * Determines whether a doctor with the specified name exists
     * Returns true if they do exist, false if they don't
     *
     * @param name  doctor name to be searched for
     * @return whether a doctor with that name exists (boolean value)
     */
    private boolean doctorExists(String name) {
        for (Doctor doctor : doctors) {
            if (doctor != null && doctor.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines whether a pet with the specified name exists
     * Returns true if they do exist, false if they don't
     *
     * @param name  pet name to be searched for
     * @return whether a pet with that name exists (boolean value)
     */
    private boolean petExists(String name) {
        for (Pet pet : pets) {
            if (pet != null && pet.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the doctor with the specified name, if they exist
     *
     * @param name  doctor name to be searched for
     * @return doctor object, or 'none' if they don't exist
     */
    private Doctor getDoctorByName(String name) {
        for (Doctor doctor : doctors) {
            if (doctor != null && doctor.getName().equalsIgnoreCase(name)) {
                return doctor;
            }
        }

        return null; // Doctor does not exist
    }

    /**
     * Returns the pet with the specified name, if they exist
     *
     * @param name  pet name to be searched for
     * @return pet object, or 'none' if they don't exist
     */
    private Pet getPetByName(String name) {
        for (Pet pet : pets) {
            if (pet != null && pet.getName().equalsIgnoreCase(name)) {
                return pet;
            }
        }

        return null; // Pet does not exist
    }

    /**
     * Updates the pet's details to the specified values. Invalid inputs will be ignored.
     *
     * Will prompt user if pet already has assigned doctor/wrong specialisation
     * @param pet  the pet whose details should be updated
     * @param size  new size
     * @param type  new type
     * @param age  new age
     * @param weight  new weight
     * @param doctor  new doctor to be assigned
     */
    private void updatePetDetails(Pet pet, String size, String type, int age, double weight, Doctor doctor) {
        pet.setSize(size);
        pet.setType(type);
        pet.setAge(age);
        pet.setWeight(weight);
        if (doctor != null &&
            getDoctorReassignmentConfirmation(pet) &&
            getIncorrectSpecialisationConfirmation(pet, doctor)) {
            pet.setDoctor(doctor);
        }
    }

    /**
     * Unassigns the doctor from every pet which is assigned to them
     *
     * @param doctor  A doctor object
     * Returns nothing; pet 'doctor' attributes may be set to none
     */
    private void unassignPetsByDoctor(Doctor doctor) {
        for (Pet pet : pets) {
            // If we use == it compares references, which is appropriate for this usecase
            if (pet != null && pet.getDoctor() == doctor) {
                pet.setDoctor(null);
            }
        }
    }

    /**
     * Reads doctors from the input file and imports them into the program
     *
     * @param filename  name of file which data should be read from
     * @return  number of doctors imported.
     * @throws FileNotFoundException  if the specified file is missing
     */
    private int readDoctorsFromFile(String filename) throws FileNotFoundException {
        Scanner input = new Scanner(new File(filename));

        boolean doctorsFound = false;
        int numDoctorsFound = 0;
        while (input.hasNextLine()) {
            // If the loop doesn't exit, this variable will hold either the doctor name input, or the next line
            String nextLine = input.nextLine();
            if (nextLine.trim().equals("")) {
                // Don't crash on blank lines at the end of the file
                break;
            }
            else if (nextLine.equals("Doctors")) {
                doctorsFound = true;
            }
            else if (doctorsFound) {
                String specialisationInput = input.nextLine();

                String doctorName = nextLine.substring(5, nextLine.length());
                String specialisation = specialisationInput.substring(15, specialisationInput.length());
                if (doctorExists(doctorName)) {
                    System.out.print("Doctor '" + doctorName + "' is already in the system; would you like to " +
                            "update their details to match the information in HospitalManagement.txt (y/n)?  ");
                    if (!getConfirmation()) {
                        System.out.println("Information for '" + doctorName + "' was ignored. ");
                        continue;
                    }
                    else {
                        getDoctorByName(doctorName).setSpecialisation(specialisationInput);
                        numDoctorsFound++;
                    }
                }
                else {
                    Doctor doctor = new Doctor(doctorName, specialisation);
                    addDoctorToList(doctor);
                    numDoctorsFound++;
                }
            }
        }

        input.close();

        return numDoctorsFound;
    }

    /**
     * Reads pets from the input file and imports them into the program
     *
     * @param filename  name of file which data should be read from
     * @return  number of pets imported
     * @throws FileNotFoundException  if the specified file is missing
     */
    private int readPetsFromFile(String filename) throws FileNotFoundException {
        Scanner input = new Scanner(new File(filename));

        boolean petsFound = false;
        int numPetsFound = 0;
        while (input.hasNextLine()) {
            if (petsFound) {
                String firstInput = input.nextLine();
                if (firstInput.equals("Doctors")) {
                    break; // Reached end of pets; stop
                } // Else continue, using firstInput as the pet type

                // We need to read each line into a variable before slicing it, because calling
                // .length() on a .nextline() would read an extra line
                // Also so that we can check for existing pets before creation
                String sizeInput = input.nextLine();
                String nameInput = input.nextLine();
                String weightInput = input.nextLine();
                String ageInput = input.nextLine();
                String doctorInput = input.nextLine();

                String petName = nameInput.substring(5, nameInput.length());
                String doctorName = doctorInput.substring(7, doctorInput.length());
                if (petExists(petName)) {
                    System.out.print("The pet '" + petName + "' is already in the system; would you like to update" +
                                       "it's details to match the information in HospitalManagement.txt (y/n)?  ");
                    if (!getConfirmation()) {
                        System.out.println("Information for '" + petName + "' was ignored. ");
                        continue;
                    }
                    else {
                        updatePetDetails(
                                getPetByName(petName),
                                sizeInput.substring(5, sizeInput.length()),
                                firstInput.substring(5, firstInput.length()),
                                Integer.parseInt(ageInput.substring(4, ageInput.length())),
                                Double.parseDouble(weightInput.substring(7, weightInput.length())),
                                doctorName.equalsIgnoreCase("no doctor assigned") ? null : getDoctorByName(doctorName)
                        );
                        numPetsFound++;
                    }
                }
                else {
                    Pet pet = new Pet(
                            petName,
                            sizeInput.substring(5, sizeInput.length()),
                            firstInput.substring(5, firstInput.length()),
                            Integer.parseInt(ageInput.substring(4, ageInput.length())),
                            Double.parseDouble(weightInput.substring(7, weightInput.length())),
                            doctorName.equalsIgnoreCase("no doctor assigned") ? null : getDoctorByName(doctorName)
                    );
                    addPetToList(pet);
                    numPetsFound++;
                }
            }
            else if (input.nextLine().equals("Pets")) {
                petsFound = true;
            }
        }

        input.close();

        return numPetsFound;
    }

    /**
     * Writes all the program data (pets and doctors) to the specified file
     *
     * @param filename  name of file which data should be written to
     * @throws Exception  if a random error occurs while opening/writing to the file
     */
    private void writeDataToFile(String filename) throws Exception {
        PrintWriter output = new PrintWriter(new File(filename));

        output.println("Pets");
        for (Pet pet : pets) {
            if (pet != null) {
                output.println(pet.toString());
            }
        }

        output.println("Doctors");
        for (Doctor doctor : doctors) {
            if (doctor != null) {
                output.println(doctor.toString());
            }
        }

        output.close();
    }


    /* *************** Helper Methods **************** */


    /**
     * Adds an 's' to the given string if the value is not 1
     *
     * @param input  string to be (possibly) pluralised
     * @param value  value which determines pluralisation
     * @return  correctly pluralised string
     */
    private String pluralise(String input, int value) {
        if (value == 1) {
            return input;
        }
        else {
            return input + "s";
        }
    }

    /**
     * Checks whether the given string is blank (i.e. is empty or
     * contains only whitespace)
     *
     * @param str  String to be checked
     * @return  whether the string is blank (boolean value)
     */
    private boolean isBlank(String str) {
        return str.trim().length() < 1;
    }

    /**
     * Checks whether the specified is blank (i.e. is empty, doesn't exist,
     * or contains only whitespace)
     *
     * @param filename  name of file to be checked
     * @return  whether the file is blank
     */
    private boolean isBlankFile(String filename) {
        Scanner input;
        try {
            input = new Scanner(new File(filename));
        }
        catch (FileNotFoundException e) {
            // A nonexistent file is a blank file
            return true;
        }

        while (input.hasNextLine()) {
            // If there is a line that has any non-whitespace characters on it, the file isn't blank
            if (input.nextLine().trim().length() != 0) {
                return false;
            }
        }
        // If we make it through, the file must be blank
        return true;
    }

    /**
     * Resizes a doctor list by copying it to a new array with
     * the specified number of new items
     *
     * @param list  doctor list to be transformed
     * @param newItems  number of new items to be added. Should be positive.
     * @return  the new list of doctors
     */
    private Doctor[] resizeList(Doctor[] list, int newItems) {
        if (newItems < 0) {
            return null; // Do not attempt to shorten the array
        }

        Doctor[] newList = new Doctor[list.length + newItems];
        for (int i = 0; i < list.length; i++) {
            newList[i] = list[i];
        }
        return newList;
    }

    /**
     * Resizes a pet list by copying it to a new array with
     * the specified number of new items
     *
     * @param list  pet list to be transformed
     * @param newItems  number of new items to be added. Should be positive.
     * @return  the new list of pets
     */
    private Pet[] resizeList(Pet[] list, int newItems) {
        if (newItems < 0) {
            return null; // Do not attempt to shorten the array
        }

        Pet[] newList = new Pet[list.length + newItems];
        for (int i = 0; i < list.length; i++) {
            newList[i] = list[i];
        }
        return newList;
    }

    /**
     * Sorts a list of doctors alphabetically by name (A-Z).
     * Nulls entries will be ignored. Note: the output list may not have the
     * same physical size as the input, but it will have the same logical size.
     *
     * @param doctors  the list of doctors to be sorted. Original will not be modified.
     * @param logicalSize  the number of actual doctors in the input list
     * @return  the sorted list of doctors
     */
    private Doctor[] sortDoctors(Doctor[] doctors, int logicalSize) {
        String[] doctorNames = new String[logicalSize];
        for (int index = 0, name = 0; index < doctors.length; index++) {
            if (doctors[index] != null) {
                doctorNames[name] = doctors[index].getName().toLowerCase();
                name++;
            }
        }

        doctorNames = sortList(doctorNames);
        Doctor[] sortedDoctors = new Doctor[doctorNames.length];
        for (int i = 0; i < doctorNames.length; i++) {
            sortedDoctors[i] = getDoctorByName(doctorNames[i]);
        }

        return sortedDoctors;
    }

    /**
     * Sorts a list of pets alphabetically by name (A-Z).
     * Nulls entries will be ignored. Note: the output list may not have the
     * same physical size as the input, but it will have the same logical size.
     *
     * @param pets  the list of pets to be sorted. Original will not be modified.
     * @param logicalSize  the number of actual pets in the input list
     * @return  the sorted list of pets
     */
    private Pet[] sortPets(Pet[] pets, int logicalSize) {
        String[] petNames = new String[logicalSize];
        for (int index = 0, name = 0; index < pets.length; index++) {
            if (pets[index] != null) {
                petNames[name] = pets[index].getName().toLowerCase();
                name++;
            }
        }

        petNames = sortList(petNames);
        Pet[] sortedPets = new Pet[petNames.length];
        for (int i = 0; i < petNames.length; i++) {
            sortedPets[i] = getPetByName(petNames[i]);
        }

        return sortedPets;
    }

    /**
     * Sorts a list of strings in lexographic order using the
     * bubble sort algorithm
     *
     * @param list  list of strings to be sorted. This **will** be modified
     * @return  the sorted list of strings
     */
    private String[] sortList(String[] list) {
        String temp;
        for (int round = list.length - 1; round > 0; round--) {
            for (int i = 0; i < round; i++) {
                if (list[i].compareTo(list[i + 1]) > 0) {
                    temp = list[i + 1];
                    list[i + 1] = list[i];
                    list[i] = temp;
                }
            }
        }

        return list;
    }

    /**
     * Checks the console for a yes or no response
     * Retries if the input was invalid
     *
     * A prompt for the user should be printed before calling this method
     * @return whether the user typed yes (boolean value)
     */
    private boolean getConfirmation() {
        String response = console.nextLine();
        while (!(response.equalsIgnoreCase("y") ||
                 response.equalsIgnoreCase("yes") ||
                 response.equalsIgnoreCase("n") ||
                 response.equalsIgnoreCase("no"))) {
            System.out.print("Invalid input. Please provide a valid response (y/n):  ");
            response = console.nextLine();
        }

        return response.toLowerCase().startsWith("y"); // (y)es -> true, (n)o -> false
    }

    /**
     * Confirms whether the user wants to assign a pet to a new doctor if they
     * already have one assigned.
     * Will immediately return true if the pet has no assigned doctor.
     *
     * @param pet the pet in currently question
     * @return  whether the user wishes to proceed with doctor reassignment
     */
    private boolean getDoctorReassignmentConfirmation(Pet pet) {
        if (pet.hasDoctor()) {
            System.out.println("'" + pet.getName() + "' is currently assigned to Doctor " + pet.getDoctor().getName());
            System.out.print("Are you sure that you want to change doctors (y/n)?  ");
            if (!getConfirmation()) {
                System.out.println(); // Blank line
                return false;
            }
            else {
                return true;
            }
        }
        else {
            return true;
        }
    }

    /**
     * Confirms whether the user wants to assign a pet to a doctor which doesn't
     * have a matching specialisation
     * Will immediately return true if the pet/doctor have matching specialisation
     *
     * @param pet  pet for comparison
     * @param doctor  doctor for comparison
     * @return  whether the user wishes to proceed with the doctor reassignment
     */
    private boolean getIncorrectSpecialisationConfirmation(Pet pet, Doctor doctor) {
        if (!doctor.getSpecialisation().equalsIgnoreCase(pet.getType())) {
            System.out.println("Doctor " + doctor.getName() + " does not specialise in " + pet.getType() + "s.");
            System.out.print("Are you sure that you want to switch to this doctor (y/n)?  ");
            if (!getConfirmation()) {
                System.out.println();
                return false;
            }
            else {
                return true;
            }
        }
        else {
            return true;
        }
    }
}
