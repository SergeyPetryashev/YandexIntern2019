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
public class TaskCStringRLEStruct {

	public static void main(String[] args) throws IOException {
        File file = new File("inputC.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String strRLE=br.readLine();
        int numRequest = Integer.parseInt(br.readLine()); 
   	    List<StringMatcher> list = parseString(strRLE);
        for(int i=0;i<numRequest;i++) {
            String [] str=br.readLine().split(" ");
           	lengthRLESubstring(list, Long.parseLong(str[0]), Long.parseLong(str[1]));
        }        
        br.close();
	}
	
	
	private static void lengthRLESubstring(List<StringMatcher> listMatcher, long start, long end) {
		int lengthRLE=0;
		int countStart=binarySearchIndex(listMatcher, start, 0);
		int countEnd=binarySearchIndex(listMatcher, end, countStart);;
		long targetNum;
		String sStart="";
		if(countStart==countEnd) {
        	targetNum=end-start+1;
			sStart+=targetNum;
        	lengthRLE= targetNum==1 ? 1 : sStart.length()+1;
		}else {
			String sEnd="";
			long lengthStart = listMatcher.get(countStart).getSumNumber()-start+1;
			sStart+=lengthStart;
			long lengthEnd = end-listMatcher.get(countEnd-1).getSumNumber();
			sEnd+=lengthEnd;
			int st= lengthStart==1 ? 1 : sStart.length()+1;
			int nEnd= lengthEnd==1 ? 1 : sEnd.length()+1;
			int delta = listMatcher.get(countEnd-1).getEnd()-listMatcher.get(countStart).getEnd();
			lengthRLE=st+nEnd+delta;
		}
	    System.out.println(lengthRLE);
	}
	
	private static List<StringMatcher> parseString (String str){
        Pattern pattern = Pattern.compile("[a-z]");
	    Matcher matcher = pattern.matcher(str);
		List<StringMatcher> list = new ArrayList<>();
	    int lastEnd=0;
	    int num;
	    long sum=0;
	    while (matcher.find()) {
	    	if(matcher.start()==lastEnd) {
	        	num=1;
	        }else {
	        	num=Integer.parseInt(str.substring(lastEnd,matcher.start()));
	        }
	    	sum+=num;
	    	list.add(new StringMatcher(matcher.start(),matcher.end(), num, sum, matcher.group()));
	    	lastEnd=matcher.end();
	    }
	    return list;
	}
	
	private static int binarySearchIndex(List<StringMatcher> listMatcher, long key, int indexStart) {
		int end = listMatcher.size()-1;
		int start = indexStart;
		int mid = start+(end-start)/2;
		while(start<=end) {
			if(key<listMatcher.get(mid).getSumNumber()) {
				end=mid-1;
			}else if (key>listMatcher.get(mid).getSumNumber()) {
				start=mid+1;
			}else { 
				return mid;
			}
			mid = start+(end-start)/2;
		}
		return mid;
	}
}

class StringMatcher{
	int startIndex;
	int endIndex;	
	int numberChar;
	long sumNumber;
	String charSubstring;
	
	public StringMatcher(int start, int end, int number, long sum, String ch) {
		this.startIndex=start;
		this.numberChar=number;
		this.endIndex=end;
		this.sumNumber=sum;
		this.charSubstring=ch;
	}
	public int getStart(){
		return startIndex;
	}
	public int getEnd(){
		return endIndex;
	}
	public int getNumberChar(){
		return numberChar;
	}
	public long getSumNumber(){
		return sumNumber;
	}
	public String getCharSubstring(){
		return charSubstring;
	}
}
