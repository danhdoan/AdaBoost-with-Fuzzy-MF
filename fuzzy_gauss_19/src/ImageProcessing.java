import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;

public class ImageProcessing {
    public static int[][] readAndScale(String path, int W, int H) {
        int[][] img = null;
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(new File(path));
            BufferedImage scaledbufferedImage = scale(bufferedImage, W, H);
            
            img = new int[H+1][W+1];
            for (int r = 1; r <= H; r++) {
                for (int c = 1; c <= W; c++) {
                    img[r][c] = scaledbufferedImage.getRGB(c-1, r-1);
                    int rx = (img[r][c] >> 16) & 0xFF;
                    int g = (img[r][c] >> 8) & 0xFF;
                    int b = (img[r][c] & 0xFF);

                    int grayLevel = (rx + g + b) / 3;
                    //int gray = (grayLevel << 16) + (grayLevel << 8) + grayLevel; 
                    //int gray = grayLevel; 
                    img[r][c] = grayLevel;
                }
            }
            
        } catch(FileNotFoundException e) {
            System.out.println("Had a problem opening a file.");
        } catch (Exception e) {
            System.out.println(e.toString() + " caught in readPPM.");
            e.printStackTrace();
        }
        
        return img;
    }
    
    public static BufferedImage scale(BufferedImage imageToScale, int dWidth, int dHeight) {
        BufferedImage scaledImage = null;
        if (imageToScale != null) {
            scaledImage = new BufferedImage(dWidth, dHeight, imageToScale.getType());
            Graphics2D graphics2D = scaledImage.createGraphics();
            graphics2D.drawImage(imageToScale, 0, 0, dWidth, dHeight, null);
            graphics2D.dispose();
        }
        return scaledImage;
    }
    
    public static int[][] readPNGImage(String path) {
        int[][] img = null;
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(new File(path));
            
            int H = bufferedImage.getHeight();
            int W = bufferedImage.getWidth();
            img = new int[H+1][W+1];

            for (int r = 1; r <= H; r++) {
                for (int c = 1; c <= W; c++) {
                    img[r][c] = bufferedImage.getRGB(c-1, r-1);

                    int R = (img[r][c] >> 16) & 0xFF;
                    int G = (img[r][c] >> 8) & 0xFF;
                    int B = (img[r][c] & 0xFF);

                    int grayLevel = (R + G + B) / 3;
                    img[r][c] = grayLevel;                     
                }
            }
            
        } catch (IOException exception) {
        } catch(Exception exception) {
        }
        
        return img;
    }    
    
    public static void writePNGImage(String path, int[][] img) throws IOException {
        BufferedImage bufferedImage = new BufferedImage(img[0].length, img.length, BufferedImage.TYPE_INT_RGB);
        for (int r = 0; r < img.length; r++) {
            for (int c = 0; c < img[0].length; c++) {
                bufferedImage.setRGB(c, r, img[r][c] );
            }
        }
        File file = new File(path);
        ImageIO.write(bufferedImage, "png", file);
    }

    public static void writePGMImage(String path, int[][] img) {   
        try{
            FileWriter fstream = new FileWriter(path);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write("P2\n" + img[0].length + " " + img.length + "\n255\n");
            for(int i = 0; i < img.length;i++)
                for(int j = 0; j < img[0].length;j++)
                    out.write(img[i][j]+" ");
            out.close();
        } catch (Exception e){
            System.err.println("Error : " + e.getMessage());
        }
    }
    
    public static int[][] readPGMImage(String path){
        int[][] image = null;
        try {                        		    
            Scanner infile = new Scanner(new FileReader(path));
            String filetype=infile.nextLine();
         /*
            if (!filetype.equalsIgnoreCase("p2")) {
                System.out.println("[readPGM]Cannot load the image type of "+filetype);
                return null;
            }
	   */	   
            int W = infile.nextInt();
            int H = infile.nextInt();
            int maxValue = infile.nextInt();	        
            image = new int[H+1][W+1];
            for (int r = 1; r <= H; r++) 
                for (int c = 1; c <= W; c++) {
                    int b = infile.nextInt();
                    image[r][c] = (int)(b*255.0/maxValue);
                }
            
            infile.close();
        } catch(FileNotFoundException e) {
            System.out.println("Had a problem opening a file.");
        } catch (Exception e) {
            System.out.println(e.toString() + " caught in readPPM.");
            e.printStackTrace();
        }
        
        return image;
    }
    
    public static int[][] normalizeImage(int[][] img) {
        int[] histo = new int[256];
        for (int i = 0; i < histo.length; i++)
            histo[i] = 0;
        
        int[] sum = new int[256];
        for (int i = 1; i < img.length; i++)
            for (int j = 1; j < img[0].length; j++)
                histo[img[i][j]]++;
        
        sum[0] = histo[0];
        for (int i = 1; i < histo.length; i++)
            sum[i] = sum[i-1] + histo[i];
        
        int area = img.length * img[0].length;
        
        int[][] outImg = new int[img.length][img[0].length];
        for (int i = 1; i < outImg.length; i++)
            for (int j = 1; j < outImg[0].length; j++)
                outImg[i][j] = (int)(1.* 255/area * sum[img[i][j]]);
        
        return outImg;
    }
    
    public static int[][] convolution(int[][] image, int[][] kernel) {
        int[][] outImg = new int[image.length][image[0].length];
        
        int h = kernel.length / 2;
        int w = kernel[0].length / 2;
        for (int i = 0; i < image.length; i++)
        for (int j = 0; j < image[0].length; j++) {
            int sum = 0;
            for (int dx = -h; dx <= h; dx++)
            for (int dy = -w; dy <= w; dy++) {
                int u = i + dx;
                int v = j + dy;       
                if (u < 0 || u >= image.length ||
                        v < 0 || v >= image[0].length) continue;
                sum += image[i + dx][j + dy] * kernel[h + dx][w + dy];
            } 
            if (sum > 255) sum = 255;
            else if (sum < 0) sum = 0;
            outImg[i][j] = sum;
        }
        
        return outImg;
    }  
    
    public static ArrayList<int[][]> getInputImage(String path) {
        File fd = new File(path);
        File[] files = fd.listFiles();
        
        ArrayList<int[][]> lst = new ArrayList<>();
        for (File f : files) {
            if (f.isFile()) {
                String fileName = f.getName();
                if (fileName.endsWith(".pgm")) {
                    lst.add(readPGMImage(path + "/" + fileName));
                }
            }
        }
        
        return lst;
    }
    
        public static int[][] rotate90(int[][] inpImg) {
        int W = inpImg[0].length;
        int H = inpImg.length;
        int[][] outImg = new int[W][H];
        for (int row = 0; row < outImg.length; row++) {
            for (int col = 0; col < outImg[0].length; col++) {
                outImg[row][col] = inpImg[H - col - 1][row];
            }
        }
        
        return outImg;
    }
    
    public static int[][] rotate180(int[][] inpImg) {
        int W = inpImg[0].length;
        int H = inpImg.length;
        int[][] outImg = new int[H][W];
        for (int row = 0; row < outImg.length; row++) {
            for (int col = 0; col < outImg[0].length; col++) {
                outImg[row][col] = inpImg[H - row - 1][W - col - 1];
            }
        }
        
        return outImg;
    }
    
    public static int[][] rotate270(int[][] inpImg) {
        int W = inpImg[0].length;
        int H = inpImg.length;
        int[][] outImg = new int[W][H];
        for (int row = 0; row < outImg.length; row++) {
            for (int col = 0; col < outImg[0].length; col++) {
                outImg[row][col] = inpImg[col][W - row - 1];
            }
        }
        
        return outImg;
    } 
    
    public static void printImage(int[][] img) {
        for (int r = 0; r < img.length; r++) {
            for (int c = 0; c < img[0].length; c++) {
                System.out.printf("%5d", img[r][c]);
            }
            System.out.println("");
        }
        System.out.println("");
    }
}
