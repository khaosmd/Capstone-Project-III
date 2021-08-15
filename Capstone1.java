//Classes that are used in the program are imported

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import static java.lang.Integer.parseInt;

//Create the Capstone1 class and the main method

public class Capstone1 {

    public static void main(String[] args) throws ParseException {

        //Try block to test for input mismatch exceptions.
        //A project object is declared and the array list that will contain all the projects is created.
        //A scanner is constructed, which is used to read the user's input based on a message printed to screen.
        //this input is assigned to a variable and parsed from a string to an integer.

        try {
            Project project;
            ArrayList<Project> projectList = new ArrayList<>();

            Scanner sc = new Scanner(System.in);
            System.out.println("Enter \"1\" if you'd like to load projects from the file. Type \"2\" if you'd like to capture" +
                    " the details of a project manually");

            String choiceString = sc.nextLine();
            int choice = Integer.parseInt(choiceString);

            //Try and catch block to catch file not found exceptions.
            //The readFile method is run with the projectList parameter if user inputs 1, and the captureProject
            // method is run if 2 is inputted and the project is added to the projectList array.
            //If the file which the readFile method reads from isn't found, this exception is caught and a message is
            //printed to the screen.

            try {
                if (choice == 1) {
                    readFile(projectList);
                } else if (choice == 2) {
                    project = captureProject();

                    projectList.add(project);
                }
            }
            catch (FileNotFoundException e) {
                    System.out.println("The file wasn't found.");
                    e.printStackTrace();
                }

            //A message is printed to the screen asking for user input and this input is parsed to an integer.
            //If the user enters 1, new project is created by calling the captureProject method. This project is then
            //added to the projectList array.
            //The updateDetails method is then called to update details.

            System.out.println("Would you like to add a project to the project list? Type \"1\" for yes or \"2\" for no");
            String addObject = sc.nextLine();
            int addProject = Integer.parseInt(addObject);

            if (addProject == 1) {
                project = captureProject();
                projectList.add(project);
            }
            updateDetails(projectList);

            //A message is printed to the screen asking for user input and this input is parsed to an integer.
            //If the user enters 1, the viewIncomplete method is run.

            System.out.println("Would you like to see a list of projects that still need to be completed? Type \"1\" for yes " +
                    "or \"2\" for no");

            String incompleteString = sc.nextLine();
            int incomplete = Integer.parseInt(incompleteString);

            if (incomplete == 1) {
                viewIncomplete(projectList);
            }

            //A message is printed to the screen asking for user input and this input is parsed to an integer.
            //If the user enters 1, the overdue method is run.

            System.out.println("Would you like to see a list of projects that are past the due date? Type \"1\" for yes " +
                    "or \"2\" for no");

            String pastDueString = sc.nextLine();
            int pastDue = Integer.parseInt(pastDueString);

            if (pastDue == 1) {
                overdue(projectList);
            }

            //An array list of type invoice is constructed.
            //A for loop is used to iterate from the project in index position 0 of the projectList array to the last
            //index position, and an if statement is used to test whether the fee variable doesn't equal the paid variable
            //(accessed via the getters).
            //If this is true, an invoice is created by calling the invoice constructor and setting the parameters of the
            //invoice by getting them from the customer corresponding to the project with the index value that makes the
            //if statement true.
            //This invoice is then added to the invoice arraylist. The status of its corresponding project is set to
            //finalised, and a completion date (current date) is added.

            ArrayList<Invoice> invoiceList = new ArrayList<>();

            for (int i = 0; i < projectList.size(); i++) {
                if (projectList.get(i).getFee() != projectList.get(i).getPaid()) {

                    Invoice invoice = new Invoice();
                    Person customer = projectList.get(i).person[2];
                    invoice.setCustomer(customer.getName());
                    invoice.setTelephone(customer.getTel());
                    invoice.setEmailAdd(customer.getEmail());
                    invoice.setPhysAdd(customer.getAddress());
                    invoice.setPaidAmount(projectList.get(i).getPaid());
                    invoice.setTotal(projectList.get(i).getFee());

                    invoiceList.add(invoice);

                    projectList.get(i).setStatus("finalised");
                    projectList.get(i).setCompletionDate(new Date());
                }
            }

            //A try and catch block that prints an error message to screen if the invoice or project files can't be
            //created.
            //The createInvoiceFile method is run, and thereafter the createProjectFile.

            try {
                createInvoiceFile(invoiceList);
                createProjectFile(projectList);
            }
            catch (IOException e) {
                System.out.println("The file could not be created");
                e.printStackTrace();
            }
        }

        //Catch block that prints an error to screen if the user enters a number when it should have been a letter, and
        //vice versa, for all the above user inputs in the corresponding try block.

        catch (InputMismatchException e) {
            System.out.println("You typed a number when you should have typed a letter or vice versa.");
            e.printStackTrace();
        }
    }

