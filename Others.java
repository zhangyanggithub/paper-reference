package base3times16;
import java.util.ArrayList;

import fundamental.ImageOutPut;
public class Others {
	public static double  rate; 
	@SuppressWarnings("null")
	public static double getEmbeddedRate(){
		return rate;
	}
	public static int[][] changeToBinary(ArrayList<Integer> secretSeq,int radixLen){
		ArrayList<Integer> bianryNum =  new ArrayList<Integer>();
		ArrayList<Integer> secretSeqCopy = new ArrayList<Integer>();
		for (int i = 0; i < radixLen; i++) {
			secretSeqCopy.add(secretSeq.get(i));
		}
		for (int i = 0; i < radixLen; i++) {
			if (secretSeqCopy.get(i) == 0) {
				bianryNum.add(0);
			}
			while (secretSeqCopy.get(i) != 0) {
				int temp = secretSeqCopy.get(i) % 2;
				bianryNum.add(temp);
				int tempNumi = secretSeqCopy.get(i)/2;
				secretSeqCopy.set(i,tempNumi);
			}
		}
		rate = bianryNum.size()/(200*200);
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
		int[][] arr = changeToBinary(num,3);
		ImageOutPut.printBufferedImage(arr, "aatest");
	}
}
