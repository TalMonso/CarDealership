import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


/**
 * Represents a car with details such as number, manufacture date, name, mileage, and price.
 */
public class Car {
    private int carNumber;
    private int manufactureDate;
    private String manufactureName;
    private int mileage;
    private int price;

    /**
     * Constructs a new Car object with the specified details.
     *
     * @param carNumber The car's number (must be 6 digits).
     * @param manufactureDate The year the car was manufactured (between 2017 and 2023).
     * @param manufactureName The name of the car manufacturer (at least 2 letters).
     * @param mileage The car's mileage (must be non-negative).
     * @param price The car's price (must be non-negative).
     * @throws CarException If any of the parameters are invalid.
     */
    public Car(int carNumber, int manufactureDate, String manufactureName, int mileage, int price) throws CarException{
        if (carNumber < 100000 || carNumber > 999999 ){
            throw new CarException("You need to enter 6 digits \n");
        }else{ this.carNumber = carNumber; }

        if (manufactureName.length() < 2){
            throw new CarException("Manufacture name should be up to 2 letters");
        }else { this.manufactureName = manufactureName; }

        if (manufactureDate < 2017 || manufactureDate > 2023){
            throw new CarException("Manufacture date should be 2017-2023 \n");
        }else{ this.manufactureDate = manufactureDate; }

        if (mileage < 0){
            throw new CarException("Mileage must be higher than 0 \n");
        }else{ this.mileage = mileage; }

        if (price < 0){
            throw new CarException("Price must be higher than 0 \n");
        }else{ this.price = price; }
    }

    /**
     * Returns a string representation of the car.
     *
     * @return A string describing the car's details.
     */
    @Override
    public String toString(){
        return "Car number:" + carNumber + " manufacture date:" + manufactureDate
                + " manufacture name:" + manufactureName + " mileage:" + mileage
                + " price:" + price + "\n";
    }

    /**
     * Applies a discount to the car's price, reducing its value.
     *
     * @param discount The discount percentage to apply (must be non-negative).
     * @throws CarException If the discount is negative or if the depreciation value is too high.
     */
    public void depreciation(int discount) throws CarException{
        if (discount < 0){
            throw new CarException("The discount must be greater than 0 \n");
        }
        int dep = price * (int)(discount * 0.01);
        if (dep > 5000){
            throw new CarException("The value depreciation is too high \n");
        }
        price -= dep;
        System.out.println("Your value depreciation is complete, the price car now is: " + price);
    }

    /**
     * Saves the car's details to a file.
     *
     * @param car The car to be saved.
     * @param soldCar The path to the file where the car details will be written.
     * @throws IOException If there is an error writing to the file.
     */
    public void carSale(Car car, Path soldCar) throws IOException{
        Files.write(soldCar, car.toString().getBytes());
    }

    public int getCarNumber() {
        return carNumber;
    }

    public int getManufactureDate() {
        return manufactureDate;
    }

    public String getManufactureName() {
        return manufactureName;
    }

    public int getMileage() {
        return mileage;
    }

    public int getPrice() {
        return price;
    }
}
