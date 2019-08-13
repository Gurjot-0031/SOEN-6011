//Asked professor's permission regarding importing the below two classes from Math library, as I was unable to calculate big powers using
// the primitive data types like float, double etc..
import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.Scanner;
public class Main {
    /**
     * Entry point of the program.
     * <ul>
     * <li>It takes inputs from the user</li>
     * <li>Handles error(If any)</li>
     * <li>Calls other functions to process the user-inputs</li>
     * <li>Displays the result in appropriate format</li>
     * </ul>
     * @author gurjot
     */
    public static void main(String[] args) {
        BigDecimal x;
        BigDecimal y;
        String inputExp;
        Scanner scan = new Scanner(System.in);
        boolean continueCalculating = true;
        while(continueCalculating){
            try {
                System.out.println("Enter base(x): ");
                x = new BigDecimal(scan.nextLine());
                if(x.compareTo(new BigDecimal(9999))==1)
                    throw new InvalidInputException();
                System.out.println("Enter exponent(y): ");
                inputExp = scan.nextLine();
                y = new BigDecimal(inputExp);
                if(y.compareTo(new BigDecimal(9999))==1)
                    throw new InvalidInputException();

                if(x.toPlainString().startsWith("-") && (inputExp.contains(".")||(inputExp.startsWith("-"))))
                    throw new InvalidInputException("NEGATIVE BASE ON DECIMAL POWER");

                //If exponent is a decimal number..
                if(inputExp.contains(".")){
                    int[] fraction = getFraction(inputExp);
                    int num = fraction[0];
                    int den = fraction[1];
                    if(den>100)
                        throw new InvalidInputException("MORE THAN TWO PLACES AFTER DECIMAL");
                    int integralPartOfY = Integer.parseInt(inputExp.split("\\.")[0]);

                    //Calculating Nth root
                    BigDecimal nThRoot = getNthRoot(x,den);
                    BigDecimal intergralPower = getIntegralPower(x,integralPartOfY);
                    BigDecimal temp = getIntegralPower(nThRoot,num);

                    BigDecimal result;
                    if(inputExp.startsWith("-"))
                        result = new BigDecimal(1).divide(intergralPower.multiply(temp));
                    else
                        result = intergralPower.multiply(temp);

                    if(result.toPlainString().split("\\.")[0].length()>10)
                        System.out.println("RESULT : " + String.format("%30.5E", result));
                    else
                        System.out.println("RESULT : "+String.format("%30.5f", result));
                }
                else{
                    int exp = Integer.parseInt(inputExp);
                    BigDecimal result;
                    if(inputExp.startsWith("-"))
                        result= new BigDecimal(1).divide(getIntegralPower(x,exp),50,RoundingMode.HALF_DOWN);
                    else
                        result= getIntegralPower(x,exp);

                    if(result.toPlainString().length()>10)
                        System.out.println("RESULT : " + String.format("%30.5E", result));
                    else
                        System.out.println("RESULT : "+result);
                }
            }
            catch (InvalidInputException e){
                e.printErrorMessage();
            }
            catch (NumberFormatException e){
                System.out.println("Please enter a number in the range -9999 to 9999");
            }
            finally {

                while (true){
                    System.out.println("\nDo you want to continue?");
                    System.out.println("Press C - CONTINUE");
                    System.out.println("Press E - EXIT");
                    String userChoice = scan.nextLine();
                    if(userChoice.equalsIgnoreCase("c")){
                        continueCalculating=true;
                        break;
                    }
                    else if(userChoice.equalsIgnoreCase("e")){
                        continueCalculating = false;
                        break;
                    }
                    else{
                        System.out.println("Please select an appropriate option");
                        continue;
                    }
                }
            }
        }
    }
    /**
     * It converts a decimal number into its fractional notation.
     * @param inputExp It is the decimal input provided by the user(Exponent).
     * @return An integer array containing both the Numerator and Denominator of the fraction.
     */

    public static int[] getFraction(String inputExp) {
        int integralPartOfY = Integer.parseInt(inputExp.split("\\.")[0]);
        int num= Integer.parseInt(inputExp.split("\\.")[1]);
        int den=1;
        if(num!=0){
            for(int i=0;i<inputExp.split("\\.")[1].length();i++){den *= 10;}
        }

        //float y  = Float.parseFloat(inputExp);
        /*System.out.println("Integral Part: "+integralPartOfY);
        System.out.println("Decimal Part: "+num);
        System.out.println("NR: "+num);
        System.out.println("DR: "+den);*/
        return new int[]{num,den};
    }

    /**
     * It calculates the Nth root of a decimal(BigDecimal) number using approximation.
     * @param x It is the base(As inout by the user)
     * @param den It is equal to N(as in Nth root)
     * @return The Nth root of base x.
     */
    public static BigDecimal getNthRoot(BigDecimal x, int den) {
        //initial guess
        BigDecimal xCurrent = new BigDecimal(2);
        BigDecimal delX ;
        BigDecimal xLast;
        BigDecimal epsilon = new BigDecimal(0.000001);

        do{
            xLast = xCurrent;
            delX = x.divide(getIntegralPower(xLast,den-1),7,RoundingMode.HALF_DOWN).subtract(xLast).divide(new BigDecimal(den),7,RoundingMode.HALF_DOWN);
            xCurrent = xLast.add(delX);
            //System.out.println(xCurrent);
        }
        while (abs(xCurrent.subtract(xLast)).compareTo(epsilon) == 1);
        return xCurrent;
    }

    /**
     * This function is used to calculate the absolute value of a decimal number.
     * @param input It is the decimal input for which the absolute value is to be calculated.
     * @return The absolute value of provided input.
     */
    public static BigDecimal abs(BigDecimal input) {
        if(input.compareTo(new BigDecimal(0)) == 1)
            return input;
        else if(input.compareTo(new BigDecimal(0)) == -1)
            return input.multiply(new BigDecimal(-1));
        return new BigDecimal(0);
    }

    /**
     * It calculates the powers for real bases raised to Integral exponents
     * @param x The real base for which power is to be calculated
     * @param y The Integral exponent, over which the base is to be raised to.
     * @return x raised to y (where x is the real base and y is the Integral power).
     */
    public static BigDecimal getIntegralPower(BigDecimal x, int y) {
        if(y == 0)
            return new BigDecimal("1");
        if(y == 1)
            return new BigDecimal(x+"");
        if(y%2==0){
            return getIntegralPower(x.multiply(x),y/2);
        }
        else{
            BigDecimal temp = getIntegralPower(x.multiply(x),y/2);
            if(temp!=null)
                return temp.multiply(x);
            else
                return null;
        }
    }
}
