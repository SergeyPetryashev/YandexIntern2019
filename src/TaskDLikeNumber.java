import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
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
        Map<Integer, Long>  listNumb =new HashMap<>();
        Map<Integer, Long> listNumbTemp =new HashMap<>();
        Map<Integer, Long> listNumbCurrent;
        for(int shift=0; shift<numShift;shift++) {
        	listNumbCurrent=new HashMap<>();
			if(shift!=0) {
				Set<Map.Entry<Integer, Long>> setNumb = listNumb.entrySet();
				for(Map.Entry<Integer, Long> me : setNumb) {
					int [] num = didgitNumb(me.getKey());
					listNumbTemp=shiftNumber (num, num.length);
					Set<Map.Entry<Integer, Long>> set = listNumbTemp.entrySet();
					for(Map.Entry<Integer, Long> e : set) {
						if(!listNumbCurrent.containsKey(e.getKey())) {
							listNumbCurrent.put(e.getKey(),e.getValue()*me.getValue());
						}else {
							listNumbCurrent.put(e.getKey(),listNumbCurrent.get(e.getKey())+e.getValue()*me.getValue());	// текущее значение + новое значение
						}
					}
				}
				//=========================================================
				// проверка на общий делитель
				Set<Map.Entry<Integer, Long>> setNumbCurrent = listNumbCurrent.entrySet();
				long divide = maxDel(setNumbCurrent);
				if(divide>1) {
					for(Map.Entry<Integer, Long> eNC : setNumbCurrent) {
						listNumbCurrent.put(eNC.getKey(),listNumbCurrent.get(eNC.getKey())/divide);
					}
				}//*/
				//======================================================
				listNumb=new HashMap(listNumbCurrent);	// копирование чисел на следующие шаг перестановки
			}else if(shift==0) {
				listNumb=shiftNumber (numbers, numbers.length);
			}			
        }
  //      System.out.println(listNumb);
        if(numShift==0){
        	listNumb.put(number,(long)1);
        	probably(listNumb);
        }else {
        	probably(listNumb);
        }
        br.close();
	}
	
	private static Map<Integer,Long> shiftNumber(int [] number, int didg) {
		Map<Integer,Long> list =new HashMap<>();
		int [] newNumber = new  int [didg];
		int shiftNumber;
		for(int i=0; i<didg; i++) {
			for(int j=i+1; j<didg; j++) {
				newNumber = swap(number, i , j);
				shiftNumber = formNumber(newNumber);
				if(!list.containsKey(shiftNumber)) {
					list.put(shiftNumber,(long) 1);
				}else {
					
					list.put(shiftNumber,list.get(shiftNumber)+1);
				}
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
	
	private static int [] didgitNumb(int number2) {
		String s="";
		int didgit = (s+number2).length();
		int [] number = new int [didgit];
		for(int i=0;i<didgit;i++) {
			number[didgit-1-i]=number2%10;
			number2=number2/10;
		}
		return number;
	}
	
	private static void probably(Map<Integer,Long> map) {
		double trueCount=0;
		double allCount=0;
		Set<Map.Entry<Integer, Long>> set = map.entrySet();
		for(Map.Entry<Integer, Long> me : set) {
			allCount+=me.getValue();
			if(me.getKey()%5==0 || me.getKey()%6==0)
				trueCount+=me.getValue();
		}
		double pr = trueCount/allCount;
		System.out.printf("%.15f", pr);
	}
	
	private static long maxDel (Set<Entry<Integer, Long>> setNum) {
		long divide=1; 
		long min=100000000;
		boolean isDel=false;
		for(Map.Entry<Integer, Long> e : setNum) {
			if(e.getValue()<min) {
				min=e.getValue();
			}
		}
		divide=min+1;
		boolean isOne=true;
		while(!isDel && divide!=1) {
			isDel=true;
			if(!isOne && divide%2==0) {
				divide/=2;
			}else {
				divide--;
				isOne=false;
			}
			for(Map.Entry<Integer, Long> e : setNum) {
				if(e.getValue()%divide!=0) {
					isDel=false;
				}
			}			

		}
//		System.out.println("Min "+min+" Del "+divide);
		return divide;
	}

}
