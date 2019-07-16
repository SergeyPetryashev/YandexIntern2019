import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskCStringRLE {

	public static void main(String[] args) throws IOException {
        File file = new File("inputC.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String strRLE=br.readLine();
        int numRequest = Integer.parseInt(br.readLine()); 
        List<String []> startEnd = new ArrayList<>(numRequest);
        for(int i=0;i<numRequest;i++) {
        	String [] s=br.readLine().split(" ");
        	startEnd.add(s);
        }
        br.close(); 
        System.out.println("Свернутое: "+strRLE);
        for(int i=0;i<numRequest;i++) {
        	lengthRLESubstring(strRLE,Integer.parseInt(startEnd.get(i)[0]),Integer.parseInt(startEnd.get(i)[1]));
        }
	}
	
	
	private static void lengthRLESubstring(String str, int start, int end) {
		String result="";
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
	        	result= targetNum==1 ? result+matcher.group() : result+targetNum+matcher.group();	        	
	        }        
	        
	        lastEnd=matcher.end();	
	        numCharLast=numCharNew;
	        if(!isEnd) break;
	    }
	    System.out.print("Запрос [" +start+", "+end+"] Подстрока "+result+" длина ");
	    System.out.println(lengthRLE);
	}

}
