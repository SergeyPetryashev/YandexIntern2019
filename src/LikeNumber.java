/*≈сть любимые числа: 5, 6, 10.
 роме того есть число N, не содержащее нулей. Ќужно сделать так, чтобы оно делилось хот€ бы на одно из его любимых
чисел. ƒл€ этого K раз выбираютс€ две цифры, сто€щие на разных позици€х, и мен€ютс€ местами.
Ќужно найти веро€тность того, что итоговое число будет делитьс€ хот€ бы на одно из любимых чисел*/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

public class LikeNumber {
	public static void main(String[] args) throws IOException {
		//long startTime = System.currentTimeMillis();
		String[] input = getStringFile();
		//String[] input = getString();
		int [] numbers = new int[input[0].length()];
		for(int i=0;i<input[0].length();i++) {
			numbers[i]=Integer.parseInt(input[0].substring(i, i+1));
		}
		int inpK=Integer.parseInt(input[1]);
		long inpNum=Long.parseLong(input[0]);
		
		if(inpK<0 || input[0].length()<1) {
			System.out.printf("%.15f",0.0);
		}
		else if(inpK==0 && (inpNum%5==0 ||inpNum%6==0)) {
			System.out.printf("%.15f",1.0);
		}
		else {	
			double chance5=findChanceForFive(numbers,inpK);
			double chance6=findChanceForSix(numbers,inpK);
			System.out.printf("%.15f",chance5+chance6);
		}
		//System.out.format(Locale.US,"%16.15f\n",chance5+chance6);
		/*System.out.println("на 5");
		System.out.format(Locale.US,"%10.9f\n",chance5);
		System.out.println("на 6");
		System.out.format(Locale.US,"%10.9f\n",chance6);*/
		//long endTime = System.currentTimeMillis();
		//System.out.println(endTime-startTime+" ms");
	}
	//-----------------------------------------------------------
	public static double findChanceForFive(int[] input, int K) {
		int repeatTarget=repeatPrimeNumber(input,5);
		double chance=0.0;
		if(repeatTarget>0) {
			chance=solveChance(input,K,repeatTarget,5);
		}		
		return chance;
	}
	//-----------------------------------------------------------
	public static double findChanceForSix(int[] input, int K) {
		int repeatTarget=repeatPrimeNumber(input,2);
		double chance=0.0;
		if(repeatTarget>0) {
			int sum=0;
			for(int num:input) {
				sum+=num;
			}
			
			if(sum%3==0) {
				chance=solveChance(input,K,repeatTarget,2);			
			}
		}			
		return chance;
	}
	//-----------------------------------------------------------
	public static double solveChance(int[] input, int K, int repeat, int target) {
		int nSize=input.length;
		int endNumber=input[nSize-1];
		double chance=0.0;				
		double chanceBase=(double)(1./(nSize*(nSize-1)/2));//базова€ веро€тность
		double chanceBase2=(double)(nSize-2)/nSize;//(nSize-2)*(nSize-1)/2*chanceBase;
		double chanceBody=chanceBase;
		double chanceTail=chanceBase2;
		
		while(K>1) {
			chanceBody=chanceBody*chanceBase2+(1-chanceBody)*chanceBase;
			chanceTail=chanceTail*chanceBase2+(1-chanceTail)*chanceBase;
			K--;
		}
			
		if(endNumber%target==0) {
			chance=(double)chanceTail+(repeat-1)*chanceBody;
		}
		else {
			chance=(double)repeat*chanceBody;
		}			
		return chance;
	}
	//подсчет количества простых и кратных им цифр в числе
	public static int repeatPrimeNumber(int[] input,int target) {
		int repeat=0;
		for(int num:input) {
			if(num%target==0) {
				repeat++;
			}				
		}		
		return repeat;
	}
	//-----------------------------------------------------------	 
	public static String[] getString() throws IOException {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String[] s = {br.readLine(),br.readLine()};
		return s;
	}
	public static String[] getStringFile() throws IOException {
		File inp= new File("inputD.txt");
		FileReader fr=new FileReader(inp);
		BufferedReader br = new BufferedReader(fr);
		String[] s = {br.readLine(),br.readLine()};
		br.close();
		return s;
	}
}
