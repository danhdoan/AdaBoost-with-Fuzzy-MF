
import java.io.Serializable;

public class FeatureIndex implements Serializable {

    static final long serialVersionUID = 1L;

    int index;
    double meanPos, varPos;
    double meanNeg, varNeg;
    double error;
    int[] ht;
    double[] ftValue;
    int numPos;
    int numNeg;

    double gamma;

    public FeatureIndex(int i) {
        this.index = i;
    }

    public void setNumPos(int num) {
        this.numPos = num;
    }

    public int getNumPos() {
        return this.numPos;
    }

    public void setNumNeg(int num) {
        this.numNeg = num;
    }

    public int getNumNeg() {
        return this.numNeg;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public double getMeanPos() {
        return meanPos;
    }

    public void setFeatureValue(double[] arr) {
        this.ftValue = arr;
    }

    public double[] getFeatureValue() {
        return this.ftValue;
    }

    public void setMeanPos(double meanPos) {
        this.meanPos = meanPos;
    }

    public double getVarPos() {
        return varPos;
    }

    public void setVarPos(double varPos) {
        this.varPos = varPos;
    }

    public double getMeanNeg() {
        return meanNeg;
    }

    public void setMeanNeg(double meanNeg) {
        this.meanNeg = meanNeg;
    }

    public double getVarNeg() {
        return varNeg;
    }

    public void setVarNeg(double varNeg) {
        this.varNeg = varNeg;
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }

    public int[] getHt() {
        return ht;
    }

    public void setHt(int[] ht) {
        this.ht = ht;
    }

    public double[] getFtValue() {
        return ftValue;
    }

    public void setFtValue(double[] ftValue) {
        this.ftValue = ftValue;
    }

    public double getGamma() {
        return gamma;
    }

    public void setGamma(double gamma) {
        this.gamma = gamma;
    }

    
}
