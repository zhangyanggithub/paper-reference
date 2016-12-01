package base3times16;
import java.util.ArrayList;
public class Others {
	@SuppressWarnings("null")
	public static int[][] changeToBinary(ArrayList<Integer> secretSeq,int radixLen){
		ArrayList<Integer> bianryNum =  new ArrayList<Integer>();
		for (int i = 0; i < radixLen; i++) {
			if (secretSeq.get(i) == 0) {
				bianryNum.add(0);
			}
			while (secretSeq.get(i) != 0) {
				int temp = secretSeq.get(i) % 2;
				bianryNum.add(temp);
				int tempNumi = secretSeq.get(i)/2;
				secretSeq.set(i,tempNumi);
			}
		}
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
		changeToBinary(num,3);
	}
}
