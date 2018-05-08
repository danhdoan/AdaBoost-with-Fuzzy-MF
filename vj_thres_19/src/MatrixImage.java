public class MatrixImage {
	int rows, cols;
	int[]data;
	/***
	 * Get date ImageMatrix
	 * @return
	 */
	public int[] getData(){
		return this.data;
	}
	/***
	 * getRows Image
	 * @return
	 */
	public int getRows(){
		return this.rows;
	}
	/***
	 * GetCols
	 * @return
	 */
	public int getCols(){
		return this.cols;
	}
	/***
	 * create ImageMatrix from array[][]
	 * @param temp
	 */
	public MatrixImage(int[][]temp){
		int k = 0;
		int []dat = new int[temp.length * temp[0].length];
		
		for(int i=0;i<temp.length;i++){
			for(int j=0;j<temp[0].length;j++){
				dat[k++] = temp[i][j];
			}
		}
		this.rows = temp.length;
		this.cols = temp[0].length;
		
		this.data = dat;
	}
	/***
	 * Get MatrixImage pos(x0, y0), length(i1,j1) from array2[][]
	 * @param i1
	 * @param j1
	 * @param x0
	 * @param y0
	 * @param temp
	 */
	public MatrixImage(int i1, int j1, int x0, int y0, int[][]temp){
		int k = 0;
		int []dat = new int[temp.length * temp[0].length];
		
		for(int i=x0;i<x0 + i1;i++){
			for(int j=y0;j<y0 + j1;j++){
				dat[k++] = temp[i][j];
			}
		}
		this.rows = i1; this.cols = j1;
		this.data = dat;
	}
	/***
	 * Convert image to array2[][] from MatrixImage
	 * @param mtImage
	 * @return
	 */
	public static int[][] convertToMatrix(MatrixImage mtImage){
		int hImage = mtImage.getRows();
		int wImage = mtImage.getCols();
		
		int[][] temp = new int[hImage + 1][wImage + 1];
		int iRow = 1;
		int jCol = 1;
		
		for (int i = 0; i < hImage * wImage; i++) {
			temp[iRow][jCol] = mtImage.getData()[i];
			jCol++;
			if(jCol == wImage + 1){
				jCol = 1;
				iRow++;
			}
		}
		
		return temp;
	}
	/***
	 * create MaxtrixImage from (rows, cols, int[]data)
	 * @param rows
	 * @param cols
	 * @param data
	 */
	public MatrixImage(int rows, int cols, int []data){
		this.rows = rows;
		this.cols = cols;
		this.data = data;
	}
	/***
	 * create MatrixImage from rows, cols
	 * @param rows
	 * @param cols
	 */
	public MatrixImage(int rows, int cols){
		this.rows = rows;
		this.cols = cols;
		this.data = new int[rows * cols];
	}
	/***
	 * getValue (i,j) index
	 * @param i
	 * @param j
	 * @return
	 */
	public int getValue(int i, int j){
		int idx = (i) * cols + j % cols;
		return data[idx];
	}
	/***
	 * setValue Index (i,j)
	 * @param i
	 * @param j
	 * @param v
	 */
	public void setValue(int i, int j, int v){
		int idx = (i) * cols + j % cols;
		data[idx] = v;
	}
	/***
	 * Get Integral Image from MatrixImage
	 * @param mt
	 * @return
	 */
	public static MatrixImage getIntegralImage(MatrixImage mt){
		int r = mt.rows;
		int c = mt.cols;
		MatrixImage integralImage = new MatrixImage(r, c);
		
		for(int i=0;i<r;i++){
			for(int j=0;j<c;j++){
				int value = mt.getValue(i, j);
            	value += i > 0 ? integralImage.getValue(i - 1, j) : 0;
            	value += j > 0 ? integralImage.getValue(i, j - 1) : 0;
            	value -= i > 0 && j > 0 ? integralImage.getValue(i - 1, j - 1) : 0;
            	
            	integralImage.setValue(i, j, value);
			}
		}
		return integralImage;
	}
	
}
