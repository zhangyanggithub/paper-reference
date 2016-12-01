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
		}else if(x == -2){
			baseVectorLen = 10*n-13;
		}else if(x == -3){
			baseVectorLen = 2*n+1;
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
				if(sum < 0){
					sum = baseVectorLen + sum;
				}
				if(sum > 0 && sum < baseVectorLen){
					if(getListSum(baseVector[sum]) == 0){
					baseVector[sum] = p;
					}else{
						if(getListSum(p) < getListSum(baseVector[sum])){
							baseVector[sum] = p;
						}
					}
				}
				
					
			/*if(sum == i){
					if(getListSum(baseVector[i]) == 0){
						baseVector[i] = p;
					}else{
						if(getListSum(p) < getListSum(baseVector[i])){
							baseVector[i] = p;
						}
					}
					
				}*/
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
	public static ArrayList<int[]> getWeightVector16(int n){
		int[] base = getBaseVector16(n);
		int[] weights = {2, -1, 0, 1, 2};
		return vector(n,weights,base,2,0);
	} 
	public static int[] getBaseVector16(int n) {
		int[] base = new int[n];
		for(int i=0; i<n;i++){
			base[i] = 3*i;
		}
		base[0] = 1;
		return base;
	}

	public static ArrayList<int[]> getWeightVector17(int n,int x){
		int[] base = getBaseVector17(n,x);
		int[] weights = new int[2*x+1];
		weights[x] = 0;
		for (int i = 1; i < x+1; i++) {
			weights[x+i] = i;
			weights[x-i] = -1*i;
		}
		
		return vector(n,weights,base,x,0);
	} 
	public static int[] getBaseVector17(int n,int x) {
		int[] base = new int[n];
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
		return base;
	}
	public static ArrayList<int[]> getWeightVector18(int n, int c){
		int[] base = getBaseVector18(n,c);
		int[] weights = new int[c];
		if(c % 2 == 0){
			weights[0] = (int) (1 - 0.5*c);
			for (int i = 1; i < c; i++) {
				weights[i] = weights[i-1] + 1;
			}
		}else{
			weights[c/2] = 0;
			for (int i = 1; i < c/2+1; i++) {
				weights[c/2 + i] = i;
				weights[c/2 - i] = -1*i;
			}
		}
		return vector(n,weights,base,-1,c);
	} 
	public static int[] getBaseVector18(int n,int c) {
		int[] base = new int[n];
		for (int i = 0; i < n; i++) {
			base[i] = (int) Math.pow(c, i);
		}
		return base;
	}
	public static int[] getBaseVector14(int n) {
		int[] base = new int[n];
		base[0] = 1; 
		base[1] = 2;
		for (int i = 2; i < base.length; i++) {
			base[i] = 5*n-9;
		}
		return base;
	}
	public static int[] getBaseVector8(int n) {
		int[] base = new int[n];
		for (int i = 0; i < base.length; i++) {
			base[i] = i+1;
		}
		return base;
	}
	public static ArrayList<int[]> getWeightVector8(int n){
		int[] base = getBaseVector8(n);
		int[] weights = {-1,0,1};
		return vector(n,weights,base,-3,3);
	} 
	public static ArrayList<int[]> getWeightVector14(int n){
		int[] base = getBaseVector14(n);
		int[] weights = {-1,0,1};
		return vector(n,weights,base,-2,3);
	} 
	public static void main(String[] args) {
		getWeightVector8(4);
	}

}
