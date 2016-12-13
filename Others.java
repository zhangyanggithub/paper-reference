package base3times16;
import java.util.ArrayList;

import fundamental.ImageOutPut;
import notContinue.NCTriBasedSeqGenerator;
import sequenceBased.NMlogistics;
public class Others {
	public static double  rate; 
	@SuppressWarnings("null")
	public static double getEmbeddedRate(){
		return rate;
	}
	public static int[][] changeToBinary(ArrayList<Integer> secretSeq,int radixLen,int weightSize){
		ArrayList<Integer> bianryNum =  new ArrayList<Integer>();
		ArrayList<Integer> secretSeqCopy = new ArrayList<Integer>();
		int p = (int) (Math.log(weightSize)/Math.log(2));
		for (int i = 0; i < radixLen; i++) {
			secretSeqCopy.add(secretSeq.get(i));
		}
		int everyRadixBinaryNum = 0;
		for (int i = 0; i < radixLen; i++) {
			if (secretSeqCopy.get(i) == 0) {
				for (int j = 0; j < p; j++) {
					bianryNum.add(0);
				}
				continue;
			}
			while (secretSeqCopy.get(i) != 0) {
				int temp = secretSeqCopy.get(i) % 2;
				bianryNum.add(temp);
				everyRadixBinaryNum++;
				int tempNumi = secretSeqCopy.get(i)/2;
				secretSeqCopy.set(i,tempNumi);
			}
			if(everyRadixBinaryNum != p){
				for (int j = 0; j < p-everyRadixBinaryNum; j++) {
					bianryNum.add(0);
				}
				everyRadixBinaryNum = 0;
			}else{
				everyRadixBinaryNum = 0;
			}
		}
		rate = (10000*bianryNum.size()/(300*300))/100;
		int len = (int) Math.sqrt(bianryNum.size());
		int circle = 0;
		int[][] binaryImg = new int[len][len];
		for (int i1 = 0; i1 < len; i1++) {
			for (int j = 0; j < len; j++) {
				binaryImg[i1][j] = bianryNum.get(circle++);
			}
		}
		return binaryImg;
	}
	public static int[][] changeToBinaryForEMD_N_M_model(int n,int m,int pixelsNum) {
		ArrayList<int[]> weightVector = new ArrayList<int[]>();
		NCTriBasedSeqGenerator.generate(n, m, weightVector);
		ArrayList<Integer> secret = RadixSrt.getRadixForEMD_M_N_model(n,m);
		ArrayList<Integer> srtBinary = new ArrayList<Integer>();
		int radix = weightVector.size();;
		int p = (int) Math.floor(Math.log(radix)/Math.log(2));
		int everyRadixBinaryNum = 0;
		for (int i = 0; i < pixelsNum; i++) {
			if(secret.get(i) == 0){
				for (int j = 0; j < p; j++) {
					srtBinary.add(0);
				}
				continue;
 			}else{
 				int num = secret.get(i);
 				while(num>0)
 				{
 					srtBinary.add(num%2);
 					everyRadixBinaryNum++;
 					num = num/2;
 				}
 				if(everyRadixBinaryNum != p){
 					for (int j = 0; j < p-everyRadixBinaryNum; j++) {
 						srtBinary.add(0);
 					}
 					everyRadixBinaryNum = 0;
 				}else{
 					everyRadixBinaryNum = 0;
 				}
 			}
		}
		rate = (10000*srtBinary.size()/(300*300))/100;
		int len = (int) Math.sqrt(srtBinary.size());
		int circle = 0;
		int[][] binaryImg = new int[len][len];
		for (int i1 = 0; i1 < len; i1++) {
			for (int j = 0; j < len; j++) {
				binaryImg[i1][j] = srtBinary.get(circle++);
			}
		}
		return binaryImg;
	}
	public static int[][] changeToBinaryForEMD_N_M(double x0, double u, int Nmax, int IT, int pixelsNum) {
		ArrayList<Integer> carrierNum = NMlogistics.getN(x0, u, Nmax, IT);
		ArrayList<Integer> maxChange = NMlogistics.getM(x0, u, Nmax, IT);
		ArrayList<int[]> weightVector = new ArrayList<int[]>();
		ArrayList<Integer> secret = RadixSrt.getRadixForEMD_M_N(x0, u, Nmax,IT);
		ArrayList<Integer> srtBinary = new ArrayList<Integer>();
		int p = 0;
		int radix = 0;
		int x = 0;
		for (int i = 0; i < pixelsNum; i++) {
			NCTriBasedSeqGenerator.generate(carrierNum.get(x), maxChange.get(x), weightVector);
			radix = weightVector.size();
			p = (int) Math.floor(Math.log(radix)/Math.log(2));
			if(secret.get(i) == 0){
				for (int j = 0; j < p; j++) {
					srtBinary.add(0);
				}
 			}else{
 				int num = secret.get(i);
 				while(num>0)
 				{
 					srtBinary.add(num%2);
 					num = num/2;
 				}
 			}
			weightVector.clear();
			x++;
		}
		rate = (10000*srtBinary.size()/(300*300))/100;
		int len = (int) Math.sqrt(srtBinary.size());
		int circle = 0;
		int[][] binaryImg = new int[len][len];
		for (int i1 = 0; i1 < len; i1++) {
			for (int j = 0; j < len; j++) {
				binaryImg[i1][j] = srtBinary.get(circle++);
			}
		}
		return binaryImg;
	}
	public static void main(String[] args) {
		ArrayList<Integer> num = new ArrayList<Integer>();
		num.add(0);
		num.add(6);
		num.add(8);
		num.add(13);
		int[][] arr = changeToBinary(num,3,9);
		ImageOutPut.printBufferedImage(arr, "aatest");
	}
	
}
