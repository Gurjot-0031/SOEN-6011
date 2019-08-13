import org.junit.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class UnitTests {
    /**
     * It tests the absolute function
     */
    @Test
    public void absTest(){
        Assert.assertEquals(new BigDecimal(100),Main.abs(new BigDecimal(-100)));
        Assert.assertEquals(new BigDecimal(0),Main.abs(new BigDecimal(0)));
        Assert.assertEquals(new BigDecimal(90),Main.abs(new BigDecimal(90)));
        Assert.assertEquals(new BigDecimal(90.983),Main.abs(new BigDecimal(-90.983)));
    }
    /**
     * It is a test function for testing getFraction function
     */
    @Test
    public void getFractionTest(){

        Assert.assertEquals(41,Main.getFraction("3.041")[0]);
        Assert.assertEquals(1000,Main.getFraction("3.041")[1]);

        Assert.assertEquals(11,Main.getFraction("112.11")[0]);
        Assert.assertEquals(100,Main.getFraction("112.11")[1]);

        Assert.assertEquals(301,Main.getFraction("-6.301")[0]);
        Assert.assertEquals(1000,Main.getFraction("-6.301")[1]);

        Assert.assertEquals(999,Main.getFraction("8.999")[0]);
        Assert.assertEquals(1000,Main.getFraction("8.999")[1]);

        Assert.assertEquals(1,Main.getFraction("3.1")[0]);
        Assert.assertEquals(10,Main.getFraction("3.1")[1]);
    }
    /**
     * It tests getNthRoot function
     */
    @Test
    public void getNthRootTest(){
        Assert.assertEquals(new BigDecimal(2), Main.getNthRoot(new BigDecimal(1024),10).round(new MathContext(1,RoundingMode.HALF_DOWN)));
        Assert.assertEquals(new BigDecimal(6), Main.getNthRoot(new BigDecimal(7776),5).round(new MathContext(1,RoundingMode.HALF_DOWN)));
    }
    /**
     * It tests getDecimalPower function
     */
    @Test
    public void getDecimalPower(){
        Assert.assertEquals(new BigDecimal(1024), Main.getIntegralPower(new BigDecimal(2),10));
        Assert.assertEquals(new BigDecimal(7776), Main.getIntegralPower(new BigDecimal(6),5));
    }

}
