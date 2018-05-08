
/**
 * @file:   Main.java
 * @author: Danh Doan
 *
 * @description: Implementation of Viola-Jones algorithm with some new features
 * for Integral Images and new way to store and extract data
 *
 * @note: 2017-10-06: file created test passed with old data set low accuracy
 * with standard data set
 */
import java.io.*;
import java.util.*;

public class Main {

    public final static String pathProject = ".";
    public final static String pathInput = pathProject + File.separator + "inputs";
    public final static String pathOutput = pathProject + File.separator + "outputs";

    public final static String pathTypePattern = pathProject + File.separator + "typePattern";

    public final static String pathTrain = pathInput + File.separator + "train";
    public final static String pathTrainInfo = pathTrain + File.separator + "info.txt";

    public final static String pathTest = pathInput + File.separator + "test";
    public final static String pathTestInfo = pathTest + File.separator + "info.txt";

    public final static String pathWeakClass = pathOutput + File.separator + "weakclass"
            + File.separator + "20171025_vj_5000_19.wk";
    public static String face = File.separator + "face";
    public static String nonFace = File.separator + "non-face";

    public static Random r = new Random();

    public final static int numWeakClass = 200;

    public static int numFeature = 134541; // SizeOfFeature
    public final static int numType = 5;			// typeFull

    public static int numFeatureSel = 5000;			// NFeatureFull

    public static ArrayList<int[][]> lstImage = null;
    public static ArrayList<int[][]> lstIntegral = null;
    public static ArrayList<int[][]> lstImageTest = null;
    public static ArrayList<int[][]> lstIntegralTest = null;
    public static ArrayList<int[][]> lstType = null;
    public static IndexObj[] lstIndexObj = null;

    public static void main(String[] args) {
        //trainingProcess();
        //testingProcess(pathTrain, pathTrainInfo, 0.5, 0.5);
        //analyzeProcess();
        testingProcess(pathTest, pathTestInfo, 0.20, 0.8);
    }

    public static void testingProcess(String pathImg, String pathInfo, double low, double high) {
        lstImageTest = readInputImage(pathImg);

        lstIntegralTest = calculateIntegralImage(lstImageTest);

        lstType = readTypePattern(pathTypePattern);

        lstIndexObj = readIndex(pathTypePattern);

        //double rate = low;
        //while (rate <= high) {
        testData_threshold(pathInfo, 0);
        //  rate += 0.01;
        //}
    }

    public static void trainingProcess() {
        lstImage = readInputImage(pathTrain);
        //System.out.println(lstImage.size());

        lstIntegral = calculateIntegralImage(lstImage);
        //System.out.println(lstIntegral.size());

        lstType = readTypePattern(pathTypePattern);
        //System.out.println(lstType.size());

        lstIndexObj = readIndex(pathTypePattern);
        //System.out.println(lstIndexObj.length);

        WeakClass[] wk = AdaBoost_threshold();
        saveArrWeakClass(wk, pathWeakClass);
    }

