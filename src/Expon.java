import com.sun.xml.internal.fastinfoset.util.StringArray;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by Hubaishi on 14.03.17.
 */
public class Expon {

    //calculate the exponential to generate the encrypted or decrypted ASCII
    public BigInteger calculateExpon(BigInteger num, BigInteger powe, BigInteger modu) {
        double ex = 0.0;
        ArrayList binaryArr = createBinArr(powe.toString(2));
        ArrayList<String> binArrInStr = new ArrayList<>();
        ArrayList<Integer> binArrInInt = new ArrayList<>();

        //first we cast the binary Digit into String, then into integer
        for(int i=0;i<binaryArr.size();i++)
        {
            binArrInStr.add(((binaryArr.get(i).toString())));
            binArrInInt.add(Integer.valueOf(binArrInStr.get(i)));
        }


        //initilizing the variables and creating temporary value holders
        int i = binArrInInt.size()-1;
        BigInteger h = BigInteger.ONE;
        BigInteger k = num;
        BigInteger oldK = BigInteger.ZERO;
        BigInteger oldH = BigInteger.ZERO;

        //the real Exponential work happens here
        for( ; i >= -1 ; i--)
        {
            oldK = k;
            oldH = h;
            if(i < (binArrInInt.size()-1)) {
                if (binArrInInt.get(i+1) == 1) {

                    h = (oldH.multiply(oldK)).mod(modu);
                }
                k = (oldK.multiply(oldK)).mod(modu);
            }

        }

        return h;
    }

    //creating the ArrayList of the Binary representation of the power so we can check where the ones and zeros are
    public ArrayList createBinArr(String d)
    {
        char[] numbersInChar = d.toCharArray();
        ArrayList charInString = new ArrayList();
        for(int i=0;i<numbersInChar.length;i++)
        {
            charInString.add(String.valueOf(numbersInChar[i]));
        }

        return charInString;
    }
}
