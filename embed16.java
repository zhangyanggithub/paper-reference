package base3times16;
import java.util.ArrayList;
import fundamental.ImageImport;
import fundamental.ImageOutPut;
public class embed16 {
	public static int[][] EMD_n(int n,int[][] GrayArr) throws Exception {
		int[][] srtArr  = new int[GrayArr[0].length][GrayArr.length];
		int leftGrayArray = GrayArr.length * GrayArr[0].length % n;
		int[] base = new int[n];
		int p = (int)Math.pow(3, n);//能存p进制数
	    for (int i = 0; i < base.length; i++) {
	    	base[i] = (int)Math.pow(3, i);
		}
	    ArrayList<Integer> secret = RadixSrt.getRadix(p);
	    int scretIndex = 0;//控制秘密信息数组。
		int t = 0;//若t为n，则赋值0，再取n个数。
		int sum =  0;//标记n个数的和。
		int left =  0;//sum%3的n次方
		for (int i = 0; i < GrayArr.length; i++) {
			for (int j = 0; j < GrayArr[0].length; j++) {
				if (t == n)
				{
					int w = 0;
					int a = i;
					t = 0;
					int secretDetail  = secret.get(scretIndex);
					left = sum % (int)Math.pow(3, n);
					int s = secretDetail - left;
					if (s>0)   s = s%(int)Math.pow(3, n);
					if(s<0)    s = s+(int)Math.pow(3, n);
					if (s == 0) {
						for (int k = j-1,h = n;h >0 ; k--,h--) {
							if(k<0){
								k = GrayArr[0].length-1;
								a = i-1;
							}
							int GrayArrak = GrayArr[a][k];
							int srtArrak = srtArr[a][k];
							srtArr[a][k] = GrayArr[a][k];
						 }
						j--;
						sum = 0;
						continue;
						
					}
					
					for (int k = j-1,h = n;h >0 ; k--,h--) {
						 if(k<0){
							k = GrayArr[0].length-1;
							a = i-1;
						}
						boolean boo = Judgesi(s,h);
						if (Judgesi(s,h) == true) {
							if (F4(s,h) == 0)
								srtArr[a][k] = GrayArr[a][k] + 1;
							if (F4(s,h) == 1) 
								srtArr[a][k] = GrayArr[a][k] - 1;
							if(F4(s,h) != 1&&F4(s,h) != 0)
								srtArr[a][k] = GrayArr[a][k];
							
						}else if (Judgesi(s,h) == false) {
							srtArr[a][k] = GrayArr[a][k];
						}
							
						
						if (srtArr[a][k] >255) 
							srtArr[a][k] = 255;
						
						if (srtArr[a][k] <0) 
							srtArr[a][k] = 0;
					}	
					sum = 0;
				}
				sum += GrayArr[i][j]*base[t];
				t++;
			}
		}
		if(leftGrayArray != 0){
			int j = GrayArr[0].length-1;
			for(int i = 0; i < leftGrayArray;i++){
				srtArr[GrayArr.length-1][j-i] = GrayArr[GrayArr.length-1][j-i];
			}
		}
		return srtArr;
	}
	public static int F4(int s,int i)
	{
		int f4 = 0;
		int b = (int) Math.pow(3, i-1);
		f4 = ((int) Math.floor((s+((1-b)/2)-1)/b))%3;
		
		return f4;
	}
	public static boolean Judgesi(int s,int i)
	{
		int b = ((int) Math.pow(3, i-1)-1)/2;
		if (s>b) return true;
		else return false;
		
	}
	public static int[][] embed(ArrayList<int[]> weightVector,int[] baseVector,int n,int[][] GrayArr) throws Exception{
		int[][] srtArr  = new int[GrayArr[0].length][GrayArr.length];
		int weightSize = weightVector.size();
		ArrayList<Integer> secret = RadixSrt.getRadix(weightSize);
		int secretSize = secret.size();
		int scretIndex = 0;//控制秘密信息数组。
		int t = 0;//若t为n，则赋值0，再取n个数。
		int sum =  0;//标记n个数的和。
		int left =  0;//sum%weightSize
		int leftGrayArray = GrayArr.length * GrayArr[0].length % n;
		for (int i = 0; i < GrayArr.length; i++) {
			for (int j = 0; j < GrayArr[0].length; j++) {
				if (t == n) {
					t = 0;
					left = sum % weightSize;
					if (scretIndex > secretSize) return srtArr;
					int secretDetail  = secret.get(scretIndex);
					int diff = secretDetail - left;
					if(diff < 0) diff = weightSize + diff;
					int s = 0;
					int a = i;
					for(int k = j-1,h = n-1;h >-1 ; k--,h--) {
						if (s == n-1)  
						{	if(k<0){
								k = GrayArr[0].length-1;
								a = i-1;
							}
							srtArr[a][k] = GrayArr[a][k] + weightVector.get(diff)[h];
							if (srtArr[a][k] >255) 
								srtArr[a][k] = 255;
							if (srtArr[a][k] <0) 
								srtArr[a][k] = 0;
							sum = 0;
							break;
						}
						if(k<0){
							k = GrayArr[0].length-1;
							a = i-1;
						}
					    srtArr[a][k] = GrayArr[a][k] + weightVector.get(diff)[h];
						if (srtArr[a][k] >255) 
							srtArr[a][k] = 255;
						if (srtArr[a][k] <0) 
							srtArr[a][k] = 0;
						s++;
					}
				}
				sum += GrayArr[i][j]*baseVector[t];
				t++;
			}
		}
		if(leftGrayArray != 0){
			int j = GrayArr[0].length-1;
			for(int i = 0; i < leftGrayArray;i++){
				srtArr[GrayArr.length-1][j-i] = GrayArr[GrayArr.length-1][j-i];
			}
		}
		return srtArr;
	}
	public static void main(String[] args) throws Exception {
		int n = 5;
		int x = 3;
		int prefrence = 8;
		int c = 4;
		int imgType;
		int[][][] carrier = new int[4][][];
		int[][][] barrier = new int[4][][];
		String[] imgName = {"Man","Lena","Women","Lake"};
		ArrayList<int[]> weightVector = base_vector.getWeightVector8(n);
		int[] baseVector = base_vector.getBaseVector8(n);
		for (int i = 0; i < carrier.length; i++) {
			carrier[i] = ImageImport.imageimport(imgName[i]);
			imgType =  ImageImport.getImageType();
			if(prefrence == 15){
				barrier[i] = EMD_n(n, carrier[i]);
			}else{
				barrier[i] = embed(weightVector,baseVector,n,carrier[i]);
			}
			ImageOutPut.OutPut2(barrier[i], imgName[i]+prefrence+" n="+n,imgType);
		}
	}

}
