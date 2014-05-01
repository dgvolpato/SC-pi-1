package dgvolpato.SoundCloud.pi;
import java.io.*;
import static dgvolpato.SoundCloud.pi.Constants.READER_BLOCK_LENGTH;
import static dgvolpato.SoundCloud.pi.Constants.BITMAP_SIZE;

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

        FoundLogos[] TopTen = new FoundLogos[10];

        String file = "C:\\pi-billion.txt";
        String fileTest = "C:\\piTest.txt";
        int count = 0;

        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            for(String line; (line = readExactly(br, READER_BLOCK_LENGTH)) != null ;  ) {
                //System.out.println(line);
                //work on line
                TopTen = checkFileLine(TopTen, line);
            }

        printTopTenLogos(TopTen);
        }
        catch (IOException e){
            System.out.println("File IO Error");
        }
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
    public static FoundLogos[] checkFileLine (FoundLogos[] topTen, String line){
        int[] comparationArray = new int[84];
        int indexEndLine = calcEndLine();


        for (int i=0; i <= indexEndLine; i++) {
            comparationArray = fillArray(line, i);
            //topTen = compareLogo(topTen, comparationArray);
        }

        return topTen;
    }

    //private static FoundLogos[] compareLogo(FoundLogos[] topTen, int[] comparationArray) {
    //}

    private static int calcEndLine() {
        return READER_BLOCK_LENGTH - BITMAP_SIZE;
    }

    public static int[] fillArray (String line, int i) {
        int[] a = new int[BITMAP_SIZE];
        for (int j=0; j<BITMAP_SIZE; j++) {
            //a[j] = line[i+j];
            a[j]= 0;
        }
        return a;
    }
}
