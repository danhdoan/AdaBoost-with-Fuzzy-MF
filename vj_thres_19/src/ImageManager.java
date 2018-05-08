import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class ImageManager {	

    public static BufferedImage getBufferedImage(String image){
        File file = new File(image);
        BufferedImage inData = null;
        try {
            inData = ImageIO.read(file);
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return inData;
    }

    public static MatrixImage saveBufferedImagetoMatrixImage(BufferedImage buff){
        int h = buff.getHeight();
        int w = buff.getWidth();
        int []data = new int[h * w];
        int k = 0;
        try {   
            for(int x=0;x<h;x++){
                for(int y=0;y<w;y++){

                    Color c = new Color(buff.getRGB(y, x));
                       
                    int red = (int)(c.getRed() * 0.299);
                    int green = (int)(c.getGreen() * 0.587);
                    int blue = (int)(c.getBlue() * 0.114);

                    int gray = (red + green + blue);
                    data[k++] = gray;
                }
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new MatrixImage(h, w, data);
    }

    /***
     * Get listName image from full path (png, jpg,..)
     * Return arrayList<String> name of image
     * @param path
     * @return
     */
    public static ArrayList<String> getListNameImage(String path){
        File folder = new File(path);
        File[] files = folder.listFiles();

        ArrayList<String> namesList = new ArrayList<>();
        for(File f : files){
            if(f.isFile() && 
                (f.getName().endsWith(".jpg") ||
                    f.getName().endsWith(".jpeg") ||
                    f.getName().endsWith(".gif") ||
                    f.getName().endsWith(".bmp") ||
                    f.getName().endsWith(".tif") ||
                    f.getName().endsWith(".pgm") ||
                    f.getName().endsWith(".png")
                )) {
                namesList.add(f.getName());
            }
        }

        return namesList;
    }
}
