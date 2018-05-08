import java.io.Serializable;

public class FeatureIndex implements Serializable{
	private static final long serialVersionUID = 1L;

	int index;
	double meanPos, varPos;
	double meanNeg, varNeg;
	double error;
	int []ht;
	double threshold;
	double pj;
	
	public FeatureIndex(int i){
            this.index = i;
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
}
