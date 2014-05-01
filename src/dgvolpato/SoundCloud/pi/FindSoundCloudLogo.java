package dgvolpato.SoundCloud.pi;
import java.io.*;
import java.util.Scanner;

/**
 * Created by Diego on 30/04/2014.
 */

//TODO check if file finishes with the following sequence: 282046127557151713951152750455
public class FindSoundCloudLogo {
    public static void main (String[] args) {
        System.out.println("Hello, SoundCloud");

        //read file
        String file = "C:\\pi-billion.txt";
        String fileTest = "C:\\piTest.txt";
        int count = 0;

        try(BufferedReader br = new BufferedReader(new FileReader(file),20)) {
            /**for(String line; (line = br.readLine()) != null && count < 10; count++ ) {
                // process the line.
                System.out.println(line);
            }
            // line is not visible here.**/
            for(String line; (line = readExactly(br, 100)) != null ;  ) {
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
                throw new IOException("Stream terminated early");
            }
            offset += charsRead;
        }
        return new String(chars);
    }
}
