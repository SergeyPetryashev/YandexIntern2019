import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;
/*From the array A of length N from non-negative integers choose K elements of the array. 
 * The product of the selected numbers is M
 * 1<=K<=N<=5000
 *  0<=Ai<=10e9
 * */
public class TaskEPower {
	public static void main(String[] args) throws NumberFormatException, IOException {
        File file = new File("inputE.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String [] data = (br.readLine().split(" ")); 
        int lengthArr = Integer.parseInt(data[0]);
        int target = Integer.parseInt(data[1]);
        int numComponent = Integer.parseInt(data[2]);
        
        String[] list = br.readLine().split(" ");
        int[] array = new int [list.length];
        for(int i=0;i<list.length;i++) {
        	array[i]=Integer.parseInt(list[i]);            	 
        }
        br.close();
		if(target==0) {
        	int index=0;
        	while(array[index++]!=0) {}
        	
        	if(index>numComponent) {
        		for(int i=1;i<numComponent;i++) {
        			System.out.print(i+" ");
        		}
        		System.out.print(index);
        	}else {
        		for(int i=1;i<numComponent+1;i++) {
        			System.out.print(i+" ");
        		}
        	}
        }else {
	        Stack<Integer>indexSt=new Stack<>();
	        findPower(array, target, numComponent, 0, indexSt);
	        while(!indexSt.isEmpty()) {
	        	if(indexSt.size()>1) {
	        		System.out.print(indexSt.pop()+" ");
	        	}else {
	        		System.out.print(indexSt.pop());
	        	}	        	
	        }
	        System.out.println();
	        
	        int [] index=findPowerCycl(array, target, numComponent);
	        for(int i=0;i<index.length;i++) {
        		if(i==index.length-1) {
        			System.out.print(index[i]);
        		}else {
        			System.out.print(index[i]+" ");
        		}
	        }
        }

	}	
	private static boolean findPower(int[] arr, int target, int num, int index, Stack<Integer> indexSt) {

		if(target==1 && num==0) {
			return true;
		}
		if(target!=1 && num==0 || index==arr.length) {
			return false;
		}
		if(arr[index]!=0 && target%arr[index]==0) {
			indexSt.push(index+1);

			if(!findPower(arr, target/arr[index], num-1, index+1,indexSt)) {
				indexSt.pop();
			}else {
				return true;
			}
		}
		return findPower(arr, target, num, index+1, indexSt);
	}
	
	private static int[] findPowerCycl(int[] arr, int target, int numPow) { 
		int [] result = new int [numPow];
		int index=0;
		for(int i=0;i<arr.length;i++) {
			if(arr[i]!=0 && target%arr[i]==0) {
				result[index++]=i+1;
				target=target/arr[i];
				numPow--;
				if(target==1 && numPow==0) {
					return result;
				}				
			}
			if(target!=1 && numPow==0) {
				i=result[--index]-1;
				result[index]=0;
				target=target*arr[i];
				numPow++;
			}
			while(i==arr.length-1) {
				i=result[--index]-1;
				result[index]=0;
				target=target*arr[i];
				numPow++;
			}
		}
		return result;
	}
}