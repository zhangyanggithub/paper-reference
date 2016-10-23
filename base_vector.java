package base3times16;
public class base_vector {
	public static int[][] vector(int n) {
		int[] base = new int[n];
		/**
		 * 以下初始化基向量
		 */
		int[][] baseVector = new int[(int)(Math.pow(2, n+2)-1)][n];
		for(int i=0; i<base.length;i++){
			base[i] = 3*i;
		}
		base[0] = 1;
		/**
		 * 以下求基向量对应的权值向量
		 */
		return baseVector;
		
	}
	public static void main(String[] args) {
		vector(3);
	}

}
