import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.Scanner;

/**
 * Manages a car dealership by handling employees and cars.
 * Provides options to display employee lists, car lists, handle car sales,
 * add new cars, and exit the program.
 */
public class CarDealership {

    /**
     * Sorts a list of comparable objects in descending order.
     *
     * @param list The list to be sorted.
     * @param <T> The type of elements in the list, must implement Comparable.
     */
    public static <T extends Comparable> void sortArr(ArrayList<T> list) {
        Collections.sort(list);
        Collections.reverse(list);
    }

    /**
     * Enum representing the available options in the car dealership system.
     */
    public enum Options {
        employeesList(1),
        vehiclesForSales(2),
        carSale(3),
        vehicleAdding(4),
        ending(5);
        private final int i;
        Options(int i){
            this.i = i;
        }
        public int getI(){
            return i;
        }

        /**
         * Gets the option based on the provided integer choice.
         *
         * @param choice The integer value representing an option.
         * @return The corresponding Options enum, or null if no match is found.
         */
        public static Options printOption(int choice){
            for (Options o : Options.values()){
                if(o.getI() == choice){
                    return o;
                }
            }
            return null;
        }
    }

    /**
     * The main method to run the car dealership application.
     * Handles user input for managing employees and cars.
     *
     * @throws IOException If an IO error occurs.
     */
    public static void main(String[] args) throws IOException {
        Path filePath = Paths.get("carDealership/src/Sold.txt");

        try {
             Files.createFile(filePath);
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }

        Path fileCar = Paths.get("carDealership/src/CarDealership.txt");
        ArrayList<Car> cars = new ArrayList<>();
        String[] fields;

        try {
            List<String> lines = Files.readAllLines(fileCar);
            for (String line : lines){
                fields = line.split("\\s+");
                int carNumber = Integer.parseInt(fields[0]);
                int manufactureDate = Integer.parseInt(fields[1]);
                String manufactureName = fields[2];
                int mileage = Integer.parseInt(fields[3]);
                int price = Integer.parseInt(fields[4]);
                Car car = new Car(carNumber, manufactureDate, manufactureName, mileage, price);
                cars.add(car);
            }
        }
        catch (CarException | NumberFormatException e){
            System.out.println(e.getMessage());
        }

        Path fileEmployee = Paths.get("carDealership/src/Employee.txt");
        ArrayList<Employee> employees = new ArrayList<>();
        String[] field;
        try {
            List<String> lines = Files.readAllLines(fileEmployee);
            for (String line : lines){
                field = line.split("\\s+");
                String employeeName = field[0];
                int id = Integer.parseInt(field[1]);
                int numOfSales = Integer.parseInt(field[2]);
                Employee employee = new Employee(employeeName, id, numOfSales);
                employees.add(employee);
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        Scanner input = new Scanner(System.in);
        boolean flag = false;
        Options choice;
        while (!flag){
            System.out.println("Please choose your option 1-5: ");
            System.out.println("1- display the list of employees in the dealership");
            System.out.println("2- display the list of vehicles in the dealership");
            System.out.println("3- car sale");
            System.out.println("4- adding a vehicle to the dealership");
            System.out.println("5- ending");
            System.out.printf("Choose your option: ");

            Employee employee = null;
            Car car1 = null;
            int choiceUser = input.nextInt();
            choice = Options.printOption(choiceUser);
            switch (choice) {
                case employeesList:
                    sortArr(employees);
                    for (Employee e : employees) {
                        System.out.println(e.toString());
                    }

                    break;
                case vehiclesForSales:
                    System.out.println("List of unsold cars: \n");
                    for (Car c : cars) {
                        System.out.println(c.toString());
                    }

                    break;
                case carSale:
                    for (Employee e : employees) {
                        System.out.println(e);
                    }

                    Scanner scanner1 = new Scanner(System.in);
                    boolean isValid = false;
                    int id;
                    while (!isValid) {
                        System.out.println("Select an employee by ID number: ");
                        id = scanner1.nextInt();

                        try {
                            if (id < 100000000 || id > 999999999) {
                                throw new IllegalArgumentException("Invalid input. Not 9 digit number: " + id);
                            }
                            for (Employee e : employees) {
                                if (e.getId() == id) {
                                    employee = e;
                                    isValid = true;
                                }
                            }
                            if (employee == null) {
                                throw new IllegalArgumentException("Invalid input, the employee does not exist. " + id);
                            }


                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    }


                    System.out.println("List of unsold cars: \n");
                    for (Car c : cars) {
                        System.out.println(c.toString());
                    }

                    Scanner scanner2 = new Scanner(System.in);
                    boolean isValidCarNum = false;
                    int carNum;
                    while (!isValidCarNum) {
                        System.out.println("Select car by car number: ");
                        carNum = scanner2.nextInt();

                        try {
                            if (carNum < 100000 || carNum > 999999) {
                                throw new CarException("You need to enter 6 digits ");
                            }
                            for (Car c : cars) {
                                if (c.getCarNumber() == carNum) {
                                    car1 = c;
                                    isValidCarNum = true;
                                }
                            }
                            if (car1 == null) {
                                throw new CarException("Invalid car number you must choose 6 digit number from List of unsold cars. ");
                            }
                            employee.carSale(car1, filePath);
                            if (cars.contains(car1)) {
                                cars.remove(car1);
                                System.out.println("The car was successfully sold.");
                            }
                            break;

                        } catch (CarException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;
                case vehicleAdding:
                    Scanner scanner3 = new Scanner(System.in);
                    int carNum2, manuDate, mile, price;
                    String manuName;
                    Car carToAdd = null;





                     try {
                         System.out.println("Enter car number (6 digit): ");
                         carNum2 = scanner3.nextInt();
                         scanner3.nextLine();

                         System.out.println("Enter Manufacturing Year (between 2017 and current year): ");
                         manuDate = scanner3.nextInt();
                         scanner3.nextLine();

                         System.out.println("Enter Mileage: ");
                         mile = scanner3.nextInt();
                         scanner3.nextLine();

                         System.out.println("Enter Price: ");
                         price = scanner3.nextInt();
                         scanner3.nextLine();

                         System.out.println("Enter Manufacturer: ");
                         manuName = scanner3.nextLine();

                         carToAdd = new Car(carNum2, manuDate, manuName, mile, price);
                         if (carToAdd != null) {
                             System.out.println("Car added successfully!");
                             String carDetails = carToAdd.getCarNumber() + " " + carToAdd.getManufactureDate() + " " + carToAdd.getManufactureName() + " " +
                                     carToAdd.getMileage() + " " + carToAdd.getPrice();
                             Files.writeString(fileCar, "\n" + carDetails, StandardOpenOption.APPEND);
                            }

                        } catch (CarException | IOException e) {
                            System.out.println(e.getMessage());
                            System.out.println("Please enter correct details car.");
                        } catch (Exception e) {
                            System.out.println("Invalid input.");
                            scanner3.nextLine();
                        }
                     break;



                case ending:

                    System.out.println("The cars are still at the dealership: ");
                    for (Car car5 : cars) {
                        System.out.println(car5.toString());
                    }
                    System.out.println("Exiting the dealership...");
                    flag = true;
                    break;
            }
        }
    }
}

