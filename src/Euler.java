import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by Hubaishi on 08.03.17.
 */
public class Euler {

    //calculte the Euler(Phi)
    public BigInteger calculateEuler(BigInteger p, BigInteger q)
    {
        BigInteger euler = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        return euler;
    }

}