    //readFile method that takes the projectList array list as a parameter.
    //The projects file object, scanner and project object are declared. The projects file and scanner are constructed.
    //A while statement is used to run the rest of the method code whilst the text file has more lines.
    //The scanner is used to assign the contents of the text file to variables.

    public static void readFile(ArrayList<Project> projectList) throws FileNotFoundException, ParseException {

        File projects;
        Scanner scanner;
        Project project;

        projects = new File("Projects.txt");

        scanner = new Scanner(projects);

        while (scanner.hasNext()) {
            int number = parseInt(scanner.nextLine());

            String projectName = scanner.nextLine();

            String building = scanner.nextLine();

            String projectAddress = scanner.nextLine();

            String erf = scanner.nextLine();

            float fee = Float.parseFloat(scanner.nextLine());

            float paid = Float.parseFloat(scanner.nextLine());

            Date deadline = new SimpleDateFormat("dd/MM/yyyy").parse(scanner.nextLine());

            String status = scanner.nextLine();

            Date completionDate = new SimpleDateFormat("dd/MM/yyyy").parse(scanner.nextLine());

            //An array of type Person (object) of size 3 is created and variables are declared.

            Person[] person = new Person[3];

            String role;
            String name;
            String tel;
            String email;
            String address;

            //A for loop to iterate from person[0] to person[2] is used to assign the contents of the text file to
            //variables using the scanner.

            for (int x = 0; x < person.length; x++) {

                role = scanner.nextLine();

                name = scanner.nextLine();

                tel = scanner.nextLine();

                email = scanner.nextLine();

                address = scanner.nextLine();

                //New person object p is constructed.

                Person p = new Person(role, name, tel, email, address);
                person[x] = p;
            }

            //A new project is constructed using the previous variables, and this project is added to the projectList
            //array list.

            project = new Project(number, projectName, building, projectAddress, erf, fee, paid, deadline);

            project.person = person;

            projectList.add(project);
        }
    }

    //The updateDetails method is created, which takes the projectList array list parameter.
    //A scanner is constructed and a string is declared.

