import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.InputMismatchException;

/**
 * Represents an employee with details such as name, ID, and number of sales.
 * Implements Comparable to compare employees based on their number of sales.
 */
public class Employee implements Comparable<Employee>{
    private String employeeName;
    private int id;
    private int numOfSales;

    /**
     * Constructs a new Employee object with the specified details.
     *
     * @param employeeName The name of the employee (must contain only letters).
     * @param id The employee's ID (must be a 9-digit number).
     * @param numOfSales The number of sales made by the employee (must be non-negative).
     * @throws InputMismatchException If the name contains non-letter characters,
     *                                 the ID is not a 9-digit number, or
     *                                 the number of sales is negative.
     */
    public Employee(String employeeName, int id, int numOfSales) throws Exception{
        for (int i = 0; i < employeeName.length(); i++){
            if ((employeeName.charAt(i) >= 'a' || employeeName.charAt(i) <= 'z') || (employeeName.charAt(i) >= 'A' || employeeName.charAt(i) <= 'Z')) {
                this.employeeName = employeeName;
            } else{ throw new InputMismatchException("Name should contain only letters"); }

            if (id >= 100000000 || id <= 999999999){
                this.id = id;
            }else{ throw new InputMismatchException("Id should contain only 9 digit numbers"); }

            if (numOfSales >= 0){
                this.numOfSales = numOfSales;
            }else{ throw new CarException("Car sales number must be positive"); }
        }
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public int getNumOfSales() {
        return numOfSales;
    }

    /**
     * Records a car sale and updates the file with the employee's sales details.
     *
     * @param car The car being sold.
     * @param filePath The path to the file where the sale information will be written.
     * @throws IOException If there is an error writing to the file.
     */
    public void carSale(Car car, Path filePath) throws IOException {
        addSales();
        car.carSale(car, filePath);
        Files.writeString(filePath, " " + getEmployeeName() + " " + getNumOfSales(), StandardOpenOption.APPEND);
        Files.writeString(filePath, "\n", StandardOpenOption.APPEND);

    }

    public void addSales() {
        numOfSales++;
    }

    /**
     * Calculates the employee's salary based on the number of sales.
     *
     * @return The calculated salary.
     */
    public int salaryCalculate(){
        return 6000 + (numOfSales * 100);
    }

    @Override
    public String toString(){
        return "Employee name: " + employeeName + " Employee ID: " + id
                + " Sales number: " + numOfSales + " Employee Salary: "
                + salaryCalculate() + "\n";
    }

    /**
     * Compares this employee with another employee based on the number of sales.
     *
     * @param other The other employee to compare to.
     * @return A positive integer, zero, or a negative integer as this employee
     *         has more, equal to, or fewer sales than the other employee.
     */
    @Override
    public int compareTo(Employee other){
        if(numOfSales > other.numOfSales) return 1;
        if(numOfSales == other.numOfSales) return 0;
        else return -1;

    }

    public int getId() {
        return id;
    }
}
