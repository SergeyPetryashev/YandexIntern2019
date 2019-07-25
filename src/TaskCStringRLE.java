import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
public class TaskCStringRLE {

	public static void main(String[] args) throws IOException {
        File file = new File("inputC.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String strRLE=br.readLine();
        int numRequest = Integer.parseInt(br.readLine()); 
        List<String []> startEnd = new ArrayList<>(numRequest);
        String str;
        while((str=br.readLine())!=null) {
        	String [] s=str.split(" ");
        	startEnd.add(s);
        }
 
        br.close(); 
        for(int i=0;i<startEnd.size();i++) {
        	lengthRLESubstring(strRLE,Long.parseLong(startEnd.get(i)[0]),Long.parseLong(startEnd.get(i)[1]));
        }
	}
	
	
	private static void lengthRLESubstring(String str, long start, long end) {
//		String result="";
		int lastEnd=0;
		long numCharLast=0, numCharNew=0;
		int lengthRLE=0;
		boolean isStart=true;
		boolean isEnd=true;
		Pattern pattern = Pattern.compile("[a-z]");
	    Matcher matcher = pattern.matcher(str);
	    while (matcher.find()) {
	    	int num;
	        if(matcher.start()==lastEnd) {
	        	num=1;
	        }else {
	        	num=Integer.parseInt(str.substring(lastEnd,matcher.start()));
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
//	        	result= targetNum==1 ? result+matcher.group() : result+targetNum+matcher.group();	        	
	        }        
	        
	        lastEnd=matcher.end();	
	        numCharLast=numCharNew;
	        if(!isEnd) break;
	    }
//	    System.out.print("Запрос [" +start+", "+end+"] Подстрока "+result+" длина ");
	    System.out.println(lengthRLE);
	}

}
