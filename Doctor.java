/*
 * Class Name and Description:
 *    Doctor.java is the class which represents a doctor
 *    at the vet hospital.
 *
 *    Ideally, this class should be used in conjunction with
 *    a user interface (e.g. Hospital.java). However, it can also
 *    be used independently by calling it from your own program.
 *
 *    Please note that bad inputs will simply be rejected if they
 *    are invalid. It is the responsibility of the primary class
 *    (e.g. Hospital) to ask for new inputs if invalid.
 */

public class Doctor {
    private String name;
    private String specialisation;

    /**
     * Default constructor
     *
     * No inputs/preconditions
     * All instance variables will be initialised as null
     */
    public Doctor() {} // Initialise as null rather than blank, as
                       // that is more semantically correct

    /**
     * Detailed constructor
     *
     * @param name  doctor name to be assigned
     * @param specialisation  specialisation to be assigned
     * All instance variables will be initialised to the specified values
     */
    public Doctor(String name, String specialisation) {
        setName(name);                         // Use setters rather than direct assignment
        setSpecialisation(specialisation);     // so that inputs are checked
    }

    /**
     * Prints all of the doctor's properties (name and specialisation)
     *
     * No inputs/preconditions
     * Returns nothing; Doctor details will be printed to the screen
     */
    public void printDetails() {
        System.out.println("          Name: " + getName());
        System.out.println("Specialisation: " + getSpecialisation());
        System.out.println();
    }

    /**
     * Gets the doctor's name
     *
     * No inputs/preconditions
     * @return doctor name (exactly as it was inputted into the system)
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the doctor's name to the value specified
     *
     * @param name  doctor name to be assigned - any format will be accepted - must not be empty
     * Returns nothing; 'name' attribute will be set to input
     */
    public void setName(String name) {
        if (name.trim().length() > 0) {
            this.name = name;
        }
    }

    /**
     * Gets the pet's type
     *
     * No inputs/preconditions
     * @return pet type (dog or cat)
     */
    public String getSpecialisation() {
        return specialisation;
    }

    /**
     * Sets the doctor's specialisation to the value specified
     *
     * @param specialisation  doctor specialisation to be assigned - should be either 'dog' or 'cat'
     * Returns nothing; 'specialisation' attribute will be set to input
     */
    public void setSpecialisation(String specialisation) {
        if (specialisation.equalsIgnoreCase("dog") ||
            specialisation.equalsIgnoreCase("cat")) {
            this.specialisation = specialisation;
        }
    }


    /* ******** Helper Methods ******** */

    /**
     * Returns a string representation of the doctor
     * This format is developed to be compatiable with
     * the specification 'HospitalManagement.txt'
     *
     * No inputs/preconditions
     * @return  a string representing the object
     */
    public String toString() {
        return "name " + getName() + "\n" +
               "specialisation " + getSpecialisation();
    }


    /**
     * Determines whether this doctor is equal to the other doctor
     * Returns 'true' if all attributes match (ignoring case)
     *
     * @param otherDoctor  other doctor object to be compared
     * @return whether this doctor is equal to otherDoctor (boolean value)
     */
    public boolean equals(Doctor otherDoctor) {
        return (getName().equalsIgnoreCase(otherDoctor.getName()) &&
                getSpecialisation().equalsIgnoreCase(otherDoctor.getSpecialisation()));
    }
}
