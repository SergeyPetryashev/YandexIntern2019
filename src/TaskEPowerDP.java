import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/*From the array A of length N from non-negative integers choose K elements of the array. 
 * The product of the selected numbers is M
 * 1<=K<=N<=5000
 *  0<=Ai<=10e9
 * */
public class TaskEPowerDP {
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
     
	        int [] index=findPowerCycl(array, target, numComponent);
	        printRes(index);

	        System.out.println();
	        int [] index2=findPowerDP(array, target, numComponent);
	        printRes(index2);
        }

	}
	
	private static int [] findPowerDP(int[] arr, int target, int numPow) {
		int [] result = new int [numPow];
		List<int[]> listRes = new ArrayList<>();
		List<Integer> currentPow = new ArrayList<>();
		currentPow.add(1);
		listRes.add(result);
		int listPowSize = 0;
		int lastListPowSize=0;
		for(int nPow=1; nPow<=numPow;nPow++) {
			for(int i=0; i<listPowSize; i++) {
				listRes.remove(0);
				currentPow.remove(0);
			}
			listPowSize = currentPow.size();
			for(int i=0; i<arr.length; i++) {
				for(int j=lastListPowSize; j<listPowSize;j++) {
					boolean isContinue=false;
					for(int k=0; k<nPow; k++) {
						if(listRes.get(j)[k]==i+1) {	// условие что этот множитель уже использован
							isContinue=true;
							break;
						}
					}
					if(isContinue)
						continue;
					int newPow = currentPow.get(j)*arr[i];
					if(target%newPow==0) {
						currentPow.add(newPow);
						result=Arrays.copyOf(listRes.get(j), numPow);
						result[nPow-1]=i+1;
						listRes.add(result);
						if(nPow==numPow && newPow==target) {
							return result;
						}
					}
					
				}
			}
		}
		System.out.println(currentPow);

		return result;
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
	private static void printRes(int [] index) {
	    for(int i=0;i<index.length-1;i++) {
				System.out.print(index[i]+" ");
	    }
		System.out.print(index[index.length-1]);
	}
}