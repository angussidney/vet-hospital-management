/*
 * Class Name and Description:
 *    Pet.java is the class which represents a pet at the vet
 *    hospital.
 *
 *    Ideally, this class should be used in conjunction with
 *    a user interface (e.g. Hospital.java). However, it can also
 *    be used in your own program as long as you also include
 *    the 'Doctor' class (as pets can be assigned doctors).
 *
 *    Please note that bad inputs will simply be rejected if they
 *    are invalid. It is the responsibility of the primary class
 *    (e.g. Hospital) to ask for new inputs if invalid.
 */

public class Pet {
    private String name, size, type;
    private int age;
    private double weight;
    private Doctor doctor; // Define as class rather than simply a name so that doctor
                           // name changes will not dissassociate pets from their assigned
                           // doctors. Everything will point to a central memory location.

    /**
     * Default constructor
     *
     * No inputs/preconditions
     * All instance variables will be initialised as null
     */
    public Pet() {} // Initialise as null rather than blank, as
                    // that is more semantically correct

    /**
     * Detailed constructor
     *
     * @param name  pet name to be assigned
     * @param size  pet size to be assigned
     * @param type  pet type to be assigned
     * @param age  age of the pet
     * @param weight  weight of the pet
     * @param doctor  doctor to be assigned as the pet's doctor
     * All instance variables will be initialised to the specified values
     */
    public Pet(String name, String size, String type, int age, double weight, Doctor doctor) {
        setName(name);     // Use setters rather than direct assignment
        setSize(size);     // so that inputs are checked
        setType(type);
        setAge(age);
        setWeight(weight);
        setDoctor(doctor);
    }

    /**
     * Prints all of the pet's properties (name, size, type, age, weight, doctor)
     *
     * No inputs/preconditions, returns nothing
     */
    public void printDetails() {
        System.out.println("  Name: " + getName());
        System.out.println("  Size: " + getSize());
        System.out.println("  Type: " + getType());
        System.out.println("   Age: " + getAge());
        System.out.println("Weight: " + getWeight() + "kg");
        if (doctor != null) {
            System.out.println("Doctor: " + doctor.getName());
        }
        else {
            System.out.println("Doctor: none assigned");
        }
        System.out.println();
    }

    /**
     * Gets the pet's type
     *
     * No inputs/preconditions
     * @return pet type (dog or cat)
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the pet's type to the value specified
     *
     * @param type  pet type to be assigned - should be either 'dog' or 'cat'
     * Returns nothing; 'type' attribute will be set to input
     */
    public void setType(String type) {
        if (type.equalsIgnoreCase("dog") ||
            type.equalsIgnoreCase("cat")) {
            this.type = type;
        }
    }

    /**
     * Gets the pet's size
     *
     * No inputs/preconditions
     * @return pet size (small, medium or large)
     */
    public String getSize() {
        return size;
    }

    /**
     * Sets the pet's size to the value specified
     *
     * @param size  size value to be assigned - should be either 'small', 'medium' or 'large'
     * Returns nothing; 'size' attribute will be set to input
     */
    public void setSize(String size) {
        if (size.equalsIgnoreCase("small") ||
            size.equalsIgnoreCase("medium") ||
            size.equalsIgnoreCase("large")) {
            this.size = size;
        }
    }

    /**
     * Gets the pet's name
     *
     * No inputs/preconditions
     * @return pet name (exactly as it was inputted into the system)
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the pet's name to the value specified
     *
     * @param name  pet name to be assigned - any format will be accepted - must not be empty
     * Returns nothing; 'name' attribute will be set to input
     */
    public void setName(String name) {
        if (name.trim().length() > 0) {
            this.name = name;
        }
    }

    /**
     * Gets the pet's weight in kilograms
     *
     * No inputs/preconditions
     * @return pet weight (will be a positive double)
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Sets the pet's weight to the value specified
     *
     * @param weight  weight value to be assigned - should be positive
     * Returns nothing; 'weight' attribute will be set to input
     */
    public void setWeight(double weight) {
        if (weight > 0) {
            this.weight = weight;
        }
    }

    /**
     * Gets the pet's age in years
     *
     * No inputs/preconditions
     * @return pet age (will be a positive integer)
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the pet's age to the value specified
     *
     * @param age  age value to be assigned - should be positive
     * Returns nothing; 'age' attribute will be set to input
     */
    public void setAge(int age) {
        if (age >= 0) {
            this.age = age;
        }
    }

    /**
     * Gets the pet's doctor object
     *
     * No inputs/preconditions
     * @return doctor object, or 'none' if no doctor is assigned
     */
    public Doctor getDoctor() {
        return doctor;
    }

    /**
     * Assigns the specified doctor as the pet's doctor
     *
     * @param doctor  doctor object to be assigned, or null to reset
     * Returns nothing; 'doctor' attribute will be set to input
     */
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }


    /* ******** Helper Methods ******** */


    /**
     * Determines whether the pet has a doctor assigned.
     * Returns 'true' if a doctor is assigned, 'false' otherwise.
     *
     * No inputs/preconditions
     * @return whether a doctor has been assigned (boolean value)
     */
    public boolean hasDoctor() {
        return getDoctor() != null;
    }

    /**
     * Determines whether the pet is overweight, based on size and weight
     *
     * No inputs/preconditions
     * @return whether this pet is overweight (boolean value)
     */
    public boolean isOverweight() {
        if (getType().equals("cat")) {
            return ((getSize().equals("small") && getWeight() > 4) ||
                    (getSize().equals("medium") && getWeight() > 6) ||
                    (getSize().equals("large") && getWeight() > 8));
        }
        else { // Dog
            return ((getSize().equals("small") && getWeight() > 6) ||
                    (getSize().equals("medium") && getWeight() > 9) ||
                    (getSize().equals("large") && getWeight() > 12));
        }
    }

    /**
     * Returns a string representation of the pet
     * This format is developed to be compatiable with
     * the specification 'HospitalManagement.txt'
     *
     * No inputs/preconditions
     * @return  a string representing the object
     */
    public String toString() {
        return "type " + getType() + "\n" +
               "size " + getSize() + "\n" +
               "name " + getName() + "\n" +
               "weight " + getWeight() + "\n" +
               "age " + getAge() + "\n" +
               "doctor " + (hasDoctor() ? getDoctor().getName() : "no doctor assigned");
    }

    /**
     * Determines whether this pet is equal to the other pet
     * Returns 'true' if all attributes match (ignoring case)
     *
     * @param otherPet  other pet object to be compared
     * @return whether this pet is equal to otherPet (boolean value)
     */
    public boolean equals(Pet otherPet) {
        return (getType().equalsIgnoreCase(otherPet.getType()) &&
                getSize().equalsIgnoreCase(otherPet.getSize()) &&
                getName().equalsIgnoreCase(otherPet.getName()) &&
                getWeight() == otherPet.getWeight() &&
                getAge() == otherPet.getAge() &&
                getDoctor() == otherPet.getDoctor());
    }
}
