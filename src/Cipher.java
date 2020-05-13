import java.io.*;
import java.math.BigInteger;
import java.nio.CharBuffer;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.math.*;
import java.util.Random;



/**
 * Created by Hubaishi on 14.03.17.
 */
public class Cipher {

    //the encryption function
    public void encryption() {

        //reading the text which we wish to encrypt
        String textInFile = readFile("input");
        StringBuilder sb = new StringBuilder();

        //creating an array
        char[] textAsArrayOfChar = textInFile.toCharArray();

        //getting the ASCII for each character
        for(int i=0;i<textAsArrayOfChar.length;i++) {
            if (i< textAsArrayOfChar.length -1) {
                //adding a comma after each character so we can divide the start and the end of each character so we can encrypt it
                sb.append((int) textAsArrayOfChar[i] + ",");
            }
            else
            {
                sb.append((int) textAsArrayOfChar[i]);
            }
        }

        String m = sb.toString();

        //creating a list using the written comma as a splitter
        List<String> asciiString = Arrays.asList(m.split(","));

        //getting the primary key values into variables (n,e)
        String[] str = readKeyFile("pk").split(",");
        BigInteger n= new BigInteger(str[0]);
        BigInteger e=new BigInteger(str[1]);

        Expon expon = new Expon();

        BigInteger c= BigInteger.ZERO;
        ArrayList listOfBig = new ArrayList();

        //encrypting each ASCII using the public key
        for(int i=0;i<asciiString.size();i++)
        {
            BigInteger num = BigInteger.valueOf(Integer.valueOf(asciiString.get(i)));
            c = expon.calculateExpon(num,e,n);
            listOfBig.add(c);
        }

        StringBuilder listToString = new StringBuilder();

        //prepraing a String of the whole encrypted ASCII but commas in between so we can defing where is the beginning of each encrypted ASCII
        for(int i=0;i<listOfBig.size();i++)
        {
            if(i < (asciiString.size()-1)) {
                listToString.append(listOfBig.get(i) + ",");
            }
            else
            {
                //the last ASCII would have no comma after it
                listToString.append(listOfBig.get(i));
            }
        }
        String newString = listToString.toString();

        //sending the String to the function so it would written in the cipher.text
        writeToCipherFile(newString);

    }

    //decryption functoin
    public void decryption()
    {
        Euclidean euclidean = new Euclidean();
        String[] str = readKeyFile("sk").split(",");
        String cipher = readFile("cipher");

        //getting the private key values into variables (n,d)
        BigInteger n=new BigInteger(str[0]);
        BigInteger d=new BigInteger(str[1]);

        Expon expon = new Expon();

        //getting the encrypted text into a List using the written comma as a separator
        List<String> numbers = Arrays.asList(cipher.split(","));
        ArrayList afterDecry =  new ArrayList();

        //using the exponential function to decrypt each encrypted code into its ASCII representation
        for(int i=0;i<numbers.size();i++)
        {
            BigInteger num = new BigInteger(numbers.get(i));
            BigInteger m = expon.calculateExpon(num,d,n);
            afterDecry.add(m);
        }

        //because casting the BigInteger value into a Character wouldnt be directly possible, i have to cast it first to String, then Integer (ASCII) then as Character
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<afterDecry.size();i++)
        {
            sb.append(Character.toChars(Integer.valueOf(String.valueOf(afterDecry.get(i)))));
        }

        String cipherText = sb.toString();

