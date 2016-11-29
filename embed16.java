package base3times16;
import java.util.ArrayList;
import fundamental.ImageImport;
import fundamental.ImageOutPut;
import fundamental.PSNR;
import notContinue.NCTriBasedSeqGenerator;
import sequenceBased.NMlogistics;
/*
 * 文献8 EMD，x=-3
 * 文献14，EMD-2 x=-2
 * 文献15 EMD-n 
 * 文献16，关于n和x=2
 * 文献17，关于n和x 为x赋值
 * 文献18，关于n和c，x=-1
 */
public class embed16 {
	public static int embedNum = 0;
	public static int fullEmbNum = 0;
	public static int secretRadixNum = 0;
	public static ArrayList<Integer> extractSecret(int[][] barrier,int n,int weightLength,int[] baseVector){
		ArrayList<Integer> extractRadix = new ArrayList<Integer>();
		int t = 0;
		int sum = 0;
		int embedCount = 0;
		for (int i = 0; i < barrier.length; i++) {
			for (int j = 0; j < barrier[0].length; j++) {
				if(embedCount > embedNum){
					break;
				}
				sum += barrier[i][j]*baseVector[t];
				t++;
				if(t == n){
					t = 0;
					extractRadix.add(sum%weightLength);
					sum = 0;
				}
			}
		}
		return extractRadix;
	}
	public static int embedSecretNum(int n,int[][] GrayArr,int secretRadixNum){
		fullEmbNum = (GrayArr[0].length * GrayArr.length)/n;
		if(fullEmbNum >= secretRadixNum){
			embedNum = secretRadixNum;
		}else{
			embedNum = fullEmbNum;
		} 
return embedNum;
	}
	public static int[][] EMD_n(int n,int[][] GrayArr) throws Exception {
		int[][] srtArr  = new int[GrayArr[0].length][GrayArr.length];
		int leftGrayArray = GrayArr.length * GrayArr[0].length % n;
		int[] base = new int[n];
		int p = (int)Math.pow(3, n);//能存p进制数
		ArrayList<Integer> secret = RadixSrt.getRadix(p);
		int secretRadixNum = secret.size();
		embedSecretNum(n,GrayArr,secretRadixNum);
		for (int i = 0; i < base.length; i++) {
	    	base[i] = (int)Math.pow(3, i);
		}
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
		secretRadixNum = secret.size();
		embedSecretNum(n,GrayArr,secretRadixNum);
		int leftGrayArray = GrayArr.length * GrayArr[0].length % n;
		for (int i = 0; i < GrayArr.length; i++) {
			for (int j = 0; j < GrayArr[0].length; j++) {
				if (t == n) {
					t = 0;
					left = sum % weightSize;
					if (scretIndex > secretSize-1) return srtArr;
					int secretDetail  = secret.get(scretIndex++);
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
	public static void preference() throws Exception{
		String pathName = "EMDimgFrom";
		int n = 10;
		int x = 3;
		int prefrence = 17;
		int c = 3;
		String imgRealName = null;
		ArrayList<int[]> weightVector = base_vector.getWeightVector17(n,x);
		int[] baseVector = base_vector.getBaseVector17(n,x);
		int weightSize = weightVector.size();
		ArrayList<Integer> secret = RadixSrt.getRadix(weightSize);
		int imgType;
		int[][][] carrier = new int[4][][];
		int[][][] barrier = new int[4][][];
		String[] imgName = {"Man","Lena","Women","Lake"};
		secretRadixNum = secret.size();
		System.out.println("所有秘密信息数量："+secretRadixNum);
		System.out.println("当n="+n+"时，文献"+prefrence+"嵌入"+weightSize+"进制秘密信息情况");
		if(prefrence == 17){
			imgRealName = prefrence+" n="+n+" x="+x;
		}else if(prefrence == 18){
			imgRealName = prefrence+" n="+n+" x="+x+" c="+c;
		}else{
			imgRealName = prefrence+" n="+n;
		}
		for (int i = 0; i < carrier.length; i++) {
			carrier[i] = ImageImport.imageimport(imgName[i],pathName);
			imgType =  ImageImport.getImageType();
			if(prefrence == 15){
				barrier[i] = EMD_n(n, carrier[i]);
			}else{
				barrier[i] = embed(weightVector,baseVector,n,carrier[i]);
				extractSecret(barrier[i],n,weightVector.size(),baseVector);
			}
			ImageOutPut.OutPut2(barrier[i], imgName[i]+imgRealName,imgType);
			PSNR.psnr(carrier[i], barrier[i], prefrence, n, imgName[i], x, c);
		}
		System.out.println("可嵌入秘密信息最大量："+fullEmbNum);
		System.out.println("实际嵌入数量："+embedNum);
	}
	public static int[][] embedForEMD_M_N(double x0, double u, int Nmax, int IT,int[][] GrayArr) {
		ArrayList<Integer> carrierNum = NMlogistics.getN(x0, u, Nmax, IT);
		ArrayList<Integer> maxChange = NMlogistics.getM(x0, u, Nmax, IT);
		ArrayList<int[]> weightVector = new ArrayList<int[]>();
		ArrayList<Integer> secret = RadixSrt.getRadixForEMD_M_N(0.3519407329674913, 4, 6,100);
		int[][] srtArr  = new int[GrayArr[0].length][GrayArr.length];
		int secretSize = secret.size();
		int mnIndex = 0;
		int scretIndex = 0;//控制秘密信息数组。
		int i = 0;
		int j = 0;
		System.out.println("size:"+secretSize);
		for (; i < GrayArr.length; i++) {
			for (; j < GrayArr[0].length;) {
				if(scretIndex > secretSize-1){
					return srtArr;
				}
				if (i == GrayArr.length) {
					return srtArr;
				}
				NCTriBasedSeqGenerator.generate(carrierNum.get(mnIndex), maxChange.get(mnIndex), weightVector);
				int[] tempWeight = weightVector.get(secret.get(scretIndex));
				int r = 0;// 标记赋值内部序号
				do {
						if (i == GrayArr.length) {
						return srtArr;
					}
					int g = GrayArr[i][j];
					srtArr[i][j] = GrayArr[i][j] + tempWeight[r++];
					int s = srtArr[i][j];
					if (srtArr[i][j] == -1) {
						srtArr[i][j] = 2;
					}
					if (srtArr[i][j] == 256) {
						srtArr[i][j] = 253;
					}
					j++;
					if (j == GrayArr[0].length) {
						i++;
						j = 0;
					}
				} while (r != carrierNum.get(mnIndex));
				scretIndex++;
				mnIndex++;
				weightVector.clear();
				continue;
			}
		}
		return srtArr;
	}
	 public static void main(String[] args) throws Exception {
		 String pathName = "EMDimgFrom";
		 String imgName = "Man";
		 int[][] carrier = ImageImport.imageimport(imgName,pathName);
		 embedForEMD_M_N(0.3519407329674913, 4.0, 6,100,carrier);
	}

}
