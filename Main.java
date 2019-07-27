import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter base(x): ");
        BigDecimal x = new BigDecimal(scan.nextLine());

        System.out.println("Enter exponent(y): ");
        String  inputExp = scan.nextLine();

        System.out.println(System.currentTimeMillis());
        long curTime = System.currentTimeMillis();
        //If exponent is a decimal number..
        if(inputExp.contains(".") || inputExp.startsWith("-")){
            if(inputExp.startsWith("-")){
                double y = 1/Double.parseDouble(inputExp.split("-")[1]);
                inputExp = ""+y;
                System.out.println(y);
                System.out.println("I WORK");
            }
            int integralPartOfY = Integer.parseInt(inputExp.split("\\.")[0]);
            int num= Integer.parseInt(inputExp.split("\\.")[1]);
            int den = 1;
            for(int i=0;i<inputExp.split("\\.")[1].length();i++){den *= 10;}
            //float y  = Float.parseFloat(inputExp);
            System.out.println("Integral Part: "+integralPartOfY);
            System.out.println("Decimal Part: "+num);
            System.out.println("NR: "+num);
            System.out.println("DR: "+den);


            //Calculating Nth root
            BigDecimal nThRoot = getNthRoot(x,den);
            System.out.println(nThRoot);
            BigDecimal intergralPower = getDecimalPower(x,integralPartOfY, curTime);
            BigDecimal temp = getDecimalPower(nThRoot,num, curTime);
            //System.out.println("Result is :"+ intergralPower.multiply(temp));
            System.out.println("RESULT : "+String.format("%30.5e",intergralPower.multiply(temp)));



        }
        else{
            long y = Long.parseLong(inputExp);

            System.out.println("EXP: "+y);
            BigDecimal result = getDecimalPower(x,y,curTime);
            if(result!= null) {

                if(result.toPlainString().length()>10)
                    System.out.println("RESULT : " + String.format("%30.5e", result));
                else
                    System.out.println("RESULT : "+result);
            }
            else
                System.out.println("Overflow: The result cannot be calculated");


        }


        }

    private static BigDecimal getNthRoot(BigDecimal x, int den) {
        //initial guess
        BigDecimal xLast;
        //System.out.println(System.currentTimeMillis()/1000);
        long curTime = System.currentTimeMillis();
        //BigDecimal xLast = getInitialGuess(x,den);
        BigDecimal epsilon = new BigDecimal(0.000001);

        BigDecimal xCurrent = new BigDecimal(2);
        BigDecimal delX ;
        do{
            xLast = xCurrent;
            //xCurrent = (x.divide(getDecimalPower(x,(den-1))).add(xLast.multiply(new BigDecimal(den-1)))).divide(new BigDecimal(den));
            //System.out.println(xLast);
            delX = x.divide(getDecimalPower(xLast,den-1, curTime),4,RoundingMode.HALF_DOWN).subtract(xLast).divide(new BigDecimal(den),4,RoundingMode.HALF_DOWN);
            xCurrent = xLast.add(delX);

            //xCurrent = (xLast.multiply(new BigDecimal(den-1)).add(x.divide(getDecimalPower(xLast,den-1)))).divide(new BigDecimal(den)).round(mathContext);
            System.out.println(xCurrent);
            //Thread.sleep(100);
        }
        while (abs(xCurrent.subtract(xLast)).compareTo(epsilon) == 1);
        return xCurrent;
    }

    private static BigDecimal abs(BigDecimal subtract) {
        if(subtract.compareTo(new BigDecimal(0)) == 1)
            return subtract;
        else if(subtract.compareTo(new BigDecimal(0)) == -1)
            return subtract.multiply(new BigDecimal(-1));
        return new BigDecimal(0);
    }


//    private static BigInteger getIntPower(BigInteger x, int y) {
//        if(y == 0)
//            return new BigInteger("1");
//        if(y == 1)
//            return new BigInteger(x+"");
//        if(y%2==0){
//            return getIntPower(x.multiply(x),y/2);
//        }
//        else{
//            return getIntPower(x.multiply(x),y/2).multiply(x);
//        }
//    }

    private static BigDecimal getDecimalPower(BigDecimal x, long y, long curTime) {
        if((System.currentTimeMillis() - curTime) > 3000)
            return null;
        if(y == 0)
            return new BigDecimal("1");
        if(y == 1)
            return new BigDecimal(x+"");
        if(y%2==0){
            return getDecimalPower(x.multiply(x),y/2, curTime);
        }
        else{
            BigDecimal temp = getDecimalPower(x.multiply(x),y/2, curTime);
            if(temp!=null)
                return temp.multiply(x);
            else
                return null;
        }
    }
}