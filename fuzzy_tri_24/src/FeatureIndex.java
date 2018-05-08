
import java.io.Serializable;

public class FeatureIndex implements Serializable {

    private static final long serialVersionUID = 1L;

    private int index;
    private double meanPos, varPos;
    private double meanNeg, varNeg;
    private double error;
    private int[] ht;
    private double[] membershipPos;
    private double[] membershipNeg;
    private double[] ftValue;
    private double threshold;
    private double pj;
    private int numPos;
    private int numNeg;
    private double minPos;
    private double maxPos;
    private double minNeg;
    private double maxNeg;

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

    public void setMembershipPos(double[] arr) {
        this.membershipPos = arr;
    }

    public double[] getMembershipPos() {
        return this.membershipPos;
    }

    public void setMembershipNeg(double[] arr) {
        this.membershipNeg = arr;
    }

    public double[] getMembershipNeg() {
        return this.membershipNeg;
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

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public double getPj() {
        return pj;
    }

    public void setPj(double pj) {
        this.pj = pj;
    }

    public double[] getFtValue() {
        return ftValue;
    }

    public void setFtValue(double[] ftValue) {
        this.ftValue = ftValue;
    }

    public double getMinPos() {
        return minPos;
    }

    public void setMinPos(double minPos) {
        this.minPos = minPos;
    }

    public double getMaxPos() {
        return maxPos;
    }

    public void setMaxPos(double maxPos) {
        this.maxPos = maxPos;
    }

    public double getMinNeg() {
        return minNeg;
    }

    public void setMinNeg(double minNeg) {
        this.minNeg = minNeg;
    }

    public double getMaxNeg() {
        return maxNeg;
    }

    public void setMaxNeg(double maxNeg) {
        this.maxNeg = maxNeg;
    }

}
