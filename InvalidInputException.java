/**
 * This is a custom Exception class used for handling invalid inputs from the user
 */
public class InvalidInputException extends Exception {
    String errorMessage;

    /**
     * The default constructor
     */
    public InvalidInputException() {
        this.errorMessage = "Please enter a value smaller than 9999";
    }

    /**
     * The parametrized constructor.
     * @param criteria It is responsible for showing appropriate error messages to the user.
     */
    public InvalidInputException(String criteria) {
        if(criteria.equalsIgnoreCase("NEGATIVE BASE ON DECIMAL POWER"))
            this.errorMessage = "Invalid Input\nDecimal power cannot be calculated for Negative base\nPlease correct your input";
        else if(criteria.equalsIgnoreCase("MORE THAN TWO PLACES AFTER DECIMAL"))
            this.errorMessage = "Invaid Input\nPlease enter upto two decimal places only..";
    }


    /**
     * It prints the error message on the console.
     */
    public void printErrorMessage() {
        System.out.println(this.errorMessage);

    }
}
