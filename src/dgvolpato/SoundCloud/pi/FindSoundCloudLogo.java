package dgvolpato.SoundCloud.pi;
import java.io.*;
import java.util.Scanner;

/**
 * Created by Diego on 30/04/2014.
 */


public class FindSoundCloudLogo {
    public static void main (String[] args) {
        System.out.println("Hello, SoundCloud");

        String file = "C:\\pi-billion.txt";
        String fileTest = "C:\\piTest.txt";
        int count = 0;

        try(BufferedReader br = new BufferedReader(new FileReader(file),20)) {
            for(String line; (line = readExactly(br, 50000)) != null ;  ) {
                System.out.println(line);
            }
        }
        catch (IOException e){
            System.out.println("File IO Error");
        }
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
}
