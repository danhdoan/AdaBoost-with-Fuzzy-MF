import java.io.Serializable;

public class WeakClass implements Serializable{
	private static final long serialVersionUID = 1L;
	/***
	 * FeatureIndex, alpha
	 */
	FeatureIndex fi;
	double alpha;
	/**
	 * FeatureIndex fi, double a(alpha)
	 * @param fi
	 * @param a
	 */
	public WeakClass(FeatureIndex fi, double a){
		this.fi = fi;
		this.alpha = a;
	}
}
