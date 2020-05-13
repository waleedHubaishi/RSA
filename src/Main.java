import com.sun.deploy.util.StringUtils;

import java.io.*;
import java.math.BigInteger;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Hubaishi on 07.03.17.
 */
public class Main {

    //very important
    //the file to encrypt is called: input.txt
    //the file of the encryption is called: cipher.txt
    //the file of the decryption is called: text.txt
    //the primary key is stored in pk.txt
    //the secret key is stored in sk.txt

    public static void main(String[] args) {
        Cipher cipher = new Cipher();

        double sum = 0.0;
        System.out.println("*** Generating Keys ***");
        double startTime = System.currentTimeMillis();
        cipher.generateKeys();
        double endTime   = System.currentTimeMillis();
        double totalTime = endTime - startTime;
        sum =totalTime+sum;
        System.out.println("");
        System.out.println("time needed to generate keys is "+totalTime/1000+" seconds.");
        System.out.println("");
        System.out.println("*** Encryption Phase ***");
        startTime = System.currentTimeMillis();
        cipher.encryption();
        endTime   = System.currentTimeMillis();
        totalTime = endTime - startTime;
        sum =totalTime+sum;
        System.out.println("");
        System.out.println("time needed to encrypt is "+totalTime/1000+" seconds.");
        System.out.println("");
        System.out.println("*** Decryption Phase ***");
        startTime = System.currentTimeMillis();
        cipher.decryption();
        endTime   = System.currentTimeMillis();
        totalTime = endTime - startTime;
        System.out.println("");
        System.out.println("time needed to decrypt is "+totalTime/1000+" seconds.");
        sum =totalTime+sum;
        System.out.println("");
        System.out.println("Time needed for the whole process is "+sum/1000+" seconds.");



    }
}
