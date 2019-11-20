import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/*
 * Obtaining the length of the compressed substring t[l...r] 
 * of the compressed string s (t not compressed string)
 *  according to the format of the RLE.
 *  t=abbcaaa
 *  s=a2bc3a
 *  substring (1 2) = ab  length 2
 *  substring (4 7) = c3a  length 3
 *  substring (3 5) = bca  length 3
 */
// Решение без паттернов, через массив символов
public class TaskCStringRLE2 {

	public static void main(String[] args) throws IOException {
        File file = new File("inputC.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String strRLE=br.readLine();
        int numRequest = Integer.parseInt(br.readLine()); 
        List<String []> startEnd = new ArrayList<>(numRequest);
        String str;
        for(int i=0; i<numRequest; i++) {
        	str=br.readLine();
        	String [] s=str.split(" ");
        	startEnd.add(s);
        }
 
        br.close(); 
        for(int i=0;i<startEnd.size();i++) {
        	lengthRLESubstring(strRLE,Long.parseLong(startEnd.get(i)[0]),Long.parseLong(startEnd.get(i)[1]));
        }
	}
	
	
	private static void lengthRLESubstring(String str, long start, long end) {
		long numCharLast=0, numCharNew=0;
		int lengthRLE=0;
		boolean isStart=true;
		boolean isEnd=true;

	    StringBuilder t = new StringBuilder();
	    for(int i=0; i<str.length(); i++) {
	    	char c=str.charAt(i);
	    	if(c>='0' && c<='9') {
	    		t.append(c);
	    	}else {
		    	int num;
		        if(t.length()==0) {
		        	num=1;
		        }else {
		        	num=Integer.parseInt(t.toString());
		        	t= new StringBuilder();
		        }

		        numCharNew+=num;
		        long targetNum=0;
		        String s="";
		        if(isStart && numCharNew>=end) {// диапазон чисел сразу перекрыл искомый
		        	targetNum=end-start+1;
		        	isStart=false;
		        	isEnd=false;
		        }else if(isStart && numCharNew>=start) {	// начало отcчета
		        	targetNum=numCharNew-start+1;
		        	isStart=false;
	        	}else if(!isStart && numCharNew>=start && numCharNew<end) {
		        	targetNum=num;
	        	}else if(!isStart && numCharNew>=end) {	// завершение
		        	targetNum=end-numCharLast;
		        	isEnd=false;
	        	}
		        
		        if(!isStart) {
		        	s+=targetNum;
		        	lengthRLE= targetNum==1 ? lengthRLE+1 : lengthRLE+s.length()+1;
		        }        
		        
		        numCharLast=numCharNew;
		        if(!isEnd) break;
	    	}
	    }
	    System.out.println(lengthRLE);
	}
}
