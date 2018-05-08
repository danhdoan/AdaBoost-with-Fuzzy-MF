import java.awt.*;
import java.io.*;
import java.util.*;

/**
 * This class handles simple processing for PGM images The existing codes can
 * load and display a gray-scale image in PGM format It also contains an
 * implemented image processing method which flips the image horizontally. For
 * homework #1, you should only add new code inside this class without touching
 * any of the existing code. The new code includes: - Four new methods for 4
 * other simple image processing routines. - New code in the main method to
 * conduct user specified image manipulations
 *
 * @author jxue
 * @version 0.2011-1-24
 *
 * Please follow the homework submission guideline to finish the assignment.
 */
public class PgmImage extends Component {   

    public PgmImage() {
        
    }

    public static int[][] getPixels(String fileName) {
        String magicNumber;
        int height = 0, width = 0, maxValue;
        int[][] pixels = null;
        File file = new File(fileName);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            magicNumber = br.readLine(); 
            String line = br.readLine();
            while (line.startsWith("#")) 
                line = br.readLine();
            Scanner s = new Scanner(line);
            width = s.nextInt();
            height = s.nextInt();
            line = br.readLine(); 
            s = new Scanner(line);
            maxValue = s.nextInt();
            pixels = new int[height+1][width+1];
            br.close();
        } catch (IOException t) {
            t.printStackTrace(System.err);
        }

        DataInputStream stream;
        try {
            stream = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
            int newlinecount = 0;
            char previous = (char) 'a';
            char c;
            do{
                c = (char) stream.readByte();
                if(c == '\n' || c == '\r')
                    newlinecount++;
                if(c == (char) '#' && (previous == '\n' || previous == '\r'))
                    newlinecount--;
                previous = c;
            } while(newlinecount < 3);
            
            for(int row = 1; row <= height; row++){
                for(int col = 1; col <= width; col++){
                    int b = stream.readUnsignedByte();
                    pixels[row][col] = b;
                }
            }
            stream.close();
        } catch (FileNotFoundException e1) {
                System.out.println("File not found");
        } catch (IOException e) {
                System.out.println("IO stuff went wrong when reading the file body.");
        }

        return pixels;
    }
}
