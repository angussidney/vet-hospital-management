# Vetinary Hospital Management Software

Clone the respository, compile the code and run the Hospital class:

    git clone https://github.com/angussidney/vet-hospital-management
    cd vet-hospital-management
    javac *.java
    java Hospital
    
## Importing/exporting data

Pet/doctor data can also be imported and exported via the `HospitalManagement.txt` file.
The format of this file is as follows:

    Pets
    type <cat|dog>
    size <small|medium|large>
    name <string>
    weight <double>
    age <integer>
    doctor <doctor name|no doctor assigned>
    <more pets can be added here>
    Doctors
    name <string>
    specialisation <cat|dog>
    <more doctors can be added here>

Note: if the file contains zero pets or zero doctors, the `Pets` and `Doctors` headings must be preserved.

A sample import file is included.