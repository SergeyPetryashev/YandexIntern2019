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
public class TaskDLikeNumber {

	public static void main(String[] args) throws IOException {
        File file = new File("inputD.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        int number = Integer.parseInt(br.readLine());
        int numShift = Integer.parseInt(br.readLine());
        int [] numbers = didgitNumb(number);
        List<Integer> listNumb =new ArrayList<>();
        List<Integer> listNumbTemp =new ArrayList<>();
        List<Integer> listNumbAll =new ArrayList<>();
        for(int shift=0; shift<numShift;shift++) {
			if(shift!=0) {
				for(int i=0;i<listNumb.size();i++) {
					int [] num = didgitNumb(listNumb.get(i));
					listNumbTemp=shiftNumber (num, num.length);
					for(int e :listNumbTemp) {
						if(!listNumbAll.contains(e))
							listNumbAll.add(e);
					}
				}
				listNumb=listNumbAll;
			}else if(shift==0) {
				listNumb=shiftNumber (numbers, numbers.length);
			}                  	
        }
//        System.out.println(listNumbAll);
        System.out.println(listNumb);
        if(numShift!=1 && numShift!=0) {
        	probably(listNumbAll);
        }else if(numShift==0){
        	listNumb.add(number);
        	probably(listNumb);
        }else {
        	probably(listNumb);
        }
	}
	
	private static List<Integer> shiftNumber(int [] number, int didg) {
		List<Integer> list =new ArrayList<>();
		int [] newNumber = new  int [didg];
		int shiftNumber;
		for(int i=0; i<didg; i++) {
			for(int j=i+1; j<didg; j++) {
				newNumber = swap(number, i , j);
				shiftNumber = formNumber(newNumber);
				if(!list.contains(shiftNumber))
					list.add(shiftNumber);
//				System.out.print(shiftNumber+" ");
			}
		}
		return list;

	}
	
	private static int formNumber(int [] arr) {
		int number=0;
		int [] res = new int [arr.length];
		for(int i=0;i<arr.length;i++) {
			res[i]=(int) (arr[i]*Math.pow(10, arr.length-1-i));
			number = number+res[i];
		}
		return number;
	}
	
	private static int [] swap(int [] data, int start, int end) {
		int[] arr = new int [data.length];
		System.arraycopy(data, 0, arr, 0, data.length);
		int temp = arr[start];
		arr[start] = arr [end];
		arr[end] = temp;
		return arr;
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
