package base3times16;
import java.util.ArrayList;
import java.util.Iterator;
public class base_vector {
private static ArrayList<int[]> allSeq = new ArrayList<int[]>();
private static ArrayList<int[]> baseArrayList = new ArrayList<int[]>();
	private static int getListSum(int arr[]){
		int sum = 0;
		for (int i = 0; i < arr.length; i++) {
			sum += Math.abs(arr[i]);
		}
		return sum;
	}	
	private static int[] toSequence(int baseLength, int num,int[] weights)
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
	public static void getAllSeq(int baseLen,int maxNum,int[] weights){
		for (int i = 0; i < maxNum+1; i++) {
			allSeq.add(toSequence(baseLen,i,weights));
		}
	}
	public static ArrayList<int[]> vector(int n,int[] weights,int[] base,int x,int c) {
		int baseVectorLen;
		int[][] baseVector;
		// 以下求所有可能的序列
		getAllSeq(n,(int)Math.pow(weights.length, n)-1,weights);
		int len;
		if(x == -1){
			baseVectorLen = (int) Math.pow(c, n);
		}else{
			baseVectorLen = (int)(Math.pow(2, n+x)-1);
		}
		baseVector= new int[baseVectorLen][n];
		if(x == -1){
			len = baseVectorLen;
		}else{
			len = baseVector.length/2+1;
		}
		for(int i=1; i<len; i++){
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
		if(x != -1){
			int k = 1;
			for(int i = baseVector.length/2+1; i<baseVector.length; i++){
				for (int j = 0; j < baseVector[0].length; j++) {
					baseVector[i][j] = -1*baseVector[i-k][j];
				}
				k += 2;
			}
		}else{
			int maxChange = (int) Math.ceil((c-1)*0.5);
			int repeat = 0;
			for(int i = baseVector.length/2+1; i<baseVector.length; i++){
				if(getListSum(baseVector[i]) == maxChange*n){
					repeat = i;
					break;
				}
			}  
			for (int i = repeat+1; i < baseVector.length; i++) {
				for (int j = 0; j < baseVector[0].length; j++) {
					baseVector[i][j] = -1*baseVector[baseVectorLen - i][j];
				}
			}
		}
		for (int i = 0; i < baseVector.length; i++) {
			baseArrayList.add(baseVector[i]);
		}
		return baseArrayList;
	}
	public static ArrayList<int[]> preference16(int n){
		int[] base = new int[n];
		/**
		 * 以下初始化基向量
		 */
		for(int i=0; i<n;i++){
			base[i] = 3*i;
		}
		
		base[0] = 1;
		int[] weights = {2, -1, 0, 1, 2};
		return vector(n,weights,base,2,0);
	} 
	public static ArrayList<int[]> preference17(int x,int n){
		int[] base = new int[n];
		/**
		 * 以下初始化基向量
		 */
		if(x>2){
				for(int i=1; i<n;i++){
				base[i] = 6*i;
			}
		}
		if(x == 2){
				for(int i=1; i<n;i++){
				base[i] = 3*i;
			}
		}
		base[0] = 1;
		int[] weights = new int[2*x+1];
		weights[x] = 0;
		for (int i = 1; i < x+1; i++) {
			weights[x+i] = i;
			weights[x-i] = -1*i;
		}
		
		return vector(n,weights,base,x,0);
	} 
	public static ArrayList<int[]> preference18(int n, int c){
		int[] base = new int[n];
		/**
		 * 以下初始化基向量
		 */
		for (int i = 0; i < n; i++) {
			base[i] = (int) Math.pow(c, i);
		}
		int[] weights = new int[c];
		if(c % 2 == 0){
			weights[0] = (int) (1 - 0.5*c);
			for (int i = 1; i < c; i++) {
				weights[i] = weights[i-1] + 1;
			}
		}else{
			weights[c/2] = 0;
			for (int i = 1; i < c+1; i++) {
				weights[c/2 + i] = i;
				weights[c/2 - i] = -1*i;
			}
		}
		return vector(n,weights,base,-1,c);
	} 
	public static void main(String[] args) {
		preference17(3,4);
	}

}
