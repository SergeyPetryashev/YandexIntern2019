import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/* There is a number N, not containing zeros. Do K times the following: 
 * select two numbers that are in different positions, and swap them.
 * Find the probability that the final number will be divided by at least 
 * one of the numbers 5, 6, 10
 */
// алгоритм не готов
public class TaskDLikeNumber2 {

	public static void main(String[] args) throws IOException {
        File file = new File("inputD.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        int number = Integer.parseInt(br.readLine());
        int numShift = Integer.parseInt(br.readLine());
        int [] numbers = didgitNumb(number);
        int summ=0;
        int numFive = 0, numTwo = 0;
        
        for(int e : numbers) {
        	summ+=e;
        	if(e==5) {
        		numFive++;
        	}
        	if(e%2==0) {
        		numTwo++;
        	}
        }
        double res = 0;
        double allComb=0.0;
        double success=0.0;
        double notSuccess=0.0;
        allComb=factCn2(numbers.length);
        
        if(summ%3==0 && numTwo!=0) {
        	notSuccess=factorial(numbers.length-numTwo);
        	res+=(allComb-numTwo)/allComb;
        }
        if(numFive!=0){
        	notSuccess=factorial(numbers.length-numFive);        	
        	res+=(allComb-numFive)/allComb;
        }
        
        System.out.println(res);
 //       System.out.printf("%.15f",res);
        br.close();
	}
	
	private static long factorial(int n) {
		long res = 1;
		for(int i=2; i<n+1; i++) {
			res*=i;
		}
		return res;
	}
	
	private static double factCn2(int n) {
		if(n<2)
			return 1.0;
		
		double chisl=1;
		double znam=2.0;
		for(int i=n-2+1; i<n+1;i++) {
			chisl=chisl*i;
		}
		
		return chisl/znam;
	}
	
	private static int [] didgitNumb(int numb) {
		String s="";
		int didgit = (s+numb).length();
		int [] number = new int [didgit];
		for(int i=0;i<didgit;i++) {
			number[didgit-1-i]=numb%10;
			numb=numb/10;
		}
		return number;
	}
	
	private static void probably(List<Integer> list) {
		int count=0;
		for(int e : list) {
			if(e%5==0 || e%6==0)
				count++;
		}
		double pr = (double)count/(double)list.size();
		System.out.printf("%.15f", pr);
	}

}