        //write the decrypted text into the text.text
        writeAsString(cipherText);

    }

    //this function would return the content of each key file in a String
    public String readKeyFile(String fileName)
    {
        File file = new File(fileName+".txt");

        String line="";
        Scanner scan = null;

        try {
            scan = new Scanner(file);
            while (scan.hasNextLine()) {
                line = scan.nextLine();

                //the next line would exclude the brackets at the beginning and the end from the string
                line = line.substring(1, line.length()-1);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            scan.close();
        }

        return line;
    }

    //this function would return the content of the file in a String
    public String readFile(String fileName)
    {
        File file = new File(fileName+".txt");

        String line="";
        Scanner scan = null;

        try {
            scan = new Scanner(file);
            while (scan.hasNextLine()) {
                line = scan.nextLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            scan.close();
        }

        return line;
    }

    //this function chooses randomly a bigInteger less than BigInteger n
    public BigInteger nextRandomBigInteger(BigInteger n) {
        Random rand = new Random();
        BigInteger result = new BigInteger(n.bitLength(), rand);
        //check if the BigInteger is bigger or equal the given BigInteger so we create another one instead
        while( result.compareTo(n) >= 0 ) {
            result = new BigInteger(n.bitLength(), rand);
        }
        return result;
    }


    //this function creates the keys both public and private(secret key)
    public void generateKeys()
    {
        Euler eu = new Euler();

        Random rnd = new Random();

        //choose randomly two BigIntegers with a given number of Bits
        BigInteger p = new BigInteger(100, rnd);
        BigInteger q = new BigInteger(100, rnd);

        //those two BigIntegers values must be primes, otherwise re-choose
        while (!(p.isProbablePrime(1)) || !(q.isProbablePrime(1))) {

            p = new BigInteger(100, rnd);
            q = new BigInteger(100, rnd);

        }

        //they mustn't be equal
        while(p.compareTo(q) == 0)
        {
            p = new BigInteger(100, rnd);
            q = new BigInteger(100, rnd);

        }

        //creating the n and euler(Phi)
        BigInteger n = p.multiply(q);
        BigInteger euler = eu.calculateEuler(p,q);

        //chose a random value for e
        BigInteger eKey = nextRandomBigInteger(euler);

        //e must be prime, so we assure that it would be a coprime with the euler
        while(!(eKey.isProbablePrime(1)))
        {
            eKey = nextRandomBigInteger(euler);
        }



        Euclidean ec= new Euclidean();

        //calculate d using the extended Euclidean Alogrithm
        BigInteger d = ec.calcEcu(euler,eKey);


        File sk = new File("sk.txt");
        File pk = new File("pk.txt");

        //writing the key values in the files using this format "(n,e)" and "(n,d)"
        String pkKey = ("("+n+","+eKey+")");
        String skKey = ("("+n+","+d+")");

        try (FileOutputStream fop = new FileOutputStream(sk)) {

            // if file doesn't exists, then create it
            if (!sk.exists()) {
                sk.createNewFile();
            }

            // get the content in bytes
            byte[] contentInBytes = skKey.getBytes();

            fop.write(contentInBytes);
            fop.flush();
            fop.close();

            System.out.println("Done with writing the secret key to SK.text");

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileOutputStream fop = new FileOutputStream(pk)) {

            // if file doesn't exists, then create it
            if (!pk.exists()) {
                pk.createNewFile();
            }

            // get the content in bytes
            byte[] contentInBytes = pkKey.getBytes();

            fop.write(contentInBytes);
            fop.flush();
            fop.close();

            System.out.println("Done with writing the primary key to PK.txt");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //writing the Cipher text into the cipher.text
    public void writeToCipherFile(String str)
    {

        File cipherFile = new File("cipher.txt");

        try (FileOutputStream fop = new FileOutputStream(cipherFile)) {

            // if file doesn't exists, then create it
            if (!cipherFile.exists()) {
                cipherFile.createNewFile();
            }

            // get the content in bytes
            byte[] contentInBytes = str.getBytes();

            fop.write(contentInBytes);
            fop.flush();
            fop.close();

            System.out.println("Done encrypting and writing the encryption into Cipher.txt");

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //writing the decrypted text into text.text
    public void writeAsString(String m)
    {
        File textFile = new File("text.txt");

        try (FileOutputStream fop = new FileOutputStream(textFile)) {

            // if file doesn't exists, then create it
            if (!textFile.exists()) {
                textFile.createNewFile();
            }

            // get the content in bytes
            byte[] contentInChar = m.getBytes();
            fop.write(contentInChar);
            fop.flush();
            fop.close();

            System.out.println("Done with decrypting and writing the Decryption into text.txt");

        } catch (IOException e) {
            e.printStackTrace();
        }


}
}
