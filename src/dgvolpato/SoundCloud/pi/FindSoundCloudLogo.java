package dgvolpato.SoundCloud.pi;
import java.io.*;

import static java.lang.Math.abs;
import static java.util.Arrays.copyOfRange;
import static dgvolpato.SoundCloud.pi.Constants.*;

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

        long bigIndex = 0;

        String[] tempArray;
        int[] intArray = new int[READER_BLOCK_LENGTH];

        try(BufferedReader br = new BufferedReader(new FileReader(FILE_DIRECTORY))) {
            String lineStart = readExactly(br, 2);
            for(String line; (line = readExactly(br, READER_BLOCK_LENGTH)) != null ;  ) {
                tempArray = line.split("");

                for(int i=1;i<tempArray.length;i++){
                    intArray[i-1] = Integer.parseInt(tempArray[i]);
                }

                for (int i=0;BITMAP_SIZE+i<READER_BLOCK_LENGTH;i++) {
                    topTen = isTopTen(calcDelta(copyOfRange(intArray, i, BITMAP_SIZE+i)),bigIndex, topTen);
                    bigIndex++;
                }

                break;
            }
            int [] beta = {2,2,7,9,7,2,9,0,0,9,1,4,8,7,7,1,2,3,9,6,7,2,9,9,3,9,2,9,5,7,6,2,4,7,2,9,0,3,2,8,8,8,4,9,2,7,5,3,7,7,6,8,3,3,0,3,1,2,7,3,5,9,8,8,4,3,6,6,5,7,7,9,7,3,6,7,1,4,2,2,9,4,6,4};
            int [] alfa = {9,0,0,4,9,9,9,3,9,5,3,2,2,1,5,3,6,2,2,7,4,8,4,7,6,6,0,3,6,1,3,6,7,7,6,9,7,9,7,8,5,6,7,3,8,6,5,8,4,6,7,0,9,3,6,6,7,9,5,8,8,5,8,3,7,8,8,7,9,5,6,2,5,9,4,6,4,6,4,8,9,1,3,7};
            System.out.println("Beta test:" + calcDelta(beta));

            printTopTenLogos(topTen);
            System.out.println("finished.");
        }
        catch (IOException e){
            System.out.println("File IO Error");
        }
    }

    public static FoundLogos[] initTopTen (int size) {
        FoundLogos[] topTen = new FoundLogos[size];
        for (int i=0; i<topTen.length; i++){
            topTen[i] = new FoundLogos(0,1000);
        }
        return topTen;
    }

    public static FoundLogos[] sortTopTen (FoundLogos[] topTen) {
        FoundLogos temp = new FoundLogos(0,1000);

        for (int i=0;i<topTen.length;i++) {
            for (int j=i+1; j<topTen.length; j++){
                if (topTen[j].getDelta() < topTen[i].getDelta()  ) {
                    temp.setOffset(topTen[i].getOffset());
                    temp.setDelta(topTen[i].getDelta());

                    topTen[i].setOffset(topTen[j].getOffset());
                    topTen[i].setDelta(topTen[j].getDelta());

                    topTen[j].setOffset(temp.getOffset());
                    topTen[j].setDelta(temp.getDelta());
                }
            }
        }

        return topTen;
    }

    public static FoundLogos[] isTopTen(int delta, long offSet, FoundLogos[] topTen) {

        if (topTen[9].getDelta() > delta) {
            topTen[9].setDelta(delta);
            topTen[9].setOffset(offSet);
        }

        topTen = sortTopTen(topTen);

        return topTen;
    }

    private static void printTopTenLogos(FoundLogos[] topTen) {
        int [] sequenceArray = new int[BITMAP_SIZE];
        for (int i=0;i<topTen.length;i++){
            sequenceArray = getSequence(topTen[i].getOffset());
            System.out.println("Position: "+(i+1)+" delta:"+topTen[i].getDelta()+" offset:"+topTen[i].getOffset());
            System.out.print("Sequence: ");
            for (int j=0;j<BITMAP_SIZE;j++){
                System.out.print(sequenceArray[j]);
            }
            System.out.println(" ");
        }

        return;
    }

    public static String readExactly(Reader reader, int length) throws IOException {
        char[] chars = new char[length];
        int offset = 0;
        while (offset < length) {
            int charsRead = reader.read(chars, offset, length - offset);
            if (charsRead <= 0) {
                return null;
            }
            offset += charsRead;
        }
        return new String(chars);
    }

    public static int calcDelta (int[]a) {
        int average = 0;
        for (int i=0; i<BITMAP_SIZE;i++) {
            average += abs(a[i] - SOUNDCLOUD_LOGO_ARRAY[i]);
        }

        return average;
    }

    public static int[] getSequence (long offset) {
        int[] bitmapArray = new int[BITMAP_SIZE];
        String[] tempArray;
        int[] intArray = new int[READER_BLOCK_LENGTH];

        try(BufferedReader br = new BufferedReader(new FileReader(FILE_DIRECTORY))) {
            String lineStart = readExactly(br, 2);
            int count = 0;
            int div = (int)(offset/READER_BLOCK_LENGTH);
            int modulo = (int) (offset%READER_BLOCK_LENGTH);
            String line;

            do {
                line = readExactly(br, READER_BLOCK_LENGTH);
                count++;
            }
            while (count<=div);

            if ((count-1) == div) {
                tempArray = line.split("");

                for(int i=1;i<tempArray.length;i++){
                    intArray[i-1] = Integer.parseInt(tempArray[i]);
                }

                bitmapArray = copyOfRange(intArray, modulo, modulo + BITMAP_SIZE);
            }
        }
        catch (IOException e){
            System.out.println("File IO Error");
        }

        return bitmapArray;
    }

}
