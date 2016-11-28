package base3times16;
import java.util.ArrayList;
import java.util.Iterator;
public class base_vector {
private static int[] weights = {-2, -1, 0, 1, 2};
private static ArrayList<int[]> allSeq = new ArrayList<int[]>();
private static ArrayList<int[]> baseArrayList = new ArrayList<int[]>();
	private static int getListSum(int arr[]){
		int sum = 0;
		for (int i = 0; i < arr.length; i++) {
			sum += Math.abs(arr[i]);
		}
		return sum;
	}	
	private static int[] toSequence(int baseLength, int num)
	{
		int len = weights.length; 
		int[] res = new int[baseLength];
		for(int i = baseLength-1; i > -1; --i)
		{
			res[i] = weights[num % len];
			num = num / len;
		}
		return res;
	}
	public static void getAllSeq(int baseLen,int maxNum){
		for (int i = 0; i < maxNum+1; i++) {
			allSeq.add(toSequence(baseLen,i));
		}
	}
	public static ArrayList<int[]> vector(int n) {
		int[] base = new int[n];
		
		/**
		 * 以下初始化基向量
		 */
		for(int i=0; i<n;i++){
			base[i] = 3*i;
		}
		
		base[0] = 1;
		/**
		 * 以下求基向量对应的权值向量，eg:n=3,base=[1,3,6]
		 */
		
		int[][] baseVector = new int[(int)(Math.pow(2, n+2)-1)][n];
		
		/**
		 * 以下求所有可能的0 -1 1序列
		 */
		getAllSeq(n,(int)Math.pow(weights.length, n)-1);
		
		/**
		 * 测试输出所有可能的0 -1 1序列
		 */
		for(int i=1; i<baseVector.length/2+1;i++){
			Iterator it = allSeq.iterator();
			while (it.hasNext()) {
				int p[] = (int[]) it.next();
				int sum = 0;
				for (int j = 0; j < p.length; j++) {
					sum += p[j]*base[j];
				}
				if(sum == i){
					if(getListSum(baseVector[i]) == 0){
						baseVector[i] = p;
					}else{
						if(getListSum(p) < getListSum(baseVector[i])){
							baseVector[i] = p;
						}
					}
					
				}
			}
		}
		int k = 1;
		for(int i = baseVector.length/2+1; i<baseVector.length; i++){
			for (int j = 0; j < baseVector[0].length; j++) {
				baseVector[i][j] = -1*baseVector[i-k][j];
			}
			k += 2;
		}
		for (int i = 0; i < baseVector.length; i++) {
			int[] p = baseVector[i];
			baseArrayList.add(p);
		}
		return baseArrayList;
		
	}
	public static void main(String[] args) {
		vector(3);
	}

}
