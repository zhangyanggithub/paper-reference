package base3times16;
import java.util.ArrayList;

import fundamental.ImageOutPut;
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
