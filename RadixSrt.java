package base3times16;
import java.util.ArrayList;
import java.util.Iterator;
public class RadixSrt {
	public static int[] produceRandomInt() {
		double x0 = 0.3519407329674913, u = 4;
		double temp[] = new double[256*256];
		int[] num = new int[256*256];
		temp[0] = x0;
		for (int i = 1; i < temp.length; i++) {
			temp[i] = u*temp[i-1]*(1-temp[i-1]);
		}
		for (int i = 0; i < temp.length; i++) {
			num[i] = (int) Math.ceil(temp[i]*100000);
		}
		return num;
	}
	public static int[] getBinary(){
		int[] num = produceRandomInt();
		int index = 0;
		int[] binary = new int[200*200];
		for (int i = 0; i < num.length; i++) {
			String string = Integer.toBinaryString(num[i]);
			for (int j = 0; j < string.length(); j++) {
				if(index == binary.length){break;}
				binary[index++] = (int)string.charAt(j)-48;
			}
		}
		return binary;
	}
	public static ArrayList<Integer> getRadix(int radix) throws Exception{//生成radix进制数 
		int p = Integer.toBinaryString(radix*radix-1).length()-1;
		int[] binary = getBinary();
		ArrayList<Integer> srtRadix = new ArrayList<Integer>();
		for (int start = p-1,end = -1;start<binary.length; start += p,end += p) {
			int num = 0;//最终转换为radix进制的数。
			int t = 1;
			for (int i = start; i >end; i--) {//这里将2进制转为十进制
				num = num + binary[i]*t;
				t = t*2;
			}
 			while(num>0)
			{
 				srtRadix.add(num%radix);
				num = num/radix;
			}
		}
	/*	Iterator<Integer> iterator = srtRadix.iterator();
		while (iterator.hasNext()) {
			int type = iterator.next();
			System.out.print(type);	
		}
		System.out.println();
		System.out.println(srtRadix.size());*/
		return srtRadix;
	}
	public static void main(String[] args) throws Exception {
		getRadix(7);
	}
}
