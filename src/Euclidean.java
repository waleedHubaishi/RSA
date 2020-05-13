import java.math.BigInteger;

/**
 * Created by Hubaishi on 07.03.17.
 */
public class Euclidean {

    BigInteger a =BigInteger.valueOf(0);
    BigInteger b = BigInteger.valueOf(0);
    BigInteger x0 = BigInteger.valueOf(1);
    BigInteger y0 = BigInteger.valueOf(0);
    BigInteger x1=BigInteger.valueOf(0);
    BigInteger y1=BigInteger.valueOf(1);
    BigInteger q = BigInteger.valueOf(0);
    BigInteger r = BigInteger.valueOf(0);



    //calculate the d value in the private key using the extended Euclidian Algorithm
    public BigInteger calcEcu(BigInteger num1, BigInteger num2)
    {

        //initializing the temporary valuse holders
        BigInteger tempX0 = BigInteger.valueOf(0);
        BigInteger tempX1 = BigInteger.valueOf(0);
        BigInteger tempY0 = BigInteger.valueOf(0);
        BigInteger tempY1 = BigInteger.valueOf(0);
        BigInteger tempA = BigInteger.valueOf(0);
        BigInteger tempB = BigInteger.valueOf(0);
        BigInteger tempQ = BigInteger.valueOf(0);
        BigInteger tempR = BigInteger.valueOf(0);

        //the a and b in the table are set
        a = num1;
        b = num2;


        //while the b is bigger than zero, here is the actual algorithm
        while (b.compareTo(BigInteger.valueOf(0)) == 1) {
        tempX0 = x0;
        tempX1 = x1;
        tempY0 = y0;
        tempY1 = y1;
        tempA = a;
        tempB = b;


        q=a.divide(b);

        r = a.mod(b);

            tempQ=q;
            tempR = r;

        a = tempB;
        b = r;

            x1=tempX0.subtract(tempQ.multiply(tempQ));



            y1 = tempY0.subtract(tempQ.multiply(tempY1));

        x0 = tempX1;
        y0 = tempY1;

        }

        //if the y0 is less than 0, then add num1 to it. if y0 is bigger than num1, then subtract num1 from it
        while((y0.compareTo(BigInteger.valueOf(0)) == -1) || (y0.compareTo(num1) == 1)) {

            if (y0.compareTo(BigInteger.valueOf(0))== -1) {
                y0 = num1.add(y0);
            }
            if((y0.compareTo(num1) == 1))
            {
                y0 = y0.subtract(num1);
            }

        }

        return y0;

    }




}
