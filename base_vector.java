package base3times16;
public class base_vector {
	public static int[][] vector(int n) {
		int[] base = new int[n];
		/**
		 * ���³�ʼ��������
		 */
		int[][] baseVector = new int[(int)(Math.pow(2, n+2)-1)][n];
		for(int i=0; i<base.length;i++){
			base[i] = 3*i;
		}
		base[0] = 1;
		/**
		 * �������������Ӧ��Ȩֵ����
		 */
		return baseVector;
		
	}
	public static void main(String[] args) {
		vector(3);
	}

}