    public static void updateDetails(ArrayList<Project> projectList) {

        Scanner input = new Scanner(System.in);
        String update;

        //Try and catch blocks to catch Input Mismatch Exceptions and print a message to screen if there is one.
        //The scanner is used to read the user's input based on a message printed to screen, and this value is stored
        //in the string variable "update".

        try {
            System.out.print("Would you like to update a project on the list? Type \"y\" for yes or \"n\" for no: ");
            update = input.nextLine();

            //If statement that tests if the user entered "y", if he/she did, a message is printed to screen and the
            //resulting user input is parsed and stored as an integer.
            //A for loop is used to iterate from the project at index value 0 of the project array list till the last
            //index position.
            //An if statement is used to test whether the user's input matches the project number of the project at
            //position i in the array list. If it does, the captureProject method is run. This project then is stored
            //at the same position in the array list as the old project, thereby overwriting it and updating it.

            if (update.equals("y")) {
                System.out.println("Enter the number of the project that you'd like to update");
                String projectNum = input.nextLine();
                int projectNumber = parseInt(projectNum);

                    for (int i = 0; i < projectList.size(); i++) {
                        if (projectList.get(i).getNumber() == projectNumber) {
                            Project project = captureProject();
                            projectList.set((projectNumber - 1), project);
                        }
                    }
            }

            //Asks the user for input and stores it in the "update" string variable.
            //An if statement is used to test whether the user enter "y" - if he/she did, another statement is printed
            //to screen and the resulting user input is parsed to an integer and stored in a variable.
            //Another statement is printed to screen, and the user input stored as a variable. This string variable
            //is then parsed to a Date object in a try block, and set as the deadline for the project with variable
            //"projectNumber" minus 1 (due to the fact that the index positions start at 0).
            //The catch block is run if the date is entered in the incorrect format, and a message is printed to the
            //screen.

            System.out.print("Would you like to update the project deadline? Type \"y\" for yes or \"n\" for no: ");
            update = input.nextLine();

            if (update.equals("y")) {

                System.out.println("Enter the number of the project that you'd like to update");
                String projectNum = input.nextLine();
                int projectNumber = Integer.parseInt(projectNum);

                System.out.println("Enter the deadline for the project (dd/MM/yyyy): ");
                String deadlineString = input.nextLine();

                try {
                    Date deadline = new SimpleDateFormat("dd/MM/yyyy").parse(deadlineString);
                    projectList.get(projectNumber - 1).setDeadline(deadline);
                }
                catch (ParseException e) {
                    System.out.println("The date was entered in the incorrect format.");
                    e.printStackTrace();
                }
            }

            //Asks the user for input and stores it in the "update" string variable.
            //An if statement is used to test whether the user enter "y" - if he/she did, another statement is printed
            //to screen and the resulting user input is parsed to an integer and stored in a variable.
            //Another statement is printed to the screen, and the resulting input from the user is parsed from a string
            //to a float variable.
            //The fee float variable is set as the "paid" variable for the project with in index position (projectNumber - 1)
            //in the project array list (due to the fact that the index positions start at 0).

            System.out.print("Would you like to update the total amount of the fee paid to date? Type \"y\" for yes or \"n\" for no: ");
            update = input.nextLine();

            if (update.equals("y")) {

                System.out.println("Enter the number of the project that you'd like to update");
                String projectNum = input.nextLine();
                int projectNumber = Integer.parseInt(projectNum);

                System.out.print("Enter the new fee paid to date: ");

                String feeString = input.nextLine();
                float fee = Float.parseFloat(feeString);
                projectList.get(projectNumber - 1).setPaid(fee);
            }

            //Asks the user for input and stores it in the "update" string variable.
            //An if statement is used to test whether the user enter "y" - if he/she did, another statement is printed
            //to screen and the resulting user input is parsed to an integer and stored in the projectNumber variable.
            //Various statements are printed to the screen, and the resulting user inputs are stored in variables, which
            //are then used to update the corresponding variables of the relevant project in the arraylist.
            //The catch block is run when an Input Mismatch Exception is encountered and a number is typed instead of a
            //letter and vice versa.

            System.out.print("Would you like to update the contractor's contact details? Type \"y\" for yes or \"n\" for no: ");
            update = input.nextLine();

            if (update.equals("y")) {
                Scanner input3 = new Scanner(System.in);

                System.out.println("Enter the number of the project that you'd like to update");
                String projectNum = input.nextLine();
                int projectNumber = Integer.parseInt(projectNum);

                System.out.print("Enter the name of the contractor: ");
                String name = input3.nextLine();
                projectList.get(projectNumber - 1).person[1].setName(name);

                System.out.print("Enter the telephone number of the contractor: ");
                String tel = input3.nextLine();
                projectList.get(projectNumber - 1).person[1].setTel(tel);

                System.out.print("Enter the email address of the contractor: ");
                String email = input3.nextLine();
                projectList.get(projectNumber - 1).person[1].setEmail(email);

                System.out.print("Enter the physical address of the contractor: ");
                String address = input3.nextLine();
                projectList.get(projectNumber - 1).person[1].setAddress(address);
            }
        } catch (InputMismatchException e) {
            System.out.println("You typed a number when you should have typed a letter or vice versa.");
            e.printStackTrace();
        }
    }

    //The createProjectFile method with a project array list parameter.
    //Try block where a new FileWriter object is created to write to a text file called "ProjectFile".
    //A for loop is used to run the toString methods of all the projects in the projectList array list, and write to
    //the file, with each project starting on a new line in the file.
    //If an IO error is encountered, the catch block is run and a statement is printed to screen.

    public static void createProjectFile(ArrayList<Project> projectList) {

        try {
            FileWriter myWriter = new FileWriter("ProjectFile.txt");

            for (Project project : projectList) {
                myWriter.write(project.toString() + System.lineSeparator());
            }
            myWriter.close();
        }
        catch (IOException e) {
            System.out.println("An IO error occurred.");
            e.printStackTrace();
        }
    }

    //The createInvoiceFile method with an invoice array list parameter.
    //Try block where a new FileWriter object is created to write to a text file called "completedProject".
    //A for loop is used to run the toString methods of all the invoices in the invoiceList array list, and write to
    //the file, with each invoice starting on a new line in the file.
    //If an IO error is encountered, the catch block is run and a statement is printed to screen.

    public static void createInvoiceFile(ArrayList<Invoice> invoiceList) throws IOException {

        try {
            FileWriter myWriter = new FileWriter("completedProject.txt");

            for (Invoice invoice : invoiceList) {
                myWriter.write(invoice.toString() + System.lineSeparator());
            }
            myWriter.close();
        }
        catch (IOException e) {
        System.out.println("An IO error occurred.");
        e.printStackTrace();
        }
    }

    //captureProject method with no parameters.
    //A scanner is constructed to capture user input.
    //The project object is declared and its variables are declared and initiated.

