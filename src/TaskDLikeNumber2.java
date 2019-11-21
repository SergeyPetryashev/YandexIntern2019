import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
/* There is a number N, not containing zeros. Do K times the following: 
 * select two numbers that are in different positions, and swap them.
 * Find the probability that the final number will be divided by at least 
 * one of the numbers 5, 6, 10
 */
public class TaskDLikeNumber2 {

	public static void main(String[] args) throws IOException {
        File file = new File("inputD.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        long number = Long.parseLong(br.readLine());
        int numShift = Integer.parseInt(br.readLine());
        br.close();
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
        double resFive=0;
        double resSix=0;
      
        if(summ%3==0 && numTwo!=0) {
        	resSix=result(numTwo, 2, numbers, numShift);
        }
        if(numFive!=0){
        	resFive=result(numFive, 5, numbers, numShift);
        }
        
 //       System.out.println("allComb "+allComb);
        System.out.printf("%.15f", resFive+resSix);
 //       System.out.printf("%.15f",res);

	}
	
	private static double result(int numTargetNumbers, int targetNum, int [] numbers, int comb) {
		double allComb=factCn2(numbers.length);
		double combWithoutTargetNum=factCn2(numbers.length-1);;
		double [] success = new double [2];	// два случая когда целевая цифра в конце или в теле
		success[0]=1;
		success[1]=0;
		double  baseChanseEnd = combWithoutTargetNum/allComb;
		double baseChanseBody = 1/allComb;
		double chanseEnd, chanseBody;

		for(int i=1; i<=comb;i++) {
			chanseEnd=success[0];
			chanseBody=success[1];
			success[0]=chanseEnd*baseChanseEnd + (1-chanseEnd)*baseChanseBody;
			success[1]=chanseBody*baseChanseEnd + (1-chanseBody)*baseChanseBody;
		}
		if(numbers[numbers.length-1]%targetNum==0) {
			return success[0]+success[1]*(numTargetNumbers-1);
		}else {
			return success[1]*numTargetNumbers;
		}
	}
	
	private static double factCn2(int n) {
		return n*(n-1)/2;
	}
	
	private static int [] didgitNumb(Long numb) {
		String s="";
		int didgit = (s+numb).length();
		int [] number = new int [didgit];
		for(int i=0;i<didgit;i++) {
			number[didgit-1-i]=(int) (numb%10);
			numb=numb/10;
		}
		return number;
	}
}
