/*Есть любимые числа: 5, 6, 10.
Кроме того есть число N, не содержащее нулей. Нужно сделать так, чтобы оно делилось хотя бы на одно из его любимых
чисел. Для этого K раз выбираются две цифры, стоящие на разных позициях, и меняются местами.
Нужно найти вероятность того, что итоговое число будет делиться хотя бы на одно из любимых чисел*/
//package taskD;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

public class LikeNumber2 {
	static int LIKE_NUM1=5;
	static int LIKE_NUM2=6;

	public static void main(String[] args) throws IOException {
		//long startTime = System.currentTimeMillis();
		String[] input = getStringFile();		
		//long inpN=Long.parseLong(input[0]);
		int inpK=Integer.parseInt(input[1]);
		double chanceFive=calсulateChanceFive(input[0],inpK);
		double chanceSix=calсulateChanceSix(input[0],inpK);
		
		//System.out.println("ответ");
		System.out.format(Locale.US,"%16.15f",chanceFive+chanceSix);
		/*System.out.println();
		System.out.println("на 5");
		System.out.format(Locale.US,"%16.15f",chanceFive);
		System.out.println();
		System.out.println("на 6");
		System.out.format(Locale.US,"%16.15f",chanceSix);
		System.out.println();*/
		
		//long endTime = System.currentTimeMillis();		
		//System.out.println(endTime-startTime+" ms");
		/*for(int i=1;i<5;i++) {
			System.out.println(triangleNumber(6,i));
		}*/		
	}
	//-----------------------------------------------------------
	//вычисление количества возможных комбинаций
	public static long triangleNumber(int nSize, int K) {
		long res=0;
		if(K==0) {
			res=1;
		}
		else if(K>=nSize-1) {
			res=factorial(nSize)/2;
		}
		else {
			res=triangleNumber(nSize-1,K)+(nSize-1)*triangleNumber(nSize-1,K-1);
		}		
		return res;
	}	
	//-----------------------------------------------------------
	//подсчет количества цифры 5 в числе
	public static int fiveN(String input) {
		int res=0;
		for(int i=0;i<input.length();i++) {
			if(input.charAt(i)=='5') {
				res++;
			}				
		}		
		return res;
	}
	//подсчет четных цифр в числе
	public static int evenN(String input) {
		int res=0;
		for(int i=0;i<input.length();i++) {
			if(Integer.parseInt(input.substring(i, i+1))%2==0) {
				res++;
			}				
		}		
		return res;
	}
	//-----------------------------------------------------------
	public static long factorial(int number) {
		long result=1;
		while(number>1) {
			result=result*number--;			
		}			
		return result;
	}
	//-----------------------------------------------------------
	public static double calсulateChanceFive(String input, int K) {
		int nSize=input.length();
		int five=fiveN(input);
		long nComb;
		double chance=0.0;				
		if(nSize==2) {
			if(fiveEnd(input)) {
				if(K%2==0 || five==2)
					chance=1.0;
				else
					chance=0.0;
			}
			else {
				if(K%2==0 || five==0)
					chance=0.0;
				else
					chance=1.0;				
			}
		}
		else {
			nComb=triangleNumber(nSize,K);		
			if(fiveEnd(input)) {
				chance=(double)(five-1)*triangleNumber(nSize-1,K-1)/nComb+
						(double)triangleNumber(nSize-1,K)/nComb;
			}
			else {
				chance=(double)(five)*triangleNumber(nSize-1,K-1)/nComb;
			}
		}
		return chance;
	}
	//-----------------------------------------------------------
	public static double calсulateChanceSix(String input, int K) {
		int nSize=input.length();
		int even=evenN(input);
		long nComb;
		double chance=0.0;
		int sum=0;
		for(int i=0;i<nSize;i++) {
			sum+=Integer.parseInt(input.substring(i, i+1));
		}
		
		if(sum%3==0) {
			if(nSize==2) {
				if(evenEnd(input)) {
					if(K%2==0 || even==2)
						chance=1.0;
					else
						chance=0.0;
				}
				else {
					if(K%2==0|| even==0)
						chance=0.0;
					else
						chance=1.0;				
				}
			}
			else {
				nComb=triangleNumber(nSize,K);
				long nCombCentre=triangleNumber(nSize-1,K-1);//кол-во комбинаций с числом в центре
				if(evenEnd(input)) {
					chance=(double)(even-1)*nCombCentre/nComb+
							(double)triangleNumber(nSize-1,K)/nComb;
				}
				else {
					chance=(double)(even)*nCombCentre/nComb;
				}
			}
		}			
		return chance;
	}
	//-----------------------------------------------------------
	public static double calсulateChance(String input, int K, int repeat) {
		int nSize=input.length();
		int five=fiveN(input);
		long nComb;
		double chance=0.0;				
		if(nSize==2) {
			if(fiveEnd(input)) {
				if(K%2==0 || five==2)
					chance=1.0;
				else
					chance=0.0;
			}
			else {
				if(K%2==0 || five==0)
					chance=0.0;
				else
					chance=1.0;				
			}
		}
		else {
			nComb=triangleNumber(nSize,K);		
			if(fiveEnd(input)) {
				chance=(double)(five-1)*triangleNumber(nSize-1,K-1)/nComb+
						(double)triangleNumber(nSize-1,K)/nComb;
			}
			else {
				chance=(double)(five)*triangleNumber(nSize-1,K-1)/nComb;
			}
		}
		return chance;
	}
	//-----------------------------------------------------------
	public static boolean fiveEnd(String input) {
		if(input.charAt(input.length()-1)=='5')
			return true;
		else
			return false;
	}
	//-----------------------------------------------------------
	public static boolean evenEnd(String input) {
		String end=input.substring(input.length()-1);
		if(Integer.parseInt(end)%2==0)
			return true;
		else
			return false;
	}	
	//-----------------------------------------------------------	 
	public static String getString() throws IOException {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String s = br.readLine();
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
