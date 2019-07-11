import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TaskBPhoneNumber {

	public static void main(String[] args) throws NumberFormatException, IOException {
        File file = new File("inputB.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        int numNumber = Integer.parseInt(br.readLine()); 
        List<String> number = new ArrayList<>();
        for(int i=0;i<numNumber;i++) {
        	number.add(br.readLine());        	
        }
        int numForm = Integer.parseInt(br.readLine()); 
        List<String> form = new ArrayList<>();
        for(int i=0;i<numForm;i++) {
        	form.add(br.readLine());        	
        }
        List<Format> listForm =addForm(form);
        printNumber(number, listForm);
        br.close();        
	}
	
	private static List<Format> addForm(List<String> strForm) {
		List<Format> list = new ArrayList<>();
		for(int i=0; i<strForm.size();i++) {
			String[] str = strForm.get(i).split(" ");
			String country="";
			for(int j=4;j<str.length;j++) {
				country+=str[j]+" ";
			}
			list.add(new Format(str[0],str[1],str[2],country.substring(0,country.length()-1)));
		}
		return list;
	}
	
	private static void printNumber(List<String> number, List<Format> form) {
		for(int i=0;i<number.size();i++) {
			String str="+";
			for(int j=0;j<number.get(i).length();j++) {
				if(!number.get(i).substring(j, j+1).equals("+")&&
						!number.get(i).substring(j, j+1).equals("(")&&
						!number.get(i).substring(j, j+1).equals(")")&&
						!number.get(i).substring(j, j+1).equals("-")&&
						!number.get(i).substring(j, j+1).equals(" ")){
					str+=number.get(i).substring(j, j+1);
				}
			}
			for(int j=0;j<form.size();j++) {
				isTrue(str, form.get(j));
			}
		}
	}
	
	private static boolean isTrue(String number, Format form){
		
		for(int i=2; i<5;i++) {
			String str=number.substring(0,i);
			if(str.equals(form.countryCode)) {
				for(int j=2; j<5;j++) {
					String str2=number.substring(i,j+i);
					if(str2.equals(form.operatorCode.substring(1,form.operatorCode.length()-1))) {
						int a1=number.length()-(i+j);
						if(a1==form.number.length()) {
							boolean key=true;
							for(int k=0;k<form.number.length();k++) {
								if(!form.number.substring(k, k+1).equals("X")) {
									key=form.number.substring(k, k+1).equals(number.substring(j+i+k, j+i+k+1));
									if(!key) break;
								}
							}
							
							if(key) {
							System.out.println(form.countryCode+" "+form.operatorCode+" "+
									number.substring(j+i,number.length())+" - "+form.country);
							return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
}

class Format {
	//+COUNTRY_CODE (OPERATOR_CODE) PERSONAL_NUMBER Country/Operator
	String countryCode;
	String operatorCode;
	String number;
	String country;
	public Format(String cCode, String opCode, String number, String country) {
		countryCode=cCode;
		operatorCode=opCode;
		this.number=number;
		this.country=country;
	}
	public void printFormat() {
		System.out.println(countryCode+" "+operatorCode+" "+number+" - "+country);
	}
	
}