    public static void testData_threshold(String fileTestInfo, double rate) {
        int numPosTest = 0, numNegTest = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileTestInfo));
            numPosTest = Integer.parseInt(br.readLine());
            numNegTest = Integer.parseInt(br.readLine());
            br.close();
        } catch (IOException | NumberFormatException e) {
        }

        WeakClass[] wk = loadWeakClass(pathWeakClass);
        try {
            for (int len = 1; len <= numWeakClass; len++) {
                int cntPos = 0, cntNeg = 0;
                for (int i = 0; i < numPosTest + numNegTest; i++) {
                    double sumAlpha = 0, sum = 0;
                    for (int j = 0; j < len; j++) {
                        WeakClass wk1 = wk[j];
                        sumAlpha += wk1.alpha;
                        int idx = wk1.fi.getIndex();
                        int type = lstIndexObj[idx].getType();
                        int x = lstIndexObj[idx].getX();
                        int y = lstIndexObj[idx].getY();
                        int h = lstIndexObj[idx].getH();
                        int w = lstIndexObj[idx].getW();
                        FeatureTemplate ftemp = new FeatureTemplate(lstType.get(type - 1));
                        double val = ftemp.getFeatureValue(
                                lstIntegralTest.get(i), x, y, x + h - 1, y + w - 1);
                        if (val * wk1.fi.getPj() > wk1.fi.getPj() * wk1.fi.getThreshold()) {
                            sum += wk1.alpha;
                        }
                    }
                    sumAlpha *= 0.5;
                    if (sum >= sumAlpha) {
                        if (i < numPosTest) {
                            cntPos++;
                        }
                    } else {
                        if (i >= numPosTest) {
                            cntNeg++;
                        }
                    }
                }

                double err = 1 - 1. * (cntPos + cntNeg) / (numPosTest + numNegTest);
                System.out.printf("%d %f\n", len, err);
            /*
            System.out.printf("%.2f %.3f %.3f\n", rate, 
                    100. * cntPos / numPosTest,
                    100. * (numNegTest - cntNeg) / numNegTest);
            */
/*
            System.out.println("Detection Rate: " + 1.0 * cntPos / numPosTest);
            System.out.println("False Positive Rate: " + 1.0 * (numNegTest - cntPos) / numNegTest);
*/
            }
        } catch (Exception e) {
            System.out.println("err:" + e.getMessage());
        }

    }

    public static WeakClass[] AdaBoost_threshold() {
        System.out.println("algorithmAdaboost - threshold");
        int numPosTrain = 0, numNegTrain = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(pathTrainInfo));
            numPosTrain = Integer.parseInt(br.readLine());

            numNegTrain = Integer.parseInt(br.readLine());
            br.close();
        } catch (IOException | NumberFormatException e) {
            System.out.println(e.getMessage());
        }

        double[] weights = new double[numPosTrain + numNegTrain];

        for (int i = 0; i < numPosTrain; i++) {
            weights[i] = 1.0 / (2 * numPosTrain);
        }
        for (int i = numPosTrain; i < numPosTrain + numNegTrain; i++) {
            weights[i] = 1.0 / (2 * numNegTrain);
        }

        WeakClass[] arrWeakClass = new WeakClass[numWeakClass];

        for (int t = 0; t < numWeakClass; t++) {
            long tStart = new Date().getTime();

            System.out.println("Weak Class: " + t);
            double res = 0;
            for (int i = 0; i < numPosTrain + numNegTrain; i++) {
                res += weights[i];
            }
            for (int i = 0; i < numPosTrain + numNegTrain; i++) {
                weights[i] /= res;
            }

            FeatureIndex bestFeature = getFeatureIndex_threshold(weights, numPosTrain, numNegTrain);
            System.out.println("Index: " + bestFeature.getIndex());
            System.out.println("Error: " + bestFeature.getError());

            double err = bestFeature.getError();
            double beta = err / (1 - err);
            if (t > 0 && err >= 0.5) {
                arrWeakClass[t] = arrWeakClass[t - 1];
                continue;
            }

            arrWeakClass[t] = new WeakClass(bestFeature, Math.log(1. / beta));

            for (int i = 0; i < numPosTrain + numNegTrain; i++) {
                int label = i < numPosTrain ? 1 : 0;

                int ei = label == bestFeature.getHt()[i] ? 0 : 1;

                weights[i] = weights[i] * Math.pow(beta, 1 - ei);
            }

            bestFeature.setHt(null);

            long tEnd = new Date().getTime();
            System.out.println("Time: " + (tEnd - tStart) / 1000. + "\n");
        }

        return arrWeakClass;
    }

    public static FeatureIndex getFeatureIndex_threshold(double[] weights, int numOfPosTrain, int numOfNegTrain) {
        int[] indexRandom = genIndex(numFeature, numFeatureSel);

        FeatureIndex bestFeature = null;
        double minErr = Double.MAX_VALUE;
        double tAll = 0;
        //for (int idx = 0; idx < lstIndexObj.length; idx++) {
        for (int k = 0; k < indexRandom.length; k++) {
            int idx = indexRandom[k];
            FeatureIndex obj = new FeatureIndex(idx);

            double[] arrPosNeg = new double[numOfPosTrain + numOfNegTrain];
            int type = lstIndexObj[idx].getType();
            int x = lstIndexObj[idx].getX();
            int y = lstIndexObj[idx].getY();
            int h = lstIndexObj[idx].getH();
            int w = lstIndexObj[idx].getW();

            FeatureTemplate ftemp = new FeatureTemplate(lstType.get(type - 1));
            for (int i = 0; i < arrPosNeg.length; i++) {
                arrPosNeg[i] = ftemp.getFeatureValue(
                        lstIntegral.get(i), x, y, x + h - 1, y + w - 1);
            }

            long time1 = new Date().getTime();
            double[] resThreshold = getThreshold(arrPosNeg, weights,
                    numOfPosTrain + numOfNegTrain, numOfPosTrain);
            long time2 = new Date().getTime();
            tAll += (time2 - time1);

            obj.setThreshold(resThreshold[0]);
            obj.setPj(resThreshold[1]);

            double err = 0;
            int[] ht = new int[numOfPosTrain + numOfNegTrain];

            for (int i = 0; i < numOfPosTrain + numOfNegTrain; i++) { // 6k
                int label = 1;
                if (i >= numOfPosTrain) {
                    label = 0;
                }

                double val = arrPosNeg[i];

                int labelclassification = 1;

                if (val * obj.getPj() < obj.getThreshold() * obj.getPj()) {
                    labelclassification = 0;
                }

                ht[i] = labelclassification;

                if (labelclassification != label) {
                    err += weights[i];
                }
            }

            if (minErr > err) {
                minErr = err;
                obj.setError(err);
                obj.setHt(ht);
                bestFeature = obj;
            }
        }
        System.out.println(tAll);
        return bestFeature;
    }

    public static double[] getThreshold(double[] data, double[] weights, int N, int K) {
        ArrayList<Obj> arr = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            arr.add(new Obj(i, data[i], weights[i]));
        }
        Collections.sort(arr);

        double[] f = new double[N + 1];
        f[0] = 0;

        double errPos = 0;
        for (int i = 0; i < K; i++) {
            errPos += weights[i];
        }

        for (int i = 1; i <= N; i++) {
            f[i] = f[i - 1];
            if (arr.get(i - 1).index < K) {
                f[i] += arr.get(i - 1).weight;//f[i]++;
            }
        }

        double errNeg = 0;
        for (int i = K; i < N; i++) {
            errNeg += weights[i];
        }

        double[] g = new double[N + 1];
        g[0] = errNeg;
        for (int i = 1; i <= N; i++) {
            g[i] = g[i - 1];
            if (arr.get(i - 1).index >= K) {
                g[i] -= arr.get(i - 1).weight;//g[i]++;
            }
        }

        int cs = -1;
        double errFull = Double.MAX_VALUE;
        double p = 0;

        for (int i = 1; i <= N; i++) {
            double temp1 = f[i] + g[i];
            double temp2 = 1 - g[i] - f[i];
            double temp3 = Math.min(temp1, temp2);

            if (errFull > temp3) {
                errFull = temp3;
                cs = i;
                if (temp1 < temp2) {
                    p = 1;
                } else {
                    p = -1;
                }
            }
        }
        return new double[]{arr.get(cs - 1).value, p};
    }

    public static int[] genIndex(int n, int sl) {// sl = 1000
        ArrayList<Integer> arrList = new ArrayList<>();
        do {
            int temp = r.nextInt(n);
            if (!arrList.contains(temp)) {
                arrList.add(temp);
            }
        } while (arrList.size() < sl);

        int[] arr = new int[sl];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arrList.get(i);
        }

        return arr;
    }

    public static ArrayList<int[][]> calculateIntegralImage(ArrayList<int[][]> lstImg) {
        ArrayList<int[][]> lst = new ArrayList<>();
        for (int idx = 0; idx < lstImg.size(); idx++) {
            int[][] img = lstImg.get(idx);
            int[][] dp = new int[img.length + 1][img[0].length + 1];

            for (int i = 1; i < img.length; i++) {
                for (int j = 1; j < img[0].length; j++) {
                    dp[i][j] = img[i][j] + dp[i - 1][j]
                            + dp[i][j - 1] - dp[i - 1][j - 1];
                }
            }
            lst.add(dp);
        }
        return lst;
    }

    public static IndexObj[] readIndex(String path) {
        String pathLookUpTable = path + File.separator + "lookUpTable.txt";
        File file = new File(pathLookUpTable);

        IndexObj[] arrIdxObj = new IndexObj[numFeature];
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            for (int i = 0; i < numFeature; i++) {
                String line = br.readLine();
                Scanner sc = new Scanner(line);
                byte type = sc.nextByte();
                byte x = sc.nextByte();
                byte y = sc.nextByte();
                byte h = sc.nextByte();
                byte w = sc.nextByte();

                IndexObj idxObj = new IndexObj(type, x, y, h, w);
                arrIdxObj[i] = idxObj;
            }
            br.close();
        } catch (IOException e) {

        }

        return arrIdxObj;
    }

    public static ArrayList<int[][]> readTypePattern(String path) {
        ArrayList<int[][]> lst = new ArrayList<>();

        for (int type = 1; type <= numType; type++) {
            String pathType = path + File.separator + "type" + type + ".txt";
            File file = new File(pathType);
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = br.readLine();

                Scanner sc = new Scanner(line);
                int H = sc.nextInt();
                int W = sc.nextInt();

                int[][] pattern = new int[H][W];
                for (int i = 0; i < H; i++) {
                    line = br.readLine();
                    sc = new Scanner(line);
                    for (int j = 0; j < W; j++) {
                        pattern[i][j] = sc.nextInt();
                    }
                }
                lst.add(pattern);
                br.close();
            } catch (IOException e) {
            }
        }

        return lst;
    }

    public static ArrayList<int[][]> readInputImage(String path) {
        ArrayList<int[][]> lst = new ArrayList<>();

        String pathTrainFace = path + face;
        File fd_face = new File(pathTrainFace);
        File[] files_face = fd_face.listFiles();
        for (File f : files_face) {
            if (f.isFile()) {
                String fileName = f.getName();
                if (fileName.endsWith(".pgm")) {
                    lst.add(ImageProcessing.readPGMImage(pathTrainFace
                            + File.separator + fileName));
                } else if (fileName.endsWith(".png")) {
                    int[][] img = ImageProcessing.readPNGImage(pathTrainFace
                            + File.separator + fileName);
                    lst.add(ImageProcessing.normalizeImage(img));
                }
            }
        }

        String pathTrainNonFace = path + nonFace;
        File fd_nonFace = new File(pathTrainNonFace);
        File[] files_nonFace = fd_nonFace.listFiles();
        for (File f : files_nonFace) {
            if (f.isFile()) {
                String fileName = f.getName();
                if (fileName.endsWith(".pgm")) {
                    lst.add(ImageProcessing.readPGMImage(pathTrainNonFace
                            + File.separator + fileName));
                } else if (fileName.endsWith(".png")) {
                    int[][] img = ImageProcessing.readPNGImage(pathTrainNonFace
                            + File.separator + fileName);
                    lst.add(ImageProcessing.normalizeImage(img));
                }
            }
        }

        return lst;

    }

    static class Obj implements Comparable<Obj> {

        int index;
        double value;
        double weight;

        public Obj(int i, double v, double w) {
            this.index = i;
            this.value = v;
            this.weight = w;
        }

        @Override
        public int compareTo(Obj arg0) {
            return this.value == arg0.value ? 0 : (this.value < arg0.value ? -1 : 1);
        }
    }

    public static WeakClass[] loadWeakClass(String path) {
        WeakClass[] arr = null;

        try {
            FileInputStream fis = new FileInputStream(new File(path));
            ObjectInputStream in = new ObjectInputStream(fis);
            arr = (WeakClass[]) in.readObject();

            in.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return arr;
    }

    public static void saveArrWeakClass(WeakClass[] arr, String path) {
        try {
            FileOutputStream fos = new FileOutputStream(new File(path));
            try (ObjectOutputStream out = new ObjectOutputStream(fos)) {
                out.writeObject(arr);

                out.flush();
            }
        } catch (IOException e) {
        }
    }
}
