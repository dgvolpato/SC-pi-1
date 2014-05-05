package dgvolpato.SoundCloud.pi;
import java.io.*;

import static java.lang.Math.abs;
import static java.util.Arrays.copyOfRange;
import static dgvolpato.SoundCloud.pi.Constants.*;

/**
 * Created by Diego on 30/04/2014.
 */

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
            for(String line; (line = readExactly(br, READER_BLOCK_LENGTH)) != null;  ) {
                tempArray = line.split("");

                for(int i=1;i<tempArray.length;i++){
                    intArray[i-1] = Integer.parseInt(tempArray[i]);
                }

                for (int i=0;BITMAP_SIZE+i<READER_BLOCK_LENGTH;i++) {
                    topTen = isTopTen(calcDelta(copyOfRange(intArray, i, BITMAP_SIZE+i)),bigIndex, topTen);
                    bigIndex++;

                }
                bigIndex += 84;
                //break;
            }

            printTopTenLogos(topTen);
            System.out.println("finished.");
        }
        catch (IOException e){
            System.out.println("File IO Error\nPlease, be sure the data set is on C:\\pi-billion.txt");
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
        int [] sequenceArray;
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