    public static Project captureProject() {

        Scanner input = new Scanner(System.in);

        Project project;

        int number = 0;
        String projectName = null;
        String building = null;
        String projectAddress = null;
        String erf = null;
        float fee = 0;
        float paid = 0;
        Date deadline = null;

        //Try block where the scanner is used to capture user input after a message is printed to screen.

        try {
            System.out.println("Enter the project number: ");
            number = input.nextInt();

            input.nextLine();

            System.out.println("Enter the project name. If there is no name, enter \"none\": ");
            projectName = input.nextLine();

            System.out.println("Enter the type of building being designed: ");
            building = input.nextLine();

            System.out.println("Enter the physical address of the project: ");
            projectAddress = input.nextLine();

            System.out.println("Enter the ERF number of the project: ");
            erf = input.nextLine();

            System.out.println("Enter the total fee being charged for the project: ");

            //Convert the value captured as a string object to a float variable.

            String feeString = input.nextLine();
            fee = Float.parseFloat(feeString);

            System.out.println("Enter the total amount paid to date for the project: ");

            //Convert the value captured as a string object to a float variable.

            String paidString = input.nextLine();
            paid = Float.parseFloat(paidString);

            System.out.println("Enter the deadline for the project (dd/MM/yyyy): ");
            String deadlineString = input.nextLine();

            //Try block where the deadlineString string is parsed to a SimpleDateFormat object. If this fails, the catch
            //block is run and an error message is printed to screen.

            try {
                deadline = new SimpleDateFormat("dd/MM/yyyy").parse(deadlineString);
            } catch (ParseException e) {
                System.out.println("The date was entered in the incorrect format.");
                e.printStackTrace();
            }

            //Catch block that prints an error to screen if the user enters a number when it should have been a letter,
            //and vice versa, for all the above user inputs in the corresponding try block.

        } catch (InputMismatchException e) {
            System.out.println("You typed a number when you should have typed a letter or vice versa.");
            e.printStackTrace();
        }

        //An array of type Person (object) of size 3 is created, and its variables/parameters are declared.

        Person[] person = new Person[3];

        String role;
        String name;
        String tel;
        String email;
        String address;

        //A for loop inside a try block to iterate from person[0] to person[2] is used to ask the user for input for each person
        //using a scanner that is constructed. The user's inputs are stored in the previously declared variables for
        //each person.
        //Each person object is then constructed using the variables as parameters.

        try {
            for (int x = 0; x < person.length; x++) {

                Scanner input1 = new Scanner(System.in);

                System.out.print("Enter the role of the person in the following order: 1. architect, 2. contractor, 3. customer): ");
                role = input1.nextLine();

                System.out.print("Enter the name of the person (e.g. John Smith): ");
                name = input1.nextLine();

                System.out.print("Enter the telephone number of the person: ");
                tel = input1.nextLine();

                System.out.print("Enter the email address of the person: ");
                email = input1.nextLine();

                System.out.print("Enter the physical address of the person: ");
                address = input1.nextLine();

                Person p = new Person(role, name, tel, email, address);
                person[x] = p;
            }

        }

        //Catch block which is run if there is an input mismatch exception, and prints a message to screen.

        catch (InputMismatchException e) {
            System.out.println("You typed a number when you should have typed a letter or vice versa.");
            e.printStackTrace();
        }

        //If statement used to determine if the project has a name, and if it doesn't (project name is "none"),
        //it uses the type of building and the customer's surname to name the project. The split string method is used
        //to split the first name from the last name, and the building type and surname are used to name the project.

            assert projectName != null;
            if (projectName.equals("none"))

            {
                String customerName = person[2].getName();
                String[] splitName = customerName.split(" ");
                projectName = building + " " + splitName[1];
            }


        //Constructor to create a new project using the variables above as parameters. The method returns a project
        //object.

        project = new Project(number, projectName, building, projectAddress, erf, fee, paid, deadline);

        project.person = person;

        return project;
    }

    //ViewIncomplete method that takes the projectList array list as a parameter.
    //A for loop iterates through the projects in the projectList array list, and an if statement is run to test whether
    //the project fee equals the amount paid. If it doesn't (means that the project isn't finished) that specific
    //project is printed to screen.

    public static void viewIncomplete(ArrayList<Project> projectList) {

        for (int i = 0; i < projectList.size(); i++) {
            if (projectList.get(i).getFee() != projectList.get(i).getPaid()) {
                System.out.println(projectList.get(i) + "\n");
            }
        }

    }

    //overdue method that takes the projectList array list as a parameter.
    //A date object is constructed with the current date.
    //A for loop iterates through to projects in the projectList array list, and an if statement is run to test whether
    //the deadline date has passed. If it has, that specific project is printed to screen.

    public static void overdue(ArrayList<Project> projectList) {

        Date currentDate = new Date();

        for (int i = 0; i < projectList.size(); i++) {
            if (projectList.get(i).getDeadline().before(currentDate)) {
                System.out.println(projectList.get(i) + "\n");

            }
        }
    }
}


