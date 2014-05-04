package dgvolpato.SoundCloud.pi;
import java.io.*;
import java.math.BigInteger;

import static com.sun.org.apache.xerces.internal.util.XMLChar.trim;
import static dgvolpato.SoundCloud.pi.Constants.READER_BLOCK_LENGTH;
import static dgvolpato.SoundCloud.pi.Constants.BITMAP_SIZE;
import static dgvolpato.SoundCloud.pi.Constants.SOUNDCLOUD_LOGO_ARRAY;
import static java.lang.Math.abs;
import static java.math.BigInteger.valueOf;
import static java.util.Arrays.copyOfRange;

/**
 * Created by Diego on 30/04/2014.
 */
//TODO work on case of numbers between the groups of 50000
//TODO review logic of the arrays used as parameters
//TODO develop and test delta function
//TODO review case of topTen array still empty or not plainly full
//TODO review structure of FoundLogos class

public class FindSoundCloudLogo {

    public static void main (String[] args) {
        System.out.println("Hello, SoundCloud");

        FoundLogos[] topTen = new FoundLogos[10];
        topTen = initTopTen(10);

        String file = "C:\\pi-billion.txt";
        String fileTest = "C:\\piTest.txt";
        int count = 0;
        BigInteger bigIndex = BigInteger.valueOf(0);

        //variables for testing purpose
        int[] testArray = new int[BITMAP_SIZE];
        int[] lineArray = new int[BITMAP_SIZE];
        String[] stringArray = new String[READER_BLOCK_LENGTH];
        String[] tempArray;
        int[] intArray = new int[READER_BLOCK_LENGTH];

        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String lineStart = readExactly(br, 2);
            //System.out.println("lineStart: " + lineStart);
            for(String line; (line = readExactly(br, READER_BLOCK_LENGTH)) != null ;  ) {
                //System.out.println(line);
                //work on line
                //TopTen = checkFileLine(TopTen, line);
                //lineArray = line.split(",");

                //System.out.println("line:" + line);

                tempArray = line.split("");

                for(int i=1;i<tempArray.length;i++){
                    intArray[i-1] = Integer.parseInt(tempArray[i]);
                }

                System.out.println("primeiro:" + intArray[0]+".");

                /*for (int i=0;i<BITMAP_SIZE;i++){
                    System.out.print(stringArray[i] +",");
                }*/

                //System.out.println("\nlength:" + stringArray.length);

                for (int i=0;BITMAP_SIZE+i<READER_BLOCK_LENGTH;i++) {
                    bigIndex = bigIndex.add(valueOf(1));


                    System.out.println("Delta:" + calcDelta(copyOfRange(intArray, i, BITMAP_SIZE+i))+" Offset:"+bigIndex);

                }

                //testArray = fillArray(line, 0);

                break;
            }



        //printTopTenLogos(TopTen);
        }
        catch (IOException e){
            System.out.println("File IO Error");
        }
    }

    public static FoundLogos[] initTopTen (int size) {
        FoundLogos[] topTen = new FoundLogos[size];
        for (int i=0; i<topTen.length; i++){
            topTen[i] = new FoundLogos(BigInteger.valueOf(0),1000);
        }
        return topTen;
    }

    public static FoundLogos[] isTopTen(int delta, BigInteger offSet, FoundLogos[] topTen) {

        if (topTen[9].getDelta() != null) {

        }

        return topTen;
    }

    private static void printTopTenLogos(FoundLogos[] topTen) {
        System.out.println("Implement printTopTenLogos");
        return;
    }

    public static String readExactly(Reader reader, int length) throws IOException {
        char[] chars = new char[length];
        int offset = 0;
        while (offset < length) {
            int charsRead = reader.read(chars, offset, length - offset);
            if (charsRead <= 0) {
                //throw new IOException("Stream terminated early");
                return null;
            }
            offset += charsRead;
        }
        return new String(chars);
    }

    private static int calcEndLine() {
        return READER_BLOCK_LENGTH - BITMAP_SIZE;
    }

    public static int calcDelta (int[]a) {
        int average = 0;
        for (int i=0; i<BITMAP_SIZE;i++) {
            average += abs(a[i] - SOUNDCLOUD_LOGO_ARRAY[i]);
        }

        return average;
    }

}
