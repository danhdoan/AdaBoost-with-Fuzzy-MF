###Analysis of Face Detection by AdaBoost algorithm using Fuzzy Membership###

NetBean project:

	- fuzzy_gauss_19: implementation of AdaBoost with Gaussian Fuzzy MF on MIT cbcl dataset
		- 20180125_fuzzy_gauss1_19.wk
		- 20180125_fuzzy_gauss2_19.wk
		- 20180125_fuzzy_gauss3_19.wk

	- fuzzy_gauss_24: implementation of AdaBoost with Gaussian Fuzzy MF on Yi-qing dataset
		- 20180125_fuzzy_gauss1_24.wk
		- 20180125_fuzzy_gauss3_24.wk

	- fuzzy_tri_19: implementation of AdaBoost with Triangular Fuzzy MF on MIT cbcl dataset
		- 20180117_fuzzy_tri_std1_19.wk
		- 20180118_fuzzy_tri_std15_19.wk
		- 20180116_fuzzy_tri_std2_19.wk
		- 20180118_fuzzy_tri_std25_19.wk
		- 20180118_fuzzy_tri_std3_19.wk

	- fuzzy_tri_24: implementation of AdaBoost with Triangular Fuzzy MF on Yi-qing dataset
		- 20180119_fuzzy_tri_std1_24.wk
		- 20180119_fuzzy_tri_std15_24.wk
		- 20180117_fuzzy_tri_std2_24.wk
		- 20180119_fuzzy_tri_std25_24.wk
		- 20180119_fuzzy_tri_std3_24.wk

	- vj_thres_19: implementation of AdaBoost with Viola-Jones method on MIT cbcl dataset
		- 20171025_vj_5000_19.wk

	- vj_thres_24: implementation of AdaBoost with Viola-Jones method on Yi-qing dataset
		- 20171025_vj_24.wk

	Each Strongclass (.wk file) mentioned above consists of 200 Weakclass 

##Program structure:##

main
|__ trainingProcess
|__ testingProcess

#Training Process:#
	trainProcess
	|__ readInputImage
	|__ calculateIntegralImage
	|__ readTypePattern
	|__ readIndex
	|__ algorithmAdaboost
	|__ saveArrWeakClass

Functions:

	ArrayList<int[][]> readInputImage(String path)
		Parameter:
			- path: directory path to get input images
		Usage:
			- Read and pre-process input image
		Step:
			- Read images from given path
			- Convert to Grayscale (if using Yi-qing dataset with PNG image)
			- Normalize image
			- Return an ArrayList

	ArrayList<int[][]> calculateIntegralImage(ArrayList<int[][]> lstImg)
		Parameter:
			- lstImg: ArrayList of input image
		Usage:
			- Compute integral images
		Step:
			- Get Integral images and store in an ArrayList

	ArrayList<int[][]> readTypePattern(String path)
		Parameter:
			- path: directory path of color map
		Usage:
			- Read color map for each Haar-like pattern

	IndexObj[] readIndex(String path)
		Parameter:
			- path: directory path of feature index
		Usage:
			- Read all feature index from Lookup table

	WeakClass[] algorithmAdaboost()
		Usage:
			- Train Strongclass from given Integral images and Haar-like pattern
			- Each WeakClass is chosen from 5000 random features
		Step:
			- Initialize Weight for Face and Non-face samples
			- For each round:
				- Normalize Weights
				- Get Feature with smallest error
				- Calculate Alpha
				- Update Weights

	FeatureIndex getFeatureIndex_weights(double[] weights, int numPosTrain, int numNegTrain)
		Parameter:
			- weights: Weights of all face and non-face samples
			- numPosTrain: number of face samples
			- numNegTrain: number of non-face samples
		Usage:
			- Choose the Feature that yields the smallest error rate with given Weights for Face and Non-face
		Step:
			- Get K (=5000) random features
			- For each feature:
				- Get feature values for the whole set of samples
				- Calculate mean, variance, (standard deviation if Triangular Fuzzy MF)  for each distribution
				- Calculate membership or compare for classification
				- Compute error to choose feature producing least error rate

	Boolean isPositiveDist(double meanPos, double varPos, double meanNeg, double varNeg,         double val, double gamma)
		Parameter:
			- meanPos, varPos: mean and variance of Face distribution
			- meanNeg, varNeg: mean and variance of Non-face distribution
		Usage:
			- Compare density function to Face and Non-face distribution without directly calculating the function value
		Step:
			- Just simplify ratio of 2 density function formulas
		Note: only use for Gaussian Fuzzy MF

	double getMembershipTriangle(double min, double mean, double max, double val)
		Parametere:
			- min, max: minimum and maximum values of distribution
			- mean: mean of distribution
			- val: value to find the corresponding membership
		Usage:
			- Calculate membership of a value to a Triangular Fuzzy distribution
		Step:
			- Just apply Fuzzy MF formula
		Note: only use for Triangular Fuzzy MF

#Test Process:
	trainProcess
	|__ readInputImage
	|__ calculateIntegralImage
	|__ readTypePattern
	|__ readIndex
	|__ loadWeakClass
	|__ testData

Functions:

	void testData(String fileTestInfo, double rate)
		Parameter:
			- fileTestInfo: file that contains information about the number of face and non-face for testing
			- rate: rate used for testing (instead of using 0.5 as in origin AdaBoost algorihtm)
		Usage:
			- Test StrongClass with given number of WeakClass and rate to find TPR, FPR
		Step:
			- For each test sample:
				- For each Weakclass
					- Calculate feature value
					- Calculate membership to classify
				- Classify face or non-face













